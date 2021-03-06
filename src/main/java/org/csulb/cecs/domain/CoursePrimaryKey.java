package org.csulb.cecs.domain;

import java.io.Serializable;



import javax.persistence.Column;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;


public class CoursePrimaryKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6809713921762087964L;

	public CoursePrimaryKey() {
		// TODO Auto-generated constructor stub
	}
	
	public CoursePrimaryKey(String prefix,String courseNo){
		this.prefix=prefix;
		this.courseNo=courseNo;
	}
	
	@NotEmpty(message="Prefix can not be blank")
	@Column(name="prefix")
	private String prefix;
	
	@Column(name="courseno", nullable=false)
	@NotEmpty(message="Course No can not be blank.")
	private String courseNo;
		
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(prefix).append(courseNo).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if( obj == null ) return false;
		if( obj == this ) return true;
		if( obj.getClass() != getClass()) return false;
		CoursePrimaryKey rhs = (CoursePrimaryKey)obj;
		return new EqualsBuilder().append(prefix, rhs.prefix).append(courseNo, rhs.courseNo).isEquals();
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
