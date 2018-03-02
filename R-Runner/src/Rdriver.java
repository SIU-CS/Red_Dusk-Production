//Imports packages for handling Files, IO Exceptions for
//if File initializations fail, and Process Builder to,
//you guessed it, build processes.
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

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
	 */
	public static void main(String[] args){
		//Trys to run the program without failure to build the process and with the
		//correct number of arguments.
		try{
			//Checks number of arguments.
			if(args.length < 3)
				throw new IOException("Invalid argument number :: Must be 3 arguments");
			
			//Generates a generally useless log file :/
			logFile = new File("logFile.txt");
			
			//Uses arguments to generate CWD, inputFile, and outputFile for the rBuilder.
			sessionDir = new File(args[0]);
			String inputFile = args[1];
			String outputFile = args[2];
			
			//Process builder that constructs the process necessary to run R.
			ProcessBuilder rBuilder = new ProcessBuilder(rProgLocale, "CMD", "BATCH", "--quiet", "--vanilla", inputFile, outputFile);

			//Sets CWD and vainly attempts to set up a logfile.
			rBuilder.directory(sessionDir);
			rBuilder.redirectOutput(logFile);
			
			//Runs the process after full construction.
			Process rRunner = rBuilder.start();
			
			//TO DO CLEAN UP THE OUTPUT FILE
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
