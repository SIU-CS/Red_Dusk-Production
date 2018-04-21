package controller;

import model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class Interpreter_Servlet extends HttpServlet {

    private static final CWD_Manager fileManager = new CWD_Manager();

    /**
     * Processes requests for the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param recievedRCode
     * @param interpretedRCode interpreted R Code from Model
     * @param pictureList
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
            String recievedRCode, String interpretedRCode, ArrayList pictureList) throws ServletException, IOException {
       
        //Create New JSP Page To Display Interpreted R Code
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("r_input", recievedRCode);
        request.setAttribute("r_output", interpretedRCode);
        request.setAttribute("pictureList", pictureList);
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
            HttpSession userSession = request.getSession();
            String completedRCode, recievedRCodeFilename;
            String recievedRCode = parseParameters(request);
            
            if (recievedRCode == null) {
                processRequest(request, response, recievedRCode, "Error Interpreting Code", null);
            }

            //Write Recieved Code to File and Send it to Interpreter
            recievedRCodeFilename = fileManager.writeRToFile(recievedRCode, userSession.getId());
            completedRCode = interpreter.interpretCode(recievedRCodeFilename, userSession.getId());
   
            //Get User Generated Plot List
            ArrayList pictureList = fileManager.getPlotList(userSession.getId());
            
            //End User Interpreter Job
            //fileManager.killSession(userSession.getId());
            //userSession.invalidate();
               
            //Generate New .jsp Page
            processRequest(request, response, recievedRCode, completedRCode, pictureList);

        } catch (InterruptedException ex) {
            Logger.getLogger(Interpreter_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String parseParameters(HttpServletRequest request) {
        String recievedRCode = null;

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> uploadItems = upload.parseRequest(new ServletRequestContext(request));

            for (FileItem uploadItem : uploadItems) {
                //Check if Input is Standard Form Field
                if (uploadItem.isFormField()) {
                    String fieldName = uploadItem.getFieldName();
                    String value = uploadItem.getString();

                    //Find Recieved R Code
                    if (fieldName.equals("editor")) {
                        recievedRCode = value;
                    }
                //Check If Input is a File
                } else {
                    fileManager.writeCSV(uploadItem);
                }
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(Interpreter_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recievedRCode;
    }
}
