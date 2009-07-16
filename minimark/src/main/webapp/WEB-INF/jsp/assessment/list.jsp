<%@include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="YourAssessments"  text="?YourAssessments?"/></h3>

<div class="displaytag">
    <display:table  id="ca"  name="assessments" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" requestURI="${cp}/assessment/list.html" >
        <display:column property="title" titleKey="Title"/>
        <display:column property="description"  titleKey="Description"/>
        <display:column titleKey="Course">${ca.course.name}</display:column>
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
        <display:column>
            <a class="action-edit" href="${cp}/assessment/edit.html?id=${ca.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteAssessment' text='?confirmDeleteAssessment?' var="confirmDeleteAssessmentMessage"/>
            <a class="action-delete" href="${cp}/assessment/delete.html?id=${ca.id}" onclick="return confirm('${confirmDeleteAssessmentMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
            <a class="action-detail-go" href="${cp}/assessment/questions.html?id=${ca.id}"><spring:message code="Questions" text="?Questions?"/></a>
            <a class="action-detail-go" href="${cp}/results/list.html?id=${ca.id}"><spring:message code="Results" text="?Results?"/></a>
        </display:column>
    </display:table>
</div>
