package com.benfante.minimark.blo;

import com.benfante.minimark.dao.UserProfileDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.CourseTeacher;
import com.benfante.minimark.po.UserProfile;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Code for managing the user profile.
 *
 * @author lucio
 */
@Component
public class UserProfileBo {

    @Resource
    private UserProfileDao userProfileDao;

    /**
     * Returns the username of the authenticated user.
     * @return the username. null if the current user is not authenticated.
     */
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null; //not authenticated
    }

    /**
     * Return the profile of the current authenticated user.
     *
     * @return The user profile. null if thje current user is not authenticated.
     */
    public UserProfile getCurrentUser() {
        UserProfile result = null;
        String username = getAuthenticatedUsername();
        if (username != null) {
            result = userProfileDao.findByUsername(username);
        }
        return result;
    }

    /**
     * Search all users associated with at least one course.
     *
     * @return The list of all teachers
     */
    public List<UserProfile> searchAllTeachers() {
        DetachedCriteria crit = DetachedCriteria.forClass(UserProfile.class);
        crit.add(Restrictions.isNotEmpty("courseTeachers"));
        crit.addOrder(Order.asc("name"));
        return userProfileDao.searchByCriteria(crit);
    }

    public boolean canUserEditCourse(UserProfile userProfile, Course course) {
        boolean result = false;
        final List<CourseTeacher> courseTeachers = course.getCourseTeachers();
        for (CourseTeacher courseTeacher : courseTeachers) {
            if (courseTeacher.getUserProfile().equals(userProfile)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean canUserEditAssessment(UserProfile userProfile,
            Assessment assessment) {
        return canUserEditCourse(userProfile, assessment.getCourse());
    }

    public boolean canUserMonitorAssessment(UserProfile userProfile,
            Assessment assessment) {
        boolean result = false;
        if (canCurrentUserEditAssessment(assessment) || ArrayUtils.contains(
                assessment.getMonitoringUsersAsArray(), userProfile.getUser().
                getUsername())) {
            result = true;
        }
        return result;
    }

    public boolean canCurrentUserEditCourse(Course course) {
        return canUserEditCourse(getCurrentUser(), course);
    }

    public boolean canCurrentUserEditAssessment(Assessment assessment) {
        return canUserEditAssessment(getCurrentUser(), assessment);
    }

    public boolean canCurrentUserMonitorAssessment(Assessment assessment) {
        return canUserMonitorAssessment(getCurrentUser(), assessment);
    }

    public void checkEditAuthorization(Assessment assessment) {
        if (!canCurrentUserEditAssessment(assessment)) {
            throw new RuntimeException("The current user (" + getCurrentUser().
                    getId() + ") is not authorized to edit this assessment (" +
                    assessment.getId() + ")");
        }
    }

    public void checkEditAuthorization(Course course) {
        if (!canCurrentUserEditCourse(course)) {
            throw new RuntimeException("The current user (" + getCurrentUser().
                    getId() + ") is not authorized to edit this course (" +
                    course.getId() + ")");
        }
    }
}
