package com.sora.service.Impl;

import com.sora.dao.IAbsenceRecordDao;
import com.sora.dao.ICourseDao;
import com.sora.domain.AbsenceRecord;
import com.sora.domain.Course;
import com.sora.service.IAbsenceRecordService;
import com.sora.utils.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 19:00
 */
@Service
public class AbsenceRecordServiceImpl implements IAbsenceRecordService {

    @Autowired
    private IAbsenceRecordDao absenceRecordDao;

    @Autowired
    private ICourseDao courseDao;

    @Override
    public int addAbsenceRecord(List<AbsenceRecord> absenceRecordList) throws Exception {
        for(int i=0;i<absenceRecordList.size();i++){
            absenceRecordDao.addAbsenceRecord(absenceRecordList.get(i));
        }
        return 1;
    }

    public void exportAllByCourseIdWithExcel(long courseId, HttpServletResponse response) throws Exception{
        List<AbsenceRecord> absenceRecordList = absenceRecordDao.findAllByCourseId(courseId);
        HSSFWorkbook hssfWorkbook = FileUtils.exportAbsenceRecord(absenceRecordList);
        for(int i=0;i<absenceRecordList.size();i++){
            AbsenceRecord absenceRecord = absenceRecordList.get(i);
            absenceRecordDao.setExported(absenceRecord);
        }
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/msexcel;charset=UTF-8");
        Course course = courseDao.findByCourseId(courseId);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String fileName = URLEncoder.encode(course.getName()+"缺答全部记录报表"+dateString+".xls","UTF-8");
        response.setHeader("Content-disposition", "attachment; filename="+fileName);
        hssfWorkbook.write(out);
        out.flush();
        out.close();
    }

    public void exportAllNotExportedByCourseIdWithExcel(long courseId,HttpServletResponse response) throws Exception{
        List<AbsenceRecord> absenceRecordList = absenceRecordDao.findAllNotExportedByCourseId(courseId);
        HSSFWorkbook hssfWorkbook = FileUtils.exportAbsenceRecord(absenceRecordList);
        for(int i=0;i<absenceRecordList.size();i++){
            AbsenceRecord absenceRecord = absenceRecordList.get(i);
            absenceRecordDao.setExported(absenceRecord);
        }
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/msexcel;charset=UTF-8");
        Course course = courseDao.findByCourseId(courseId);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String fileName = URLEncoder.encode(course.getName()+"缺答新记录报表"+dateString+".xls","UTF-8");
        response.setHeader("Content-disposition", "attachment; filename="+fileName);
        hssfWorkbook.write(out);
        out.flush();
        out.close();
    }
}
