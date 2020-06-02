package com.sora.websocket;

import com.alibaba.fastjson.JSONObject;
import com.sora.domain.*;
import com.sora.service.IAbsenceRecordService;
import com.sora.service.ICourseService;
import com.sora.service.IUserService;
import com.sora.vo.InstantQuesionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 12:48
 */
public class QuestionHandler extends TextWebSocketHandler {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IAbsenceRecordService absenceRecordService;

    private static Map<Long,WebSocketSession> idAndSession = new ConcurrentHashMap<>();

    private static Map<Long,Map<Long,Integer>> idAndQuestionInfo = new ConcurrentHashMap<>();

    // 建立连接时候触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        long id = (Long) session.getAttributes().get("id");
        idAndSession.put(id,session);
    }


    // 关闭连接时候触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        long id = (Long) session.getAttributes().get("id");
        idAndSession.remove(id);
    }

    // 处理消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        InstantQuesionVO instantQuesionVO = JSONObject.parseObject(payload, InstantQuesionVO.class);
        long courseId = instantQuesionVO.getCourseId();
        Course course = courseService.findByCourseId(courseId);
        long id = (Long) session.getAttributes().get("id");
        User user = userService.findInfoById(id);
        instantQuesionVO.setUser(user);
        if(userService.isTeacher(id)){
            if(!instantQuesionVO.isEnd()){
                if(!instantQuesionVO.isAgain()) {
                    Map<Long, Integer> quesitonInfo = new HashMap();
                    for (int i = 0; i < course.getStudentList().size(); i++) {
                        quesitonInfo.put(course.getStudentList().get(i).getId(), 0);
                    }
                    idAndQuestionInfo.put(courseId, quesitonInfo);
                }
                List<Student> studentList = course.getStudentList();
                for (int i = 0; i < studentList.size(); i++) {
                    Student student = studentList.get(i);
                    WebSocketSession studentSession = idAndSession.get(student.getId());
                    if(studentSession!=null){
                        TextMessage textMessage = new TextMessage(JSONObject.toJSONString(instantQuesionVO));
                        studentSession.sendMessage(textMessage);
                    }
                }
            }else{
                Map<Long, Integer> quesitonInfo = idAndQuestionInfo.get(courseId);
                List<AbsenceRecord> absenceRecordList = new ArrayList();
                Iterator it = quesitonInfo.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    long studentId = (Long)entry.getKey();
                    int number = (Integer) entry.getValue();
                    if(number==0){
                        Student student = userService.findStudentById(studentId);
                        AbsenceRecord absenceRecord = new AbsenceRecord();
                        absenceRecord.setStudent(student);
                        absenceRecord.setCourse(course);
                        absenceRecord.setTime(instantQuesionVO.getPublishTime());
                        absenceRecordList.add(absenceRecord);
                    }
                }
                absenceRecordService.addAbsenceRecord(absenceRecordList);
                idAndQuestionInfo.remove(courseId);
            }
        }else{
            Map<Long, Integer> quesitonInfo = idAndQuestionInfo.get(courseId);
            int number = quesitonInfo.get(user.getId());
            quesitonInfo.put(user.getId(),++number);
            WebSocketSession teacherSession = idAndSession.get(course.getTeacher().getId());
            TextMessage textMessage = new TextMessage(JSONObject.toJSONString(instantQuesionVO));
            teacherSession.sendMessage(textMessage);
        }
    }
}
