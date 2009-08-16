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
