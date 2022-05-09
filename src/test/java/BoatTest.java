package test.java;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import system.Boat;

class BoatTest {
	final int id = 1000;
	final String name = "Boat Name";
	final String owner = "KNGVSS98R66Z335F";
	final double length = 25.5;
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Boat boatTest;

	@BeforeEach
	public void setup() throws SQLException {
		resultSetMock  = mock(ResultSet.class);
		statement = mock(Statement.class);
		jdbcConnection = mock(Connection.class);
		
		driverManagerMock = mockStatic(DriverManager.class);
		driverManagerMock.when(() -> DriverManager.getConnection(anyString(),anyString(),anyString()))
		          .thenReturn(jdbcConnection);
		assertTrue(DriverManager.getConnection(anyString(),anyString(),anyString()) == jdbcConnection);


		when(jdbcConnection.createStatement()).thenReturn(statement);
		when(statement.executeQuery(anyString())).thenReturn(resultSetMock);
		when(statement.executeUpdate(anyString())).thenReturn(1);
        
        when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getInt("id")).thenReturn(id);
        when(resultSetMock.getString("name")).thenReturn(name);
        when(resultSetMock.getString("owner")).thenReturn(owner);
        when(resultSetMock.getDouble("lengthInFeet")).thenReturn(length);
        
        boatTest  = new Boat(id, name, owner, length);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testBoatIntStringStringDouble() {
		boatTest = new Boat(1555, name, owner, length);
		assertAll(() -> assertEquals(1555, boatTest.getId()),
				() -> assertEquals(name, boatTest.getName()),
				() -> assertEquals(owner, boatTest.getOwner()),
				() -> assertEquals(length, boatTest.getLength())
				);
	}

	@Test
	void testGetBoats() throws SQLException {
		ArrayList<Boat> boats = new ArrayList<Boat> ();
		boats.add(new Boat(1011, "boat1", "GRGMCN76P26F462P", 25.90));
		boats.add(new Boat(1022, "boat2", "GRGMCN76P26F462P", 14.90));
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(boats.get(0).getId()).thenReturn(boats.get(1).getId());
        when(resultSetMock.getString("name")).thenReturn(boats.get(0).getName()).thenReturn(boats.get(1).getName());
        when(resultSetMock.getString("owner")).thenReturn(boats.get(0).getOwner()).thenReturn(boats.get(1).getOwner());
        when(resultSetMock.getDouble("lengthInFeet")).thenReturn(boats.get(0).getLength()).thenReturn(boats.get(1).getLength());
		
        ArrayList<Boat> racesRecieved = boatTest.getBoats("GRGMCN76P26F462P");
		assertAll(() -> assertEquals(boats.get(0).getId(), racesRecieved.get(0).getId()),
				() -> assertEquals(boats.get(0).getName(), racesRecieved.get(0).getName()),
				() -> assertEquals(boats.get(0).getOwner(), racesRecieved.get(0).getOwner()),
				() -> assertEquals(boats.get(0).getLength(), racesRecieved.get(0).getLength()),
				() -> assertEquals(boats.get(1).getId(), racesRecieved.get(1).getId()),
				() -> assertEquals(boats.get(1).getName(), racesRecieved.get(1).getName()),
				() -> assertEquals(boats.get(1).getOwner(), racesRecieved.get(1).getOwner()),
				() -> assertEquals(boats.get(1).getLength(), racesRecieved.get(1).getLength())
				);
	}

	@Test
	void testRemoveBoat() {
		assertTrue(boatTest.removeBoat(id));
	}

	@Test
	void testGetBoat() {
		assertTrue(boatTest.removeBoat(id));
	}

	@Test
	void testIsValidStorage() {
		assertTrue(boatTest.isValidStorage());
	}

}
