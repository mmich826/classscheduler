package net.mich.explore;

public class Activity {
	private String name;
	private int capacity;
	private int enrollment;
	
	public Activity(String name, int capacity) {
		super();
		this.capacity = capacity;
		this.name = name;
	}
	
	public boolean isFull() {
		return (capacity == enrollment);
	}
	
	public boolean enrollmentIncr() {
		if (enrollment < capacity) {
			enrollment++;
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Activity [name=" + name + ", capacity=" + capacity
				+ ", enrollment=" + enrollment + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(int enrollment) {
		this.enrollment = enrollment;
	}
}