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
<h3>
<c:choose>
    <c:when test="${empty course.id}"><spring:message code="CreateCourse" text="?CreateCourse?"/></c:when>
    <c:otherwise><spring:message code="EditCourse" text="?EditCourse?"/></c:otherwise>
</c:choose>
</h3>
<form:form commandName="course" method="POST" action="${cp}/course/save.html">
    <form:errors path="name" cssClass="errorBox"/>
    <form:label path="name"><spring:message code="Name" text="?Name?"/>:</form:label><form:input path="name" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="incumbent" cssClass="errorBox"/>
    <form:label path="incumbent"><spring:message code="Incumbent" text="?Incumbent?"/>:</form:label><form:input path="incumbent" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="description" cssClass="errorBox"/>
    <form:label path="description"><spring:message code="Description" text="?Description?"/>:</form:label><form:textarea path="description" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <form:errors path="mainGroup" cssClass="errorBox"/>
    <form:label path="mainGroup"><spring:message code="MainGroup" text="?MainGroup?"/>:</form:label><form:input path="mainGroup" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="secondaryGroup" cssClass="errorBox"/>
    <form:label path="secondaryGroup"><spring:message code="SecondaryGroup" text="?SecondaryGroup?"/>:</form:label><form:input path="secondaryGroup" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <div class="formButtons">
        <a href="${cp}" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>
