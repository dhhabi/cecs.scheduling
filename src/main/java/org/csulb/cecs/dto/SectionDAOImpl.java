package org.csulb.cecs.dto;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SectionDAOImpl implements SectionDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public void updateSection(Section section) {
		getSession().update(section);
	}

}
