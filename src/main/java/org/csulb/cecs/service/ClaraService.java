package org.csulb.cecs.service;

import com.vaadin.server.StreamResource;

public interface ClaraService {
	
	public StreamResource createProgram(String semester,String year);
	
}
