package com.benfante.minimark.dao;

import com.benfante.minimark.po.AssessmentFilling;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity = AssessmentFilling.class)
public interface AssessmentFillingDao extends GenericDao<AssessmentFilling, Long> {
    List<AssessmentFilling> findByAssessmentIdOrderByLastNameAndFirstNameAndIdentifier(Long assessmentId);
    List<AssessmentFilling> findByIdentifierOrderBySubmittedDate(String identifier);
}
