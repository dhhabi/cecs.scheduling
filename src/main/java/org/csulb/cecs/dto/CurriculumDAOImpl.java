package org.csulb.cecs.dto;

import org.csulb.cecs.domain.Curriculum;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CurriculumDAOImpl implements CurriculumDAO {

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean isAlreadyExist(String curriculumName) {
		return (getSession().createQuery("select 1 from Curriculum c where c.name =:name")
				.setParameter("name", curriculumName)
				.uniqueResult() != null);
	}

	@Override
	public void saveCurriculum(Curriculum curriculum) {
		getSession().saveOrUpdate(curriculum);
	}

	@Override
	public Curriculum getCurriculum(String curriculumName) {
		Curriculum curriculum = (Curriculum) getSession().createQuery("from Curriculum where name =:name")
				.setParameter("name", curriculumName)
				.uniqueResult();
		Hibernate.initialize(curriculum);
		curriculum.getCurriculumCourseList().size();
		return curriculum;
	}

}
