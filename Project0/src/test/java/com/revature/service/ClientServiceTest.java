package com.revature.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;

import static org.mockito.ArgumentMatchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Client;
import com.revature.repo.ClientRepo;

public class ClientServiceTest {
	
	private static ClientRepo mockClientRepo = new ClientRepo();
	public ClientService mockClientService;
	
	private static Connection mockConnection;
	
	@BeforeClass
	public static void setUp() throws DatabaseException, ClientNotFoundException {
		mockConnection = mock(Connection.class);
		when(mockClientRepo.getClientById(eq("2"))).thenReturn(new Client(2, "Johnny", "boy", 1400));
	}
	
	@Before
	public void BeforeEachTest() {
		mockClientService = new ClientService();
	}

	@Test
	public void Test_getClientById() throws DatabaseException, ClientNotFoundException {
		Client mockClient = mockClientService.getClientById(2);
		Client mockClient2 = new Client(2, "Johnny", "boy", 1400);
		assertEquals(mockClient2, mockClient);
		
				fail("It failed.");
	}

}
