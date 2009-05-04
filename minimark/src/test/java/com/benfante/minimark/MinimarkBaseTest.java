package com.benfante.minimark;

import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.CourseTeacher;
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
        return new Class[] {User.class, Authority.class, UserProfile.class, Course.class, CourseTeacher.class};
    }


}
