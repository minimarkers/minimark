/**
 * Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>
 *
 * This file is part of minimark Web Application.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
