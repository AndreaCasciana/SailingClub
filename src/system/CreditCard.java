package system;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utility.ConnectDB;

/**
 * 
 * CREDIT CARD CLASS
 *
 */
public class CreditCard extends ConnectDB{

	private int id;
	private String type;
	private String cardName;
	private BigInteger cardNumber;
	private int expMonth;
	private int expYear;
	
	/**
	 * Constructor
	 */
	public CreditCard() {super();}
	/**
	 * Constructor
	 * @param id
	 * @param type
	 * @param cardName
	 * @param cardNumber
	 * @param expMonth
	 * @param expYear
	 */
	public CreditCard(int id, String type, String cardName, BigInteger cardNumber, int expMonth, int expYear) {
		super();
		this.id = id;
		this.type = type;
		this.cardName = cardName;
		this.cardNumber = cardNumber;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}
	/**
	 * Constructor
	 * @param type
	 * @param cardName
	 * @param cardNumber
	 * @param expMonth
	 * @param expYear
	 */
	public CreditCard(String type, String cardName, BigInteger cardNumber, int expMonth, int expYear) {
		super();
		this.type = type;
		this.cardName = cardName;
		this.cardNumber = cardNumber;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}

	/**
	 * gets the credit card id
	 * @return credit card id
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets the credit card id
	 * @param id credit card id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * gets the credit card type
	 * @return credit card type
	 */
	public String getType() {
		return type;
	}

	/**
	 * sets the credit card type
	 * @param type credit card type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * gets the credit card name
	 * @return credit card name
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * sets the credit card name
	 * @param cardName credit card name
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * gets the card number
	 * @return card number
	 */
	public BigInteger getCardNumber() {
		return cardNumber;
	}

	/**
	 * sets the card number
	 * @param cardNumber credit card number
	 */
	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * gets the expiration month of the credit card
	 * @return credit card expiration month
	 */
	public int getExpMonth() {
		return expMonth;
	}

	/**
	 * sets the expiration month of the credit card
	 * @param expMonth credit card expiration month
	 */
	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}

	/**
	 * gets the expiration year of the credit card
	 * @return credit card expiration year
	 */
	public int getExpYear() {
		return expYear;
	}

	/**
	 * sets the expiration year of the credit card
	 * @param expYear credit card expiration year
	 */
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	
	/**
	 * gets all credit cards of given owner
	 * @param owner credit cards owner
	 * @return ArrayList containing all information about the credit cards
	 */
	public ArrayList<CreditCard> getCards(String owner) {
		ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
		String query = "SELECT * FROM payment, credit_card "
						+ "WHERE credit_card.id = payment.methodID_CC "
						+ "AND payment.memberID = '" + owner + "' "
						+ "GROUP BY credit_card.id";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					if(!result.getBoolean("removed"))
						cards.add(new CreditCard(result.getInt("methodID_CC"),result.getString("type"),result.getString("cardName"),
								result.getBigDecimal("cardNumber").toBigInteger(), result.getInt("exp_month"), result.getInt("exp_year")));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cards;
	}
	
	/**
	 * gets the credit card information given the id
	 * @param idCard credit card id
	 * @return credit card
	 */
	public CreditCard getCard(int idCard) {
		String query = "SELECT * FROM credit_card  WHERE id = " + idCard;
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					return new CreditCard(result.getInt("id"),result.getString("type"),result.getString("cardName"),
							result.getBigDecimal("cardNumber").toBigInteger(), result.getInt("exp_month"), result.getInt("exp_year"));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * adds a credit card to database
	 * @param creditCard credit card object
	 * @return LAST_INSERT_ID if successful
	 */
	public int addCard(CreditCard creditCard) {
		String query = "INSERT INTO credit_card (type, cardName, cardNumber, exp_month, exp_year, removed) "
						+ "VALUES ('"+creditCard.getType()+"','"+creditCard.getCardName()+"',"+creditCard.getCardNumber()+","+creditCard.getExpMonth()+","+creditCard.getExpYear()+",false)";
		if (executeUpdate(query)) {
			query = "SELECT LAST_INSERT_ID()";
			ResultSet result = executeQuery(query);
			if (result != null)
				try {
					return result.getInt("LAST_INSERT_ID()");
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return 0;
	}
	
	/**
	 * flags a credit card as removed given the id
	 * @param card credit card id
	 * @return true if successful
	 */
	public boolean removeCard(int card) {
		String query = "UPDATE credit_card SET removed = true WHERE id = "+card+"";
		return executeUpdate(query);
	}
	
}
