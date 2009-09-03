package com.benfante.minimark.controllers;

import com.benfante.minimark.blo.AssessmentBo;
import com.benfante.minimark.blo.UserProfileBo;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentTemplateDao;
import com.benfante.minimark.dao.CourseDao;
import com.benfante.minimark.dao.TagQuestionLinkDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentTemplate;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.TagQuestionLink;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Resource;
import org.parancoe.web.validation.Validation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * The controller managing the assessment templates.
 *
 * @author lucio
 */
@Controller
@RequestMapping("/assessment/template/*.html")
@SessionAttributes(AssessmentTemplateController.ASSESSMENT_TEMPLATE_ATTR_NAME)
public class AssessmentTemplateController {

    public static final String ASSESSMENT_TEMPLATE_ATTR_NAME =
            "assessmentTemplate";
    public static final String EDIT_VIEW = "assessment/template/edit";
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private AssessmentTemplateDao assessmentTemplateDao;
    @Resource
    private AssessmentBo assessmentBo;
    @Resource
    private TagQuestionLinkDao tagQuestionLinkDao;
    @Resource
    private UserProfileBo userProfileBo;

    @RequestMapping
    @Validation(view = EDIT_VIEW)
    public String save(
            @ModelAttribute(ASSESSMENT_TEMPLATE_ATTR_NAME) AssessmentTemplate template,
            BindingResult result, SessionStatus status) {
        template.buildTagSelectors();
        Assessment assessment = assessmentBo.createFromTemplate(template);
        userProfileBo.checkEditAuthorization(assessment);
        assessmentDao.store(assessment);
        assessmentTemplateDao.store(template);
        status.setComplete();
        return "redirect:/assessment/list.html";
    }

    @RequestMapping
    public String create(@RequestParam("course.id") Long courseId, Model model) {
        final Course course = courseDao.get(courseId);
        userProfileBo.checkEditAuthorization(course);
        AssessmentTemplate template = null;
        List<AssessmentTemplate> templates =
                assessmentTemplateDao.findByCourseId(courseId);
        if (templates.isEmpty()) {
            template = new AssessmentTemplate();
            template.setCourse(course);
        } else {
            template = templates.get(0);
        }
        template.buildQuestionRequests();
        model.addAttribute(ASSESSMENT_TEMPLATE_ATTR_NAME, template);
        return EDIT_VIEW;
    }

    @ModelAttribute("tags")
    public SortedSet<String> retriveTags(@RequestParam(value = "course.id",
            required = false) Long courseId) {
        SortedSet<String> tags = new TreeSet<String>();
        if (courseId != null) {
            List<TagQuestionLink> tagLinks =
                    tagQuestionLinkDao.findByCourseId(courseId);
            for (TagQuestionLink tagQuestionLink : tagLinks) {
                tags.add(tagQuestionLink.getTag().getName());
            }
        }
        return tags;
    }
}
