package org.csulb.cecs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Section {
		
	@Id
	@GeneratedValue
	private Long sectionId;
	
	@NotNull(message="Semester cannot be null")
	private String semester;
	
	@NotNull(message="Year can not be null")
	private String year;
	
	@OneToOne
	private Course course;
	
	@OneToOne
	private Instructor instructor;
	
	@OneToOne
	private Room meetingRoom;
	
	@OneToOne
	private Room labRoom;
	
		
	private DayTime timing;
	
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Room getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(Room meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public Room getLabRoom() {
		return labRoom;
	}

	public void setLabRoom(Room labRoom) {
		this.labRoom = labRoom;
	}

	public DayTime getTiming() {
		return timing;
	}

	public void setTiming(DayTime timing) {
		this.timing = timing;
	}

}
