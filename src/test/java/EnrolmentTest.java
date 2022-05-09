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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import system.enrolment;


class EnrolmentTest {
	final String memberID = "GRGMCN76P26F462P";
	final int raceID = 100;
	final int boatID = 111;
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	enrolment enrolmentTest;
	SimpleDateFormat sdt;

	@BeforeEach
	public void setup() throws SQLException {
		sdt = new SimpleDateFormat("YYYY-MM-dd");
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
        
        enrolmentTest = new enrolment();
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testEnrolmentStringIntIntDate() throws SQLException, ParseException {
		Date date =  sdt.parse("2012-02-01");
		
        when(resultSetMock.getString("memberID")).thenReturn(memberID);
        when(resultSetMock.getInt("raceID")).thenReturn(raceID);
        when(resultSetMock.getInt("boatID")).thenReturn(boatID);
        when(resultSetMock.getDate("date")).thenReturn(new java.sql.Date(date.getTime()));
        
        enrolmentTest = new enrolment(memberID, raceID, boatID, new java.sql.Date(date.getTime()));
        assertAll(() -> assertEquals(memberID, enrolmentTest.getMemberID()),
				() -> assertEquals(raceID, enrolmentTest.getRaceID()),
				() -> assertEquals(boatID, enrolmentTest.getBoatID()),
				() -> assertEquals(date, enrolmentTest.getDate())
				);
	}

	@Test
	void testMemberIsEnrolled() {
		assertTrue(enrolmentTest.memberIsEnrolled(memberID, raceID));
	}

}
