package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class ScheduleProject{
	
	@Id
	@GeneratedValue
	private Long id;

	private String year;
	
	private String semester;
	
	@ElementCollection
	@ManyToMany
	private final List<Account> instructorList = new ArrayList<Account>();
	
	@ElementCollection
	@ManyToMany
	private final List<Course> courseList = new ArrayList<Course>();

	@ElementCollection
	@ManyToMany
	private final List<Room> roomList = new ArrayList<Room>();
	
	@ElementCollection
	@OneToMany
	private final List<Section> sections = new ArrayList<Section>();

	public Long getId() {
		return id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public List<Account> getInstructorList() {
		return instructorList;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public List<Section> getSections() {
		return sections;
	}
	
	
	
	
}
