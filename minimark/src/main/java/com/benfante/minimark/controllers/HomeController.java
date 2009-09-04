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
// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.UserProfile;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home/*.html")
public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class);

    @Resource
    private AssessmentBo assessmentBo;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    public String welcome(Model model) {
        Map<Course, List<Assessment>> assessments = assessmentBo.mapAssessmentsOnCourse(
                Boolean.TRUE, Boolean.TRUE, null);
        List<UserProfile> teachers = userProfileBo.searchAllTeachers();
        model.addAttribute("assessments", assessments);
        model.addAttribute("teachers", teachers);
        return "welcome";
    }

    /**
     * Login action
     */
    @RequestMapping
    public ModelAndView acegilogin(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("acegilogin", params);
    }

    /**
     * Access denied
     */
    @RequestMapping
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("accessDenied", params);
    }

    // EXAMPLE
    @RequestMapping
    public ModelAndView pageThatRaiseAnUnHandledException(HttpServletRequest req, HttpServletResponse res){
        if (1 == 1){
            throw new RuntimeException("UNHANDLED BOOM!!!");
        }
        return null;
    }

}
