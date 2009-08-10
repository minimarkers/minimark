<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.*" %>
<%@ include file="../common.jspf" %>
<h1>Spring Beans</h1>

<ul>
    <c:forEach var="bean" items="${beans}"><li>${bean}</li></c:forEach>
</ul>
