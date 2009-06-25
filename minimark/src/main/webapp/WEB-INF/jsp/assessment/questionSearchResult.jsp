<%@ include file="/WEB-INF/jsp/common.jspf" %>
<h4><spring:message code="AvailableQuestions" text="?AvailableQuestions?"/></h4>
<spring:message code="Type" text="?Type?" var="TypeTitle"/>
<spring:message code="Title" text="?Title?" var="TitleTitle"/>
<spring:message code="Content" text="?Content?" var="ContentTitle"/>
<spring:message code="Weight" text="?Weight?" var="WeightTitle"/>
<spring:message code="Tags" text="?Tags?" var="TagsTitle"/>
<display:table id="curr"  name="questionSearchResult" sort="list" defaultsort="1" defaultorder="ascending" >
    <display:column title="${TypeTitle}">
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
    <display:column property="title" title="${TitleTitle}"/>
    <display:column property="filteredContent"  title="${ContentTitle}"/>
    <display:column property="weight"  title="${WeightTitle}"/>
    <display:column property="tagList"  title="${TagsTitle}"/>
    <display:column>
        <a class="action-right" href="#" onclick="addQuestion(${curr.id}); return false;" title="<spring:message code="Add" text="?Add?"/>"></a>
    </display:column>
</display:table>
