package com.benfante.minimark.blo;

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.po.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Resource;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.dao.TagDao;
import com.benfante.minimark.dao.TagQuestionLinkDao;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.OpenQuestion;
import com.benfante.minimark.po.Tag;
import com.benfante.minimark.po.TagQuestionLink;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
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
     * Search questions by example.
     * 
     * @param questionBean The values for searching questions.
     * @return The list of questions matching the questionBean values.
     */
    public List<Question> search(QuestionBean questionBean) {
        DetachedCriteria crit = null;
        if (StringUtils.isBlank(questionBean.getType())) {
            crit = DetachedCriteria.forClass(Question.class);
        } else {
            if ("open".equals(questionBean.getType())) {
                crit = DetachedCriteria.forClass(OpenQuestion.class);
                if (StringUtils.isNotBlank(questionBean.getVisualization())) {
                    crit.add(Restrictions.eq("visualization", questionBean.
                            getVisualization()));
                }
            } else if ("closed".equals(questionBean.getType())) {
                crit = DetachedCriteria.forClass(ClosedQuestion.class);
            } else {
                throw new IllegalArgumentException("Unknown question type (" + questionBean.
                        getType() + ")");
            }
        }
        if (StringUtils.isNotBlank(questionBean.getTitle())) {
            crit.add(Restrictions.ilike("title", questionBean.getTitle(), MatchMode.ANYWHERE));
        }
        if (questionBean.getWeight() != null) {
            crit.add(Restrictions.eq("weight", questionBean.getWeight()));
        }
        if (questionBean.getCourse() != null) {
            crit.add(Restrictions.eq("course.id",
                    questionBean.getCourse().getId()));
        }
        return questionDao.searchByCriteria(crit);
    }

    /**
     * Return the list of questions with a specified tag.
     * 
     * @param tag The tag to search
     * @return The list of questions
     */
    public List<Question> retrieveQuestionsByTag(String tag) {
        List<TagQuestionLink> tagLinks = tagQuestionLinkDao.findByTagName(tag.
                toLowerCase());
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
