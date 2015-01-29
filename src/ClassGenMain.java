import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ClassGenMain {
	
	Map< String,Activity > actCapacityMap = null;
	Map< String,List<KidAct> > scheduleMap = null;
	Map< String,Integer > gradeScheduleMap = null;

	public static void main(String[] args) {
		
		ClassGenMain th = new ClassGenMain();
		try {
			List<Kid> kidList = th.genData();

			List<KidAct> clazList = null;
			
			// Schedule grade specific activities first
			for (int i = 0; i < 4; i++) {  // CLASSES
				for(Kid k : kidList) {   // KIDS
					KidAct ka = new KidAct();
					ka.name = k.name; 
					try {
						ka.act = k.act.get(i);
					} catch (IndexOutOfBoundsException e) {
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("No activity choice for ").append(i+1);
						System.out.println(sb.toString());
						continue;
					}
					ka.grade = k.grade;
	
					Integer actHour = th.gradeScheduleMap.get(ka.act + "-" + ka.grade);
					if (actHour != null && !k.actSchedList.get(actHour-1)) {						
						clazList = th.scheduleMap.get(ka.act + "-" + actHour );			
						clazList.add(ka);
						k.actSchedList.set(actHour-1, true);
					}
					else if ( actHour != null && k.actSchedList.get(actHour-1) ){
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						System.out.println(sb.toString());
					}
	
				}
			}
			
			// Now schedule remaining activities
			for (int i = 0; i < 4; i++) {  // CLASSES
				for(Kid k : kidList) {   // KIDS
					
					KidAct ka = new KidAct();
					ka.name = k.name; 
					try {
						ka.act = k.act.get(i);
					} catch (IndexOutOfBoundsException e) {
						// Nothing to log.  Logged in prev loop
						continue;
					}
					ka.grade = k.grade;

					Integer actHour = th.gradeScheduleMap.get(ka.act + "-" + k.grade);
					if (actHour != null) continue;
					
					boolean isRegistrationSuccess = false;
					for(int j=0; j<4; j++) {   // KID CLASSES
					
						boolean isBooked = k.actSchedList.get(j);
						if (isBooked) continue;
						
						String actClassName = ka.act + "-" + (j+1);
						Activity act = th.actCapacityMap.get(actClassName);
						if (act == null) {
							StringBuilder sb = new StringBuilder();
							sb.append(ka.act).append("|").append(ka.name).append("|")
								.append("Activity not found:  ").append(actClassName);
							System.out.println(sb.toString());
							continue;
						}
						
						if (act.isFull()) continue;
						
						clazList = th.scheduleMap.get(actClassName);			
						clazList.add(ka);
						act.enrollment++;
						k.actSchedList.set(j, true);
						isRegistrationSuccess = true;
						break;
					}
					
					if (!isRegistrationSuccess) {
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						System.out.println(sb.toString());
					}
				}
			}
			
			//System.out.println("-----------> End Loop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//th.printFullSchedule(th.scheduleMap);
		//th.printRosterByActivity(th.scheduleMap);
		th.printStudentSchedule(th.scheduleMap);
		
	}
	
	void printFullSchedule(Map< String,List<KidAct> > scheduleMap) {
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<KidAct> kids = scheduleMap.get(key) ;
			for (KidAct kid : kids) {
				System.out.println(key + "|  " + kid.name);		
			}
		}
	}
	
	void printRosterByActivity(Map< String,List<KidAct> > scheduleMap) {
		 Object[] keys = scheduleMap.keySet().toArray();
		 Arrays.sort(keys);
		 
		 for (Object key : keys) {			
			List<KidAct> kids = scheduleMap.get((String)key) ;
			for (KidAct kid : kids) {
				System.out.println(key + "|  " + kid.name);		
			}
		}
	}
	
	void printStudentSchedule(Map< String,List<KidAct> > scheduleMap) {
		List<KidAct> kidActList = new ArrayList<KidAct>();
		
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<KidAct> kids = scheduleMap.get(key) ;
			for (KidAct kid : kids) {
				//System.out.println(key + "|  " + kid.name);
				kidActList.add(kid);
			}
		}
		
		KidAct[] studentActivities = new KidAct[kidActList.size()];
		kidActList.toArray(studentActivities);
		Arrays.sort(studentActivities, new KidActByStudentComparator() );
		 
		 for (KidAct studentAct : studentActivities) {			
			System.out.println(studentAct.getAct() + "|  " + studentAct.getName());		
		}
	}
	
	
	public List<Kid> genData() {
		gradeScheduleMap = new HashMap< String,Integer >();
		gradeScheduleMap.put("bb-1", new Integer(1) );
		gradeScheduleMap.put("bb-2", new Integer(2) );
		gradeScheduleMap.put("bb-3", new Integer(3) );
		gradeScheduleMap.put("bb-4", new Integer(4) );
		gradeScheduleMap.put("fb-1", new Integer(1) );
		gradeScheduleMap.put("fb-2", new Integer(2) );
		gradeScheduleMap.put("fb-3", new Integer(3) );
		gradeScheduleMap.put("fb-4", new Integer(4) );
		
		scheduleMap = new HashMap< String,List<KidAct> >();
		scheduleMap.put("bb-1", new ArrayList<KidAct>() );
		scheduleMap.put("bb-2", new ArrayList<KidAct>() );
		scheduleMap.put("bb-3", new ArrayList<KidAct>() );
		scheduleMap.put("bb-4", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-1", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-2", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-3", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-4", new ArrayList<KidAct>() );
		scheduleMap.put("rock-1", new ArrayList<KidAct>() );
		scheduleMap.put("rock-2", new ArrayList<KidAct>() );
		scheduleMap.put("rock-3", new ArrayList<KidAct>() );
		scheduleMap.put("rock-4", new ArrayList<KidAct>() );
		scheduleMap.put("fb-1", new ArrayList<KidAct>() );
		scheduleMap.put("fb-2", new ArrayList<KidAct>() );
		scheduleMap.put("fb-3", new ArrayList<KidAct>() );
		scheduleMap.put("fb-4", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-1", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-2", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-3", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-4", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-1", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-2", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-3", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-4", new ArrayList<KidAct>() );
		scheduleMap.put("flag-1", new ArrayList<KidAct>() );
		scheduleMap.put("flag-2", new ArrayList<KidAct>() );
		scheduleMap.put("flag-3", new ArrayList<KidAct>() );
		scheduleMap.put("flag-4", new ArrayList<KidAct>() );
		
		
		actCapacityMap = new HashMap<String, Activity>();
		actCapacityMap.put("bb-1", new Activity(null, 2));
		actCapacityMap.put("bb-2", new Activity(null, 2));
		actCapacityMap.put("bb-3", new Activity(null, 2));
		actCapacityMap.put("bb-4", new Activity(null, 2));
		actCapacityMap.put("hockey-1", new Activity(null, 2));
		actCapacityMap.put("hockey-2", new Activity(null, 2));
		actCapacityMap.put("hockey-3", new Activity(null, 2));
		actCapacityMap.put("hockey-4", new Activity(null, 2));
		actCapacityMap.put("fb-1", new Activity(null, 2));
		actCapacityMap.put("fb-2", new Activity(null, 2));
		actCapacityMap.put("fb-3", new Activity(null, 2));
		actCapacityMap.put("fb-4", new Activity(null, 2));
		actCapacityMap.put("rock-1", new Activity(null, 2));
		actCapacityMap.put("rock-2", new Activity(null, 2));
		actCapacityMap.put("rock-3", new Activity(null, 2));
		actCapacityMap.put("rock-4", new Activity(null, 2));
		actCapacityMap.put("soccer-1", new Activity(null, 2));
		actCapacityMap.put("soccer-2", new Activity(null, 2));
		actCapacityMap.put("soccer-3", new Activity(null, 2));
		actCapacityMap.put("soccer-4", new Activity(null, 2));
		actCapacityMap.put("pizza-1", new Activity(null, 2));
		actCapacityMap.put("pizza-2", new Activity(null, 2));
		actCapacityMap.put("pizza-3", new Activity(null, 2));
		actCapacityMap.put("pizza-4", new Activity(null, 2));
		actCapacityMap.put("flag-1", new Activity(null, 2));
		actCapacityMap.put("flag-2", new Activity(null, 2));
		actCapacityMap.put("flag-3", new Activity(null, 2));
		actCapacityMap.put("flag-4", new Activity(null, 2));
		
		
		StudentReader reader = new StudentReader();
		List<Kid> kidList = reader.readStudentActivities();
		
		return kidList;
	}
	
	List<Kid> populateKidList() {
		List<Kid> kidList = new ArrayList<Kid>();
		Kid kid = new Kid();
		kid.name = "Mike M";
		kid.grade = "1";
		kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Willie W";
		kid.grade = "1";
		// TEST accid reg for same classes
		kid.act.add("bb"); kid.act.add("bb"); kid.act.add("bb"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Fetti M";
		kid.grade = "1";
		kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Dave M";
		kid.grade = "1";
		kid.act.add("flag"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Chris C";
		kid.grade = "2";
		kid.act.add("bb"); kid.act.add("fb"); kid.act.add("rock"); kid.act.add("soccer");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Bill B";
		kid.grade = "2";
		kid.act.add("hockey"); kid.act.add("soccer"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);

		kid = new Kid();
		kid.name = "Julie B";
		kid.grade = "3";
		kid.act.add("hockey"); kid.act.add("soccer"); kid.act.add("rock"); kid.act.add("bb");
		kidList.add(kid);

		for (int i=0; i<10; i++) {
			kid = new Kid();
			kid.name = "Kid " + i;
			kid.grade = "3";
			kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
			kidList.add(kid);	
		}

		return kidList;
	}

}

class Kid {
	String name;
	String grade;
	String teacher;
	List<String> act = new ArrayList<String>();
	List<Boolean> actSchedList = new ArrayList<Boolean>();
	
	public Kid() {
		super();
		for(int i=0; i<4; i++) {
			actSchedList.add(false);
		}
	}

	@Override
	public String toString() {
		return "Kid [name=" + name + ", grade=" + grade + ", teacher="
				+ teacher + ", act=" + act + ", actSchedList=" + actSchedList
				+ "]";
	}

}

class KidAct {
	String name;
	String act;
	String grade;
	String teacher;
	
	@Override
	public String toString() {
		return "KidAct [name=" + name + ", act=" + act + ", grade=" + grade
				+ "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
}

class Activity {
	String name;
	int capacity;
	int enrollment;
	
	public Activity(String name, int capacity) {
		super();
		this.capacity = capacity;
		this.name = name;
	}
	
	public boolean isFull() {
		return (capacity == enrollment);
	}

	@Override
	public String toString() {
		return "Activity [name=" + name + ", capacity=" + capacity
				+ ", enrollment=" + enrollment + "]";
	}
}