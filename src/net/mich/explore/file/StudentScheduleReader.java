package net.mich.explore.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import net.mich.explore.Student;
import net.mich.explore.scheduler.GradeActivityScheduler;


public class StudentScheduleReader {
	
	private static final Logger LOGGER = Logger.getLogger(StudentScheduleReader.class);

	
	public List<Student> readStudentActivities() {
		
		List<Student> StudentList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("StudentActivities.csv")))
		{
 
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				//System.out.println(sCurrentLine);
				List<String> tokList = Arrays.asList( sCurrentLine.split(",") );
				
				if (tokList == null || tokList.isEmpty() || tokList.get(0) == null || tokList.get(0).isEmpty()) {
					System.out.println("Bad line reading file - either blank line or empty name.  Line number " + i);
					continue;
				}
				
				Student k = new Student();
				k.setName( tokList.get(0) + ", " + tokList.get(1) );
				k.setGrade( tokList.get(2) );
				k.setTeacher( tokList.get(3) );
				k.setAct( tokList.subList(4, tokList.size()) );

				StudentList.add(k);
				//System.out.println(k);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
		return StudentList;
	}

}
