//Imports packages for handling Files, IO Exceptions for
//if File initializations fail, and Process Builder to,
//you guessed it, build processes.
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

/**
 * Acts as a driver program that acts as an interface to run R scripts from
 * the server.
 * 
 * @author RED DUSK ENTERPRISES
 */
public class Rdriver {
	//Just as R is too stubborn to give me output, I am to stubborn to remove
	//the logfile it refuses to send output to :/
	static private File logFile;
	
	//The CWD for when the R process is built
	static private File sessionDir;
	
	//This is the file path to get to the R executable file
	private final static String rProgLocale = "C:\\Program Files\\R\\R-3.4.3\\bin\\i386\\R";
	
	/**
	 * The driver method that takes in a string of arguments for an input file, output file,
	 * and CWD for a clients session. The driver takes the input file and runs it through an
	 * R interpreter and captures output into another file. This is all done within the
	 * specified CWD by the args.
	 * 
	 * @param args : The array of arguments pass to the function. This should be passed in the
	 * style of : Rdriver CWD inputFile outputFile
	 * 
	 * NOTE:: For testing, supply arguments RFiles\\ Rscript.R rout.rout
	 * @throws InterruptedException : If the R Runner Script is interrupted mid Execution
	 */
	public static void main(String[] args) throws InterruptedException{
		//------------------------------------------------------------------------------------
		//TAKES ARGUMENTS AND RUNS SPECIFIED RSCRIPT IN SPECIFIED CWD TO SPECIFIED OUTPUT FILE
		//------------------------------------------------------------------------------------
		
		//Trys to run the program without failure to build the process and with the
		//correct number of arguments.
		try{
			//Checks number of arguments.
			if(args.length < 3)
				throw new IOException("Invalid argument number :: Must be 3 arguments");
			
			//Generates a generally useless log file :/
			logFile = new File("logFile.txt");
			
			//Uses arguments to generate CWD, inputFileName, and outputFileName for the rBuilder.
			sessionDir = new File(args[0]);
			String inputFileName = args[1];
			String outputFileName = args[2];
			
			//Process builder that constructs the process necessary to run R.
			ProcessBuilder rBuilder = new ProcessBuilder(rProgLocale, "CMD", "BATCH", "--slave", "--vanilla", inputFileName, outputFileName);

			//Sets CWD and vainly attempts to set up a logfile.
			rBuilder.directory(sessionDir);
			rBuilder.redirectOutput(logFile);
			
			//Runs the process after full construction.
			Process rRunner = rBuilder.start();
			
			//------------------------------------------------------------------------------------
			//CLEANS UP THE OUTPUT FILE'S UNNECESSARY FOOTER
			//------------------------------------------------------------------------------------
			
			//Insure that the above process has fully executed and the output file is complete
			rRunner.waitFor();
			
			//Creates the CWD string, outputFile object, and the fileReader to begin
			//file clean up.
			String CWD = args[0];
			File outputFile = new File(CWD+outputFileName);
			BufferedReader fileReader = new BufferedReader(new FileReader(outputFile));
			
			//Reads the first line and sets the newFileContents to an empty string
			//to finish priming the file cleanup.
			String lineRead = fileReader.readLine();
			String newFileContents = "";
			
			//While there is morel lines to process...
			while(lineRead != null){
				//System.out.println(lineRead); //DEBUGGING::PRINTS OUTPUT RAW
				//Append to newFileContents the line and its newline character
				newFileContents = newFileContents + lineRead+System.lineSeparator();
				//Read the next line of input
				lineRead = fileReader.readLine();
			}
			//Removes the footer "proc.time()" call that ends every execution.
			newFileContents = newFileContents.substring(0, newFileContents.lastIndexOf("> proc.time()"));
			//System.out.println(newFileContents); //DEBUGGING PRINTS PROCESSED OUTPUT
			
			//Create a file writer and write tidied-up output to outputFile.
			FileWriter fileCleaner = new FileWriter(outputFile);
			fileCleaner.write(newFileContents);
			
			//Close the reader and writer
			fileReader.close();
			fileCleaner.close();
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
