<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SoundWave - Create User</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
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
		<h1 class="text-center mb-4">Create User</h1>
		<form action="usercreate" method="post">
			<div class="mb-3">
				<label for="username" class="form-label">UserName</label>
				<input id="username" name="username" class="form-control" value="">
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password</label>
				<input id="password" name="password" class="form-control" type="password" value="">
			</div>
			<div class="mb-3">
				<label for="firstname" class="form-label">FirstName</label>
				<input id="firstname" name="firstname" class="form-control" value="">
			</div>
			<div class="mb-3">
				<label for="lastname" class="form-label">LastName</label>
				<input id="lastname" name="lastname" class="form-control" value="">
			</div>
			<div class="mb-3">
				<label for="email" class="form-label">Email</label>
				<input id="email" name="email" class="form-control" value="">
			</div>
			<div class="mb-3">
				<label for="phone" class="form-label">Phone</label>
				<input id="phone" name="phone" class="form-control" value="">
			</div>
			<div class="d-grid">
				<button class="btn btn-primary" type="submit">Submit</button>
			</div>
		</form>
		<p class="mt-3 text-center text-success">
			<b>${messages.success}</b>
		</p>
	</div>
</div>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>