<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../user/sign-up">Sign up</a>
<h1>Sign In</h1>
<form:form method="post" action="sign-in" modelAttribute="user">
    <table>
        <tr>
            <td>Email:</td>
            <td><form:input path="email" type="text" required="required"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="password" type="password" required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Sign In"/></td>
        </tr>
    </table>
</form:form>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">
</body>
</html>
