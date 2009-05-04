package com.benfante.minimark.blo;

import com.benfante.minimark.dao.UserProfileDao;
import com.benfante.minimark.po.UserProfile;
import javax.annotation.Resource;
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
}
