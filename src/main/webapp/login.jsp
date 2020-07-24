<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%!
    int i = 0;
%>
<html>
<body>
Welcome to IntelliJ IDEA
${ (info['version'] le 12 )? "Evaluate latest version":"" }
Release timestamp: ${ info.getReleaseDate(majorVersion, minorVersion).time }
<% for (i = 1; i < 5; i++) { %>
<h<%= i %>>Try it, it's cool!</h<%= i %>>
<% } %>
<from>
    account；<input type="text" name="account">
    password：<input type="text" name="password">
    <input type="button" name="提交">
</from>
</body>
</html>
<!-- HTML comments are seen by clients, JSP aren't -->
