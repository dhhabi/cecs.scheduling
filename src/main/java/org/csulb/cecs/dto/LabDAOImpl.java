package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Lab;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class LabDAOImpl implements LabDAO {

	
	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addLab(Lab lab) throws HibernateException {
		getSession().save(lab);
	}

	@Override
	public void updateLab(Lab lab) {
		getSession().update(lab);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lab> getAllLabs() {
		return getSession().createCriteria(Lab.class)
				.add(Restrictions.ne("labType", "none"))
				.list();
	}

	@Override
	public boolean isAlreadyExist(String building, String roomNo) {
		return (getSession().createQuery("select 1 from Room r where r.building =:building and r.roomNo =:roomNo")
				.setParameter("building", building)
				.setParameter("roomNo", roomNo)
				.uniqueResult() != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lab> searchLab(String searchString) {
		String search = "%"+searchString+"%";
		return getSession().createCriteria(Lab.class)
			    .add( Restrictions.disjunction()
			    		.add(Restrictions.ne("labType", "none"))
			            .add( Restrictions.ilike("building", search ) )
			            .add( Restrictions.ilike("roomNo", search ) )
			            .add( Restrictions.ilike("labType", search ) )
			        )
			    .list();
	}

	@Override
	public void deleteRoom(Lab lab) {
		getSession().delete(lab);		
	}

}
