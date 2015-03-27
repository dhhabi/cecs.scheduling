package org.csulb.cecs.ui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
/**
 * View tokens are String Constants, used for navigation purpose 
 * @author Gurprit Singh
 *
 */
public interface ViewToken extends Serializable {
	
	public static final String HOME="";
	public static final String USER="/user";
	public static final String ADMIN="/admin";
	public static final String ADMIN_HIDDEN="/hidden";
	public static final String SIGNIN="/signin";
	public static final String SIGNUP="/signup";
	public static final String SURVEY="/survey";
	public static final String SURVEYREQUEST="/surveyRequest";
	public static final String COURSE="/course";
	public static final String ROOMS="/rooms";	
	public static final String LABS="/labs";
	public static final String PROJECT="/project";
	
	public static final List<String> VALID_TOKENS = Arrays.asList(new String[] {HOME, USER, ADMIN, ADMIN_HIDDEN, SIGNIN, SIGNUP,SURVEY,SURVEYREQUEST, 
			COURSE,ROOMS,
			LABS, PROJECT
			});		
}
