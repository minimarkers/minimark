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
<h3><spring:message code="AssessmentQuestions" text="?AssessmentQuestions?"/></h3>
<div class="summary">
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
    <form:input path="title" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
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
    <form:input path="weight" maxlength="5" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="tags" cssClass="errorBox"/>
    <form:label path="tags"><spring:message code="Tags" text="?Tags?"/>:</form:label>
    <form:input path="tags" maxlength="255" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <div class="form-aligned form-help-text"><span><spring:message code="help.tagListField" text="?tagListField?"/></span></div>
    <div class="formButtons">
        <a href="list.html?courseId=${assessment.course.id}" class="action action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Search' text='?Search?'/>" class="submit-button"/>
    </div>
</form:form>

<div>
    <div id="searchResult" class="midWidth smallText">
        <%@include file="questionSearchResult.jsp" %>
    </div>
    <div id="assessmentQuestions" class="midWidth smallText">
        <%@include file="assessmentQuestions.jsp" %>
    </div>
</div>

<div id="statusBar">
    <div id="statusBar_content">
        <div id="statusBar_left">
            <div class="statusBar_question">
                <spring:message code="OpenShortQuestions" text="?OpenShortQuestions?"/>: <span id="OpenShortQuestionsCount">${assessment.countOpenShortQuestions}</span>
            </div>
            <div class="statusBar_question">
                <spring:message code="OpenLongQuestions" text="?OpenLongQuestions?"/>: <span id="OpenLongQuestionsCount">${assessment.countOpenLongQuestions}</span>
            </div>
            <div class="statusBar_question">
                <spring:message code="ClosedSingleQuestions" text="?ClosedSingleQuestions?"/>: <span id="ClosedSingleQuestionsCount">${assessment.countClosedSingleQuestions}</span>
            </div>
            <div class="statusBar_question">
                <spring:message code="ClosedMultiQuestions" text="?ClosedMultiQuestions?"/>: <span id="ClosedMultiQuestionsCount">${assessment.countClosedMultiQuestions}</span>
            </div>
            <div class="statusBar_question">
                <spring:message code="TotalQuestions" text="?TotalQuestions?"/>: <span id="TotalQuestionsCount">${assessment.countAllQuestions}</span>
            </div>
            <div class="statusBar_question">
                <spring:message code="TotalWeight" text="?TotalWeight?"/>: <span id="TotalWeightSum">${assessment.questionsTotalWeight}</span>
            </div>
        </div>
        <div id="statusBar_right">
            <div id="duplicatedQuestionsWarning" class="statusBar_warning" <c:if test="${!assessment.containsDuplicatedQuestions}">style="display: none;"</c:if>><spring:message code="status.warning.duplicated"  text="?status.warning.duplicated?"/></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function searchQuestions() {
        questionABo.updateQuestionSearchResult({
            type: $F('type'),
            visualization: $F('visualization'),
            title: $F('title'),
            weight: $F('weight'),
            tags: $F('tags'),
            course: {id: ${assessment.course.id}}
        });
    }

    function addQuestion(id) {
        questionABo.addQuestionToAssessment(id, ${assessment.id});
    }

    function removeQuestion(id) {
        questionABo.removeQuestionFromAssessment(id, ${assessment.id});
    }

    function moveUpQuestion(id) {
        questionABo.moveUpQuestionInAssessment(id, ${assessment.id});
    }

    function moveDownQuestion(id) {
        questionABo.moveDownQuestionInAssessment(id, ${assessment.id});
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