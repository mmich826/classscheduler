package net.mich.explore.scheduler;

import java.util.List;

import org.apache.log4j.Logger;

import net.mich.explore.Activity;
import net.mich.explore.SchedulerMain;
import net.mich.explore.Student;
import net.mich.explore.StudentActivity;

public class ActivityScheduler {
	
	private static final Logger LOGGER = Logger.getLogger(ActivityScheduler.class);
	
	public void schedule(SchedulerMain mainSched, List<Student> studentList) { 
		List<StudentActivity> clazList = null;
		StudentActivity studentActivity = null;
		
		try {
			for (int i = 0; i < 4; i++) {  // CLASS PRIORITY
				for(Student student : studentList) {   // StudentS
					
					studentActivity = new StudentActivity();
					studentActivity.setName(student.getName()); 
					try {
						studentActivity.setAct(student.getAct().get(i));
					} catch (IndexOutOfBoundsException e) {
						// Nothing to log.  Logged in prev loop
						continue;
					}
					studentActivity.setGrade(student.getGrade());
					studentActivity.setTeacher(student.getTeacher());
		            studentActivity.setHour(Integer.toString(i+1));
					
					List<Integer> gradeActHours = mainSched.getGradeScheduleMap2().get(studentActivity.getAct() + "-" + student.getGrade());
					boolean isGradeSpecActivity = gradeActHours != null && !gradeActHours.isEmpty();
					if (isGradeSpecActivity) continue;
					
					boolean isRegistrationSuccess = false;
					for(int j=0; j<4; j++) {   // ACT HOURS
					
						boolean isBooked = student.getActSchedList().get(j);
						if (isBooked) continue;
						
		                String actClassName = studentActivity.getAct() + "-" + (j+1);
		                studentActivity.setHour(String.valueOf(j+1));
						Activity act = mainSched.getActCapacityMap().get(actClassName);
						if (act == null) {
							StringBuilder sb = new StringBuilder();
							sb.append(studentActivity.getAct()).append("|").append(studentActivity.getName()).append("|")
								.append("Activity not found:  ").append(actClassName);
							System.out.println(sb.toString());
							continue;
						}
						
						if (act.isFull()) continue;
						
						clazList = mainSched.getScheduleMap().get(actClassName);	
						clazList.add(studentActivity);
						act.enrollmentIncr();
						student.getActSchedList().set(j, true);
						isRegistrationSuccess = true;
						break;
					}
					
					if (!isRegistrationSuccess) {
						StringBuilder sb = new StringBuilder();
						sb.append(studentActivity.getAct()).append("|").append(studentActivity.getName()).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						System.out.println(sb.toString());
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error scheduling:  " + studentActivity);
			e.printStackTrace();
		}
	}
		

}
