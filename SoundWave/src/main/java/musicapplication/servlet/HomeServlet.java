package musicapplication.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import musicapplication.dal.AlbumsDao;
import musicapplication.dal.TracksDao;
import musicapplication.dal.UsersDao;
import musicapplication.model.MoodTag;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected TracksDao tracksDao;
    protected UsersDao usersDao;
    protected AlbumsDao albumsDao;

    @Override
    public void init() throws ServletException {
        tracksDao = TracksDao.getInstance();
        usersDao = UsersDao.getInstance();
        albumsDao = AlbumsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set available moods for the dropdown
        req.setAttribute("moods", MoodTag.Mood.values());
        
        // Set the active tab (default to 'tracks' or whatever you prefer)
        req.setAttribute("activeTab", "tracks");

        // You can add more attributes here if needed for other tabs

        req.getRequestDispatcher("/Home.jsp").forward(req, resp);
    }
}