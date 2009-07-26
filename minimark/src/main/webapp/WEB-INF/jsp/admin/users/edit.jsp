<%@ include file="../../common.jspf" %>
<h3><spring:message code="UserProfile" text="?UserProfile?"/></h3>
<form:form commandName="userBean" method="POST" action="${cp}/admin/users/save.form">
    <form:errors path="user.username" cssClass="errorBox"/>
    <form:label path="user.username"><spring:message code="Username" text="?Username?"/>:</form:label><form:input path="user.username" cssClass="full-size" cssErrorClass="fieldInError full-size"/>*<br/>
    <form:errors path="userProfile.name" cssClass="errorBox"/>
    <form:label path="userProfile.name"><spring:message code="Name" text="?Name?"/>:</form:label><form:input path="userProfile.name" cssClass="full-size" cssErrorClass="fieldInError full-size"/>*<br/>
    <form:errors path="userProfile.email" cssClass="errorBox"/>
    <form:label path="userProfile.email"><spring:message code="Email" text="?Email?"/>:</form:label><form:input path="userProfile.email" cssClass="full-size" cssErrorClass="fieldInError full-size"/>*<br/>
    <form:errors path="userProfile.site" cssClass="errorBox"/>
    <form:label path="userProfile.site"><spring:message code="Site" text="?Site?"/>:</form:label><form:input path="userProfile.site" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="newPassword" cssClass="errorBox"/>
    <form:label path="newPassword"><spring:message code="NewPassword" text="?NewPassword?"/>:</form:label><form:password path="newPassword" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <form:errors path="confirmPassword" cssClass="errorBox"/>
    <form:label path="confirmPassword"><spring:message code="ConfirmPassword" text="?ConfirmPassword?"/>:</form:label><form:password path="confirmPassword" cssClass="full-size" cssErrorClass="fieldInError full-size"/><br/>
    <h3><spring:message code="Authorities" text="?Authorities?"/></h3>
    <form:errors path="authorityCheckBoxes" cssClass="errorBox"/>
    <c:forEach items="${userBean.authorityCheckBoxes}" var="authorityCheckBox" varStatus="loopStatus">
        <div class="form-aligned">
            <spring:bind path="userBean.authorityCheckBoxes[${loopStatus.index}].checked">
                <input type="hidden" name="_<c:out value="${status.expression}"/>">
                <input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked</c:if>/>&nbsp;${authorityCheckBox.authority.role}
            </spring:bind><br/>
        </div>
    </c:forEach>
    <div class="formButtons">
        <a href="${cp}/admin/users/list.html" class="action-back"><spring:message code="Back" text="?Back?"/></a>&nbsp;&nbsp;<input type="submit" value="<spring:message code='Save' text='?Save?'/>" class="submit-button"/>
    </div>
</form:form>
