package com.sora.service;

import com.sora.domain.AbsenceRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 18:59
 */
public interface IAbsenceRecordService {
    public int addAbsenceRecord(List<AbsenceRecord> absenceRecordList) throws Exception;

    public void exportAllByCourseIdWithExcel(long courseId, HttpServletResponse response) throws Exception;

    public void exportAllNotExportedByCourseIdWithExcel(long courseId, HttpServletResponse response) throws Exception;
}
