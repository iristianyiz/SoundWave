<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SoundWave - Delete User</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
}

.card {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	width: 100%;
	max-width: 400px;
}

.card-header {
	background-color: rgba(0, 123, 255, 0.1);
	border-bottom: 1px solid #eee;
	padding: 15px;
	color: #333;
	font-size: 1.25rem;
	font-weight: 600;
}
</style>
</head>
<body>
<div class="container d-flex justify-content-center align-items-center min-vh-100">
	<div class="card">
		<h1 class="text-center mb-4">${messages.title}</h1>
		<form action="userdelete" method="post">
			<div class="mb-3" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<label for="username" class="form-label">UserName</label>
				<input id="username" name="username" class="form-control" value="${fn:escapeXml(param.username)}" readonly>
			</div>
			<div class="d-grid" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<button type="submit" class="btn btn-danger">Delete</button>
			</div>
		</form>
	</div>
</div>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>