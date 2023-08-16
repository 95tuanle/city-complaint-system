<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../user/sign-in">Sign in</a>
<h1>Sign Up</h1>
<form:form method="post" action="sign-up" modelAttribute="user">
    <table>
        <tr>
            <td>Email:</td>
            <td><form:input path="email" type="email" required="required"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="password" type="password" required="required"/></td>
        </tr>
        <tr>
            <td>Type:</td>
            <td>
                <form:select path="type" required="required">
                    <form:option value="customer">Customer</form:option>
                    <form:option value="admin">Admin</form:option>
                    <form:option value="employee">Employee</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Sign Up"/></td>
        </tr>
    </table>
</form:form>
<br>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">
</body>
</html>
