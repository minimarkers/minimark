<%@ include file="common.jspf" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>
<div id="loginBox">
    <div id="loginTitle"><spring:message code="loginTitle"/></div>
    <c:if test="${not empty param.login_error}">
        <div id="loginMessageBox">
            Your login attempt was not successful, try again.<br/>
            If you are testing MiniMark, try <b>parancoe/parancoe</b> and <b>admin/admin</b>.<br/>
            Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage()%>
        </div>
    </c:if>
    <form name="loginForm" action="<c:url value='/securityCheck.secure'/>" method="POST">
        <label for="username"><spring:message code="username"/></label>
        <input id="username" class="loginField" type='text' name='j_username' tabindex="1" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</c:if>/><br/>
        <label for="password"><spring:message code="password"/></label>
        <input id="password" class="loginField" type='password' name='j_password' tabindex="2"/><br/>
        <label for="remember">&nbsp;</label>
        <input id="remember" type="checkbox" name="_acegi_security_remember_me" tabindex="3"/><span style="float: left;"> <spring:message code="remember_me"/></span><br/>
        <input id="submit" name="submit" type="submit" value="<spring:message code='sign_in'/>" tabindex="4" class="submit-button"/>
    </form>
</div>
