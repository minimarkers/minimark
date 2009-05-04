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
    <form:errors path="description" cssClass="errorBox"/>
    <form:label path="description"><spring:message code="Description" text="?Description?"/>:</form:label><form:textarea path="description" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <form:errors path="mainGroup" cssClass="errorBox"/>
    <form:label path="mainGroup"><spring:message code="MainGroup" text="?MainGroup?"/>:</form:label><form:input path="mainGroup" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="secondaryGroup" cssClass="errorBox"/>
    <form:label path="secondaryGroup"><spring:message code="SecondaryGroup" text="?SecondaryGroup?"/>:</form:label><form:input path="secondaryGroup" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
</form:form>
