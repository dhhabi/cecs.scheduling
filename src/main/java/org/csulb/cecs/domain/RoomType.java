package org.csulb.cecs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface RoomType extends Serializable {
	
	public static final String LAB_ROOM="Lab Room";
	public static final String LECTURE_ROOM="Lecture Room";
	public static final String DISCUSSION_ROOM="Discussion Room";
	public static final String BALL_ROOM="Ballroom";
	public static final String HALL_ROOM="Hall";
	public static final String LARGE = "Large";
	public static final String SMALL = "Small";
	
	public static final String[] rooms ={LARGE, SMALL};
	
}
