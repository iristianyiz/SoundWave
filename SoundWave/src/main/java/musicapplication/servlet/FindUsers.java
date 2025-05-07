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

@WebServlet("/findusers")
public class FindUsers extends HttpServlet {
	protected UsersDao usersDao;
	private static final int PAGE_SIZE = 10;

	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
		req.setAttribute("messages", messages);

		String name = req.getParameter("name");
		int page = 1;
		String pageStr = req.getParameter("page");
		if (pageStr != null && !pageStr.isEmpty()) {
			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				messages.put("success", "Invalid page number.");
			}
		}

		List<Users> users;
		try {
			users = usersDao.getUsersPaginated(name, page, PAGE_SIZE);
			int totalUsers = usersDao.getTotalUsersCount(name);
			int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

			req.setAttribute("users", users);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.setAttribute("activeTab", "users");
		req.getRequestDispatcher("/Home.jsp").forward(req, resp);
	}
}