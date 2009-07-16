package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.Course;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests of the class QuestionBo.
 *
 * @author Lucio Benfante
 */
public class QuestionBoTest extends MinimarkBaseTest {
    @Resource
    private QuestionBo questionBo;
    @Resource
    private CourseDao courseDao;
    
    /**
     * Test of search method, of class QuestionBo.
     */
    public void testSearchAll() {
        List result = questionBo.search(new QuestionBean());
        assertSize(5, result);
    }

    public void testSearchByCourse() {
        final QuestionBean questionBean = new QuestionBean();
        List<Course> courses = courseDao.findByNameAndMainGroupAndSecondaryGroup("Informatica Generale", "Facoltà di Lettere e Filosofia", "MLC");
        assertNotEmpty(courses);
        assertSize(1, courses);
        questionBean.setCourse(courses.get(0));
        List result = questionBo.search(questionBean);
        assertSize(1, result);
    }

}
