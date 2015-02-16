package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Course;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CourseDAOImpl implements CourseDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addCourse(Course course) throws HibernateException {
		getSession().save(course);
	}

	@Override
	public void updateCourse(Course course) {
		getSession().update(course);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getAllCourses() {
		return getSession().createQuery("from course").list();
	}

	@Override
	public Course getCourseById(Long id) {
		return (Course) getSession().createQuery("from Course where id=:id")
				.setParameter("id", id)
				.uniqueResult();
	}

	@Override
	public void deleteCourse(Course course) {
		getSession().delete(course);		
	}

}
