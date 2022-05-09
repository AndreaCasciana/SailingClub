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

import system.Boat;
import system.Member;
import system.Notification;
import system.Race;

class MemberTest {
	
	final private String name = "Vanessa";
	final private String surname = "Kingsley";
	final private String address = "Via Giuseppe Rossi, 13";
	final private String id = "KNGVSS98R66Z335F";
	final private String password = "ehi";
	
	// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Member memberTest;

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
		when(resultSetMock.getString("id")).thenReturn(id);
        when(resultSetMock.getString("name")).thenReturn(name);
        when(resultSetMock.getString("surname")).thenReturn(surname);
        when(resultSetMock.getString("address")).thenReturn(address);
        when(resultSetMock.getString("password")).thenReturn(password);
        
		memberTest  = new Member(name, surname, address, id, password);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	 }
	
	@Test
	void testMemberStringStringStringStringString() { // constructor
		memberTest = new Member("Maria", surname, address, id, password);
		assertAll(() -> assertEquals("Maria", memberTest.getName()),
				() -> assertEquals(surname, memberTest.getSurname()),
				() -> assertEquals(address, memberTest.getAddress()),
				() -> assertEquals(id, memberTest.getId()),
				() -> assertEquals(password, memberTest.getPassword()));
		
	}

	@Test
	void testLogin() throws SQLException {
		assertTrue(memberTest.login(id, password));
		assertTrue(memberTest.sessionOn);
	}

	@Test
	void testExists() {
		assertTrue(memberTest.exists(id));
	}

	@Test
	void testRegister() throws SQLException {
		when(resultSetMock.getString("id")).thenReturn("DCPGRL57B08G917R");
		assertTrue(memberTest.register(new Member(name, surname, address, "DCPGRL57B08G917R", password)));
		assertEquals(memberTest.getId(), "DCPGRL57B08G917R");
	}

	@Test
	void testSetNewName() throws SQLException {
		String newName = "Nessa";
		when(resultSetMock.getString("name")).thenReturn(newName);
		assertTrue(memberTest.setNewName(newName));
		assertEquals(memberTest.getName(), newName);
	}

	@Test
	void testSetNewSurname() throws SQLException {
		String newSurname = "King";
		when(resultSetMock.getString("surname")).thenReturn(newSurname);
		assertTrue(memberTest.setNewSurname(newSurname));
		assertEquals(memberTest.getSurname(), newSurname);
	}

	@Test
	void testSetNewAddress() throws SQLException {
		String newAddress = "Via Bixio, 19";
		when(resultSetMock.getString("address")).thenReturn(newAddress);
		assertTrue(memberTest.setNewAddress(newAddress));
		assertEquals(memberTest.getAddress(), newAddress);
	}

	@Test
	void testSetNewPassword() throws SQLException {
		String newPassword = "vane";
		when(resultSetMock.getString("password")).thenReturn(newPassword);
		assertTrue(memberTest.setNewPassword(newPassword));
		assertEquals(memberTest.getPassword(), newPassword);
	}

	@Test
	void testGetRaces() throws SQLException {
		ArrayList<Race> races = new ArrayList<Race> ();
		races.add(new Race(101, "race1", Timestamp.valueOf("2022-09-01 09:01:15"), 15.90));
		races.add(new Race(102, "race2", Timestamp.valueOf("2022-06-11 02:01:15"), 15.90));
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(races.get(0).getId()).thenReturn(races.get(1).getId());
        when(resultSetMock.getString("name")).thenReturn(races.get(0).getName()).thenReturn(races.get(1).getName());
        when(resultSetMock.getTimestamp("race.date")).thenReturn(races.get(0).getDate()).thenReturn(races.get(1).getDate());
        when(resultSetMock.getDouble("fee")).thenReturn(races.get(0).getFee()).thenReturn(races.get(1).getFee());
		
        ArrayList<Race> racesRecieved = memberTest.getRaces("Upcoming");
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
	void testDisableAccount() {
		assertTrue(memberTest.disableAccount());
	}

	@Test
	void testGetMember() {
		memberTest = memberTest.getMember("KNGVSS98R66Z335F");
		assertAll(() -> assertEquals(name, memberTest.getName()),
				() -> assertEquals(surname, memberTest.getSurname()),
				() -> assertEquals(address, memberTest.getAddress()),
				() -> assertEquals(id, memberTest.getId()),
				() -> assertEquals(password, memberTest.getPassword()));
	}
	
	@Test
	void testAddBoat() throws SQLException {
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("LAST_INSERT_ID()")).thenReturn(1111);
		assertEquals(memberTest.addBoat(new Boat(1111, "boat2", "GRGMCN76P26F462P", 14.90)), 1111);
	}

	@Test
	void testIsValidMembership() {
		assertTrue(memberTest.isValidMembership());
		assertFalse(memberTest.sessionOn);
	}

	@Test
	void testGetAllNotifications() throws SQLException {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		notifications.add(new Notification(1001, memberTest.getId(), "heading1", "hody1"));
		notifications.add(new Notification(1002, memberTest.getId(), "Heading2", "body2"));
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(notifications.get(0).getId()).thenReturn(notifications.get(1).getId());
        when(resultSetMock.getString("memberID")).thenReturn(notifications.get(0).getMemberID()).thenReturn(notifications.get(1).getMemberID());
        when(resultSetMock.getString("head")).thenReturn(notifications.get(0).getHead()).thenReturn(notifications.get(1).getHead());
        when(resultSetMock.getString("body")).thenReturn(notifications.get(0).getBody()).thenReturn(notifications.get(1).getBody());
		
        ArrayList<Notification> notificationsRecieved = memberTest.getAllNotifications("all");
		assertAll(() -> assertEquals(notifications.get(0).getId(), notificationsRecieved.get(0).getId()),
				() -> assertEquals(notifications.get(0).getMemberID(), notificationsRecieved.get(0).getMemberID()),
				() -> assertEquals(notifications.get(0).getHead(), notificationsRecieved.get(0).getHead()),
				() -> assertEquals(notifications.get(0).getBody(), notificationsRecieved.get(0).getBody()),
				() -> assertEquals(notifications.get(1).getId(), notificationsRecieved.get(1).getId()),
				() -> assertEquals(notifications.get(1).getMemberID(), notificationsRecieved.get(1).getMemberID()),
				() -> assertEquals(notifications.get(1).getHead(), notificationsRecieved.get(1).getHead()),
				() -> assertEquals(notifications.get(1).getBody(), notificationsRecieved.get(1).getBody())
				);
	}

	@Test
	void testCountUnreadNotification() throws SQLException {
		when(resultSetMock.getInt(1)).thenReturn(2);
		assertEquals(memberTest.countUnreadNotification(), 2);
	}

}
