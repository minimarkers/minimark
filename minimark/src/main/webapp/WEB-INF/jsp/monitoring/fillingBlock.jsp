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
                    <a href="#" onclick="monitoringABo.unconfirmAssessmentFilling(${filling.id}, ${filling.assessment.course.id}); return false;">U</a>
                </c:when>
                <c:otherwise>
                    <c:if test="${filling.currentState != 'not_started'}">
                    <a href="#" onclick="monitoringABo.confirmAssessmentFilling(${filling.id}, ${filling.assessment.course.id}); return false;">C</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:forEach>
