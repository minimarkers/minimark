package com.benfante.minimark.dao;

import com.benfante.minimark.po.AssessmentQuestion;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity = AssessmentQuestion.class)
public interface AssessmentQuestionDao extends GenericDao<AssessmentQuestion, Long> {
    Long getNextOrdering(Long assessmentId);
    List<AssessmentQuestion> findByAssessmentId(Long assessmentId);
}