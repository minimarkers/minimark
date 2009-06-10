<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3>
<c:choose>
    <c:when test="${empty assessment.id}"><spring:message code="CreateAssessment" text="?CreateAssessment?"/></c:when>
    <c:otherwise><spring:message code="EditAssessment" text="?EditAssessment?"/></c:otherwise>
</c:choose>
</h3>
<div>
    <p><spring:message code="Course" text="?Course?"/>: ${assessment.course.name}</p>
</div>
<form:form commandName="assessment" method="POST" action="${cp}/assessment/save.html">
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label><form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="description" cssClass="errorBox"/>
    <form:label path="description"><spring:message code="Description" text="?Description?"/>:</form:label><form:textarea path="description" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <form:errors path="assessmentDate" cssClass="errorBox"/>
    <form:label path="assessmentDate"><spring:message code="Date" text="?Date?"/>:</form:label><form:input path="assessmentDate" maxlength="10" size="10" cssErrorClass="fieldInError full-size"/><img id="assessmentDateCalendar" src="${cp}/images/silk/icons/calendar.png" alt="Calendar icon" class="calendar-icon"/>&nbsp;<span class="form-help-text">(dd/MM/yyyy)</span>&nbsp;*<br/>
    <form:errors path="active" cssClass="errorBox"/>
    <form:label path="active"><spring:message code="Active" text="?Active?"/>:</form:label>
    <form:select path="active">
        <spring:message code="Yes" text="?Yes?" var="activeYesLabel"/>
        <form:option value="true" label="${activeYesLabel}"/>
        <spring:message code="No" text="?No?" var="activeNoLabel"/>
        <form:option value="false" label="${activeNoLabel}"/>
    </form:select><br/>
    <form:errors path="newPassword" cssClass="errorBox"/>
    <form:label path="newPassword"><spring:message code="Password" text="?Password?"/>:</form:label><form:password path="newPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="confirmPassword" cssClass="errorBox"/>
    <form:label path="confirmPassword"><spring:message code="ConfirmPassword" text="?ConfirmPassword?"/>:</form:label><form:password path="confirmPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="formButtons">
        <input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>&nbsp;&nbsp;<a href="list.html" class="action-back"><spring:message code="Back" text="?Back?"/></a>
    </div>
</form:form>

<script type="text/javascript">
attachCalendar('assessmentDateCalendar', 'assessmentDate', false);
</script>