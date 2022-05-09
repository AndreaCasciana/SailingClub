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
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import system.Race;

class RaceTest {
	
	final int id = 100;
	final String name = "Race";
	final Timestamp date = Timestamp.valueOf("2022-09-01 09:01:15");
	final double fee = 5;
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Race raceTest;

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
        when(resultSetMock.getTimestamp("date")).thenReturn(date);
        when(resultSetMock.getDouble("fee")).thenReturn(fee);
        
        raceTest  = new Race(id, name, date, fee);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testRaceIntStringTimestampDouble() {
		raceTest = new Race(105, name, date, fee);
		assertAll(() -> assertEquals(105, raceTest.getId()),
				() -> assertEquals(name, raceTest.getName()),
				() -> assertEquals(date, raceTest.getDate()),
				() -> assertEquals(fee, raceTest.getFee())
				);
	}

	@Test
	void testGetRaces() throws SQLException {
		ArrayList<Race> races = new ArrayList<Race> ();
		races.add(new Race(101, "race1", Timestamp.valueOf("2022-05-05 09:15:15"), 15.90));
		races.add(new Race(102, "race2", Timestamp.valueOf("2022-06-11 02:01:15"), 24.90));
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(races.get(0).getId()).thenReturn(races.get(1).getId());
        when(resultSetMock.getString("name")).thenReturn(races.get(0).getName()).thenReturn(races.get(1).getName());
        when(resultSetMock.getTimestamp("date")).thenReturn(races.get(0).getDate()).thenReturn(races.get(1).getDate());
        when(resultSetMock.getDouble("fee")).thenReturn(races.get(0).getFee()).thenReturn(races.get(1).getFee());
		
        ArrayList<Race> racesRecieved = raceTest.getRaces("Upcoming");
		assertAll(() -> assertEquals(races.get(0).getId(), racesRecieved.get(0).getId()),
				() -> assertEquals(races.get(0).getName(), racesRecieved.get(0).getName()),
				() -> assertEquals(races.get(0).getDate(), racesRecieved.get(0).getDate()),
				() -> assertEquals(races.get(0).getFee(), racesRecieved.get(0).getFee()),
				() -> assertEquals(races.get(1).getId(), racesRecieved.get(1).getId()),
				() -> assertEquals(races.get(1).getName(), racesRecieved.get(1).getName()),
				() -> assertEquals(races.get(1).getDate(), racesRecieved.get(1).getDate()),
				() -> assertEquals(races.get(1).getFee(), racesRecieved.get(1).getFee())
				);
	}
	
	@Test
	void testIsCancelled() throws SQLException { //test also testIsCancelledInt()
		when(resultSetMock.getBoolean("cancelled")).thenReturn(false);
		assertFalse(raceTest.isCancelled());
	}

	@Test
	void testIsFinished() throws SQLException { // test also testIsFinishedInt
		when(resultSetMock.getBoolean("cancelled")).thenReturn(true);
		assertTrue(raceTest.isCancelled());
	}

	@Test
	void testGetNumberEnrolled() throws SQLException {
		when(resultSetMock.getInt(1)).thenReturn(10);
		assertEquals(raceTest.getNumberEnrolled(), 10);
	}

	@Test
	void testCancel() {
		assertTrue(raceTest.cancel());
	}

	@Test
	void testChangeRaceDetails() throws SQLException {
		assertTrue(raceTest.changeRaceDetails(name, date));
	}

	@Test
	void testGetRace() {
		Race raceReturned = raceTest.getRace(id);
		assertAll(() -> assertEquals(raceReturned.getId(), raceTest.getId()),
				() -> assertEquals(raceReturned.getName(), raceTest.getName()),
				() -> assertEquals(raceReturned.getDate(), raceTest.getDate()),
				() -> assertEquals(raceReturned.getFee(), raceTest.getFee())
				);
	}

	@Test
	void testAddRace() throws SQLException {
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("LAST_INSERT_ID()")).thenReturn(id);
		Race raceReturned = raceTest.addRace(raceTest);
		assertAll(() -> assertEquals(raceReturned.getId(), raceTest.getId()),
				() -> assertEquals(raceReturned.getName(), raceTest.getName()),
				() -> assertEquals(raceReturned.getDate(), raceTest.getDate()),
				() -> assertEquals(raceReturned.getFee(), raceTest.getFee())
				);
	}

}
