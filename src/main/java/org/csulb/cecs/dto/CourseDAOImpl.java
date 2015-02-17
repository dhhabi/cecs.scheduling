package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Course;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
		return getSession().createCriteria(Course.class).list();
	}

	
	@Override
	public void deleteCourse(Course course) {
		getSession().delete(course);
	}

	@Override
	public boolean isAlreadyExist(String prefix, String courseNo) {
		return (getSession().createQuery("select 1 from Course c where c.prefix =:prefix and c.courseNo =:courseNo")
		.setParameter("prefix", prefix)
		.setParameter("courseNo", courseNo)
		.uniqueResult() != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> searchCourse(String searchString) {
		String search = "%"+searchString+"%";
		return getSession().createCriteria(Course.class)
			    .add( Restrictions.disjunction()
			            .add( Restrictions.ilike("title", search ) )
			            .add( Restrictions.ilike("prefix", search ) )
			            .add( Restrictions.ilike("courseNo", search ) )
			            .add( Restrictions.ilike("activity", search ) )
			        )
			    .list();
	}

}
