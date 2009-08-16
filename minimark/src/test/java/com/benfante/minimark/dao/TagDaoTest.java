package com.benfante.minimark.dao;

import com.benfante.minimark.MinimarkBaseTest;
import com.benfante.minimark.po.Course;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.Tag;
import com.benfante.minimark.po.TagQuestionLink;
import java.util.List;
import javax.annotation.Resource;

/**
 * Tests for the TagDao.
 *
 * @author lucio
 */
public class TagDaoTest extends MinimarkBaseTest {
    @Resource
    private TagDao tagDao;

    public void testFindAll() {
        List<Tag> tags = tagDao.findAll();
        assertNotNull(tags);
        assertSize(3, tags);
    }

    public void testFindByName() {
        Tag tag = tagDao.findByName("test1");
        assertNotNull(tag);
        assertEquals("test1", tag.getName());
        assertNotEmpty(tag.getTagLinks());
        assertSize(1, tag.getTagLinks());
        assertEquals("OpenQuestion 001", ((TagQuestionLink)tag.getTagLinks().iterator().next()).getQuestion().getTitle());
    }

}
