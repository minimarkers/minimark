package com.benfante.minimark.dao;

import com.benfante.minimark.po.Question;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=Question.class)
public interface QuestionDao extends GenericDao<Question, Long> {
    List<Question> findByCourseId(Long courseId);
}
