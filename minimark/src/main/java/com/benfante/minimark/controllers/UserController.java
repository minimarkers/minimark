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

import com.benfante.minimark.blo.AssessmentBo;
import com.benfante.minimark.dao.UserProfileDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.UserProfile;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/*.html")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserProfileDao userProfileDao;
    @Resource
    private AssessmentBo assessmentBo;

    @RequestMapping
    public void page(@RequestParam("username") String username, Model model) {
        UserProfile user = userProfileDao.findByUsername(username);
        Map<Course, List<Assessment>> assessmentsByCourse =
                assessmentBo.mapAssessmentsOnCourse(true, null, username);
        model.addAttribute("user", user);
        model.addAttribute("assessments", assessmentsByCourse);
    }
}
