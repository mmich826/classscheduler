package net.mich.explore.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.mich.explore.SchedulerConstants.*;
import net.mich.explore.Activity;
import net.mich.explore.SchedulerMain;
import net.mich.explore.StudentActByStudentComparator;
import net.mich.explore.StudentActivity;
import net.mich.explore.file.AllStudentFileWriter;

import org.apache.log4j.Logger;

public class ReportGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ReportGenerator.class);

	public void printFullSchedule(SchedulerMain mainSched) {
		Map< String,List<StudentActivity> > scheduleMap = mainSched.getScheduleMap();
		
		StringBuilder sb = new StringBuilder();
		
		List<StudentActivity> StudentActivityList = new ArrayList<StudentActivity>();
		
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<StudentActivity> Students = scheduleMap.get(key) ;
			for (StudentActivity Student : Students) {
				StudentActivityList.add(Student);
			}
		}
		
		StudentActivity[] studentActivities = new StudentActivity[StudentActivityList.size()];
		StudentActivityList.toArray(studentActivities);
		Arrays.sort(studentActivities, new StudentActByStudentComparator() );
		 
		 for (StudentActivity studentAct : studentActivities) {	
			String actCode = studentAct.getAct() + "-" + studentAct.getHour();
			sb.append(actCode)
					.append(STUDENT_SCHEDULE_FILE_DELIMITER).append(studentAct.getName())
					.append(STUDENT_SCHEDULE_FILE_DELIMITER).append(studentAct.getGrade())
					.append(STUDENT_SCHEDULE_FILE_DELIMITER).append(studentAct.getTeacher());
					
					Activity act = mainSched.getActCapacityMap().get(actCode);
		            if (act != null) {
			            sb.append(STUDENT_SCHEDULE_FILE_DELIMITER).append(act.getLocation())
						.append(STUDENT_SCHEDULE_FILE_DELIMITER).append(act.getAltLocation());
		            }

					sb.append("\n");
			
			new AllStudentFileWriter().writeFile(sb.toString());
		}
	}
	
	public void printRosterByActivity(Map< String,List<StudentActivity> > scheduleMap) {
		 Object[] keys = scheduleMap.keySet().toArray();
		 Arrays.sort(keys);
		 
 		 for (Object key : keys) {			
			List<StudentActivity> Students = scheduleMap.get((String)key) ;
			// Set html header
			for (StudentActivity Student : Students) {
				System.out.println(key + "|" + Student.getName());		
			}
			// Set html footer
		}
	}
	
	public void printStudentSchedule(Map< String,List<StudentActivity> > scheduleMap) {
		List<StudentActivity> StudentActivityList = new ArrayList<StudentActivity>();
		
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<StudentActivity> Students = scheduleMap.get(key) ;
			for (StudentActivity Student : Students) {
				//System.out.println(key + "|" + Student.name);
				StudentActivityList.add(Student);
			}
		}
		
		StudentActivity[] studentActivities = new StudentActivity[StudentActivityList.size()];
		StudentActivityList.toArray(studentActivities);
		Arrays.sort(studentActivities, new StudentActByStudentComparator() );
		 
		String name = "_BEGIN";
		for (StudentActivity studentAct : studentActivities) {	
			 if (name == null || !name.equals(studentAct.getName()) ) {
				 name = studentAct.getName();
				 // If not first, set footer hnml
				 // Set header html
			 }
			 System.out.println(studentAct.getAct() + "-" + studentAct.getHour() + "|" + name + " " + studentAct.getName() + "|" + studentAct.getGrade());		
		}
		// Pg end.  Set footer html
	}

}
