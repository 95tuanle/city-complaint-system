<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Customer - View complaint</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../index">List complaints</a>
<a href="../../user/sign-out">Sign out</a>
<h1>Customer - View complaint</h1>
<table border="">
    <tr>
        <td>Id</td>
        <td>${complaint.id}</td>
    </tr>
    <tr>
        <td>Customer</td>
        <td>${complaint.customer.firstName}</td>
    </tr>
    <tr>
        <td>Employee</td>
        <td>${complaint.employee.firstName}</td>
    </tr>
    <tr>
        <td>Title</td>
        <td>${complaint.title}</td>
    </tr>
    <tr>
        <td>Description</td>
        <td>${complaint.description}</td>
    </tr>
    <tr>
        <td>Date</td>
        <td>${complaint.date}</td>
    </tr>
</table>
<h2>Replies</h2>
<table border="">
    <tr>
        <td>Description</td>
        <td>User</td>
        <td>Date</td>
    </tr>
    <c:forEach items="${replies}" var="reply">
        <tr>
            <td>${reply.description}</td>
            <td>${reply.user.email}</td>
            <td>${reply.date}</td>
            <td><a href="reply/${reply.id}/delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<h2>Reply</h2>
<form:form method="post" action="${complaint.id}/reply" modelAttribute="reply">
    <table>
        <tr>
            <td>Description:</td>
            <td><form:input path="description" type="text" required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Reply"/></td>
        </tr>
    </table>
</form:form>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">
</body>
</html>
