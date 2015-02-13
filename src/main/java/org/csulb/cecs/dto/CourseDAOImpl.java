package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAOImpl implements CourseDAO{

	@Autowired
	SessionFactory _sessionFactory;
	//Session session = _sessionFactory.openSession();
	@Override
	public Long addCourse(Course course) throws HibernateException {
		/*session.beginTransaction();
		Long id = (Long) session.save(course);
		session.getTransaction().commit();
		session.close();
		return id;*/
		return 0L;
	}

	@Override
	public int updateCourse(Course course) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getCourseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
