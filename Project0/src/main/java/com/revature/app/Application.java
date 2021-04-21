package com.revature.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.StaticFieldController;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Client;
import com.revature.service.ClientService;
import com.revature.util.ConnectionUtil;

import io.javalin.Javalin;

public class Application {
	
	private static Javalin app;
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		
		ClientService clientService = new ClientService();
		
		app = Javalin.create();
		
		app.before(ctx -> {
			String URI = ctx.req.getRequestURI();
			String httpMethod = ctx.req.getMethod();
			logger.info(httpMethod + " request to endpoint " + URI + " received");
		});
		
		mapControllers(new ClientController(), new ExceptionController(), 
				new StaticFieldController());
		app.start(7000);
		
		try {
			List<Client> clients = clientService.getClients();
			System.out.println(clients);
			
			Client client = clientService.getClientById(2);
			System.out.println(client);
			
		} catch (DatabaseException e1) {
			
			System.out.println(e1.getMessage());
		} catch (ClientNotFoundException e2) {
			System.out.println(e2.getMessage());
		}
		
		// TODO Auto-generated method stub

	}
	
	public static void mapControllers(Controller... controllers) {
		for(int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndPoints(app);
		}
		
	}

}
