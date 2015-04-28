package org.csulb.cecs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import org.springframework.stereotype.Service;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

@Service
public class ClaraServiceImpl implements ClaraService{

	@SuppressWarnings("serial")
	@Override
	public StreamResource createProgram() {
		return new StreamResource(new StreamSource() {
			
			@Override
			public InputStream getStream() {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintWriter writer = new PrintWriter(bos, true);
				//Write the clara program lines from here 
				writer.write("//This is a auto-generated program, Please make sure all the entries are correct \n");
				writer.write("//\tCECS Class Scheduling");
				writer.flush();
				
				return new ByteArrayInputStream(bos.toByteArray());
			}
		}, "cecs_schedule");
	}

}
