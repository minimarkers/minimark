package com.benfante.minimark.blo;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.UserProfile;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests on methods of the UserProfileBo.
 *
 * @author Lucio Benfate
 */
public class UserProfileBoTest extends MinimarkBaseTest {
    @Resource
    private UserProfileBo userProfileBo;
    
    /**
     * Test of searchAllTeachers method, of class UserProfileBo.
     */
    public void testSearchAllTeachers() {
        List<UserProfile> teachers = userProfileBo.searchAllTeachers();
        assertNotEmpty(teachers);
        for (UserProfile teacher : teachers) {
            assertNotEmpty("The list of courses of "+teacher.getUser().getUsername()+" is empty", teacher.getCourseTeachers());
        }
    }

}
