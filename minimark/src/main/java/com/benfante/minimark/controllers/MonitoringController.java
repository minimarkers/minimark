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

import com.benfante.minimark.blo.AssessmentFillingBo;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.Course;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller for monitoring activities.
 *
 * @author Lucio Benfante
 *
 */
@Controller
@RequestMapping("/monitoring/*.html")
public class MonitoringController {
    @Resource
    private AssessmentFillingBo assessmentFillingBo;

    @RequestMapping
    public void fillings(Model model) {
        Map<Course, List<AssessmentFilling>> fillings =
                assessmentFillingBo.mapFillingsOnCourse();
        model.addAttribute("fillings", fillings);
    }
}
