package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccounts() {
		return getSession().createCriteria(Account.class).list();
	}

}
