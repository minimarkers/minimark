package com.benfante.minimark.dao;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentQuestion;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;

/**
 * Test of the AssessmentQuestionDao.
 *
 * @author lucio
 */
public class AssessmentQuestionDaoTest extends MinimarkBaseTest {
    private static final Logger logger = Logger.getLogger(AssessmentQuestionDaoTest.class);

    @Resource
    private AssessmentQuestionDao assessmentQuestionDao;
    @Resource
    private AssessmentDao assessmentDao;

    public void testFindAll() {
        final List<AssessmentQuestion> assessmentQuestions = assessmentQuestionDao.findAll();
        assertNotEmpty(assessmentQuestions);
        for (AssessmentQuestion assessmentQuestion : assessmentQuestions) {
            assertNotNull(assessmentQuestion.getAssessment());
            assertNotNull(assessmentQuestion.getQuestion());
        }
    }

    /**
     * Test of getNextOrdering method, of class AssessmentQuestionDao.
     */
    public void testGetNextOrdering() {
        List<Assessment> assessments = assessmentDao.findByTitle("igLCM1");
        assertSize(1, assessments);
        assertSize(1, assessments.get(0).getQuestions());
        assertEquals(Long.valueOf(1), assessments.get(0).getQuestions().get(0).getOrdering());
        Long nextOrdering = assessmentQuestionDao.getNextOrdering(assessments.get(0).getId());
        assertEquals(Long.valueOf(2), nextOrdering);
    }

}
