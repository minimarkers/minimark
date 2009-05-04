package com.benfante.minimark.dao;

import com.benfante.minimark.po.Tag;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 *
 * @author lucio
 */
@Dao(entity=Tag.class)
public interface TagDao extends GenericDao<Tag, Long> {
    Tag findByName(String name);
}
