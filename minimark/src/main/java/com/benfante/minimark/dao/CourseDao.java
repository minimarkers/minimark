package com.benfante.minimark.dao;

import com.benfante.minimark.po.Course;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=Course.class)
public interface CourseDao extends GenericDao<Course, Long> {
    List<Course> findByTeacherUsername(String username);
}
