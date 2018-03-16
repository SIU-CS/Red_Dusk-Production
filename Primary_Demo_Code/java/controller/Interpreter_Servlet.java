package controller;

import model.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interpreter_Servlet extends HttpServlet {


    /**
     * Processes requests for the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param interpretedRCode interpreted R Code from Model
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String interpretedRCode) throws ServletException, IOException {
        
        //Create New JSP Page To Display Interpreted R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_output", interpretedRCode);
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/Interpreter_View.jsp");
        jspDispatcher.forward(request, response);
    }

        
    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            //Get User's R Code and Send Out to Interpreter
            String recievedRCode = request.getParameter("r_input");
            String completedCode;
            R_Driver interpreter = new model.R_Driver();
           
            completedCode = interpreter.interpretCode(recievedRCode); //Send Code To Application Server Side
            processRequest(request, response, completedCode); //Send Interpreted Code to User
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Interpreter_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
