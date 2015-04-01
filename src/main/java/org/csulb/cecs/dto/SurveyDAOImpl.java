package org.csulb.cecs.dto;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Survey;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SurveyDAOImpl implements SurveyDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
		
	@Override
	public void saveSurvey(Survey survey) {
		getSession().save(survey);
	}

	@Override
	public Survey getSurvey(String surveyId) {
		Survey survey = (Survey) getSession().createQuery("from Survey where surveyId =:surveyId")
				.setParameter("surveyId", surveyId)
				.uniqueResult();
		Hibernate.initialize(survey);
		survey.getAvailabilityList().size();
		survey.getPreferredCourses().size();
		survey.getPreferredRooms().size();
		//survey.getAvailablityTable().size();
		return survey;
	}


	@Override
	public boolean isAlreadyExist(String instructorEmailId, String semester,
			String year) {
		return (getSession().createQuery("select 1 from Survey s where s.instructorEmailId =:instructorEmailId and s.semester =:semester and s.year=:year")
				.setParameter("instructorEmailId", instructorEmailId)
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult() != null);
	}


	@Override
	public Long getSurveyId(String instructorEmailId, String semester,
			String year) {
		return (Long) getSession().createQuery("select surveyId from Survey s where s.instructorEmailId =:instructorEmailId and s.semester =:semester and s.year=:year").uniqueResult();
	}

}
