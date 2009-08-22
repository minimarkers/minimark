package com.benfante.minimark.blo;

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.beans.QuestionRequest;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentQuestionDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.AssessmentTemplate;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.Question;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Methods for managing the assessments.
 *
 * @author Lucio Benfante
 */
@Service
public class AssessmentBo {

    @Resource
    private QuestionDao questionDao;
    @Resource
    private AssessmentDao assessmentDao;
    @Resource
    private AssessmentQuestionDao assessmentQuestionDao;
    @Resource
    private QuestionBo questionBo;

    /**
     * Add a question to an assessment.
     * 
     * @param questionId The id of the question (of Question type).
     * @param assessmentId The id of the assessment.
     */
    public void addQuestionToAssessment(Long questionId, Long assessmentId) {
        Question question = questionDao.get(questionId);
        Assessment assessment = assessmentDao.get(assessmentId);
        AssessmentQuestion aq = new AssessmentQuestion();
        aq.setAssessment(assessment);
        aq.setQuestion(question);
        Long nextOrdering = assessmentQuestionDao.getNextOrdering(assessmentId);
        aq.setOrdering(nextOrdering != null ? nextOrdering : Long.valueOf(1));
        assessmentQuestionDao.store(aq);
    }

    /**
     * Remove a question from an assessment.
     *
     * @param assessmentQuestionId The assessment-question id.
     */
    public void removeQuestionFromAssessment(Long assessmentQuestionId) {
        AssessmentQuestion aq = assessmentQuestionDao.get(assessmentQuestionId);
        assessmentQuestionDao.delete(aq);
    }

    @Transactional
    public void moveUpQuestionInAssessment(Long assessmentQuestionId,
            Long assessmentId) {
        List<AssessmentQuestion> questions = assessmentQuestionDao.
                findByAssessmentId(assessmentId);
        int i = 0;
        AssessmentQuestion prev = null;
        for (AssessmentQuestion question : questions) {
            if (question.getId().equals(assessmentQuestionId) && prev != null) {
                question.setOrdering(Long.valueOf(i - 1));
                prev.setOrdering(Long.valueOf(i++));
            } else {
                question.setOrdering(Long.valueOf(i++));
            }
            prev = question;
        }
    }

    public void moveDownQuestionInAssessment(Long assessmentQuestionId,
            Long assessmentId) {
        List<AssessmentQuestion> questions = assessmentQuestionDao.
                findByAssessmentId(assessmentId);
        int i = 0;
        AssessmentQuestion prev = null;
        boolean changeNext = false;
        for (AssessmentQuestion question : questions) {
            if (changeNext && prev != null) {
                question.setOrdering(Long.valueOf(i - 1));
                prev.setOrdering(Long.valueOf(i++));
            } else {
                question.setOrdering(Long.valueOf(i++));
            }
            prev = question;
            changeNext = false;
            if (question.getId().equals(assessmentQuestionId)) {
                changeNext = true;
            }
        }
    }

    /**
     * Build a map of courses and their assessments.
     *
     * @param active If true, map only active assessments. null, any value.
     * @param showInHomePage If true, map only assessments that must be shown in the home page. null, any value.
     * @param username map only the assessments of this user.
     *
     * @return The map of courses on assessments
     */
    public Map<Course, List<Assessment>> mapAssessmentsOnCourse(Boolean active,
            Boolean showInHomePage, String username) {
        List<Assessment> assessments = searchAssessments(active, showInHomePage,
                username);
        Map<Course, List<Assessment>> assessmentsByCourse =
                new HashMap<Course, List<Assessment>>();
        for (Assessment assessment : assessments) {
            Course course = assessment.getCourse();
            List<Assessment> courseAssessments = assessmentsByCourse.get(course);
            if (courseAssessments == null) {
                courseAssessments =
                        new LinkedList<Assessment>();
                assessmentsByCourse.put(course, courseAssessments);
            }
            courseAssessments.add(assessment);
        }
        return assessmentsByCourse;
    }

    /**
     * Find assessments.
     *
     * @param active If true, map only active assessments. null, any value.
     * @param showInHomePage If true, map only assessments that must be shown in the home page. null, any value.
     * @param username map only the assessments of this user. null, any user.
     *
     * @return The map of courses on assessments
     */
    public List<Assessment> searchAssessments(Boolean active,
            Boolean showInHomePage, String username) {
        DetachedCriteria crit = DetachedCriteria.forClass(Assessment.class);
        if (active != null) {
            crit.add(Restrictions.eq("active", active));
        }
        if (showInHomePage != null) {
            crit.add(Restrictions.eq("showInHomePage", showInHomePage));
        }
        if (username != null) {
            crit.createAlias("course.courseTeachers", "courseTeachers");
            crit.createAlias("courseTeachers.userProfile.user", "user");
            crit.add(Restrictions.eq("user.username", username));
        }
        List<Assessment> assessments =
                assessmentDao.searchByCriteria(crit);
        return assessments;
    }

    /**
     * Create a new assessment from a template.
     *
     * @param assessmentTemplate The template
     * @return The new assessment.
     */
    public Assessment createFromTemplate(AssessmentTemplate template) {
        Assessment result = new Assessment();
        result.setCourse(template.getCourse());
        result.setAssessmentDate(new Date());
        result.setDescription(template.getDescription());
        result.setDuration(template.getDuration());
        result.setEvaluationClosedMinimumEvaluation(template.
                getEvaluationClosedMinimumEvaluation());
        result.setEvaluationClosedType(template.getEvaluationClosedType());
        result.setEvaluationMaxValue(template.getEvaluationMaxValue());
        result.setEvaluationType(template.getEvaluationType());
        result.setExposedResult(template.getExposedResult());
        result.setMinPassedValue(template.getMinPassedValue());
        result.setTitle(template.getTitle());
        extractQuestions(result, template);
        return result;
    }

    private void extractQuestions(Assessment assessment,
            AssessmentTemplate template) {
        Random random = new Random();
        template.buildQuestionRequests();
        List<AssessmentQuestion> assessmentQuestions = null;
        if (assessment.getQuestions() != null) {
            assessmentQuestions = assessment.getQuestions();
        } else {
            assessmentQuestions = new LinkedList<AssessmentQuestion>();
        }
        long order = 0;
        for (QuestionRequest questionRequest : template.getQuestionRequests()) {
            if (StringUtils.isNotBlank(questionRequest.getTag())) {
                QuestionBean questionBean =
                        new QuestionBean();
                questionBean.setCourse(template.getCourse());
                questionBean.setTags(questionRequest.getTag());
                List<Question> questions = questionBo.search(questionBean);
                int count = questionRequest.getHowMany();
                while (count > 0) {
                    boolean added = false;
                    while (!added && !questions.isEmpty()) {
                        int index = random.nextInt(questions.size());
                        Question question = questions.remove(index);
                        if (!alreadyIncluded(assessmentQuestions, question)) {
                            AssessmentQuestion assessmentQuestion =
                                    new AssessmentQuestion();
                            assessmentQuestion.setAssessment(assessment);
                            assessmentQuestion.setQuestion(question);
                            assessmentQuestion.setOrdering(Long.valueOf(order));
                            assessmentQuestions.add(assessmentQuestion);
                            order++;
                            added = true;
                        }
                    }
                    count--;
                }
            }
        }
        assessment.setQuestions(assessmentQuestions);
    }

    private boolean alreadyIncluded(List<AssessmentQuestion> assessmentQuestions, Question question) {
        boolean result = false;
        for (AssessmentQuestion assessmentQuestion : assessmentQuestions) {
            if (assessmentQuestion.getQuestion().equals(question)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
