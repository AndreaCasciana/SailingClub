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

import system.Member;
import system.Staff;


class StaffTest {
	
	final int id = 1000;
	final String name = "Andrea";
	final String surname = "Casciana";
	final String username = "andy";
	final String password = "andy95";
	final boolean isAdmin = true;
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Staff staffTest;

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
        
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("id")).thenReturn(id);
        when(resultSetMock.getString("name")).thenReturn(name);
        when(resultSetMock.getString("surname")).thenReturn(surname);
        when(resultSetMock.getString("username")).thenReturn(username);
        when(resultSetMock.getString("password")).thenReturn(password);
        when(resultSetMock.getBoolean("isAdmin")).thenReturn(isAdmin);
        
        staffTest  = new Staff(id, name, surname, username, password, isAdmin);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testStaffIntStringStringStringStringBoolean() {
		 staffTest  = new Staff(id, "Andrew", surname, username, password, isAdmin);
		 assertAll(() -> assertEquals(id, staffTest.getId()),
					() -> assertEquals("Andrew", staffTest.getName()),
					() -> assertEquals(surname, staffTest.getSurname()),
					() -> assertEquals(username, staffTest.getUsername()),
					() -> assertEquals(password, staffTest.getPassword()),
					() -> assertEquals(isAdmin, staffTest.getIsAdmin())
					);
	}

	@Test
	void testLogin() {
		assertFalse(staffTest.getSessionOn());
		staffTest.login(username, password);
		assertTrue(staffTest.getSessionOn());
	}

	@Test
	void testGetMembershipFee() throws SQLException {
		when(resultSetMock.getDouble("AnnualMembershipFee")).thenReturn(198.2);
		assertEquals(staffTest.getMembershipFee(), 198.2);
	}

	@Test
	void testSetNewName() throws SQLException {
		String newName = "Daniello";
		when(resultSetMock.getString("name")).thenReturn(newName);
		assertTrue(staffTest.setNewName(newName));
		assertEquals(staffTest.getName(), newName);
	}

	@Test
	void testSetNewSurname() throws SQLException {
		String newSurname = "Lorenzo";
		when(resultSetMock.getString("surname")).thenReturn(newSurname);
		assertTrue(staffTest.setNewSurname(newSurname));
		assertEquals(staffTest.getSurname(), newSurname);
	}

	@Test
	void testSetNewUsername() throws SQLException {
		String newUsername = "King";
		when(resultSetMock.getString("username")).thenReturn(newUsername);
		assertTrue(staffTest.setNewUsername(newUsername));
		assertEquals(staffTest.getUsername(), newUsername);
	}

	@Test
	void testSetNewPassword() throws SQLException {
		String newPassword = "pippo";
		when(resultSetMock.getString("password")).thenReturn(newPassword);
		assertTrue(staffTest.setNewPassword(newPassword));
		assertEquals(staffTest.getPassword(), newPassword);
	}

	@Test
	void testGetBoatStorageFeePerFoot() throws SQLException {
		when(resultSetMock.getDouble("AnnualBoatStoragePricePerFoot")).thenReturn(45.5);
		assertEquals(staffTest.getBoatStorageFeePerFoot(), 45.5);
	}

	@Test
	void testExists() {
		assertTrue(staffTest.exists(username));
	}

	@Test
	void testGetStaffs() throws SQLException {
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		staffs.add( new Staff(1234, name, surname, "bento", password, false));
		staffs.add( new Staff(4444, name, "Paolo", username, "pop3", isAdmin));
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(staffs.get(0).getId()).thenReturn(staffs.get(1).getId());
        when(resultSetMock.getString("name")).thenReturn(staffs.get(0).getName()).thenReturn(staffs.get(1).getName());
        when(resultSetMock.getString("surname")).thenReturn(staffs.get(0).getSurname()).thenReturn(staffs.get(1).getSurname());
        when(resultSetMock.getString("username")).thenReturn(staffs.get(0).getUsername()).thenReturn(staffs.get(1).getUsername());
        when(resultSetMock.getString("password")).thenReturn(staffs.get(0).getPassword()).thenReturn(staffs.get(1).getPassword());
        when(resultSetMock.getBoolean("isAdmin")).thenReturn(staffs.get(0).getIsAdmin()).thenReturn(staffs.get(1).getIsAdmin());
        
        ArrayList<Staff> staffsRecieved = staffTest.getStaffs();
		assertAll(() -> assertEquals(staffs.get(0).getId(), staffsRecieved.get(0).getId()),
				() -> assertEquals(staffs.get(0).getName(), staffsRecieved.get(0).getName()),
				() -> assertEquals(staffs.get(0).getSurname(), staffsRecieved.get(0).getSurname()),
				() -> assertEquals(staffs.get(0).getUsername(), staffsRecieved.get(0).getUsername()),
				() -> assertEquals(staffs.get(0).getPassword(), staffsRecieved.get(0).getPassword()),
				() -> assertEquals(staffs.get(0).getIsAdmin(), staffsRecieved.get(0).getIsAdmin()),
				() -> assertEquals(staffs.get(1).getId(), staffsRecieved.get(1).getId()),
				() -> assertEquals(staffs.get(1).getName(), staffsRecieved.get(1).getName()),
				() -> assertEquals(staffs.get(1).getSurname(), staffsRecieved.get(1).getSurname()),
				() -> assertEquals(staffs.get(1).getUsername(), staffsRecieved.get(1).getUsername()),
				() -> assertEquals(staffs.get(1).getPassword(), staffsRecieved.get(1).getPassword()),
				() -> assertEquals(staffs.get(1).getIsAdmin(), staffsRecieved.get(1).getIsAdmin())
				);
	}
	
	@Test
	void testGetAllMembers() throws SQLException {
		ArrayList<Member> members = new ArrayList<Member>();
		members.add( new Member("Thomas", "Pompeo", "Via Colpi, 5", "PMPTMS16M03F380V", "tom"));
		members.add( new Member("Federico", "Cotto", "Via Monte, 1", "CTTFRC96D24A497D", "fed"));
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getString("id")).thenReturn(members.get(0).getId()).thenReturn(members.get(1).getId());
        when(resultSetMock.getString("name")).thenReturn(members.get(0).getName()).thenReturn(members.get(1).getName());
        when(resultSetMock.getString("surname")).thenReturn(members.get(0).getSurname()).thenReturn(members.get(1).getSurname());
        when(resultSetMock.getString("address")).thenReturn(members.get(0).getAddress()).thenReturn(members.get(1).getAddress());
        when(resultSetMock.getString("password")).thenReturn(members.get(0).getPassword()).thenReturn(members.get(1).getPassword());
        
        ArrayList<Member> racesRecieved = staffTest.getAllMembers("pending");
		assertAll(() -> assertEquals(members.get(0).getId(), racesRecieved.get(0).getId()),
				() -> assertEquals(members.get(0).getPassword(), racesRecieved.get(0).getPassword()),
				() -> assertEquals(members.get(0).getName(), racesRecieved.get(0).getName()),
				() -> assertEquals(members.get(0).getSurname(), racesRecieved.get(0).getSurname()),
				() -> assertEquals(members.get(0).getAddress(), racesRecieved.get(0).getAddress()),
				() -> assertEquals(members.get(1).getId(), racesRecieved.get(1).getId()),
				() -> assertEquals(members.get(0).getPassword(), racesRecieved.get(0).getPassword()),
				() -> assertEquals(members.get(1).getName(), racesRecieved.get(1).getName()),
				() -> assertEquals(members.get(1).getSurname(), racesRecieved.get(1).getSurname()),
				() -> assertEquals(members.get(1).getAddress(), racesRecieved.get(1).getAddress())
				);
	}

	@Test
	void testCountAdmins() throws SQLException {
		when(resultSetMock.getInt(1)).thenReturn(3);
		assertEquals(staffTest.countAdmins(), 3);
	}

	@Test
	void testGetMembersEnrolled() throws SQLException {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("KNGVSS98R66Z335F");
		ids.add("GRGMCN76P26F462P");
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getString("memberID")).thenReturn(ids.get(0)).thenReturn(ids.get(1));
		
		ArrayList<String> idsRecieved = staffTest.getMembersEnrolled(222);
		assertAll(() -> assertEquals(ids.get(0), idsRecieved.get(0)),
					() -> assertEquals(ids.get(0), idsRecieved.get(0)));
	}
}
