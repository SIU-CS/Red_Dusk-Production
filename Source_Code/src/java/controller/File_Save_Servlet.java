package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CWD_Manager;
import model.Database_Manager;

public class File_Save_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param recievedRCode
     * @param interpretedRCode
     * @param pictureList
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String recievedRCode, String interpretedRCode, ArrayList pictureList)
            throws ServletException, IOException {
        
        //Create New JSP Page To Display Page
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_input", recievedRCode);
        request.setAttribute("r_output", interpretedRCode);
        request.setAttribute("pictureList", pictureList);
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
      
        //Get User's R Code and Send Out to Interpreter
        CWD_Manager fileManager = new CWD_Manager();
        Database_Manager dataWorker = new Database_Manager();
        HttpSession userSession = request.getSession();
        String completedRCode = request.getParameter("output");
        String recievedRCode = request.getParameter("editor");
        String fileName = request.getParameter("filename");
        String userName = null;
        
        //Get User Generated Plot List
        ArrayList pictureList = fileManager.getPlotList(userSession.getId());
        if (recievedRCode == null) {
            processRequest(request, response, recievedRCode, "Error Saving Code", pictureList);
        }
        
        //Guest Login Checker and Warning Handler
        try{
            userName = userSession.getAttribute("Username").toString();
        }catch(Exception ex){
             //ProcessErrorRequest
            processRequest(request, response, recievedRCode, completedRCode, null);
        }
        
        //Verify that the Username is Valid
        if(userName == null){
            //ProcessErrorRequest
            processRequest(request, response, recievedRCode, completedRCode, null);
        }
        
        //Save User File to Database
        if(!dataWorker.saveFile(userName, fileName, recievedRCode)){
            //ProcessErrorRequest
        }
        
        //Generate New .jsp Page
        processRequest(request, response, recievedRCode, completedRCode, pictureList);
    }
}