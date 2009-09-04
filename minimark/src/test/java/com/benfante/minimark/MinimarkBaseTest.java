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
package com.benfante.minimark;

import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.CourseTeacher;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.Tag;
import com.benfante.minimark.po.TagQuestionLink;
import com.benfante.minimark.po.UserProfile;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.User;
import org.parancoe.web.test.BaseTest;

/**
 * Basetest for the minimark application.
 *
 * @author lucio
 */
public abstract class MinimarkBaseTest extends BaseTest {

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{User.class, Authority.class, UserProfile.class,
                    Course.class, CourseTeacher.class, OpenQuestion.class,
                    ClosedQuestion.class, FixedAnswer.class, Assessment.class,
                    AssessmentQuestion.class, AssessmentFilling.class,
                    OpenQuestionFilling.class, ClosedQuestionFilling.class,
                    FixedAnswerFilling.class, Tag.class, TagQuestionLink.class};
    }
}
