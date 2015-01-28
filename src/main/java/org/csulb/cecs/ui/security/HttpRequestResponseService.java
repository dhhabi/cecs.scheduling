package org.csulb.cecs.ui.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestResponseService {
	
	public HttpServletRequest getCurrentRequest();
	
	public HttpServletResponse getCurrentResponse();
	
}
