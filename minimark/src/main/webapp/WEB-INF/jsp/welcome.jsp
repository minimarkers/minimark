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
<%@ include file="common.jspf" %>
<h3><spring:message code="Teachers" text="?Teachers?"/></h3>
<c:forEach var="teacher" items="${teachers}">
    <div class="teacher">
        <a href="${cp}/user/page.html?username=${teacher.user.username}">${teacher.name}</a>
    </div>
</c:forEach>
<h3><spring:message code="AvailableAssessments" text="?AvailableAssessments?"/></h3>
<c:choose>
    <c:when test="${empty assessments}">
        <spring:message code="NoActiveAssessments" text="?NoActiveAssessments?"/>
    </c:when>
    <c:otherwise>
        <c:forEach var="course" items="${assessments}">
            <h4>${course.key.name} (${course.key.incumbent})</h4>
            <c:forEach var="assessment" items="${course.value}">
                <div class="assessment">
                    <div class="course">${assessment.course.name}</div>
                    <div class="title">${assessment.title}</div>
                    <div class="description">${assessment.description}</div>
                    <c:if test="${!empty assessment.course.incumbent}">
                        <div class="incumbent">${assessment.course.incumbent}</div>
                    </c:if>
                    <div class="actions">
                        <a href="${cp}/assessmentFilling/logon.html?id=${assessment.id}" class="action action-start"><spring:message code="start" text="?start?"/></a>
                    </div>
                </div>
            </c:forEach>
            <br style="clear: both;"/>
        </c:forEach>
    </c:otherwise>
</c:choose>
