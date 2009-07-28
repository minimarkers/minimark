<%@include file="/WEB-INF/jsp/common.jspf" %>
<h3><spring:message code="Questions"  text="?Questions?"/></h3>

<p>${course.name}</p>
<p>${course.description}</p>

<div>
    <a class="action-add" href="${cp}/question/create.html?courseId=${course.id}"><spring:message code="addQuestion" text="?addQuestion?"/></a>
</div>

<div class="displaytag">
    <display:table  id="curr"  name="questions" sort="list" pagesize="20" defaultsort="1" defaultorder="ascending" requestURI="${cp}/question/list.html">
        <display:column titleKey="Type">
            <c:choose>
                <c:when test="${curr.class.name == 'com.benfante.minimark.po.OpenQuestion'}">                    
                    <c:choose>
                        <c:when test="${curr.visualization == 'short'}">
                            <img src="${cp}/images/silk/icons/textfield.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="short" text="?short?"/>)" />
                        </c:when>
                        <c:when test="${curr.visualization == 'long'}">
                            <img src="${cp}/images/silk/icons/textfield_add.png" title="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" alt="<spring:message code="OpenQuestion" text="?OpenQuestion?"/> (<spring:message code="long" text="?long?"/>)" />
                        </c:when>
                        <c:otherwise><spring:message code="UnknownVisualization" text="?UnknownVisualization?"/></c:otherwise>
                    </c:choose>
                </c:when>
                <c:when test="${curr.class.name == 'com.benfante.minimark.po.ClosedQuestion'}">
                    <c:choose>
                        <c:when test="${curr.multipleAnswer}">
                            <img src="${cp}/images/silk/icons/text_list_numbers.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="multiple" text="?multiple?"/>)" />
                        </c:when>
                        <c:otherwise>
                            <img src="${cp}/images/silk/icons/text_list_bullets.png" title="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" alt="<spring:message code="ClosedQuestion" text="?ClosedQuestion?"/> (<spring:message code="single" text="?single?"/>)" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise><spring:message code="UnknownQuestionType" text="?UnknownQuestionType?"/></c:otherwise>
            </c:choose>
        </display:column>
        <display:column property="title" titleKey="Title" class="left-aligned"/>
        <display:column property="filteredContent"  titleKey="Content" class="left-aligned"/>
        <display:column property="weight"  titleKey="Weight"/>
        <display:column property="tagList"  titleKey="Tags" class="left-aligned"/>
        <display:column>
            <a class="action-edit" href="${cp}/question/edit.html?id=${curr.id}"><spring:message code="Edit" text="?Edit?"/></a>
            <spring:message code='confirmDeleteQuestion' text='?confirmDeleteQuestion?' var="confirmDeleteQuestionMessage"/>
            <a class="action-delete" href="${cp}/question/delete.html?id=${curr.id}" onclick="return confirm('${confirmDeleteQuestionMessage}')"><spring:message code="Delete" text="?Delete?"/></a>
        </display:column>
    </display:table>
</div>
