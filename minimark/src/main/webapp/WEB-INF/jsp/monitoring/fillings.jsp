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
<h3><spring:message code="AssessmentFillings" text="?AssessmentFillings?"/></h3>
<a href="#" class="action action-refresh" onclick="monitoringABo.refreshAllFillingsBlocks(); return false;"><spring:message code='RefreshAll' text='?RefreshAll?'/></a><br/>
<span class="legend_started">&diams;</span> <spring:message code="Started" text="?Started?"/>
<span class="legend_completed">&diams;</span> <spring:message code="Completed" text="?Completed?"/>
<c:choose>
    <c:when test="${empty fillings}">
        <spring:message code="NoAssessmentFillings" text="?NoAssessmentFillings?"/>
    </c:when>
    <c:otherwise>
        <c:forEach var="course" items="${fillings}">
            <div class="filling_course">
                <div class="filling_header">
                    <div class="fh_title">
                        ${course.key.name}<br/><span class="smallText">${course.key.incumbent}</span>
                    </div>
                    <div class="fh_actions">
                        <a href="#" class="action action-no-text action-refresh" onclick="monitoringABo.refreshFillingsBlock(${course.key.id}); return false;" title="<spring:message code='Refresh' text='?Refresh?'/>"></a>
                    </div>
                </div>
                <h4></h4>
                <div id="course_${course.key.id}_fillings">
                    <c:set var="blockFillings" value="${course.value}"/>
                    <%@include file="fillingBlock.jsp" %>
                </div>
            </div>
        </c:forEach>
        <br style="clear: both;"/>
    </c:otherwise>
</c:choose>
