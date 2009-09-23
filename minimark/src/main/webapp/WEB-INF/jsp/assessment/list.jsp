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
<%@include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="YourAssessments"  text="?YourAssessments?"/></h3>

<div class="displaytag">
    <display:table  id="ca"  name="assessments" sort="list" pagesize="20" defaultsort="3" defaultorder="descending" requestURI="${cp}/assessment/list.html" >
        <display:column property="title" titleKey="Title" class="left-aligned"/>
        <display:column property="description"  titleKey="Description" class="left-aligned"/>
        <display:column titleKey="Course" class="left-aligned">${ca.course.name}</display:column>
        <display:column titleKey="Date">
            <fmt:formatDate value="${ca.assessmentDate}" dateStyle="short"/>
        </display:column>
        <display:column titleKey="Active">
            <c:choose>
                <c:when test="${ca.active}">
                    <spring:message code="Yes" text="?Yes?"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="No" text="?No?"/>
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="InHomePage">
            <c:choose>
                <c:when test="${ca.showInHomePage}">
                    <spring:message code="Yes" text="?Yes?"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="No" text="?No?"/>
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column>
            <a class="action action-edit" href="${cp}/assessment/edit.html?id=${ca.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <a class="action action-copy" href="${cp}/assessment/copy.html?id=${ca.id}"><spring:message code="Copy" text="?Copy?"/></a>
            <spring:message code='confirmDeleteAssessment' text='?confirmDeleteAssessment?' var="confirmDeleteAssessmentMessage"/>
            <a class="action action-delete" href="${cp}/assessment/delete.html?id=${ca.id}" onclick="return confirm('${confirmDeleteAssessmentMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
            <a class="action action-detail-go" href="${cp}/assessment/questions.html?id=${ca.id}"><spring:message code="Questions" text="?Questions?"/></a>
            <a class="action action-detail-go" href="${cp}/results/list.html?id=${ca.id}"><spring:message code="Results" text="?Results?"/></a>
        </display:column>
    </display:table>
</div>
