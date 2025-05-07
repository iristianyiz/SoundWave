<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Playlist: ${playlist.getName()}</title>
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

.playlist-card {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
	margin-bottom: 30px;
}

.table-responsive {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
	padding: 20px;
	margin-bottom: 30px;
	max-height: 600px;
	overflow-y: auto;
}

.section-title {
	color: #333;
	font-size: 1.25rem;
	font-weight: 600;
	margin: -20px -20px 20px -20px;
	padding: 10px 20px;
	background-color: rgba(0, 123, 255, 0.1);
	border-bottom: 1px solid #eee;
}

.star-rating {
	color: #ffc107;
}

.table-hover tbody tr:hover {
	background-color: rgba(0, 0, 0, .03);
}
</style>
</head>
<body>
	<div class="container">
		<div class="playlist-card">
			<h1 class="mb-4">Playlist: ${playlist.getName()}</h1>
			<p>
				<strong>Description:</strong> ${playlist.getDescription()}
			</p>
			<p>
				<strong>Created:</strong>
				<fmt:formatDate value="${playlist.getCreated()}"
					pattern="MMMM d, yyyy" />
			</p>
			<p>
				<strong>Visibility:</strong> ${playlist.isIsPublic() ? 'Public' : 'Private'}
			</p>
			<p>
				<strong>Total Tracks:</strong> ${fn:length(tracks)}
			</p>
		</div>

		<div class="table-responsive">
			<h2 class="section-title">Tracks</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Track Name</th>
						<th>Duration</th>
						<th>Popularity</th>
						<th>Explicit</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tracks}" var="track" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><c:out value="${track.getTrackName()}" /></td>
							<td><fmt:formatNumber
									value="${track.getDurationMs() / 60000}" pattern="#0" />: <fmt:formatNumber
									value="${track.getDurationMs() % 60000 / 1000}" pattern="00" />
							</td>
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
							<td><a href="trackdetails?trackid=${track.getTrackId()}"
								class="btn btn-primary btn-sm"> <i
									class="fas fa-info-circle"></i> Details
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
