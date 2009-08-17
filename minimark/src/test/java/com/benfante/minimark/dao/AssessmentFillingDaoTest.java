package com.benfante.minimark.dao;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests on methods of the AssessmentFillingDao.
 *
 * @author Lucio Benfante
 */
public class AssessmentFillingDaoTest extends MinimarkBaseTest {

    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentDao assessmentDao;

    /**
     * Test of findByAssessmentIdAndIdentifier method, of class AssessmentFillingDao.
     */
    public void testFindByAssessmentIdAndIdentifier() {
        List<Assessment> assessment = assessmentDao.findByTitle("igLCM1");
        AssessmentFilling filling = assessmentFillingDao.
                findByAssessmentIdAndIdentifier(assessment.get(0).
                getId(), "12345");
        assertNotNull(filling);
        assertEquals("12345", filling.getIdentifier());
    }
}
