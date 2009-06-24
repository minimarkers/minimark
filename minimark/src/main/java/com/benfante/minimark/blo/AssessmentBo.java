package com.benfante.minimark.blo;

import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.AssessmentQuestionDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.Question;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
        aq.setOrdering(nextOrdering != null?nextOrdering:Long.valueOf(1));
        assessmentQuestionDao.store(aq);
    }
}
