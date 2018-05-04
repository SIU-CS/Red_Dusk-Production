package controller;

import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Interpreter_Servlet extends HttpServlet {

    /**
     * Processes requests for the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param recievedRCode
     * @param interpretedRCode interpreted R Code from Model
     * @param filename
     * @param pictureList
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String recievedRCode, String interpretedRCode, String filename, ArrayList pictureList) throws ServletException, IOException {
       
        //Create New JSP Page To Display Interpreted R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_input", recievedRCode);
        request.setAttribute("r_output", interpretedRCode);
        request.setAttribute("pictureList", pictureList);
        request.setAttribute("filename", filename);
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/Interpreter_Page.jsp");
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
            R_Driver interpreter = new model.R_Driver();
            CWD_Manager fileManager = new CWD_Manager();
            HttpSession userSession = request.getSession();
            String completedRCode, recievedRCodeFilename;
            String recievedRCode = request.getParameter("editor");
            String filename = request.getParameter("filename");
            
            if(filename == null){
                filename = "Untitled_Script.R";
            }
            
            if (recievedRCode == null) {
                processRequest(request, response, recievedRCode, "Error Interpreting Code", filename, null);
            }

            //Write Recieved Code to File and Send it to Interpreter
            recievedRCodeFilename = fileManager.writeRToFile(recievedRCode, userSession.getId());
            completedRCode = interpreter.interpretCode(recievedRCodeFilename, userSession.getId());
   
            //Get User Generated Plot List
            ArrayList pictureList = fileManager.getPlotList(userSession.getId());
                   
            //Generate New .jsp Page
            processRequest(request, response, recievedRCode, completedRCode, filename, pictureList);

        } catch (InterruptedException ex) {
            Logger.getLogger(Interpreter_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
