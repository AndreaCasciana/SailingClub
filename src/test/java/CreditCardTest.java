package test.java;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import system.CreditCard;


class CreditCardTest {
	
	final int id = 1000;
	final String type = "Visa";
	final String cardName = "Name Surname";
	final BigInteger cardNumber = new BigInteger("4165701987571789");
	final int expMonth = 10;
	final int expYear = 2026;
	
	// mocked version of the class for database
	MockedStatic<DriverManager> driverManagerMock;
	Connection jdbcConnection; 
	Statement statement;
	ResultSet resultSetMock;
	
	CreditCard creditCardTest;

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
        
		creditCardTest = new CreditCard(id, type, cardName, cardNumber, expMonth, expYear);
	}
	
	@AfterEach
	public void afterEach() {
		driverManagerMock.close(); // else error ->  static mocking is already registered in the current thread
	}

	@Test
	void testCreditCardIntStringStringBigIntegerIntInt() { // constructor
		creditCardTest = new CreditCard(1999, type, cardName, cardNumber, expMonth, expYear);
		assertAll(() -> assertEquals(1999, creditCardTest.getId()),
				() -> assertEquals(type, creditCardTest.getType()),
				() -> assertEquals(cardName, creditCardTest.getCardName()),
				() -> assertEquals(cardNumber, creditCardTest.getCardNumber()),
				() -> assertEquals(expMonth, creditCardTest.getExpMonth()),
				() -> assertEquals(expYear, creditCardTest.getExpYear())
				);
	}

	@Test
	void testGetCards() throws SQLException {
		ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
		cards.add(new CreditCard(1001, "Mastercard", "Giorgo Gino", new BigInteger("5325976334692694"), 06, 2025));
		cards.add(new CreditCard(1002, "AMEX", "Giorgo Gino", new BigInteger("349449518338693"), 11, 2035));
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(cards.get(0).getId()).thenReturn(cards.get(1).getId());
        when(resultSetMock.getString("type")).thenReturn(cards.get(0).getType()).thenReturn(cards.get(1).getType());
        when(resultSetMock.getString("cardName")).thenReturn(cards.get(0).getCardName()).thenReturn(cards.get(1).getCardName());
        when(resultSetMock.getBigDecimal("cardNumber")).thenReturn(new BigDecimal(cards.get(0).getCardNumber())).thenReturn(new BigDecimal(cards.get(1).getCardNumber()));
        when(resultSetMock.getInt("expMonth")).thenReturn(cards.get(0).getExpMonth()).thenReturn(cards.get(1).getId());
        when(resultSetMock.getInt("expYear")).thenReturn(cards.get(0).getExpYear()).thenReturn(cards.get(1).getId());
        
        ArrayList<CreditCard> cardsRecieved = creditCardTest.getCards("KNGVSS98R66Z335F");
        assertAll(() -> assertEquals(cardsRecieved.get(0).getId(), cardsRecieved.get(0).getId()),
				() -> assertEquals(cardsRecieved.get(0).getType(), cardsRecieved.get(0).getType()),
				() -> assertEquals(cardsRecieved.get(0).getCardName(), cardsRecieved.get(0).getCardName()),
				() -> assertEquals(cardsRecieved.get(0).getCardNumber(), cardsRecieved.get(0).getCardNumber()),
				() -> assertEquals(cardsRecieved.get(0).getExpMonth(), cardsRecieved.get(0).getExpMonth()),
				() -> assertEquals(cardsRecieved.get(0).getExpYear(), cardsRecieved.get(0).getExpYear()),
				() -> assertEquals(cardsRecieved.get(1).getId(), cardsRecieved.get(1).getId()),
				() -> assertEquals(cardsRecieved.get(1).getType(), cardsRecieved.get(1).getType()),
				() -> assertEquals(cardsRecieved.get(1).getCardName(), cardsRecieved.get(1).getCardName()),
				() -> assertEquals(cardsRecieved.get(1).getCardNumber(), cardsRecieved.get(1).getCardNumber()),
				() -> assertEquals(cardsRecieved.get(1).getExpMonth(), cardsRecieved.get(1).getExpMonth()),
				() -> assertEquals(cardsRecieved.get(1).getExpYear(), cardsRecieved.get(1).getExpYear()));
				
	}

	@Test
	void testAddCard() throws SQLException {
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("LAST_INSERT_ID()")).thenReturn(id);
		assertEquals(creditCardTest.addCard(creditCardTest), id);
	}

	@Test
	void testRemoveCard() {
		assertTrue(creditCardTest.removeCard(expMonth));
	}

}
