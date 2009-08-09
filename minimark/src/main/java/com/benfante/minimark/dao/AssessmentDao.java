package com.benfante.minimark.dao;

import com.benfante.minimark.po.Assessment;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity = Assessment.class)
public interface AssessmentDao extends GenericDao<Assessment, Long> {
    List<Assessment> findByTitle(String title);
    List<Assessment> findByTeacherUsername(String username);
    List<Assessment> findByActiveOrderByAssessmentDateAndTitle(Boolean active);
    List<Assessment> findByActiveAndShowInHomePageOrderByAssessmentDateAndTitle(Boolean active, Boolean showInHomePage);
}
