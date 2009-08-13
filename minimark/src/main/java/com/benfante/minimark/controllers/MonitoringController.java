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
