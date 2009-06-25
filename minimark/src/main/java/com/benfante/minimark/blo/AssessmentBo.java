package com.benfante.minimark.blo;

import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentQuestionDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.Question;
import java.util.List;
import javax.annotation.Resource;
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
    public void moveUpQuestionInAssessment(Long assessmentQuestionId, Long assessmentId) {
        List<AssessmentQuestion> questions = assessmentQuestionDao.findByAssessmentId(assessmentId);
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

    public void moveDownQuestionInAssessment(Long assessmentQuestionId, Long assessmentId) {
        List<AssessmentQuestion> questions = assessmentQuestionDao.findByAssessmentId(assessmentId);
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
}
