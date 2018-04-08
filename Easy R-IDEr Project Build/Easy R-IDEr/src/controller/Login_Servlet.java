package controller;

import model.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login_Servlet", urlPatterns = {"/Login_Servlet"})
public class Login_Servlet extends HttpServlet {

    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        //Create Database Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes Jobs for the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param username user provided username
     * @param password user provided password after hash
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String username, String password) throws ServletException, IOException {

        //Create New JSP Page To Interpret R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_input", username);   //Empty Input Text Field Initially
        request.setAttribute("r_output", password);  //Empty Output Text Field Initially
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/Interpreter_Page.jsp");
        jspDispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Server_Side_Hash hasher = new Server_Side_Hash();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isGuest = request.getParameter("guestBtn") != null; //Determine if User is Guest

        if (isGuest) {
            processRequest(request, response, " ", " ");
        } else {
            //Give Database Worker Session to access Connection
            HttpSession userSession = request.getSession();
            Database_Manager loginWorker = new Database_Manager(userSession);

            //Connect User To Database
            Connection databaseConnection = loginWorker.connectToDatabase();
            if (databaseConnection == null) {
                //Error Connecting to Database

            }

            //Complete Server Side Hash
            password = hasher.hashThis(username, password);

            //Verify Login Information and Store it in Session
            if (loginWorker.verifyAccount(username, password)) {
                userSession.setAttribute("Username", username);
                processRequest(request, response, username, password);
            } else {
                //Could Not Login
            }
        }
    }
}
