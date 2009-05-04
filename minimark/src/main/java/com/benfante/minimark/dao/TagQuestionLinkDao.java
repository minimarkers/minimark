package com.benfante.minimark.dao;

import com.benfante.minimark.beans.TagCloudItem;
import com.benfante.minimark.po.TagQuestionLink;
import java.util.List;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=TagQuestionLink.class)
public interface TagQuestionLinkDao extends GenericDao<TagQuestionLink, Long> {
    List<TagQuestionLink> findByTagName(String tag);
    TagQuestionLink findByTagNameAndQuestionId(String tag, Long questionId);
    List<TagCloudItem> retrieveTagCloud();
}
