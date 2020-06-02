package com.sora.service.Impl;

import com.sora.dao.IKeywordDao;
import com.sora.dao.IQuestionDao;
import com.sora.domain.Keyword;
import com.sora.domain.Option;
import com.sora.domain.Question;
import com.sora.service.IQuestionService;
import com.sora.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/8 15:51
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private IKeywordDao keywordDao;

    @Autowired
    private IQuestionDao questionDao;

    public List<Question> findAllByBlogId(long blogId) throws Exception{
        List<Question> questionList = questionDao.findAllByBlogId(blogId);
        if(questionList.size()>10){
            List<Question> questionListTemp = new ArrayList<>();
            for(int i=0;i<10;i++){
                Random random = new Random();
                int n = random.nextInt(questionList.size());
                Question question = questionList.get(n);
                questionListTemp.add(question);
                questionList.remove(n);
            }
            questionList = questionListTemp;
        }
        return questionList;
    }

    public List<Question> findAllByCourseId(long courseId) throws Exception{
        return questionDao.findAllByCourseId(courseId);
    }

    public List<Question> findAllByCourseIdAndKey(long courseId,String key) throws Exception{
        key = "%"+key+"%";
        return questionDao.findAllByCourseIdAndKey(courseId,key);
    }

    public int deleteQuestion(long userId, Question question) throws Exception{
        if(userId == questionDao.findUser(question).getId()){
            return questionDao.deleteQuestion(question);
        }
        return 0;
    }

    @Override
    public int importQuestions(long courseId, MultipartFile file) throws Exception {
        try{
            String fileName = file.getOriginalFilename();//获取文件名
            List<Question> questionList = null;
            if (!FileUtils.validateExcel(fileName)) {// 验证文件名是否合格
                throw new Exception("格式错误，请上传Excel文件（后缀名为xls或xlsx）");
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (FileUtils.isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            questionList = FileUtils.createQuestionsExcel(file.getInputStream(), isExcel2003);
            for(int i=0;i<questionList.size();i++){
                Question question = questionList.get(i);
                questionDao.addQuestion(question,courseId);
                List<Option> optionList = question.getOptionList();
                for(int j=0;j<optionList.size();j++){
                    Option option = optionList.get(j);
                    questionDao.addOption(option,question);
                }
                List<Keyword> keywordList = question.getKeywordList();
                for(int j=0;j<keywordList.size();j++){
                    Keyword keyword = keywordDao.findByNameAndCourseId(keywordList.get(j).getName(),courseId);
                    if(keyword==null){
                        throw new Exception("关键词错误");
                    }
                    questionDao.addKeyword(keyword,question);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
