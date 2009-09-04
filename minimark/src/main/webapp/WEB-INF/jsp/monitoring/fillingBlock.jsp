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
<c:forEach var="filling" items="${blockFillings}">
    <div class="filling filling_${filling.currentState}">
        <div class="af_data">
            <c:choose>
                <c:when test="${!empty filling.startDate}">
                    <div class="af_student">${filling.lastName} ${filling.firstName}</div>
                    <div class="af_assessment smallText">${filling.assessment.title}</div>
                </c:when>
                <c:otherwise>
                    <div class="af_message"><spring:message code="NotStartedYet" text="?NotStartedYet?"/>...</div>
                    <div class="af_assessment smallText">${filling.assessment.title}</div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="af_actions">
            <c:choose>
                <c:when test="${filling.currentState == 'confirmed'}">
                    <a href="#" class="action action-no-text action-cancel" onclick="monitoringABo.unconfirmAssessmentFilling(${filling.id}, ${filling.assessment.course.id}); return false;" title="<spring:message code='Unconfirm' text='?Unconfirm?'/>"></a>
                </c:when>
                <c:otherwise>
                    <c:if test="${filling.currentState != 'not_started'}">
                        <a href="#" class="action action-no-text action-confirm" onclick="monitoringABo.confirmAssessmentFilling(${filling.id}, ${filling.assessment.course.id}); return false;" title="<spring:message code='Confirm' text='?Confirm?'/>"></a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:forEach>
