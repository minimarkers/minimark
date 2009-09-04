<%--

    Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>

    This file is part of minimark Web Application.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
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
        <div class="formButtons">
            <input id="submit" name="submit" type="submit" value="<spring:message code='sign_in'/>" tabindex="4" class="submit-button"/>
        </div>
    </form>
</div>
