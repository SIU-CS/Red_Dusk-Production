
package controller;

import model.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login_Servlet", urlPatterns = {"/Login_Servlet"})
public class Login_Servlet extends HttpServlet {

    /**
     * Processes requests for the HTTP POST method.
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
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/Interpreter_View.jsp");
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
        
        Database_Manager loginWorker = new Database_Manager();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if(request.getParameter("guestBtn") != null){             //Guest Account Login Case
            processRequest(request, response, " ", " ");
        }else if(loginWorker.verifyAccount(username, password)){  //User Account Case
            processRequest(request, response, username, password);
        }else{                                                    //Login Failed 
        }
    }
}
