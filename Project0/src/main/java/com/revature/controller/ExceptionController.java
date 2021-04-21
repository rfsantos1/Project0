package com.revature.controller;

import com.revature.dto.MessageDTO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller{
	
private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	/*
	 * Exception handler
	 */
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx) -> {
		logger.warn("A user provided a bad parameter. Exception message is: \n" + e.getMessage());
		ctx.status(400); // Provide an appropriate status code, such as 400
		ctx.json(new MessageDTO(e.getMessage()));
	};
	
	private ExceptionHandler<ClientNotFoundException> clientNotFoundExceptionHandler = (e, ctx) -> {
		logger.warn("A user tried to retrieve a client, but it was not found. Exception message is \n" + e.getMessage());
		ctx.status(404); // 404 not found
		ctx.json(new MessageDTO(e.getMessage()));
	};
	
	private ExceptionHandler<DatabaseException> databaseExceptionHandler = (e, ctx) -> {
		logger.error("Could not connect to database. Exception message is \n" + e.getMessage());
		ctx.status(500);
		ctx.json(new MessageDTO(e.getMessage()));
	};

	@Override
	public void mapEndPoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		app.exception(ClientNotFoundException.class, clientNotFoundExceptionHandler);
		app.exception(DatabaseException.class, databaseExceptionHandler);
	}

}
