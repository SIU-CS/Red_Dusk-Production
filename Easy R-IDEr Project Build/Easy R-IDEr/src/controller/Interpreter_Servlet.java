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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Interpreter_Servlet extends HttpServlet{
	private static CWD_Manager fileMaker = new CWD_Manager();
    /**
     * Processes requests for the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param recievedRCode
     * @param interpretedRCode interpreted R Code from Model
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String recievedRCode, String interpretedRCode) throws ServletException, IOException {

        //Create New JSP Page To Display Interpreted R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_input", recievedRCode);
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
            String completedRCode, recievedRCodeFilename;
            String recievedRCode = request.getParameter("r_input");
            R_Driver interpreter = new model.R_Driver();
            
            HttpSession userSession = request.getSession();
            System.out.println(userSession.getId());
            
            recievedRCodeFilename = fileMaker.writeRToFile(recievedRCode, userSession.getId());        //Save R Input as File in CWD
            completedRCode = interpreter.interpretCode(recievedRCodeFilename, userSession.getId());    //Send R File To Application Server Side
            processRequest(request, response, recievedRCode, completedRCode);     //Send Interpreted Code to User
            System.out.println(completedRCode);
            System.out.println("kill!!!");
            fileMaker.killSession(userSession.getId());
            System.out.println("kill!!!");
            userSession.invalidate();
        } catch (InterruptedException ex) {
            Logger.getLogger(Interpreter_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public class SessionListener implements HttpSessionListener{
    	public void sessionCreated(HttpSessionEvent arg0) {
    		System.out.println("asdafs");
    	}
    }
}
