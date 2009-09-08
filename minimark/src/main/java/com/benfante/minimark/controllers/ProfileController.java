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
package com.benfante.minimark.controllers;

import com.benfante.minimark.beans.AuthorityCheckBox;
import com.benfante.minimark.beans.UserBean;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.UserProfileDao;
import com.benfante.minimark.po.UserProfile;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.AuthorityDao;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.web.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller for managing the current user profile.
 * 
 * @author Lucio Benfante
 */
@Controller
@RequestMapping({"/profile/*.form", "/profile/*.html"})
@SessionAttributes(ProfileController.USER_ATTR_NAME)
public class ProfileController {

    public static final String USER_ATTR_NAME = "userBean";
    public static final String EDIT_VIEW = "profile/edit";
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public String edit(Model model) {
        UserBean userBean = new UserBean();
        UserProfile currentUser = userProfileBo.getCurrentUser();
        userBean.setUserProfile(currentUser);
        userBean.setUser(currentUser.getUser());
        final AuthorityCheckBox fakeAuthority = new AuthorityCheckBox();
        fakeAuthority.setChecked(true);
        userBean.getAuthorityCheckBoxes().add(fakeAuthority);
        model.addAttribute(USER_ATTR_NAME, userBean);
        return EDIT_VIEW;
    }

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(@ModelAttribute(USER_ATTR_NAME) UserBean userBean,
            BindingResult result, SessionStatus status) {
        User user = userBean.getUser();
        if (StringUtils.isNotBlank(userBean.getNewPassword())) {
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            user.setPassword(encoder.encodePassword(
                    userBean.getNewPassword(), user.getUsername()));
        }
        userDao.store(user);
        userProfileDao.store(userBean.getUserProfile());
        status.setComplete();
        return "redirect:/";
    }

}
