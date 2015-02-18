package net.mich.explore.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mich.explore.SchedulerConstants;
import net.mich.explore.SchedulerMain;
import net.mich.explore.StudentActivity;

import org.apache.log4j.Logger;


public class StudentClassScheduleReader {
	
	private static final Logger LOGGER = Logger.getLogger(StudentClassScheduleReader.class);

	public Map< String,List<StudentActivity> > read(SchedulerMain mn) {
		
		Map< String,List<StudentActivity> > scheduleMap = mn.getScheduleMap();
		
		try (BufferedReader br = new BufferedReader(new FileReader(SchedulerConstants.STUDENT_SCHEDULE_FILENAME)))
		{
 
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				LOGGER.debug(sCurrentLine);
				List<String> tokList = Arrays.asList( sCurrentLine.split("\\|") );
				
				if (tokList == null || tokList.isEmpty() || tokList.get(0) == null || tokList.get(0).isEmpty()) {
					LOGGER.error("Bad line reading file - either blank line or empty name.  Line number " + i);
					continue;
				}
				StudentActivity studentAct = new StudentActivity();
				String clazzHour = tokList.get(0);
				List<String> tokClazzHour = Arrays.asList( clazzHour.split("-") );
				studentAct.setAct(tokClazzHour.get(0));
				studentAct.setHour(tokClazzHour.get(1));
				
				studentAct.setName( tokList.get(1) );
				studentAct.setGrade( tokList.get(2) );
				studentAct.setTeacher( tokList.get(3) );
				
				LOGGER.debug(studentAct);
				
				scheduleMap.get(clazzHour).add(studentAct);
			}
 
		} catch (IOException e) {
			LOGGER.error("Error reading " + SchedulerConstants.STUDENT_SCHEDULE_FILENAME + " file to generate reports:  " + e.getMessage(), e);
		} 
 
		return scheduleMap;
	}

}
