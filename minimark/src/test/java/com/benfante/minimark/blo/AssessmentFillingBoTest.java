package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.Course;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

/**
 * Tests on methods of the AssessmentFillingBo.
 *
 * @author Lucio Benfante
 */
public class AssessmentFillingBoTest extends MinimarkBaseTest {

    @Resource
    private AssessmentFillingBo assessmentFillingBo;

    /**
     * Test of searchActiveFillings method, of class AssessmentFillingBo.
     */
    public void testSearchActiveFillings() {
        List<AssessmentFilling> fillings =
                assessmentFillingBo.searchActiveFillings();
        assertNotEmpty(fillings);
        for (AssessmentFilling filling : fillings) {
            assertTrue("The assessment is not active", filling.getAssessment().getActive().booleanValue());
        }
    }

    /**
     * Test of mapFillingsOnCourse method, of class AssessmentFillingBo.
     */
    public void testMapFillingsOnCourse() {
        Map<Course, List<AssessmentFilling>> fillingMap =
                assessmentFillingBo.mapFillingsOnCourse();
        assertTrue("The map is empty", !fillingMap.isEmpty());
        assertEquals(1, fillingMap.size());
    }
}
