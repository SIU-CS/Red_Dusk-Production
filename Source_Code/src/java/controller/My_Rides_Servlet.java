package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Database_Manager;


@WebServlet(name = "My_Rides_Servlet", urlPatterns = {"/My_Rides_Servlet"})
public class My_Rides_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param fileList
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String fileList) throws ServletException, IOException {
        
        //Create New JSP Page To Display Interpreted R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("file_list", fileList);
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/My_Rides_Page.jsp");
        jspDispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        Database_Manager dataWorker = new Database_Manager();
        HttpSession userSession = request.getSession();
        String username = userSession.getAttribute("Username").toString();
        String sort = request.getParameter("sort");
        String fileList;
        
        //Choose File Output Ordering
        if(sort != null && sort.equals("date")){
            fileList = dataWorker.listUserFilesDateSort(username);
        }else{
            fileList = dataWorker.listUserFiles(username);
        }
        processRequest(request, response, fileList);
    }
}
