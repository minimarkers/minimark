package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.Question;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

/**
 * Tests for the importer features.
 *
 * @author lucio
 */
public class ImporterBoTest extends MinimarkBaseTest {
    private static final Logger logger = Logger.getLogger(ImporterBoTest.class);

    @Resource
    private ImporterBo importerBo;

    @Resource
    private CourseDao courseDao;

    /**
     * Test of importQuestionSet method, of class ImporterBo.
     */
    public void testReadQuestionSet() throws UnsupportedEncodingException {
        InputStream is = this.getClass().getResourceAsStream("/test/files/benfante.txt");
        InputStreamReader r = new InputStreamReader(is, "UTF-8");
        try {
            List<Question> result = importerBo.readQuestionSet(r);
            assertNotEmpty(result);
            assertSize(4, result);
        } catch (Exception ex) {
            fail(ex.getLocalizedMessage());
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    public void testPersistQuestionSet() throws UnsupportedEncodingException {
        InputStream is = this.getClass().getResourceAsStream("/test/files/benfante.txt");
        InputStreamReader r = new InputStreamReader(is, "UTF-8");
        try {
            List<Question> result = importerBo.readQuestionSet(r);
            List<Course> courses = courseDao.findByNameAndMainGroupAndSecondaryGroup("Informatica Generale", "Facoltà di Lettere e Filosofia", "MLC");
            assertSize(1, courses);
            Course course = courses.get(0);
            importerBo.addQuestionsToCourse(course, result);
            courseDao.getHibernateTemplate().flush();
            courseDao.getHibernateTemplate().clear();
            course = courseDao.get(course.getId());
            assertSize(5, course.getQuestions());
            for (Question question : course.getQuestions()) {
                if (question instanceof ClosedQuestion) {
                    assertNotEmpty(((ClosedQuestion)question).getFixedAnswers());
                }
            }
        } catch (Exception ex) {
            fail(ex.getLocalizedMessage());
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }
}
