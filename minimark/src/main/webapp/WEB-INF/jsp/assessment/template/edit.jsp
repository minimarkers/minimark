<%@ include file="/WEB-INF/jsp/common.jspf" %>
<div class="summary">
    <p><spring:message code="Course" text="?Course?"/>: ${assessmentTemplate.course.name}</p>
</div>
<h3><spring:message code="AssessmentTemplate" text="?AssessmentTemplate?"/></h3>
<spring:message code="Yes" text="?Yes?" var="yesLabel"/>
<spring:message code="No" text="?No?" var="noLabel"/>
<form:form commandName="assessmentTemplate" method="POST" action="${cp}/assessment/template/save.html">
    <form:hidden path="course.id"/>
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label><form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="description" cssClass="errorBox"/>
    <form:label path="description"><spring:message code="Description" text="?Description?"/>:</form:label><form:textarea path="description" cols="25" rows="5" cssErrorClass="fieldInError"/>&nbsp;*<br/>
    <form:errors path="duration" cssClass="errorBox"/>
    <form:label path="duration"><spring:message code="Duration" text="?Duration?"/>:</form:label><form:input path="duration" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.durationField" text="?help.durationField?"/></span></div>
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
    <form:label path="tagSelectors"><spring:message code="Questions" text="?Questions?"/></form:label><br/>
    <c:forEach var="questionRequest" items="${assessmentTemplate.questionRequests}" varStatus="loopStatus">
        <form:label path="questionRequests[${loopStatus.index}].tag"><spring:message code="Tag" text="?Tag?"/></form:label>
        <form:select path="questionRequests[${loopStatus.index}].tag" cssStyle="width: 150px;">
            <form:option value="" label=""/>
            <form:options items="${tags}"/>
        </form:select>
        <form:label path="questionRequests[${loopStatus.index}].howMany" cssStyle="width: inherit; margin-left: 10px;"><spring:message code="HowMany" text="?HowMany?"/></form:label><form:input path="questionRequests[${loopStatus.index}].howMany" size="3"/>
        <br/>
    </c:forEach>
    <div class="formButtons">
        <a href="${cp}/course/list.html" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='CreateAssessment' text='?CreateAssessment?'/>" class="submit-button"/>
    </div>
</form:form>

<script type="text/javascript">

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