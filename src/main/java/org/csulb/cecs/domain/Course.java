package org.csulb.cecs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@IdClass(CoursePrimaryKey.class)
public class Course {
	
	@Id
	@NotEmpty(message="Prefix can not be blank")
	@Column(name="prefix")
	private String prefix;
	@Id
	@Column(name="courseno", nullable=false)
	@NotEmpty(message="Course No can not be blank.")
	private String courseNo;
	
	@Column(name="activity", nullable=false)
	@NotEmpty(message="Course No can not be blank.")
	private String activity;
	
	@Column(name="title", nullable=false)
	@NotEmpty(message="Course No can not be blank.")
	private String title;
	
	@Column(name="units")
	private int units;
		
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
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
	
	@Override
	public String toString(){
		return title + ", "+ prefix +" "+ courseNo;
	}
	
}
