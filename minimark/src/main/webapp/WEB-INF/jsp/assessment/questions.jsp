<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="AssessmentQuestions" text="?AssessmentQuestions?"/></h3>
<div>
    <p><spring:message code="Assessment" text="?Assessment?"/>: ${assessment.title}</p>
</div>
<form:form commandName="questionSearch" method="post" action="#" onsubmit="searchQuestions(); return false;">
    <form:errors path="type" cssClass="errorBox"/>
    <form:label path="type"><spring:message code="Type" text="?Type?"/>:</form:label>
    <spring:message code='OpenQuestion' text='?OpenQuestion?' var="OpenQuestionLabel"/>
    <spring:message code='ClosedQuestion' text='?ClosedQuestion?' var="ClosedQuestionLabel"/>
    <form:select path="type" onchange="showQuestionFields();">
        <form:option value="" label=""/>
        <form:option value="open" label="${OpenQuestionLabel}"/>
        <form:option value="closed" label="${ClosedQuestionLabel}"/>
    </form:select><br/>
    <form:errors path="title" cssClass="errorBox"/>
    <form:label path="title"><spring:message code="Title" text="?Title?"/>:</form:label>
    <form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <div id="openFields">
        <form:errors path="visualization" cssClass="errorBox"/>
        <form:label path="visualization"><spring:message code="Visualization" text="?Visualization?"/>:</form:label>
        <form:select path="visualization">
            <form:option value="" label=""/>
            <spring:message code='ShortVisualization' text='?ShortVisualization?' var="ShortVisualizationLabel"/>
            <form:option value="short" label="${ShortVisualizationLabel}"/>
            <spring:message code='LongVisualization' text='?LongVisualization?' var="LongVisualizationLabel"/>
            <form:option value="long" label="${LongVisualizationLabel}"/>
        </form:select><br/>
    </div>
    <form:errors path="weight" cssClass="errorBox"/>
    <form:label path="weight"><spring:message code="Weight" text="?Weight?"/>:</form:label>
    <form:input path="weight" maxlength="5" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <form:errors path="tags" cssClass="errorBox"/>
    <form:label path="tags"><spring:message code="Tags" text="?Tags?"/>:</form:label>
    <form:input path="tags" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/>&nbsp;*<br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.tagListField" text="?tagListField?"/></span></div>
    <div class="formButtons">
        <a href="list.html?courseId=${assessment.course.id}" class="action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Search' text='?Search?'/>" class="submit-button"/>
    </div>
</form:form>

<div>
    <div id="searchResult" class="midWidth">
        <%@include file="questionSearchResult.jsp" %>
    </div>
    <div id="assessmentQuestions" class="midWidth">
        <%@include file="assessmentQuestions.jsp" %>
    </div>
</div>

<script type="text/javascript">
    function searchQuestions() {
        questionABo.updateQuestionSearchResult({
            type: $F('type'),
            course: {id: ${assessment.course.id}}
        });
    }

    function addQuestion(id) {
        questionABo.addQuestionToAssessment(id, ${assessment.id});
    }

    function showQuestionFields() {
        hideVariableQuestionFields();
        if ($('type').value == 'open') {
            $('openFields').show();
        } else if ($('type').value == 'closed') {
            //            $('fixedAnswers').show();
        }
    }

    function hideVariableQuestionFields() {
        $('openFields').hide();
        //        $('fixedAnswers').hide();
    }

    dwr.util.setEscapeHtml(false);

    showQuestionFields();

</script>