<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Index</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/style.css"/>">
</head>
<body>
<a href="../user/sign-out">Sign out</a>
<h1>Admin - Index</h1>
<table border="">
    <tr>
        <td>Id</td>
        <td>Customer</td>
        <td>Employee</td>
        <td>Title</td>
        <td>Description</td>
        <td>Date</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${complaints}" var="complaint">
        <tr>
            <td>${complaint.id}</td>
            <td>${complaint.customer.firstName}</td>
            <td>${complaint.employee.firstName}</td>
            <td>${complaint.title}</td>
            <td>${complaint.description}</td>
            <td>${complaint.date}</td>
            <td><a href="complaint/${complaint.id}">View</a></td>
            <td><a href="../complaint/delete/${complaint.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<img src="<c:url value="/resources/images/dummy-image.jpg"/>" alt="">
</body>
</html>
