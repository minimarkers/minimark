package com.benfante.minimark.blo;

import com.benfante.minimark.po.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Resource;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.dao.TagDao;
import com.benfante.minimark.dao.TagQuestionLinkDao;
import com.benfante.minimark.po.Tag;
import com.benfante.minimark.po.TagQuestionLink;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Methods managing the questions
 *
 * @author lucio
 */
@Component
public class QuestionBo {

    private static final Logger logger = Logger.getLogger(QuestionBo.class);
    @Resource
    private QuestionDao questionDao;
    @Resource
    private TagQuestionLinkDao tagQuestionLinkDao;
    @Resource
    private TagDao tagDao;

    /**
     * Persist the question data.
     *
     * @param question The question to persist
     */
    public void save(Question question) {
        updateTagSet(question);
        questionDao.store(question);
    }

    /**
     * Return the list of questions with a specified tag.
     * 
     * @param tag The tag to search
     * @return The list of questions
     */
    public List<Question> retrievePostsByTag(String tag) {
        List<TagQuestionLink> tagLinks = tagQuestionLinkDao.findByTagName(tag.toLowerCase());
        List<Question> result = new ArrayList<Question>(tagLinks.size());
        for (TagQuestionLink tagLink : tagLinks) {
            result.add(tagLink.getQuestion());
        }
        return result;
    }

    /**
     * Search a tag by name in a set of tags.
     *
     * @param tagName The tag name
     * @param currentTags
     * @return
     */
    private TagQuestionLink searchInSet(String tagName,
            SortedSet<TagQuestionLink> currentTags) {
        TagQuestionLink result = null;
        for (TagQuestionLink ctag : currentTags) {
            if (ctag.getTag().getName().equals(tagName.toLowerCase())) {
                result = ctag;
                break;
            }
        }
        return result;
    }

    /**
     * Update the set of tags of a post.
     *
     * @param post The post to update
     */
    private void updateTagSet(Question question) {
        String[] tags = question.getTagList().split(",");
        SortedSet<TagQuestionLink> currentTags = question.getTags();
        SortedSet<TagQuestionLink> oldTags =
                new TreeSet<TagQuestionLink>(currentTags);
        currentTags.clear();
        for (String stag : tags) {
            if (StringUtils.isNotBlank(stag)) {
                stag = stag.trim().toLowerCase();
                TagQuestionLink tagLink = searchInSet(stag, oldTags);
                if (tagLink == null) {
                    // new tag for this post
                    Tag tag = tagDao.findByName(stag);
                    if (tag == null) {
                        // totally new tag
                        tag = new Tag(stag);
                        tagDao.store(tag);
                    }
                    tagLink = new TagQuestionLink(tag, question);
                }
                currentTags.add(tagLink);
            }
        }
    }
}
