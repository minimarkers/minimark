package com.benfante.minimark.ajax;

import com.benfante.minimark.blo.AssessmentFillingBo;
import com.benfante.minimark.dao.AssessmentFillingDao;
import com.benfante.minimark.po.AssessmentFilling;
import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.ui.dwr.Util;
import org.springframework.stereotype.Service;

/**
 * Ajax methods for managin the fixed answers.
 *
 * @author Lucio Benfante
 */
@Service
@RemoteProxy(name = "monitoringABo")
public class MonitoringABo {

    @Resource
    private AssessmentFillingDao assessmentFillingDao;
    @Resource
    private AssessmentFillingBo assessmentFillingBo;

    @RemoteMethod
    public void confirmAssessmentFilling(Long assessmentFillingId,
            final Long courseId) throws IOException, ServletException {
        final WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        AssessmentFilling filling =
                assessmentFillingDao.get(assessmentFillingId);
        filling.setConfirmedDate(new Date());
        assessmentFillingDao.store(filling);
        Browser.withSession(session.getId(), new Runnable() {

            public void run() {
                try {
                    HttpServletRequest req = wctx.getHttpServletRequest();
                    req.setAttribute("blockFillings", assessmentFillingBo.
                            searchActiveFillingsByCourse(courseId));
                    Util.setValue("course_"+courseId+"_fillings", wctx.forwardToString(
                            "/WEB-INF/jsp/monitoring/fillingBlock.jsp"), false);
                } catch (Exception ex) {
                    Util.setValue("searchResult", ex.getMessage());
                }
            }
        });

    }

    @RemoteMethod
    public void unconfirmAssessmentFilling(Long assessmentFillingId,
            final Long courseId) throws IOException, ServletException {
        final WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        AssessmentFilling filling =
                assessmentFillingDao.get(assessmentFillingId);
        filling.setConfirmedDate(null);
        assessmentFillingDao.store(filling);
        Browser.withSession(session.getId(), new Runnable() {

            public void run() {
                try {
                    HttpServletRequest req = wctx.getHttpServletRequest();
                    req.setAttribute("blockFillings", assessmentFillingBo.
                            searchActiveFillingsByCourse(courseId));
                    Util.setValue("course_"+courseId+"_fillings", wctx.forwardToString(
                            "/WEB-INF/jsp/monitoring/fillingBlock.jsp"), false);
                } catch (Exception ex) {
                    Util.setValue("searchResult", ex.getMessage());
                }
            }
        });

    }

}
