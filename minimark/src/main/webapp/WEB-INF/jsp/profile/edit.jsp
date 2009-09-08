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
<h3><spring:message code="YourProfile" text="?YourProfile?"/></h3>
<form:form commandName="userBean" method="POST" action="save.form">
    <form:errors path="user.username" cssClass="errorBox"/>
    <form:label path="user.username"><spring:message code="Username" text="?Username?"/>:</form:label> ${userBean.user.username}<br/>
    <form:errors path="userProfile.name" cssClass="errorBox"/>
    <form:label path="userProfile.name"><spring:message code="Name" text="?Name?"/>:</form:label><form:input path="userProfile.name" cssClass="full-size" cssErrorClass="fieldInError full-size"/>*<br/>
    <form:errors path="userProfile.email" cssClass="errorBox"/>
    <form:label path="userProfile.email"><spring:message code="Email" text="?Email?"/>:</form:label><form:input path="userProfile.email" cssClass="full-size" cssErrorClass="fieldInError full-size"/>*<br/>
    <form:errors path="userProfile.site" cssClass="errorBox"/>
    <form:label path="userProfile.site"><spring:message code="Site" text="?Site?"/>:</form:label><form:input path="userProfile.site" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="newPassword" cssClass="errorBox"/>
    <form:label path="newPassword"><spring:message code="NewPassword" text="?NewPassword?"/>:</form:label><form:password path="newPassword" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="confirmPassword" cssClass="errorBox"/>
    <form:label path="confirmPassword"><spring:message code="ConfirmPassword" text="?ConfirmPassword?"/>:</form:label><form:password path="confirmPassword" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="formButtons">
        <a href="${cp}/" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>
