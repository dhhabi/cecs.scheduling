package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.engine.internal.Cascade;

@Entity
public class Survey {
	
	@Id
	@GeneratedValue
	private Long surveyId; 
	
	@NotNull(message="Can not be null")
	private String instructorEmailId;
	
	@NotNull(message="Can not be null")
	private String semester;
	
	@NotNull(message="Can not be null")
	private String year;
	
	//@NotNull(message="Can not be null")
	private int noOfCourseWantToTeach;
	
	@ElementCollection
	@ManyToMany(cascade=CascadeType.ALL)
	private final List<Course> preferredCourses = new ArrayList<Course>();
	
	
	@ElementCollection
	@ManyToMany(cascade=CascadeType.ALL)
	private final List<Room> preferredRooms = new ArrayList<Room>();
	
	@ElementCollection
	@OneToMany(cascade=CascadeType.ALL)
	private final List<Availability> availabilityList = new ArrayList<Availability>();
		
	public Survey(){
		
	}
	
	
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getInstructorEmailId() {
		return instructorEmailId;
	}

	public void setInstructorEmailId(String instructorEmailId) {
		this.instructorEmailId = instructorEmailId;
	}

	public int getNoOfCourseWantToTeach() {
		return noOfCourseWantToTeach;
	}

	public void setNoOfCourseWantToTeach(int noOfCourseWantToTeach) {
		this.noOfCourseWantToTeach = noOfCourseWantToTeach;
	}

	public List<Course> getPreferredCourses() {
		return preferredCourses;
	}

	public List<Availability> getAvailabilityList() {
		return availabilityList;
	}

	public List<Room> getPreferredRooms() {
		return preferredRooms;
	}


	public Long getSurveyId() {
		return surveyId;
	}

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
	
	
}
