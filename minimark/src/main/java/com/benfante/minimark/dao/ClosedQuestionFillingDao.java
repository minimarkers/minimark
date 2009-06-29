package com.benfante.minimark.dao;

import com.benfante.minimark.po.ClosedQuestionFilling;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=ClosedQuestionFilling.class)
public interface ClosedQuestionFillingDao extends GenericDao<ClosedQuestionFilling, Long> {
}
