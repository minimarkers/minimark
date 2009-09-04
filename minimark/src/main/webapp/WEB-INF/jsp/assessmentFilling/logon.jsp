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
<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Assessment" text="?Assessment?"/></h3>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessmentFilling.assessment.course.name}</p>
    <p><spring:message code="Title" text="?Title?"/>: ${assessmentFilling.assessment.title}</p>
    <p><spring:message code="Incumbent" text="?Incumbent?"/>: ${assessmentFilling.assessment.course.incumbent}</p>
</div>
<form:form commandName="assessmentFilling" method="POST" action="${cp}/assessmentFilling/start.html">
    <form:errors path="firstName" cssClass="errorBox"/>
    <form:label path="firstName"><spring:message code="FirstName" text="?FirstName?"/>:</form:label><form:input path="firstName" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="lastName" cssClass="errorBox"/>
    <form:label path="lastName"><spring:message code="LastName" text="?LastName?"/>:</form:label><form:input path="lastName" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="identifier" cssClass="errorBox"/>
    <form:label path="identifier"><spring:message code="StudentIdentifier" text="?StudentIdentifier?"/>:</form:label><form:input path="identifier" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <c:if test="${!empty assessmentFilling.assessment.password}">
        <form:errors path="password" cssClass="errorBox"/>
        <form:label path="password"><spring:message code="Password" text="?Password?"/>:</form:label><form:password path="password" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    </c:if>
    <div class="formButtons">
        <input type="submit" value="<spring:message code='Start' text='?Start?'/>" class="submit-button"/>&nbsp;&nbsp;<a href="${cp}" class="action action-back"><spring:message code="Back" text="?Back?"/></a>
    </div>
</form:form>
