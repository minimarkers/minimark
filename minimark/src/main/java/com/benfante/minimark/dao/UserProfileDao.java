package com.benfante.minimark.dao;

import com.benfante.minimark.po.UserProfile;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=UserProfile.class)
public interface UserProfileDao extends GenericDao<UserProfile, Long> {
    UserProfile findByUsername(String username);
}
