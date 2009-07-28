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
    <form:errors path="evaluationType" cssClass="errorBox"/>
    <form:label path="evaluationType"><spring:message code="EvaluationType" text="?EvaluationType?"/>:</form:label>
    <form:select path="evaluationType">
        <spring:message code="ETSimpleSum" text="?ETSimpleSum?" var="ETSimpleSumLabel"/>
        <form:option value="simple_sum" label="${ETSimpleSumLabel}"/>
        <spring:message code="ETNormalizedSum" text="?ETNormalizedSum?" var="ETNormalizedSumLabel"/>
        <form:option value="normalized_sum" label="${ETNormalizedSumLabel}"/>
    </form:select><br/>
    <div id="ETNormalizedSumFields">
        <form:errors path="evaluationMaxValue" cssClass="errorBox"/>
        <form:label path="evaluationMaxValue"><spring:message code="EvaluationMaxValue" text="?EvaluationMaxValue?"/>:</form:label><form:input path="evaluationMaxValue" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    </div>
    <form:errors path="description" cssClass="errorBox"/>
    <form:errors path="evaluationClosedType" cssClass="errorBox"/>
    <form:label path="evaluationClosedType"><spring:message code="EvaluationClosedType" text="?EvaluationClosedType?"/>:</form:label>
    <form:select path="evaluationClosedType">
        <spring:message code="ETCSumCorrectAnswers" text="?ETCSumCorrectAnswers?" var="ETCSumCorrectAnswersLabel"/>
        <form:option value="sum_correct_answers" label="${ETCSumCorrectAnswersLabel}"/>
    </form:select><br/>
    <form:errors path="newPassword" cssClass="errorBox"/>
    <form:label path="newPassword"><spring:message code="Password" text="?Password?"/>:</form:label><form:password path="newPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="confirmPassword" cssClass="errorBox"/>
    <form:label path="confirmPassword"><spring:message code="ConfirmPassword" text="?ConfirmPassword?"/>:</form:label><form:password path="confirmPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="formButtons">
        <a href="list.html" class="action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>

<script type="text/javascript">
attachCalendar('assessmentDateCalendar', 'assessmentDate', false);

new Field.Observer('evaluationType', 0.5, showETFields);

showETFields();

function showETFields() {
    $('ETNormalizedSumFields').hide();
    $('evaluationMaxValue').disable();
    if ($F('evaluationType') == 'normalized_sum') {
        $('evaluationMaxValue').enable();
        $('ETNormalizedSumFields').show();
    }
}
</script>