package net.mich.explore.file;

import java.io.FileWriter;
import java.io.IOException;

import net.mich.explore.scheduler.GradeActivityScheduler;

import org.apache.log4j.Logger;

public class AllStudentFileWriter {
	
	private static final Logger LOGGER = Logger.getLogger(AllStudentFileWriter.class);

	String sFileName = "all_student_schedule.csv";

	   public void writeFile(String content)
	   {
			try
			{
			    FileWriter writer = new FileWriter(sFileName);
		 
			    writer.append(content);
		 
			    writer.flush();
			    writer.close();
			}
			catch(IOException e)
			{
			     e.printStackTrace();
			} 
	}
}
