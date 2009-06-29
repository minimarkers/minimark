package com.benfante.minimark.dao;

import com.benfante.minimark.po.FixedAnswerFilling;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=FixedAnswerFilling.class)
public interface FixedAnswerFillingDao extends GenericDao<FixedAnswerFilling, Long> {
}
