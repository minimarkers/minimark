package com.benfante.minimark.dao;

import com.benfante.minimark.po.AssessmentTemplate;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity = AssessmentTemplate.class)
public interface AssessmentTemplateDao extends GenericDao<AssessmentTemplate, Long> {
    List<AssessmentTemplate> findByCourseId(Long courseId);
}
