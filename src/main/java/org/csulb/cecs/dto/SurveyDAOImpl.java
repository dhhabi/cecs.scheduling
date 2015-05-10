package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.Room;
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
		return (Long) getSession().createQuery("select surveyId from Survey s where s.instructorEmailId =:instructorEmailId and s.semester =:semester and s.year=:year")
				.setParameter("instructorEmailId", instructorEmailId)
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
	}


	@Override
	public void updateSurvey(Survey survey) {
		getSession().update(survey);		
	}


	@Override
	public Survey getSurveyWithPreferredCourses(String instructorEmailId, String semester,
			String year) {
		Survey survey = (Survey) getSession().createQuery("from Survey where instructorEmailId =:instructorEmailId and semester=:semester and year=:year")
				.setParameter("instructorEmailId", instructorEmailId)
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		
		if(survey!=null){
			Hibernate.initialize(survey);
			survey.getPreferredCourses().size();
		}
		return survey;
	}


	@Override
	public Survey getSurvey(String instructorEmailId, String semester,
			String year) {
		Survey survey = (Survey) getSession().createQuery("from Survey where instructorEmailId =:instructorEmailId and semester=:semester and year=:year")
				.setParameter("instructorEmailId", instructorEmailId)
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		if(survey!=null){
			Hibernate.initialize(survey);
			survey.getAvailabilityList().size();
			survey.getPreferredCourses().size();
			survey.getPreferredRooms().size();
		}
		//survey.getAvailablityTable().size();
		return survey;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Survey> getAllSurvey(String semester, String year) {
		return getSession().createCriteria(Survey.class)
				.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllInstructorEmailIds(String semester, String year) {
		return getSession().createQuery("select instructorEmailId from Survey s where s.semester =:semester and s.year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.list();
	}

}
