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
<spring:message code="Yes" text="?Yes?" var="yesLabel"/>
<spring:message code="No" text="?No?" var="noLabel"/>
<form:form commandName="assessment" method="POST" action="${cp}/assessment/save.html">
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label><form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="description" cssClass="errorBox"/>
    <form:label path="description"><spring:message code="Description" text="?Description?"/>:</form:label><form:textarea path="description" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <form:errors path="assessmentDate" cssClass="errorBox"/>
    <form:label path="assessmentDate"><spring:message code="Date" text="?Date?"/>:</form:label><form:input path="assessmentDate" maxlength="10" size="10" cssErrorClass="fieldInError full-size"/><img id="assessmentDateCalendar" src="${cp}/images/silk/icons/calendar.png" alt="Calendar icon" class="calendar-icon"/>&nbsp;<span class="form-help-text">(dd/MM/yyyy)</span>&nbsp;*<br/>
    <form:errors path="duration" cssClass="errorBox"/>
    <form:label path="duration"><spring:message code="Duration" text="?Duration?"/>:</form:label><form:input path="duration" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.durationField" text="?help.durationField?"/></span></div>
    <form:errors path="active" cssClass="errorBox"/>
    <form:label path="active"><spring:message code="Active" text="?Active?"/>:</form:label>
    <form:select path="active">
        <form:option value="true" label="${yesLabel}"/>
        <form:option value="false" label="${noLabel}"/>
    </form:select><br/>
    <form:errors path="showInHomePage" cssClass="errorBox"/>
    <form:label path="showInHomePage"><spring:message code="InHomePage" text="?InHomePage?"/>:</form:label>
    <form:select path="showInHomePage">
        <form:option value="true" label="${yesLabel}"/>
        <form:option value="false" label="${noLabel}"/>
    </form:select><br/>
    <form:errors path="shuffleQuestions" cssClass="errorBox"/>
    <form:label path="shuffleQuestions"><spring:message code="ShuffleQuestions" text="?ShuffleQuestions?"/>:</form:label>
    <form:select path="shuffleQuestions">
        <form:option value="false" label="${noLabel}"/>
        <form:option value="true" label="${yesLabel}"/>
    </form:select><br/>
    <form:errors path="evaluationType" cssClass="errorBox"/>
    <form:label path="evaluationType"><spring:message code="EvaluationType" text="?EvaluationType?"/>:</form:label>
    <form:select path="evaluationType">
        <spring:message code="ETNormalizedSum" text="?ETNormalizedSum?" var="ETNormalizedSumLabel"/>
        <form:option value="normalized_sum" label="${ETNormalizedSumLabel}"/>
        <spring:message code="ETSimpleSum" text="?ETSimpleSum?" var="ETSimpleSumLabel"/>
        <form:option value="simple_sum" label="${ETSimpleSumLabel}"/>
    </form:select><br/>
    <div id="ETNormalizedSumFields">
        <form:errors path="evaluationMaxValue" cssClass="errorBox"/>
        <form:label path="evaluationMaxValue"><spring:message code="EvaluationMaxValue" text="?EvaluationMaxValue?"/>:</form:label><form:input path="evaluationMaxValue" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    </div>
    <form:errors path="evaluationClosedType" cssClass="errorBox"/>
    <form:label path="evaluationClosedType"><spring:message code="EvaluationClosedType" text="?EvaluationClosedType?"/>:</form:label>
    <form:select path="evaluationClosedType">
        <spring:message code="ETCSumCorrectMinusWrongAnswers" text="?ETCSumCorrectMinusWrongAnswers?" var="ETCSumCorrectMinusWrongAnswersLabel"/>
        <form:option value="sum_correct_answers" label="${ETCSumCorrectMinusWrongAnswersLabel}"/>
        <spring:message code="ETCSumCorrectAnswers" text="?ETCSumCorrectAnswers?" var="ETCSumCorrectAnswersLabel"/>
        <form:option value="sum_correct_answers" label="${ETCSumCorrectAnswersLabel}"/>
    </form:select><br/>
    <form:errors path="evaluationClosedMinimumEvaluation" cssClass="errorBox"/>
    <form:label path="evaluationClosedMinimumEvaluation"><spring:message code="EvaluationClosedMinimumEvaluation" text="?EvaluationClosedMinimumEvaluation?"/>:</form:label><form:input path="evaluationClosedMinimumEvaluation" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="allowStudentPrint" cssClass="errorBox"/>
    <form:label path="allowStudentPrint"><spring:message code="StudentPrint" text="?StudentPrint?"/>:</form:label>
    <form:select path="allowStudentPrint">
        <form:option value="false" label="${noLabel}"/>
        <form:option value="true" label="${yesLabel}"/>
    </form:select><br/>
    <form:errors path="exposedResult" cssClass="errorBox"/>
    <form:label path="exposedResult"><spring:message code="ExposedResult" text="?ExposedResult?"/>:</form:label>
    <form:select path="exposedResult">
        <spring:message code="ERNone" text="?ERNone?" var="ERNoneLabel"/>
        <form:option value="none" label="${ERNoneLabel}"/>
        <spring:message code="ERValue" text="?ERValue?" var="ERValueLabel"/>
        <form:option value="value" label="${ERValueLabel}"/>
        <spring:message code="ERPassed" text="?ERPassed?" var="ERPassedLabel"/>
        <form:option value="passed" label="${ERPassedLabel}"/>
    </form:select><br/>
    <div id="ERPassedFields">
        <form:errors path="minPassedValue" cssClass="errorBox"/>
        <form:label path="minPassedValue"><spring:message code="MinPassedValue" text="?MinPassedValue?"/>:</form:label><form:input path="minPassedValue" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    </div>
    <form:errors path="newPassword" cssClass="errorBox"/>
    <form:label path="newPassword"><spring:message code="Password" text="?Password?"/>:</form:label><form:password path="newPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="confirmPassword" cssClass="errorBox"/>
    <form:label path="confirmPassword"><spring:message code="ConfirmPassword" text="?ConfirmPassword?"/>:</form:label><form:password path="confirmPassword" showPassword="true" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="formButtons">
        <a href="list.html" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>

<script type="text/javascript">
attachCalendar('assessmentDateCalendar', 'assessmentDate', false);

new Field.Observer('evaluationType', 0.5, showETFields);
new Field.Observer('exposedResult', 0.5, showERFields);

showETFields();
showERFields();

function showETFields() {
    $('ETNormalizedSumFields').hide();
    $('evaluationMaxValue').disable();
    if ($F('evaluationType') == 'normalized_sum') {
        $('evaluationMaxValue').enable();
        $('ETNormalizedSumFields').show();
    }
}

function showERFields() {
    $('ERPassedFields').hide();
    $('minPassedValue').disable();
    if ($F('exposedResult') == 'passed') {
        $('minPassedValue').enable();
        $('ERPassedFields').show();
    }
}

</script>