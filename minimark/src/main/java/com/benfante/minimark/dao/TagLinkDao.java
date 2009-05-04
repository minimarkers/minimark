package com.benfante.minimark.dao;

import com.benfante.minimark.po.TagLink;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=TagLink.class)
public interface TagLinkDao extends GenericDao<TagLink, Long> {

}
