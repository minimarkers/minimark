package com.benfante.minimark.dao;

import com.benfante.minimark.po.AssessmentFilling;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity = AssessmentFilling.class)
public interface AssessmentFillingDao extends GenericDao<AssessmentFilling, Long> {
}
