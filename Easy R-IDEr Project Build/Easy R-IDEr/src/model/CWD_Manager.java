package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;

public class CWD_Manager {

    enum fileType {
        R_FILE, PLOT_FILE
    }

    public final String R_EXTEN = "-RFILE.R";
    public final String PLOT_EXTEN = "-PLOT.png";
    private final String RPROGLOCAL_NIX = "/usr/bin/R";
    private final String CWD_NIX = "/home/amcowden97/Workspaces/tempDir/";
    private final String RPROGLOCAL_WIN = "C:\\Program Files\\R\\R-3.4.3\\bin\\i386\\R.exe";
    private final String CWD_WIN = "C:\\Red_Dusk-Production\\Easy R-IDEr Project Build\\Easy R-IDEr\\R-Workspace\\";
    private static long rFileTally;
    private static long plotFileTally;

    public String writeRToFile(String recievedRCode, String userId) {
    	//Locations of Current Working Directory and R terminal
    	String RPROGLOCAL;
        String CWD;
        
        //Sets the above locations depending on OS
    	String OS = System.getProperty("os.name");
    	if(OS.toLowerCase().indexOf("win")>=0) {
    		RPROGLOCAL = RPROGLOCAL_WIN;
    		CWD = CWD_WIN+userId+"\\";
    	}else if(OS.toLowerCase().indexOf("nux")>=0){
    		RPROGLOCAL = RPROGLOCAL_NIX;
    		CWD = CWD_NIX+userId+"/";
    	}else {
    		RPROGLOCAL = null;
    		CWD = null;
    	} 

    	new File(CWD).mkdir();
    	
        FileWriter writer;
        String fileName = null;
        try {
            fileName = CWD + getFileHandle(fileType.R_FILE);
            File inputFile = new File(fileName);
            writer = new FileWriter(inputFile);
            writer.write(recievedRCode);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(CWD_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }
    
    
    public boolean writeCSV(FileItem item){
        System.out.println("We got here boys...");
        return true;
    }

    private String getFileHandle(fileType type) {
        if (type == fileType.R_FILE) {
            rFileTally++;
            return rFileTally + R_EXTEN;
        } else if (type == fileType.PLOT_FILE) {
            plotFileTally++;
            return plotFileTally + PLOT_EXTEN;
        }
        return null;
    }
    
    public void killSession(String userId) {
    	String RPROGLOCAL;
        String CWD;
    	
    	//Sets the above locations depending on OS
    	String OS = System.getProperty("os.name");
    	if(OS.toLowerCase().indexOf("win")>=0) {
    		RPROGLOCAL = RPROGLOCAL_WIN;
    		CWD = CWD_WIN+userId;
    	}else if(OS.toLowerCase().indexOf("nux")>=0){
    		RPROGLOCAL = RPROGLOCAL_NIX;
    		CWD = CWD_NIX+userId;
    	}else {
    		RPROGLOCAL = null;
    		CWD = null;
    	}
    	File targetDir = new File(CWD);
    	nukeDir(targetDir);
    }
    
    private void nukeDir(File targetDir) {
    	if(!targetDir.delete()) {
    		File[] targetFiles = targetDir.listFiles();
    		for(File targetFile : targetFiles){
    			nukeDir(targetFile);
    		}
    	}else{
    		return;
    	}
    	targetDir.delete();
    }
}