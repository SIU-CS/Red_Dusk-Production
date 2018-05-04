package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class Janitor implements HttpSessionListener{
    private final String CWD = "/bin/Red_Dusk-Production/Source_Code/R_Workspace/";

    @Override
    public void sessionDestroyed(HttpSessionEvent event){
        killSession(event.getSession().getId());
    }
    
    public void killSession(String userId) {
        try {
            String userSessionDir = CWD + userId + "/";
            
            File targetDir = new File(userSessionDir);
            nukeDir(targetDir);
            
            String dockerHandle = userId.toLowerCase();
            ProcessBuilder dockerJan = new ProcessBuilder("docker", "rm", "--force", dockerHandle);
            
            Process dockerJanProc;
            dockerJanProc = dockerJan.start();
            dockerJanProc.waitFor();
            
            dockerJan.command("docker", "rmi", "--force", dockerHandle);
            dockerJanProc = dockerJan.start();
            dockerJanProc.waitFor();
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Janitor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
