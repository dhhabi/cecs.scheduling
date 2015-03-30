package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.csulb.cecs.domain.account.Account;
import org.joda.time.LocalTime;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

@Entity
public class Survey {
	
	@Id
	@GeneratedValue
	private Long surveyId; 
	
	//@NotNull(message="Can not be null")
	private String instructorEmailId;
	
	//@NotNull(message="Can not be null")
	private String semester;
	
	//@NotNull(message="Can not be null")
	private String year;
	
	//@NotNull(message="Can not be null")
	private int noOfCourseWantToTeach;
	
	@ElementCollection
	@OneToMany
	private List<Course> preferredCourses = new ArrayList<Course>();
	
	
	@ElementCollection
	@OneToMany
	private List<Room> preferredRooms = new ArrayList<Room>();
	
	@Lob
	Table<LocalTime, String, Boolean> availablityTable = HashBasedTable.create();

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

	public List<Room> getPrefferredRooms() {
		return preferredRooms;
	}

	public Table<LocalTime, String, Boolean> getAvailablityTable() {
		return availablityTable;
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
