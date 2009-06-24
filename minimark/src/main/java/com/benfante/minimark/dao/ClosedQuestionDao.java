package com.benfante.minimark.dao;

import com.benfante.minimark.po.ClosedQuestion;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=ClosedQuestion.class)
public interface ClosedQuestionDao extends GenericDao<ClosedQuestion, Long> {
}
