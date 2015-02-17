package org.csulb.cecs.test;


import static org.junit.Assert.*;

import java.util.List;

import org.csulb.cecs.dto.CourseDAO;
import org.csulb.cecs.dto.CourseDAOImpl;
import org.junit.Test;

public class CourseDAOTest {

	CourseDAO courseDao = new CourseDAOImpl();
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testCourseList(){
		List courses = courseDao.getAllCourses();
		System.out.print(courses.size());
		assertTrue(courses.size()>0);
	}
	@Test
	public void isAlreadyTest(){
		boolean isExists = courseDao.isAlreadyExist("CECS", "551");
		assertTrue(isExists);
	}
}
