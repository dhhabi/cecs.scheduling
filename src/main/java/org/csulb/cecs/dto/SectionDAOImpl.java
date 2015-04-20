package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Section> getSections(Account account) {
		return getSession().createCriteria(Section.class)
			    .add(Restrictions.eq("instructor", account))
			    .list();
	}

}
