package com.revature.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class StaticFieldController implements Controller {
	
	private Handler createFileHandler(String classPath) {
		return ctx -> {
			InputStream is = StaticFieldController.class.getResourceAsStream(classPath);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read;
			byte[] buffer = new byte[1024];
			
			while((read = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}
			
			byte[] ourFileInBytes = baos.toByteArray();
			
			String html = new String(ourFileInBytes);
			ctx.html(html);
			ctx.status(200);
		};
	}

	@Override
	public void mapEndPoints(Javalin app) {
		app.get("/", createFileHandler("/static/index.html"));
		app.get("/test", createFileHandler("/static/test.html"));
	}

}
