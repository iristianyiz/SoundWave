package musicapplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import musicapplication.dal.*;
import musicapplication.model.*;

@WebServlet("/playlisttracks")
public class PlaylistTracks extends HttpServlet {

	protected PlaylistsDao playlistsDao;
	protected TracksDao tracksDao;

	@Override
	public void init() throws ServletException {
		playlistsDao = PlaylistsDao.getInstance();
		tracksDao = TracksDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int playlistId = Integer.parseInt(req.getParameter("playlistid"));
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		try {
			Playlists playlist = playlistsDao.getPlaylistById(playlistId);
			List<Tracks> tracks = tracksDao.getTracksForPlaylist(playlistId);

			req.setAttribute("playlist", playlist);
			req.setAttribute("tracks", tracks);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/PlaylistTracks.jsp").forward(req, resp);
	}
}
