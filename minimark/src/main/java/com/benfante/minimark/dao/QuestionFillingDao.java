package com.benfante.minimark.dao;

import com.benfante.minimark.po.QuestionFilling;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=QuestionFilling.class)
public interface QuestionFillingDao extends GenericDao<QuestionFilling, Long> {
}