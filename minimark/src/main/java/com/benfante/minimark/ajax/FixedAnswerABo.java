package com.benfante.minimark.ajax;

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.controllers.QuestionController;
import com.benfante.minimark.dao.FixedAnswerDao;
import com.benfante.minimark.dao.QuestionDao;
import com.benfante.minimark.po.ClosedQuestion;
import com.benfante.minimark.po.FixedAnswer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

/**
 * Ajax methods for managin the fixed answers.
 *
 * @author Lucio Benfante
 */
@Service
@RemoteProxy(name = "fixedAnswerABo")
public class FixedAnswerABo {

    @Resource
    private FixedAnswerDao fixedAnswerDao;
    @Resource
    private QuestionDao questionDao;

    @RemoteMethod
    public void deleteAnswer(Long id) throws IOException, ServletException {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        FixedAnswer answer = fixedAnswerDao.get(id);
        QuestionBean questionBean = (QuestionBean) wctx.getSession().getAttribute(QuestionController.QUESTION_ATTR_NAME);
        if (questionBean != null && answer != null) {
            fixedAnswerDao.delete(answer);
            questionBean.getFixedAnswers().remove(answer);
            wctx.getHttpServletRequest().setAttribute("question", questionBean);
            String faDiv = wctx.forwardToString("/WEB-INF/jsp/question/fixedAnswers.jsp");
            Util util = new Util(session);
            util.setValue("fixedAnswers", faDiv, false);
        }
    }

    @RemoteMethod
    public void saveAnswer(Long id, String content, BigDecimal weight, Boolean correct, String filter) throws IOException, ServletException {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        QuestionBean questionBean = (QuestionBean) wctx.getSession().getAttribute(QuestionController.QUESTION_ATTR_NAME);
        if (questionBean != null) {
            List<FixedAnswer> fixedAnswers = questionBean.getFixedAnswers();
            FixedAnswer answer = null;
            if (id != null) {
                answer = fixedAnswerDao.get(id);
                int faIndex = fixedAnswers.indexOf(answer);
                answer = fixedAnswers.get(faIndex);
            } else { // new answer
                answer = new FixedAnswer();
                Long questionId = questionBean.getId();
                if (questionId != null) {
                    answer.setQuestion((ClosedQuestion) questionDao.get(questionId));
                }
                fixedAnswers.add(answer);
            }
            answer.setContent(content);
            answer.setWeight(weight);
            answer.setCorrect(correct);
            answer.setContentFilter(filter);
            if (questionBean.getId() != null) {
                fixedAnswerDao.store(answer);
            }
            wctx.getHttpServletRequest().setAttribute("question", questionBean);
            String faDiv = wctx.forwardToString("/WEB-INF/jsp/question/fixedAnswers.jsp");
            Util util = new Util(session);
            util.setValue("fixedAnswers", faDiv, false);
        }

    }
}
