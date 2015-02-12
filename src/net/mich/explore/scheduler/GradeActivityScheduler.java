package net.mich.explore.scheduler;

import java.util.List;

import org.apache.log4j.Logger;

import net.mich.explore.Activity;
import net.mich.explore.SchedulerMain;
import net.mich.explore.Student;
import net.mich.explore.StudentActivity;



public class GradeActivityScheduler {
	
	private static final Logger LOGGER = Logger.getLogger(GradeActivityScheduler.class);

	public void schedule(SchedulerMain mainSched, List<Student> studentList) {
		List<StudentActivity> clazList = null;
		StudentActivity studentAct = null;
		try {
	
			// Schedule grade specific activities first
			for (int i = 0; i < 4; i++) {  // CLASS PRIORITY
				for(Student student : studentList) {   // STUDENTS
					studentAct = new StudentActivity();
					studentAct.setName(student.getName()); 
					try {
						studentAct.setAct(student.getAct().get(i));
					} catch (IndexOutOfBoundsException e) {
						StringBuilder sb = new StringBuilder();
						sb.append(studentAct.getAct()).append("|").append(studentAct.getName()).append("|")
							.append("No activity choice for ").append(i+1);
						LOGGER.warn(sb.toString());
						continue;
					}
					studentAct.setGrade(student.getGrade());
					studentAct.setTeacher(student.getTeacher());
	
					List<Integer> gradeActHours = mainSched.getGradeScheduleMap2().get(studentAct.getAct() + "-" + studentAct.getGrade());
					boolean isRegistrationSuccess = false;
					boolean isGradeSpecActivity = gradeActHours != null && !gradeActHours.isEmpty();
					if (isGradeSpecActivity) {						
						for (int j=0; j<gradeActHours.size(); j++) {
							int hour = gradeActHours.get(j);
							if (student.getActSchedList().get(hour-1)) continue;  // true=Student booked this hour
							
							String actName = studentAct.getAct() + "-" + hour;
							Activity act = mainSched.getActCapacityMap().get(actName);
							if (act.isFull()) continue;
	
							clazList = mainSched.getScheduleMap().get(actName);							
							studentAct.setHour(Integer.toString(hour));
							clazList.add(studentAct);
							act.enrollmentIncr();
							student.getActSchedList().set(hour-1, true);
							isRegistrationSuccess = true;
							break;
						}
					}
					
					if ( isGradeSpecActivity && !isRegistrationSuccess ){
						StringBuilder sb = new StringBuilder();
						sb.append(studentAct.getAct()).append("|").append(studentAct.getName()).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						LOGGER.warn(sb.toString());
					}
	
				}
			}
		
		} catch (Exception e) {
			LOGGER.warn("Error scheduling:  " + studentAct);
			e.printStackTrace();
		}
	}

}
