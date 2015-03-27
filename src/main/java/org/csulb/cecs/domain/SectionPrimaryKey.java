package org.csulb.cecs.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SectionPrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String semester;
	private String year;
	
	
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
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(semester).append(year).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if( obj == null ) return false;
		if( obj == this ) return true;
		if( obj.getClass() != getClass()) return false;
		SectionPrimaryKey rhs = (SectionPrimaryKey)obj;
		return new EqualsBuilder().append(semester, rhs.semester).append(year, rhs.year).isEquals();
	}	
	
}
