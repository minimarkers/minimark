package com.benfante.minimark.dao;

import com.benfante.minimark.po.CourseTeacher;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=CourseTeacher.class)
public interface CourseTeacherDao extends GenericDao<CourseTeacher, Long> {

}
