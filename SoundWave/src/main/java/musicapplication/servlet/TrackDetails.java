package musicapplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import musicapplication.dal.*;
import musicapplication.model.*;

@WebServlet("/trackdetails")
public class TrackDetails extends HttpServlet {

	protected TracksDao tracksDao;
	protected MoodTagDao moodTagDao;
	protected LikeAndDislikeDao likeAndDislikeDao;
	protected CommentsDao commentsDao;
	protected AlbumsDao albumsDao;
	protected GenresDao genresDao;

	@Override
	public void init() throws ServletException {
		tracksDao = TracksDao.getInstance();
		moodTagDao = MoodTagDao.getInstance();
		likeAndDislikeDao = LikeAndDislikeDao.getInstance();
		commentsDao = CommentsDao.getInstance();
		albumsDao = AlbumsDao.getInstance();
		genresDao = GenresDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String trackId = req.getParameter("trackid");

		if (trackId == null || trackId.trim().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Track ID is required");
			return;
		}

		try {
			Tracks track = tracksDao.getTrackById(trackId);
			if (track == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Track not found");
				return;
			}

			String albumName = albumsDao.getAlbumById(track.getAlbumId()).getAlbumName();
			String genreName = genresDao.getGenreById(track.getGenreId()).getGenreName();
			List<MoodTag> moodTags = moodTagDao.getMoodTagsByTrackId(trackId);
			List<LikeAndDislike> likeAndDislikeList = likeAndDislikeDao.getLikeAndDislikeByTrackId(trackId);
			int likeCount = 0;
			int dislikeCount = 0;
			for (LikeAndDislike lad : likeAndDislikeList) {
				if (lad.isLikeOrDislike()) {
					likeCount++;
				} else {
					dislikeCount++;
				}
			}
			List<Comments> comments = commentsDao.getCommentsByTrackId(trackId);

			TrackDetailsModel trackDetails = new TrackDetailsModel(track, albumName, genreName, moodTags, likeCount,
					dislikeCount, comments);

			req.setAttribute("trackDetails", trackDetails);
			req.getRequestDispatcher("/TrackDetails.jsp").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String trackId = req.getParameter("trackId");
		String content = req.getParameter("content");

		if (trackId == null || trackId.trim().isEmpty() || content == null || content.trim().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Track ID and comment content are required");
			return;
		}

		try {
			// Get a random username
			String randomUserName = commentsDao.getRandomUserName();

			if (randomUserName == null) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to get a random user");
				return;
			}

			// Create a new comment
			Comments newComment = new Comments(0, new Timestamp(System.currentTimeMillis()), content, randomUserName,
					trackId);
			commentsDao.create(newComment);

			// Redirect back to the track details page
			resp.sendRedirect("trackdetails?trackid=" + trackId);
		} catch (SQLException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating comment");
		}
	}
}