package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CWD_Manager {

    enum fileType {
        R_FILE, PLOT_FILE
    }

    public final String R_EXTEN = "-RFILE.R";
    public final String PLOT_EXTEN = "-PLOT.png";
    private final String CWD = "/home/amcowden97/Workspaces/tempDir/";
    private static long rFileTally;
    private static long plotFileTally;

    public String writeRToFile(String recievedRCode) {
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
}
