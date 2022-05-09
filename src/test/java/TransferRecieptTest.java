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

import system.TransferReciept;


class TransferRecieptTest {
	final int id = 1000;
	final String IBAN = "IT37A3718699302K28B92K9X608";
	final String bank = "CREDIT AGRICOLE ITALIA SPA";
	final String accountName = "Giorgio Giovani Marconi";
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	TransferReciept transferRecieptTest;

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
        
		transferRecieptTest = new TransferReciept(id, IBAN, bank, accountName);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testTransferRecieptIntStringStringString() {
		transferRecieptTest = new TransferReciept(1555, IBAN, bank, accountName);
		assertAll(() -> assertEquals(1555, transferRecieptTest.getId()),
				() -> assertEquals(IBAN, transferRecieptTest.getIBAN()),
				() -> assertEquals(bank, transferRecieptTest.getBank()),
				() -> assertEquals(accountName, transferRecieptTest.getAccountName())
				);
	}

	@Test
	void testAddTransfer() throws SQLException {
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("LAST_INSERT_ID()")).thenReturn(id);
		assertEquals(transferRecieptTest.addTransfer(transferRecieptTest), id);
	}

	@Test
	void testGetTransfer() throws SQLException {
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(id);
		when(resultSetMock.getString("IBAN")).thenReturn(IBAN);
		when(resultSetMock.getString("bank")).thenReturn(bank);
		when(resultSetMock.getString("accountName")).thenReturn(accountName);
		
		TransferReciept transferRecieptReturned = transferRecieptTest.getTransfer(id);
		assertAll(() -> assertEquals(transferRecieptReturned.getId(), transferRecieptTest.getId()),
				() -> assertEquals(transferRecieptReturned.getIBAN(), transferRecieptTest.getIBAN()),
				() -> assertEquals(transferRecieptReturned.getBank(), transferRecieptTest.getBank()),
				() -> assertEquals(transferRecieptReturned.getAccountName(), transferRecieptTest.getAccountName())
				);
	}

}
