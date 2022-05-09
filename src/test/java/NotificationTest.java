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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import system.Notification;


class NotificationTest {
	
	final int id = 1000;
	final String memberID = "GRGMCN76P26F462P";
	final String head = "heading";
	final String body = "body";
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Notification notificationTest;

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
        when(resultSetMock.getString("memberID")).thenReturn(memberID);
        when(resultSetMock.getString("head")).thenReturn(head);
        when(resultSetMock.getString("body")).thenReturn(body);
        
        notificationTest  = new Notification(id, memberID, head, body);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testNotificationIntStringStringString() {
		notificationTest = new Notification(1555, memberID, head, body);
		assertAll(() -> assertEquals(1555, notificationTest.getId()),
				() -> assertEquals(memberID, notificationTest.getMemberID()),
				() -> assertEquals(head, notificationTest.getHead()),
				() -> assertEquals(body, notificationTest.getBody())
				);
	}

	@Test
	void testIsRead() throws SQLException {
		when(resultSetMock.getBoolean("opened")).thenReturn(false);
		assertFalse(notificationTest.isRead());
	}

}
