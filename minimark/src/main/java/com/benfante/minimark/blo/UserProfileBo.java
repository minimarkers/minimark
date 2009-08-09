package com.benfante.minimark.blo;

import com.benfante.minimark.dao.UserProfileDao;
import com.benfante.minimark.po.UserProfile;
import java.util.List;
import javax.annotation.Resource;
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
}
