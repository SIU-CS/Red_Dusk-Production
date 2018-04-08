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
    //Just as R is too stubborn to give me output, I am to stubborn to remove
    //the logfile it refuses to send output to :/

    //The CWD for when the R process is built
    static private File sessionDir;

    //This is the file path to get to the R executable file
    private final String RPROGLOCAL_NIX = "/usr/bin/R";
    private final String CWD_NIX = "/home/amcowden97/Workspaces/tempDir/";
    private final String RPROGLOCAL_WIN = "C:\\Program Files\\R\\R-3.4.3\\bin\\i386\\R.exe";
    private final String CWD_WIN = "C:\\Red_Dusk-Production\\Easy R-IDEr Project Build\\Easy R-IDEr\\R-Workspace\\";
    
    /**
     * The driver method that takes in a string of arguments for an input file,
     * output file, and CWD for a clients session. The driver takes the input
     * file and runs it through an R interpreter and captures output into
     * another file. This is all done within the specified CWD by the args.
     *
     * @param inputFileName
     *
     * NOTE:: For testing, supply arguments RFiles\\ Rscript.R rout.rout
     * @return 
     * @throws InterruptedException : If the R Runner Script is interrupted mid
     * Execution
     */
    public String interpretCode(String inputFileName, String userId) throws InterruptedException {
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
        //------------------------------------------------------------------------------------
        //TAKES ARGUMENTS AND RUNS SPECIFIED RSCRIPT IN SPECIFIED CWD TO SPECIFIED OUTPUT FILE
        //------------------------------------------------------------------------------------

        //Trys to run the program without failure to build the process and with the
        //correct number of arguments.
        //Uses arguments to generate CWD, inputFileName, and outputFileName for the rBuilder.
        sessionDir = new File(CWD);
        String outputFileName = "outputFile.txt";

        //Process builder that constructs the process necessary to run R.
        ProcessBuilder rBuilder = new ProcessBuilder(RPROGLOCAL, "CMD", "BATCH", "--slave", "--vanilla", inputFileName, outputFileName);

        //Sets CWD and vainly attempts to set up a logfile.
        rBuilder.directory(sessionDir);
        //rBuilder.redirectOutput(logFile);

        Process rRunner;
        try {
            //Runs the process after full construction.
            rRunner = rBuilder.start();

            //Insure that the above process has fully executed and the output file is complete
            rRunner.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(R_Driver.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Creates the CWD string, outputFile object, and the fileReader to begin
        //file clean up.
        File outputFile = new File(CWD + outputFileName);
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