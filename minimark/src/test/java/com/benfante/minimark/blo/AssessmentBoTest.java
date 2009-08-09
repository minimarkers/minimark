package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.CourseTeacher;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests on the methods of the AssessmentBo.
 *
 * @author Lucio Benfante
 */
public class AssessmentBoTest extends MinimarkBaseTest {

    @Resource
    private AssessmentBo assessmentBo;

    /**
     * Test of searchAssessments method, of class AssessmentBo.
     */
    public void testSearchAssessments() {
        final String username = "lucio.benfante";
        List<Assessment> assessments = assessmentBo.searchAssessments(true,
                null, username);
        assertNotEmpty(assessments);
        for (Assessment assessment : assessments) {
            assertEquals(assessment.getActive(), Boolean.TRUE);
            List<CourseTeacher> teachers =
                    assessment.getCourse().getCourseTeachers();
            boolean found = false;
            for (CourseTeacher teacher : teachers) {
                if (username.equals(teacher.getUserProfile().getUser().getUsername())) {
                    found = true;
                    break;
                }
            }
            assertTrue("The test teacher (" + username + ") was not found for this assessment (" + assessment.
                    getTitle() + ")", found);
        }
    }
}
