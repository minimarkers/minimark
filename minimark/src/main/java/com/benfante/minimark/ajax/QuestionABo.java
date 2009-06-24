/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benfante.minimark.ajax;

import com.benfante.minimark.beans.QuestionBean;
import com.benfante.minimark.blo.AssessmentBo;
import com.benfante.minimark.blo.QuestionBo;
import com.benfante.minimark.controllers.AssessmentController;
import com.benfante.minimark.dao.AssessmentDao;
import com.benfante.minimark.dao.QuestionDao;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.ui.dwr.Util;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

/**
 * Ajax methods for managing the questions.
 *
 * @author Lucio Benfante
 */
@Service
@RemoteProxy(name = "questionABo")
public class QuestionABo {

    @Resource
    private QuestionBo questionBo;
    @Resource
    private AssessmentBo assessmentBo;
    @Resource
    private AssessmentDao assessmentDao;

    @RemoteMethod
    public void updateQuestionSearchResult(final QuestionBean questionBean) {
        final WebContext ctx = WebContextFactory.get();
        ScriptSession scriptSession = ctx.getScriptSession();
        Browser.withSession(scriptSession.getId(), new Runnable() {

            public void run() {
                try {
                    HttpServletRequest req = ctx.getHttpServletRequest();
                    req.setAttribute(AssessmentController.QUESTION_SEARCH_RESULT_ATTR_NAME, questionBo.search(questionBean));
                    Util.setValue("searchResult", ctx.forwardToString("/WEB-INF/jsp/assessment/questionSearchResult.jsp"), false);
                } catch (Exception ex) {
                    Util.setValue("searchResult", ex.getMessage());
                }
            }
        });
    }

    public void addQuestionToAssessment(Long questionId, final Long assessmentId) {
        assessmentBo.addQuestionToAssessment(questionId, assessmentId);
        final WebContext ctx = WebContextFactory.get();
        ScriptSession scriptSession = ctx.getScriptSession();
        Browser.withSession(scriptSession.getId(), new Runnable() {

            public void run() {
                try {
                    HttpServletRequest req = ctx.getHttpServletRequest();
                    req.setAttribute(AssessmentController.ASSESSMENT_QUESTIONS_ATTR_NAME, assessmentDao.get(assessmentId).getQuestions());
                    Util.setValue("assessmentQuestions", ctx.forwardToString("/WEB-INF/jsp/assessment/questionSearchResult.jsp"), false);
                } catch (Exception ex) {
                    Util.setValue("assessmentQuestions", ex.getMessage());
                }
            }
        });
    }
}
