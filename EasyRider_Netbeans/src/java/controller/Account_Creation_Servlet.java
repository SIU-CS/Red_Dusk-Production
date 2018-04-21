package controller;

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
import model.Database_Manager;
import model.Server_Side_Hash;

@WebServlet(name = "Account_Creation_Servlet", urlPatterns = {"/Account_Creation_Servlet"})
public class Account_Creation_Servlet extends HttpServlet {

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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Create New JSP Page To Interpret R Code
        response.setContentType("text/html;charset=UTF-8");
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
        HttpSession userSession = request.getSession();
        Database_Manager databaseWorker = new Database_Manager();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        //Complete Server Side Hash
        password = hasher.hashThis(username, password);

        //Connect User To Database
        Connection databaseConnection = databaseWorker.connectToDatabase();
        if (databaseConnection == null) {
            //Error Connecting to Database
            final String ERRORMSG = "*Error: Could Not Connect To Database";
            processErrorRequest(request, response, ERRORMSG);
        }

        //Add User To Account
        if (!databaseWorker.addAccount(firstname, lastname, username, password)) {
            //Could Not Create Account
            final String ERRORMSG = "*Error: Username is Taken or Invalid Input was Given";
            processErrorRequest(request, response, ERRORMSG);
        }

        //Verify Login Information and Store it in Session then access interpreter
        if (databaseWorker.verifyAccount(username, password)) {
            userSession.setAttribute("Username", username);
            processRequest(request, response);
        } else {
            //Could Not Login
            final String ERRORMSG = "*Error: Could Not Login To Your Account";
            processErrorRequest(request, response, ERRORMSG);
        }
    }

    protected void processErrorRequest(HttpServletRequest request, HttpServletResponse response, 
            final String ERRORMSG) throws ServletException, IOException {

        //Create New JSP Page To Interpret R Code
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/Create_Account_Page.jsp");
        request.setAttribute("error_output", ERRORMSG);
        jspDispatcher.forward(request, response);
    }
}
