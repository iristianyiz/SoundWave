<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SoundWave - Album Details</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	padding-top: 20px;
}

.search-section, .results-section, .card {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	margin-bottom: 30px;
	overflow: hidden;
}

.pagination-container {
	display: flex;
	align-items: center;
	justify-content: center;
}

.pagination-container input {
	width: 60px;
	text-align: center;
}

.star-rating {
	color: #ffc107;
}

.card-header {
	background-color: rgba(0, 123, 255, 0.1);
	border-bottom: 1px solid #eee;
	margin: -20px -20px 20px -20px;
	padding: 15px 20px;
}

.card-title {
	margin-bottom: 0;
	color: #333;
	font-size: 1.25rem;
	font-weight: 600;
}
</style>
</head>
<body>
	<div class="container">
		<h1 class="text-center mb-4">Album Details</h1>

		<!-- Track Information Section -->
		<div class="card">
			<div class="card-header">
				<h2 class="card-title">Album Information</h2>
			</div>
			<h3 class="mt-4 mb-3">${album.getAlbumName()}</h3>
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Track Name</th>
								<th>Popularity</th>
								<th>Explicit</th>
								<th>Duration</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tracks}" var="track">
								<tr>
									<td><c:out value="${track.getTrackName()}" /></td>
									<td><span class="star-rating"> <c:forEach begin="1"
												end="5" var="i">
												<c:choose>
													<c:when test="${i <= track.getPopularity() / 20}">&#9733;</c:when>
													<c:otherwise>&#9734;</c:otherwise>
												</c:choose>
											</c:forEach>
									</span></td>
									<td><c:choose>
											<c:when test="${track.isExplicit()}">
												<span class="badge bg-danger">Explicit</span>
											</c:when>
											<c:otherwise>
												<span class="badge bg-success">Clean</span>
											</c:otherwise>
										</c:choose></td>
									<td><fmt:formatNumber
											value="${track.getDurationMs() / 60000}" pattern="#0" />:<fmt:formatNumber
											value="${track.getDurationMs() % 60000 / 1000}"
											pattern="00" /></td>
									<td><a
										href="trackdetails?trackid=<c:out value="${track.getTrackId()}"/>"
										class="btn btn-primary btn-sm">View Details</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
		</div>

		
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
