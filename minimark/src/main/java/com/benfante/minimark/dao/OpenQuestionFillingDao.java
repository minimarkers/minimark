package com.benfante.minimark.dao;

import com.benfante.minimark.po.OpenQuestionFilling;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=OpenQuestionFilling.class)
public interface OpenQuestionFillingDao extends GenericDao<OpenQuestionFilling, Long> {
}
