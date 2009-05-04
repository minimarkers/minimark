package com.benfante.minimark.dao;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Course;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests for the CourseDao.
 *
 * @author lucio
 */
public class CourseDaoTest extends MinimarkBaseTest {
    @Resource
    private CourseDao courseDao;

    public void testFindAll() {
        List<Course> courses = courseDao.findAll();
        assertNotNull(courses);
        assertSize(3, courses);
    }
    
    /**
     * Test of findByTeacherUsername method, of class CourseDao.
     */
    public void testFindByTeacherUsername() {
        List<Course> courses = courseDao.findByTeacherUsername("lucio.benfante");
        assertNotNull(courses);
        assertSize(2, courses);
    }

}
