<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin - Sign up</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../user/sign-in">Sign in</a>
<h1>Admin - Sign up</h1>
<form:form method="post" action="sign-up" modelAttribute="admin">
    <table >
        <tr>
            <td>First name: </td>
            <td><form:input path="firstName" type="text" required="required"/></td>
        </tr>
        <tr>
            <td>Last name: </td>
            <td><form:input path="lastName" type="text" required="required"/></td>
        </tr>
        <tr>
            <td>Phone: </td>
            <td><form:input path="phone" type="tel" required="required"/></td>
        </tr>
        <tr>
            <td>Address: </td>
            <td><form:input path="address" type="text" required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Sign Up"/></td>
        </tr>
    </table>
</form:form>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">

</body>
</html>
