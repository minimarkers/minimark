package com.benfante.minimark.dao;

import com.benfante.minimark.po.FixedAnswer;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=FixedAnswer.class)
public interface FixedAnswerDao extends GenericDao<FixedAnswer, Long> {
}
