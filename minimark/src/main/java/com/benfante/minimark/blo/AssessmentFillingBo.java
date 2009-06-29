package com.benfante.minimark.blo;

import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.Assessment;
import com.benfante.minimark.po.AssessmentFilling;
import com.benfante.minimark.po.AssessmentQuestion;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.ClosedQuestionFilling;
import com.benfante.minimark.po.FixedAnswer;
import com.benfante.minimark.po.FixedAnswerFilling;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.OpenQuestionFilling;
import com.benfante.minimark.po.Question;
import com.benfante.minimark.po.QuestionFilling;
import javax.annotation.Resource;
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
    public AssessmentFilling generateAndStoreAssessmentFilling(Assessment assessment) {
        AssessmentFilling assessmentFilling = new AssessmentFilling();
        assessmentFilling.setAssessment(assessment);
        int i = 0;
        for (AssessmentQuestion assessmentQuestion : assessment.getQuestions()) {
            QuestionFilling questionFilling = buildQuestionFilling(assessmentQuestion.getQuestion());
            questionFilling.setOrdering(Long.valueOf(i++));
            questionFilling.setAssessmentFilling(assessmentFilling);
            assessmentFilling.getQuestions().add(questionFilling);
        }
        assessmentFillingDao.store(assessmentFilling);
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
        return result;
    }
}
