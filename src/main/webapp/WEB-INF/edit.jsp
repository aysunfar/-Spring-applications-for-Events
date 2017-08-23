<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">

<title></title>
<style>
	h1 {
	text-align: center;
	}

	.container {
    max-width: 700px;
    padding: 20px;
    display: inline-block;
    margin-left: 100px;
	}
	
	.delete {
	display: block;
	margin-top: 20px;
	font-family: Georgia, "Times New Roman", Times, serif;

	}
	
	h3 {
	margin-left:200px;
	margin-top: 40px;
	
	}
</style>
</head>
<body>
	<p><a href="/events">All events</a></p>
	<h1>${event.name}</h1>
	<h3>Edit Event</h3>
	<div class="container">
	<p><form:errors path="event.*"/></p>
	<form:form method="POST" action="/events/${id}/edit" modelAttribute="event">
        <p>
            <form:label path="name">Name:</form:label>
            <form:input path="name"/>
        </p>
        <p>
            <form:label path="date">Date:</form:label>
            <form:input type="date" path="date"/>
        </p>
        <p>
            <form:label path="city">Location:</form:label>
            <form:input path="city"/>
            <form:select path="state" items="${states}"/>
        </p>
        <input type="submit" value="Update Event"/><br>
        <div class="delete">
        <input type="submit" value="Delete Event"/>
        </div>
    </form:form>
</div>
</body>
</html>