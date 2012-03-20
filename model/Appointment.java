package model;

import java.util.Date;

public class Appointment{

	private Date dateOfMeeting;
	private String description;
	private int start, end;

	
	public Appointment(Date dateOfMeeting, String description, int start, int end) {
		this.dateOfMeeting = dateOfMeeting;
		this.description = description;
		this.start = start;
		this.end = end;
	}
	/**
	 * Returns true of no overlap, false if they overlap
	 * @param Appointment compareTo: the meeting to compare to
	 * @return
	 */
	
	public boolean timeCheck(Appointment compareTo){
		if(this.getDateOfMeeting().equals(compareTo.getDateOfMeeting())){
			if(this.getStart() >= compareTo.getEnd() || this.getEnd() <= compareTo.getStart()){
				return true;
			}
			else 
				return false;
		}
		else
			return true;
	}

	public Date getDateOfMeeting() {
		return dateOfMeeting;
	}

	public void setDateOfMeeting(Date dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	
	
}
