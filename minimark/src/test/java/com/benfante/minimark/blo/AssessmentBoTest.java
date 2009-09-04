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
