<%@ include file="common.jspf" %>
<ul id="nav">
    <li><a href="${cp}/"><spring:message code="menu_home"/></a></li>
        <authz:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a href="${cp}/admin/index.html"><spring:message code="menu_administration" text="?menu_administration?"/></a></li>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_PARANCOE">
        <li><a href="${cp}/course/list.html"><spring:message code="menu_courses" text="?menu_courses?"/></a></li>
        </authz:authorize>
        <authz:authorize ifNotGranted="ROLE_ADMIN,ROLE_PARANCOE">
        <li><a href="${cp}/login.secure"><spring:message code="Login" text="?Login?"/></a></li>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">
        <li><a href="${cp}/logout.secure"><spring:message code="Logout" text="?Logout?"/></a></li>
        </authz:authorize>
</ul>
