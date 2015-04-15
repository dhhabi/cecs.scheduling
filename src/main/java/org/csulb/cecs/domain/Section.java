package org.csulb.cecs.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Section {
		
	@Id
	@GeneratedValue
	private Long sectionId;
	
	@ManyToOne
	private Course course;
	
	@ManyToOne
	private Instructor instructor;
	
	@ManyToOne
	private Room meetingRoom;
	
	@ManyToOne
	private Room labRoom;
	
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="startTime",column=@Column(name="meetingStartTime")),
			@AttributeOverride(name="endTime",column=@Column(name="meetingEndTime"))})
	private Interval meetingTiming;
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="startTime",column=@Column(name="labStartTime")),
			@AttributeOverride(name="endTime",column=@Column(name="labEndTime"))})
	private Interval labTiming;
	
	public Interval getMeetingTiming() {
		return meetingTiming;
	}

	public void setMeetingTiming(Interval meetingTiming) {
		this.meetingTiming = meetingTiming;
	}

	public Interval getLabTiming() {
		return labTiming;
	}

	public void setLabTiming(Interval labTiming) {
		this.labTiming = labTiming;
	}

	public Long getSectionId() {
		return sectionId;
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


}
