package com.sora.controller;

import com.sora.service.IAbsenceRecordService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/19 14:28
 */
@Controller
@RequestMapping("/absenceRecord")
public class AbsenceRecordController {

    @Autowired
    private IAbsenceRecordService absenceRecordService;

    @RequestMapping("/findAllByCourseIdWithExcel")
    @ResponseBody
    public void findAllByCourseIdWithExcel(HttpServletRequest request, HttpServletResponse response){
        try{
            long courseId = Long.parseLong(request.getParameter("courseId"));
            absenceRecordService.exportAllByCourseIdWithExcel(courseId,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/findAllNotExportedByCourseIdWithExcel")
    @ResponseBody
    public void findAllNotExportedByCourseIdWithExcel(HttpServletRequest request, HttpServletResponse response){
        try{
            long courseId = Long.parseLong(request.getParameter("courseId"));
            absenceRecordService.exportAllNotExportedByCourseIdWithExcel(courseId,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
