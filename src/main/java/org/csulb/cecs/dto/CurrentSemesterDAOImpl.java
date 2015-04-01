package org.csulb.cecs.dto;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.CurrentSemester;
import org.csulb.cecs.domain.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CurrentSemesterDAOImpl implements CurrentSemesterDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public void setCurrentSemester(CurrentSemester currentSemester) {
		getSession().saveOrUpdate(currentSemester);		
	}

	@Override
	public CurrentSemester getCurrentSemester() {
		return (CurrentSemester)getSession().createQuery("from CurrentSemester where id =:id")
		.setParameter("id", 1)
		.uniqueResult();
	}

}
