package com.benfante.minimark.dao;

import com.benfante.minimark.po.OpenQuestion;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=OpenQuestion.class)
public interface OpenQuestionDao extends GenericDao<OpenQuestion, Long> {
}
