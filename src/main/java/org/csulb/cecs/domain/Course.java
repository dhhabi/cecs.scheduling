package org.csulb.cecs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Course {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotEmpty(message="Prefix can not be blank")
	@Column(name="prefix")
	private String prefix;
	
	@Column(name="courseno", nullable=false)
	@NotEmpty(message="Course No can not be blank.")
	private String courseNo;
	
	@Column(name="activity",nullable=false)
	private String activity;
		
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}	
	
}
