package net.mich.explore.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mich.explore.SchedulerMain;
import net.mich.explore.StudentActByStudentComparator;
import net.mich.explore.StudentActivity;

import org.apache.log4j.Logger;

public class StudentReportGenerator implements ReportGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(StudentReportGenerator.class);

	public void generate(SchedulerMain mainSched) {

		Map< String,List<StudentActivity> > scheduleMap = mainSched.getScheduleMap();
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