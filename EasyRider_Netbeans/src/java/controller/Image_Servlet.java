package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CWD_Manager;

@WebServlet(name = "Image_Servlet", urlPatterns = {"/Image_Servlet"})
public class Image_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param pictureArray
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            byte[] pictureArray) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        response.setContentLength(pictureArray.length);
        response.getOutputStream().write(pictureArray);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession userSession = request.getSession();
        CWD_Manager pictureRetriever = new CWD_Manager();
        String pictureName = request.getParameter("pictureItem");
        byte[] pictureArray = pictureRetriever.getPlot(pictureName, userSession.getId());
        processRequest(request, response, pictureArray);
    }
}
