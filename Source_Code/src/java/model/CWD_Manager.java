package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import org.apache.tomcat.util.http.fileupload.FileItem;

public class CWD_Manager {
    enum fileType {
        R_FILE, PLOT_FILE
    }

    public final String R_HANDLE = "RFILE.R";
    public final String PLOT_EXTEN = "-PLOT.png";
    private final String CWD = "/bin/Red_Dusk-Production/Source_Code/R_Workspace/";
    private static long rFileTally;
    private static long plotFileTally;
    
    public String writeRToFile(String recievedRCode, String userId) {
        //Locations of Current Working Directory and R terminal
        String userSessionDir = getCWD(userId);
        
        new File(userSessionDir).mkdir();
        
        FileWriter writer;
        String fileName = null;
        try {
            fileName = userSessionDir + getFileHandle(fileType.R_FILE);
            File inputFile = new File(fileName);
            writer = new FileWriter(inputFile);
            writer.write(recievedRCode);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(CWD_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    
    public boolean writeCSV(FileItem item) {
        System.out.println("We got here boys...");
        return true;
    }

    
    public ArrayList<String> getPlotList(String userId) {
        ArrayList<String> picList = new ArrayList();
        String userSessionDir = getCWD(userId);

        File[] files = new File(userSessionDir).listFiles();

        //Find the Names of All Image Files in a Directory
        for (File file : files) {
            String mimetype = new MimetypesFileTypeMap().getContentType(file);
            String type = mimetype.split("/")[0];
            if (type.equals("image")) {
                picList.add(file.getName());
            }
        }
        return picList;
    }

    
    public byte[] getPlot(String plotName, String userId) {
        String userSessionDir = getCWD(userId);
        
        try {
            File fi = new File(userSessionDir + plotName);
            System.out.println(userSessionDir + plotName);
            return Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
            return null;
        }
    }

    
    private String getFileHandle(fileType type) {
        if (type == fileType.R_FILE) {
            return R_HANDLE;
        } else if (type == fileType.PLOT_FILE) {
            plotFileTally++;
            return plotFileTally + PLOT_EXTEN;
        }
        return null;
    }
    
    private String getCWD(String userId) {
        String OS = System.getProperty("os.name");
        String userSessionDir = CWD + userId + "/";

        return userSessionDir;
    }
}
