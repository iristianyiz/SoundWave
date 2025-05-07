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

@WebServlet("/albumdetails")
public class AlbumDetails extends HttpServlet {

	protected TracksDao tracksDao;
	protected AlbumsDao albumsDao;
	protected CommentsDao commentsDao;

	@Override
	public void init() throws ServletException {
		tracksDao = TracksDao.getInstance();
		albumsDao = AlbumsDao.getInstance();
		commentsDao = CommentsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String albumId = req.getParameter("albumid");

		if (albumId == null || albumId.trim().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Album ID is required");
			return;
		}

		try {
			Albums album = albumsDao.getAlbumById(Integer.parseInt(albumId));			if (album == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Album not found");
				return;
			}

			String albumName = albumsDao.getAlbumById(album.getAlbumId()).getAlbumName();
			List<Tracks> tracks = tracksDao.getTracksByAlbumId(Integer.parseInt(albumId));

			req.setAttribute("album", album);
			req.setAttribute("tracks", tracks);
			
			req.getRequestDispatcher("/AlbumDetails.jsp").forward(req, resp);

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