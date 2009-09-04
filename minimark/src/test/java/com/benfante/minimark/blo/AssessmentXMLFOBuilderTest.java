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
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Tests on the FO generation.
 *
 * @author Lucio Benfante
 */
public class AssessmentXMLFOBuilderTest extends MinimarkBaseTest {

    private static final Logger logger = Logger.getLogger(
            AssessmentXMLFOBuilderTest.class);
    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentXMLFOBuilder assessmentXMLFOBuilder;

    /**
     * Test of makeXMLFO method, of class AssessmentXMLFOBuilder.
     */
    public void testMakeXMLFO() {
        List<AssessmentFilling> assessments = assessmentFillingDao.findAll();
        assertNotEmpty(assessments);
        final AssessmentFilling assessment = assessments.get(0);
        assertNotNull(assessment.getStartDate());
        assertSize(4, assessment.getQuestions());
        String xmlfo = assessmentXMLFOBuilder.makeXMLFO(assessment,
                Locale.ENGLISH);
        assertTrue("XMLFO is empty", StringUtils.isNotEmpty(xmlfo));
    }

}
