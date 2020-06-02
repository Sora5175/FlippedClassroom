package com.sora.dao;

import com.sora.domain.AbsenceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/11 18:55
 */
@Repository
public interface IAbsenceRecordDao {
    public int addAbsenceRecord(AbsenceRecord absenceRecord);

    public int setExported(AbsenceRecord absenceRecord);

    public List<AbsenceRecord> findAllByCourseId(long courseId);

    public List<AbsenceRecord> findAllNotExportedByCourseId(long courseId);
}
