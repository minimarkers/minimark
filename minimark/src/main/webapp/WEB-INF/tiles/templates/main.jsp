<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="/WEB-INF/jsp/head.jspf" %>
    </head>
    <body>
        <div id="container" class="clearfix">
            <div id="header">
                <tiles:insertAttribute name="header"/>
            </div>
            <div id="content" class="clearfix">
                <div id="menu">
                    <tiles:insertAttribute name="menu"/>
                </div>
                <div id="main">
                    <tiles:insertAttribute name="main"/>
                </div>
                <div id="sidebar">
                    <tiles:insertAttribute name="sidebar"/>
                </div>
            </div>
            <div id="footer">
                <div class="sponsor">
                    <tiles:insertAttribute name="footer"/>
                </div>
            </div>
            <div class="copyright">
                &#169; 2008, <a href="http://scarabocio.googlecode.com">The Scarabocio Team</a> - <a href="mailto:info@scarabocio.com">info@scarabocio.net</a>
            </div>
        <% if (!BaseConf.isProduction()) {%>
        <jsp:include page="/WEB-INF/jsp/debug.jsp" />
        <% }%>
        </div>
    </body>
</html>
