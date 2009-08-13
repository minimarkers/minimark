<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="AssessmentFillings" text="?AssessmentFillings?"/></h3>
<c:choose>
    <c:when test="${empty fillings}">
        <spring:message code="NoAssessmentFillings" text="?NoAssessmentFillings?"/>
    </c:when>
    <c:otherwise>
        <c:forEach var="course" items="${fillings}">
            <div class="filling_course">
                <h4>${course.key.name}</h4>
                <div id="course_${course.key.id}_fillings">
                    <c:set var="blockFillings" value="${course.value}"/>
                    <%@include file="fillingBlock.jsp" %>
                </div>
            </div>
        </c:forEach>
        <br style="clear: both;"/>
    </c:otherwise>
</c:choose>
