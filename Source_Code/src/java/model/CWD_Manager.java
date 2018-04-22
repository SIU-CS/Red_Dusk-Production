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
        String RPROGLOCAL = getRLocation();
        String CWD = getCWD(userId);

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

    
    public boolean writeCSV(FileItem item) {
        System.out.println("We got here boys...");
        return true;
    }

    
    public ArrayList<String> getPlotList(String userId) {
        ArrayList<String> picList = new ArrayList();
        String CWD = getCWD(userId);

        File[] files = new File(CWD).listFiles();

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
        String CWD = getCWD(userId);
        
        try {
            File fi = new File(CWD + plotName);
            System.out.println(CWD + plotName);
            return Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
            return null;
        }
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
        String RPROGLOCAL = getRLocation();
        String CWD = getCWD(userId);

        File targetDir = new File(CWD);
        nukeDir(targetDir);
    }

    
    private void nukeDir(File targetDir) {
        if (!targetDir.delete()) {
            File[] targetFiles = targetDir.listFiles();
            for (File targetFile : targetFiles) {
                nukeDir(targetFile);
            }
        } else {
            return;
        }
        targetDir.delete();
    }

    
    private String getCWD(String userId) {
        String OS = System.getProperty("os.name");
        String CWD;

        if (OS.toLowerCase().contains("win")) {
            CWD = CWD_WIN + userId + "\\";
        } else if (OS.toLowerCase().contains("nux")) {
            CWD = CWD_NIX + userId + "/";
        } else {
            CWD = null;
        }
        return CWD;
    }

    
    private String getRLocation() {
        String OS = System.getProperty("os.name");
        String RPROGLOCAL;

        if (OS.toLowerCase().contains("win")) {
            RPROGLOCAL = RPROGLOCAL_WIN;
        } else if (OS.toLowerCase().contains("nux")) {
            RPROGLOCAL = RPROGLOCAL_NIX;
        } else {
            RPROGLOCAL = null;
        }
        return RPROGLOCAL;
    }
}
