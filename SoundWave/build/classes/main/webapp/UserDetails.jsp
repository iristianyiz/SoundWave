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
<title>SoundWave - Your Musical Journey</title>
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

.profile-card, .section {
	background-color: #ffffff;
	border-radius: 15px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
	padding: 20px;
	margin-bottom: 30px;
	overflow: hidden;
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

.table {
	margin-bottom: 0;
}

.btn {
	border-radius: 7px;
}
</style>
</head>
<body>
	<div class="container">
		<h1 class="text-center mb-4">Your Musical Journey</h1>

		<div class="profile-card">
			<h2 class="section-title">${user.firstName} ${user.lastName}'s
				Profile</h2>
			<p>
				<i class="fas fa-user"></i> ${user.userName}
			</p>
			<p>
				<i class="fas fa-envelope"></i> ${user.email}
			</p>
			<p>
				<i class="fas fa-phone"></i> ${user.phone}
			</p>
			<a href="userupdate?username=<c:out value="${user.getUserName()}"/>" class="btn btn-primary">Update User</a>
			<a href="userdelete?username=<c:out value="${user.getUserName()}"/>" class="btn btn-danger">Delete User</a>
			
		</div>

		<div class="section">
			<h3 class="section-title">Curated for you</h3>
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Track Name</th>
							<th>Artist</th>
							<th>Popularity</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${recommendedTracks}" var="track">
							<tr>
								<td>${track.trackName}</td>
								<td>${track.artistName}</td>
								<td><span class="star-rating"> <c:forEach begin="1"
											end="${track.stars}">&#9733;</c:forEach> <c:forEach
											begin="${track.stars + 1}" end="5">&#9734;</c:forEach>
								</span></td>
								<td><a href="trackdetails?trackid=${track.trackId}"
									class="btn btn-primary btn-sm">View Track</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="section">
			<h3 class="section-title">Listening History</h3>
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Track</th>
							<th>Times Listened</th>
							<th>Duration</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listeningHistory}" var="history">
							<tr>
								<td>${history.trackName}</td>
								<td>${history.timesListened}</td>
								<td><fmt:formatNumber value="${history.duration / 60}"
										maxFractionDigits="0" /> min</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="section">
			<h3 class="section-title">Your Playlists</h3>
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Playlist Name</th>
							<th>Created</th>
							<th>Visibility</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${playlists}" var="playlist">
							<tr>
								<td>${playlist.name}</td>
								<td>${playlist.created}</td>
								<td><c:choose>
										<c:when test="${playlist.isPublic}">
											<span class="badge bg-success">Public</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-secondary">Private</span>
										</c:otherwise>
									</c:choose></td>
								<td><a
									href="playlisttracks?playlistid=${playlist.playlistId}"
									class="btn btn-primary btn-sm">View Tracks</a></td>
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
