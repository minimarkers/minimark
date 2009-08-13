<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="AssessmentFillings" text="?AssessmentFillings?"/></h3>
<a href="#" class="action-refresh" onclick="monitoringABo.refreshAllFillingsBlocks(); return false;"><spring:message code='RefreshAll' text='?RefreshAll?'/></a><br/>
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
                        <a href="#" class="action-refresh" onclick="monitoringABo.refreshFillingsBlock(${course.key.id}); return false;" title="<spring:message code='Refresh' text='?Refresh?'/>"></a>
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
