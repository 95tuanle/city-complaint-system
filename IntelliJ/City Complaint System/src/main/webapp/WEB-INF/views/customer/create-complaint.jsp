<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer - Create complaint</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../customer/index">List complaints</a>
<a href="../user/sign-out">Sign out</a>
<h1>Customer - Create complaint</h1>
<form:form method="post" action="create" modelAttribute="complaint">
    <table>
        <tr>
            <td>Title:</td>
            <td><form:input path="title" type="text" required="required"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><form:input path="description" type="text" required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Create"/></td>
        </tr>
    </table>
</form:form>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">
</body>
</html>
