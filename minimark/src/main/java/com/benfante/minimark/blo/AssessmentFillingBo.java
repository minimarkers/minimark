package com.benfante.minimark.blo;

import com.benfante.minimark.beans.AssessmentFillingStatusComparator;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.Question;
import com.benfante.minimark.po.QuestionFilling;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Methods for managing an assessment filling.
 *
 * @author Lucio Benfante
 */
@Service
public class AssessmentFillingBo {

    @Resource
    private AssessmentFillingDao assessmentFillingDao;

    @Transactional
    public AssessmentFilling generateAssessmentFilling(Assessment assessment) {
        AssessmentFilling assessmentFilling = new AssessmentFilling();
        assessmentFilling.setAssessment(assessment);
        int i = 0;
        for (AssessmentQuestion assessmentQuestion : assessment.getQuestions()) {
            QuestionFilling questionFilling = buildQuestionFilling(assessmentQuestion.getQuestion());
            questionFilling.setOrdering(Long.valueOf(i++));
            questionFilling.setAssessmentFilling(assessmentFilling);
            assessmentFilling.getQuestions().add(questionFilling);
        }
        if (assessment.getShuffleQuestions() != null && assessment.getShuffleQuestions().booleanValue()) {
            Collections.shuffle(assessmentFilling.getQuestions());
            i=0;
            for (QuestionFilling questionFilling : assessmentFilling.getQuestions()) {
                questionFilling.setOrdering(Long.valueOf(i++));
            }
        }
        return assessmentFilling;
    }

    private QuestionFilling buildQuestionFilling(Question question) {
        if (question instanceof OpenQuestion) {
            return buildQuestionFilling((OpenQuestion) question);
        } else if (question instanceof ClosedQuestion) {
            return buildQuestionFilling((ClosedQuestion) question);
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    private QuestionFilling buildQuestionFilling(OpenQuestion question) {
        OpenQuestionFilling result = new OpenQuestionFilling();
        result.setAnswerMaxLength(question.getAnswerMaxLength());
        result.setContent(question.getContent());
        result.setContentFilter(question.getContentFilter());
        result.setOriginalQuestion(question);
        result.setTitle(question.getTitle());
        result.setVisualization(question.getVisualization());
        result.setWeight(question.getWeight());
        return result;
    }

    private QuestionFilling buildQuestionFilling(ClosedQuestion question) {
        ClosedQuestionFilling result = new ClosedQuestionFilling();
        result.setContent(question.getContent());
        result.setContentFilter(question.getContentFilter());
        result.setOriginalQuestion(question);
        result.setTitle(question.getTitle());
        result.setVisualization(question.getVisualization());
        result.setWeight(question.getWeight());
        int i = 0;
        for (FixedAnswer fixedAnswer : question.getFixedAnswers()) {
            FixedAnswerFilling fixedAnswerFilling = new FixedAnswerFilling();
            fixedAnswerFilling.setContent(fixedAnswer.getContent());
            fixedAnswerFilling.setContentFilter(fixedAnswer.getContentFilter());
            fixedAnswerFilling.setCorrect(fixedAnswer.getCorrect());
            fixedAnswerFilling.setOrdering(Long.valueOf(i++));
            fixedAnswerFilling.setQuestion(result);
            result.getFixedAnswers().add(fixedAnswerFilling);
            fixedAnswerFilling.setWeight(fixedAnswer.getWeight());
        }
        Collections.shuffle(result.getFixedAnswers());
        i = 0;
        for (FixedAnswerFilling fixedAnswerFilling : result.getFixedAnswers()) {
            fixedAnswerFilling.setOrdering(Long.valueOf(i++));
        }
        return result;
    }

    /**
     * Retrieve all fillings of active assessments.
     *
     * @return The list of fillings of active assesments.
     */
    public List<AssessmentFilling> searchActiveFillings() {
        DetachedCriteria crit = DetachedCriteria.forClass(AssessmentFilling.class);
        crit.createAlias("assessment", "assessment");
        crit.add(Restrictions.eq("assessment.active", Boolean.TRUE));
        List<AssessmentFilling> result =
                assessmentFillingDao.searchByCriteria(crit);
        Collections.sort(result, new AssessmentFillingStatusComparator());
        return result;
    }

    /**
     * Retrieve all fillings of active assessments.
     *
     * @return The list of fillings of active assesments.
     */
    public List<AssessmentFilling> searchActiveFillingsByCourse(Long courseId) {
        DetachedCriteria crit = DetachedCriteria.forClass(AssessmentFilling.class);
        crit.createAlias("assessment", "assessment");
        crit.add(Restrictions.eq("assessment.active", Boolean.TRUE));
        crit.createAlias("assessment.course", "course");
        crit.add(Restrictions.eq("course.id", courseId));
        List<AssessmentFilling> result =
                assessmentFillingDao.searchByCriteria(crit);
        Collections.sort(result, new AssessmentFillingStatusComparator());
        return result;
    }

    /**
     * Build a map of courses and their assessment fillings.
     *
     * @return The map of courses on assessment fillings
     */
    public Map<Course, List<AssessmentFilling>> mapFillingsOnCourse() {
        List<AssessmentFilling> fillings = searchActiveFillings();
        Map<Course, List<AssessmentFilling>> fillingsByCourse =
                new HashMap<Course, List<AssessmentFilling>>();
        for (AssessmentFilling filling : fillings) {
            Course course = filling.getAssessment().getCourse();
            List<AssessmentFilling> courseAssessments = fillingsByCourse.get(course);
            if (courseAssessments == null) {
                courseAssessments =
                        new LinkedList<AssessmentFilling>();
                fillingsByCourse.put(course, courseAssessments);
            }
            courseAssessments.add(filling);
        }
        return fillingsByCourse;
    }

}
