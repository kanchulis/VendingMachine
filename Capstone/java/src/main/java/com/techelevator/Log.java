package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
	
	static File newFile = new File("..//java", "Log.txt");
	
	//static Date date = new Date();
	
	
	public static void log (String message) throws IOException {
		

		
		
		newFile.createNewFile();
		try(PrintWriter writer = new PrintWriter(new FileOutputStream(newFile, true))) {//.getAbsoluteFile()
		writer.append(message);
		
	
		}
		}
	}


