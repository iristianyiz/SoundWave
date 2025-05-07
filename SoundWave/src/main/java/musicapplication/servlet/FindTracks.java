package musicapplication.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import musicapplication.dal.*;
import musicapplication.model.*;

@WebServlet("/findtracks")
public class FindTracks extends HttpServlet {

	protected TracksDao tracksDao;
	private static final int PAGE_SIZE = 10;

	@Override
	public void init() throws ServletException {
		tracksDao = TracksDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set the active tab
		req.setAttribute("activeTab", "tracks");
		req.getRequestDispatcher("/Home.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
		req.setAttribute("trackmessages", messages);

		String trackTitle = req.getParameter("tracktitle");
		String moodString = req.getParameter("mood");
		MoodTag.Mood mood = null;
		int page = 1;

		if (moodString != null && !moodString.isEmpty()) {
			try {
				mood = MoodTag.Mood.valueOf(moodString);
			} catch (IllegalArgumentException e) {
				messages.put("success", "Invalid mood selected.");
			}
		}

		String pageStr = req.getParameter("page");
		if (pageStr != null && !pageStr.isEmpty()) {
			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				messages.put("success", "Invalid page number.");
			}
		}

		List<Tracks> tracks = new ArrayList<>();

		try {
			tracks = tracksDao.getTracksByTitleAndMoodPaginated(trackTitle, mood, page, PAGE_SIZE);
			int totalTracks = tracksDao.getTotalTracksCount(trackTitle, mood);
			int totalPages = (int) Math.ceil((double) totalTracks / PAGE_SIZE);

			if (tracks.isEmpty()) {
				messages.put("success", "No tracks found.");
			} else {
				messages.put("success", "Displaying results for " + (trackTitle != null ? trackTitle : "")
						+ (mood != null ? " with mood: " + mood : ""));
			}

			req.setAttribute("tracks", tracks);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.setAttribute("activeTab", "tracks");

		req.getRequestDispatcher("/Home.jsp").forward(req, resp);
	}
}
