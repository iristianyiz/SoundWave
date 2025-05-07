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
<title>SoundWave - Track Details</title>
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

.track-info {
	background-color: #ffffff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.section {
	margin-top: 30px;
	background-color: #ffffff;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.star-rating {
	color: #ffc107;
}

.mood-tag {
	display: inline-block;
	padding: 5px 10px;
	margin-right: 5px;
	border-radius: 5px;
	background-color: #e9ecef;
}
</style>
</head>
<body>
	<div class="container mt-4">
		<h1 class="text-center mb-4">Track Details</h1>

		<!-- Track Information Section -->
		<div class="track-info">
			<h2>${trackDetails.track.trackName}</h2>
			<p>
				<strong>Album:</strong> ${trackDetails.albumName}
			</p>
			<p>
				<strong>Genre:</strong> ${trackDetails.genreName}
			</p>
			<p>
				<strong>Popularity:</strong> ${trackDetails.track.popularity}
			</p>
			<p>
				<strong>Duration:</strong>
				<fmt:formatNumber value="${trackDetails.track.durationMs / 60000}"
					maxFractionDigits="0" />
				min
			</p>
			<p>
				<strong>Explicit Content:</strong> ${trackDetails.track.explicit ? 'Yes' : 'No'}
			</p>
			<p>
				<strong>Danceability:</strong> ${trackDetails.track.danceability}
			</p>
			<p>
				<strong>Energy:</strong> ${trackDetails.track.energy}
			</p>
			<p>
				<strong>Tempo:</strong> ${trackDetails.track.tempo} BPM
			</p>
			<p>
				<strong>Time Signature:</strong> ${trackDetails.track.timeSignature}
			</p>
		</div>

		<!-- Mood Tags Section -->
		<div class="section">
			<h3>Mood Tags</h3>
			<div>
				<c:forEach items="${trackDetails.moodTags}" var="moodTag">
					<span class="mood-tag">${moodTag.mood}</span>
				</c:forEach>
			</div>
		</div>

		<!-- Like/Dislike Section -->
		<div class="section">
			<h3>Likes and Dislikes</h3>
			<p>
				<strong>Likes:</strong> ${trackDetails.likeCount}
			</p>
			<p>
				<strong>Dislikes:</strong> ${trackDetails.dislikeCount}
			</p>
		</div>

		<!-- Comments Section -->
		<div class="section">
			<h3>Comments</h3>
			<c:if test="${empty trackDetails.comments}">
				<p>No comments yet.</p>
			</c:if>
			<c:forEach items="${trackDetails.comments}" var="comment">
				<div class="comment mb-3">
					<p>
						<strong>${comment.userName}:</strong> ${comment.content}
					</p>
					<p>
						<small>Posted on <fmt:formatDate
								value="${comment.created}" pattern="MMM d, yyyy h:mm a" />
						</small>
					</p>
				</div>
				<hr />
			</c:forEach>
		</div>

		<!-- Add New Comment Section -->
		<div class="section">
			<h4>Add a Comment</h4>
			<form action="trackdetails" method="post">
				<div class="mb-3">
					<label for="commentContent" class="form-label">Your
						Comment:</label>
					<textarea id="commentContent" name="content" class="form-control"
						rows="3" required></textarea>
				</div>
				<input type="hidden" name="trackId"
					value="${trackDetails.track.trackId}" />
				<button type="submit" class="btn btn-primary">Submit
					Comment</button>
			</form>
		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>