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

@WebServlet("/findalbums")
public class FindAlbums extends HttpServlet {
	protected AlbumsDao albumsDao;
	private static final int PAGE_SIZE = 10;

	@Override
	public void init() throws ServletException {
		albumsDao = AlbumsDao.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
		req.setAttribute("messages", messages);

		String albumTitle = req.getParameter("albumtitle");
		int page = 1;
		String pageStr = req.getParameter("page");
		if (pageStr != null && !pageStr.isEmpty()) {
			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				messages.put("success", "Invalid page number.");
			}
		}

		List<Albums> albums;
		try {
			albums = albumsDao.getAlbumsPaginated(albumTitle, page, PAGE_SIZE);
			int totalAlbums = albumsDao.getTotalAlbumsCount(albumTitle);
			int totalPages = (int) Math.ceil((double) totalAlbums / PAGE_SIZE);

			req.setAttribute("albums", albums);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.setAttribute("activeTab", "albums");
		req.getRequestDispatcher("/Home.jsp").forward(req, resp);
	}
}