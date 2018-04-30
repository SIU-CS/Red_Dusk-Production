//Imports packages for handling Files, IO Exceptions for
//if File initializations fail, and Process Builder to,
//you guessed it, build processes.
package model;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Acts as a driver program that acts as an interface to run R scripts from the
 * server.
 *
 * @author RED DUSK ENTERPRISES
 */
public class R_Driver {
    private final String CWD = "/bin/Red_Dusk-Production/Source_Code/R_Workspace/";
    //Just as R is too stubborn to give me output, I am to stubborn to remove
    //the logfile it refuses to send output to :/

    //The CWD for when the R process is built
    static private File sessionDir;

    /**
     * The driver method that takes in a string of arguments for an input file,
     * output file, and CWD for a clients session. The driver takes the input
     * file and runs it through an R interpreter and captures output into
     * another file. This is all done within the specified CWD by the args.
     *
     * @param inputFileName
     *
     * NOTE:: For testing, supply arguments RFiles\\ Rscript.R rout.rout
     * @param userId
     * @return 
     * @throws InterruptedException : If the R Runner Script is interrupted mid
     * Execution
     */
    public String interpretCode(String inputFileName, String userId) throws InterruptedException {
    	//Locations of Current Working Directory and R terminal
        String userSessionDir = CWD+userId+"/";
        //System.out.println(CWD+"\n"+RPROGLOCAL);
        //------------------------------------------------------------------------------------
        //TAKES ARGUMENTS AND RUNS SPECIFIED RSCRIPT IN SPECIFIED CWD TO SPECIFIED OUTPUT FILE
        //------------------------------------------------------------------------------------

        //Trys to run the program without failure to build the process and with the
        //correct number of arguments.
        //Uses arguments to generate CWD, inputFileName, and outputFileName for the rBuilder.
        sessionDir = new File(userSessionDir);
        
        File dockerFile = new File(userSessionDir+"DockerFile");
        try {
            FileWriter writer = new FileWriter(dockerFile);
            writer.write("FROM rocker/r-devel-san\n" + 
            "\n" + 
            "COPY . /usr/local/src/myscripts\n" +
            "\n" +
            "WORKDIR /usr/local/src/myscripts\n" +
            "\n" +
            "CMD [\"R\", \"CMD\", \"BATCH\", \"--slave\", \"--vanilla\", \"RFILE.R\", \"outputFile.txt\"]");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        String outputFileName = "outputFile.txt";
        
        try {
            //Initializes the process builder with initial command: build our docker image
            ProcessBuilder rBuilder = new ProcessBuilder("docker", "build", "-t", userId.toLowerCase(), "-f", "DockerFile", ".");
            
            //Sets CWD for the docker process that will follow.
            rBuilder.directory(sessionDir);
            
            //Init rRunner (The process handle)
            Process rRunner;
            //The handle we will use for docker images and containers
            String dockerHandle = userId.toLowerCase();
            
            //Creates the docker image for this user
            rRunner = rBuilder.start();
            rRunner.waitFor();
            
            //Creates and runs the docker container that runs R
            rBuilder.command("docker", "run", "--name="+dockerHandle, userId.toLowerCase());
            rRunner = rBuilder.start();
            rRunner.waitFor();
            
            //Copies all files from the user's session back to their session directory
            rBuilder.command("docker", "cp", dockerHandle+":/usr/local/src/myscripts/.", ".");
            rRunner = rBuilder.start();
            rRunner.waitFor();
            
            //Prunes the user's container post copy
            rBuilder.command("docker", "rm", "--force", dockerHandle);
            rRunner = rBuilder.start();
            rRunner.waitFor();
            
            //Prunes the user's image post copy
            rBuilder.command("docker", "rmi", "--force", dockerHandle);
            rRunner = rBuilder.start();
            rRunner.waitFor();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Creates the CWD string, outputFile object, and the fileReader to begin
        //file clean up.
        File outputFile = new File(userSessionDir + outputFileName);
        BufferedReader fileReader;
        String newFileContents = "";

        //------------------------------------------------------------------------------------
        //CLEANS UP THE OUTPUT FILE'S UNNECESSARY FOOTER
        //------------------------------------------------------------------------------------
        try {
            fileReader = new BufferedReader(new FileReader(outputFile));
            //Reads the first line and sets the newFileContents to an empty string
            //to finish priming the file cleanup.
            String lineRead = fileReader.readLine();

            //While there is more lines to process...
            while (lineRead != null) {
                //System.out.println(lineRead); //DEBUGGING::PRINTS OUTPUT RAW
                //Append to newFileContents the line and its newline character
                newFileContents += lineRead + System.lineSeparator();
                //Read the next line of input
                lineRead = fileReader.readLine();
            }
            //Close the reader and writer
            fileReader.close();

            //Removes the footer "proc.time()" call that ends every execution.
            newFileContents = newFileContents.substring(0, newFileContents.lastIndexOf("> proc.time()"));

            //System.out.println(newFileContents); //DEBUGGING PRINTS PROCESSED OUTPUT
            //Create a file writer and write tidied-up output to outputFile.
            FileWriter fileCleaner = new FileWriter(outputFile);
            fileCleaner.write(newFileContents);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(R_Driver.class.getName()).log(Level.SEVERE, null, ex);
            return "Error Occurred When Trying To Find The Written File. Please Try Again...";
        } catch (IOException ex) {
            Logger.getLogger(R_Driver.class.getName()).log(Level.SEVERE, null, ex);
            return "Error Occurred When Trying To Interpret File. Please Try Again...";
        } catch (StringIndexOutOfBoundsException ex) {
            //Ignore Case When User Gives Invalid Input 
        }

        return newFileContents;
    }
}