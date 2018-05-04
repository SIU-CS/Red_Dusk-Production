package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;

public class CWD_Manager {

    public final String R_HANDLE = "RFILE.R";
    private final String CWD = "/bin/Red_Dusk-Production/Source_Code/R_Workspace/";

    public String writeRToFile(String recievedRCode, String userId) {
        //Locations of Current Working Directory and R terminal
        String userSessionDir = getCWD(userId);

        new File(userSessionDir).mkdir();

        FileWriter writer;
        String fileName = null;
        try {
            fileName = userSessionDir + R_HANDLE;
            File inputFile = new File(fileName);
            writer = new FileWriter(inputFile);
            writer.write(recievedRCode);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(CWD_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public ArrayList<String> getPlotList(String userId) {
        ArrayList<String> picList = new ArrayList();
        String userCWD = getCWD(userId);

        File[] files = new File(userCWD).listFiles();
        if(files == null){
            return null;
        }

        //Find the Names of All PDF Files in a Directory
        for (File file : files) {
            if (file.getName().endsWith(".pdf")) {
                picList.add(file.getName());
            }
        }
        return picList;
    }

    public File getObjectFile(String fileName, String userId) {
        String userCWD = getCWD(userId);
        File fi = new File(userCWD + fileName);
        return fi;
    }

    public boolean writeCSV(FileItem item) {
        System.out.println("We got here boys...");
        return true;
    }

    public void killSession(String userId) {
        String userSessionDir = getCWD(userId);

        File targetDir = new File(userSessionDir);
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
        return CWD + userId + "/";
    }
}