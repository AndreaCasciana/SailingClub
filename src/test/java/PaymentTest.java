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
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import system.Payment;

class PaymentTest {
	
	final int id = 100;
	final String memberID = "CTTFRC96D24A497D";
	final String method = "card";
	final Integer methodID_CC = 1000;
	final String cause = "membership fee";
	final double amount = 198.0;
	
// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	Payment paymentTest;
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
        
        paymentTest = new Payment();
       
        
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}
	

	@Test
	void testPaymentIntStringStringIntegerIntegerStringIntegerIntegerDoubleDate() throws SQLException, ParseException {
	    Date date = sdt.parse("2022-02-01");
		when(resultSetMock.getDate("date")).thenReturn( new java.sql.Date(date.getTime()));
		
		paymentTest = new Payment(id, memberID, method, methodID_CC, null, cause, null, null, amount, date);
		assertAll(() -> assertEquals(id, paymentTest.getId()),
				() -> assertEquals(memberID, paymentTest.getMemberID()),
				() -> assertEquals(method, paymentTest.getMethod()),
				() -> assertEquals(methodID_CC, paymentTest.getMethodID_CC()),
				() -> assertEquals(cause, paymentTest.getCause()),
				() -> assertEquals(amount, paymentTest.getAmount()),
				() -> assertEquals(date, paymentTest.getDate()));
	}

	@Test
	void testAddPayment() throws SQLException, ParseException {
	    Date date =  sdt.parse("2019-02-01");
	    when(resultSetMock.getDate("date")).thenReturn( new java.sql.Date(date.getTime()));
		
		paymentTest = new Payment(id, memberID, method, methodID_CC, null, cause, null, null, amount, date);
		assertTrue(paymentTest.addPayment(paymentTest));
	}

	@Test
	void testGetPayments() throws SQLException, ParseException {
		ArrayList<Payment> payments = new ArrayList<Payment>();
	    payments.add(new Payment(111, "KNGVSS98R66Z335F", "card", 1111,0, "membership fee", 0, 0, amount, sdt.parse("2022-02-01")));
	    payments.add(new Payment(222, "GRGMCN76P26F462P", "transfer", 0, 1111, "membership fee", 0, 0, amount, sdt.parse("2021-02-01")));
		
	    when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(payments.get(0).getId()).thenReturn(payments.get(1).getId());
		when(resultSetMock.getString("memberID")).thenReturn(payments.get(0).getMemberID()).thenReturn(payments.get(1).getMemberID());
        when(resultSetMock.getString("method")).thenReturn(payments.get(0).getMethod()).thenReturn(payments.get(1).getMethod());
        when(resultSetMock.getInt("methodID_CC")).thenReturn(payments.get(0).getMethodID_CC()).thenReturn(payments.get(1).getMethodID_CC());
        when(resultSetMock.getInt("methodID_TR")).thenReturn(payments.get(0).getMethodID_TR()).thenReturn(payments.get(1).getMethodID_TR());
        when(resultSetMock.getString("cause")).thenReturn(payments.get(0).getCause()).thenReturn(payments.get(1).getCause());
        when(resultSetMock.getInt("boatID")).thenReturn(payments.get(0).getBoatID()).thenReturn(payments.get(1).getBoatID());
        when(resultSetMock.getInt("raceID")).thenReturn(payments.get(0).getRaceID()).thenReturn(payments.get(1).getRaceID());
        when(resultSetMock.getDouble("amount")).thenReturn(payments.get(0).getAmount()).thenReturn(payments.get(1).getAmount());
        when(resultSetMock.getDate("date")).thenReturn( new java.sql.Date(payments.get(0).getDate().getTime())).thenReturn( new java.sql.Date(payments.get(1).getDate().getTime()));
        
        ArrayList<Payment> racesRecieved = paymentTest.getPayments("all");
        assertAll(() -> assertEquals(payments.get(0).getId(), racesRecieved.get(0).getId()),
				() -> assertEquals(payments.get(0).getMethod(), racesRecieved.get(0).getMethod()),
				() -> assertEquals(payments.get(0).getMethodID_CC(), racesRecieved.get(0).getMethodID_CC()),
				() -> assertEquals(payments.get(0).getCause(), racesRecieved.get(0).getCause()),
				() -> assertEquals(payments.get(0).getBoatID(), racesRecieved.get(0).getBoatID()),
				() -> assertEquals(payments.get(0).getRaceID(), racesRecieved.get(0).getRaceID()),
				() -> assertEquals(payments.get(0).getAmount(), racesRecieved.get(0).getAmount()),
				() -> assertEquals(payments.get(0).getDate(), racesRecieved.get(0).getDate()),
				() -> assertEquals(payments.get(1).getId(), racesRecieved.get(1).getId()),
				() -> assertEquals(payments.get(1).getMethod(), racesRecieved.get(1).getMethod()),
				() -> assertEquals(payments.get(1).getMethodID_CC(), racesRecieved.get(1).getMethodID_CC()),
				() -> assertEquals(payments.get(1).getCause(), racesRecieved.get(1).getCause()),
				() -> assertEquals(payments.get(1).getBoatID(), racesRecieved.get(1).getBoatID()),
				() -> assertEquals(payments.get(1).getRaceID(), racesRecieved.get(1).getRaceID()),
				() -> assertEquals(payments.get(1).getAmount(), racesRecieved.get(1).getAmount()),
				() -> assertEquals(payments.get(1).getDate(), racesRecieved.get(1).getDate()));
	}

}
