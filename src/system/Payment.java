package system;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import guiController.AlertDialogue;
import utility.ConnectDB;

/**
 * 
 * PAYMENT CLASS
 *
 */
public class Payment extends ConnectDB{
	private int id;
	private String memberID;
	private String method;
	private Integer methodID_CC;
	private Integer methodID_TR;
	private String cause;
	private Integer boatID;
	private Integer raceID;
	private double amount;
	private Date date;
	
	/**
	 *  constructor 
	 */
	public Payment() {super();}
	/**
	 * constructor
	 * @param id payment's id
	 * @param memberID member's id
	 * @param method payment method
	 * @param methodID_CC method id for the credit card
	 * @param methodID_TR method id for the transfer receipt
	 * @param cause transfer receipt's cause
	 * @param boatID boat's id
	 * @param raceID race's id
	 * @param amount total amount
	 * @param payment date
	 */
	public Payment(int id, String memberID, String method, Integer methodID_CC, Integer methodID_TR, String cause, Integer boatID,
			Integer raceID, double amount, Date date) {
		super();
		this.id = id;
		this.memberID = memberID;
		this.method = method;
		this.methodID_CC = methodID_CC;
		this.methodID_TR = methodID_TR;
		this.cause = cause;
		this.boatID = boatID;
		this.raceID = raceID;
		this.amount = amount;
		this.date = date;
	}
	/**
	 * constructor
	 * @param memberID member's id
	 * @param method payment method
	 * @param methodID_CC method id for the credit card
	 * @param methodID_TR method id for the transfer receipt
	 * @param cause transfer receipt's cause
	 * @param boatID boat's id
	 * @param raceID race's id
	 * @param amount total amount
	 * @param payment date
	 */
	public Payment(String memberID, String method, Integer methodID_CC, Integer methodID_TR, String cause, Integer boatID, 
			Integer raceID, double amount, Date date) {
		super();
		this.memberID = memberID;
		this.method = method;
		this.methodID_CC = methodID_CC;
		this.methodID_TR = methodID_TR;
		this.cause = cause;
		this.boatID = boatID;
		this.raceID = raceID;
		this.amount = amount;
		this.date = date;
	}

	/**
	 * gets the payment id
	 * @return payment id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * sets the payment id
	 * @param id 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * gets the member id
	 * @return member id
	 */
	public String getMemberID() {
		return memberID;
	}
	
	/**
	 * sets the member id
	 * @param memberID
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	/**
	 * gets the payment method
	 * @return payment method
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * sets the payment method
	 * @param method 
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * gets the method id for the credit card
	 * @return method id
	 */
	public Integer getMethodID_CC() {
		return methodID_CC;
	}
	
	/**
	 * sets the method id for the credit card
	 * @param methodID_CC method id
	 */
	public void setMethodID_CC(Integer methodID_CC) {
		this.methodID_CC = methodID_CC;
	}
	
	/**
	 * gets the method id for the transfer receipt
	 * @return method id
	 */
	public Integer getMethodID_TR() {
		return methodID_TR;
	}
	
	/**
	 * sets the method id for the transfer receipt
	 * @param methodID_TR method id
	 */
	public void setMethodID_TR(Integer methodID_TR) {
		this.methodID_TR = methodID_TR;
	}
	
	/**
	 * gets the cause of the receipt
	 * @return cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * sets the cause of the receipt
	 * @param cause
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	/**
	 * gets the boat id
	 * @return boat id
	 */
	public Integer getBoatID() {
		return boatID;
	}

	/**
	 * sets the boat id
	 * @param boatID
	 */
	public void setBoatID(Integer boatID) {
		this.boatID = boatID;
	}
	
	/**
	 * gets the race id
	 * @return race id
	 */
	public Integer getRaceID() {
		return raceID;
	}

	/**
	 * sets the race id
	 * @param raceID
	 */
	public void setRaceID(Integer raceID) {
		this.raceID = raceID;
	}
	
	/**
	 * gets the amount
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * sets amount
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * gets payment date
	 * @return payment date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * sets the payment date
	 * @param date 
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * adds a payment to the database
	 * @param payment payment object
	 * @return true if successful
	 */
	public boolean addPayment(Payment payment) {
		String query = "INSERT INTO payment (memberID, method, methodID_CC, methodID_TR, cause, boatID, raceID, amount, date) "
						+ "VALUES ('"+payment.getMemberID()+"','"+payment.getMethod()+"',"+payment.getMethodID_CC()+","+payment.getMethodID_TR()+",'"+payment.getCause()+"',"+payment.getBoatID()+","+payment.getRaceID()+",'"+payment.getAmount()+"','"+payment.getDate()+"')";
		return (executeUpdate(query));
	}
	
	/**
	 * gets all payments
	 * @param type payment type (membership fee, storage fee or race fee)
	 * @return ArrayList with all payments and their information
	 */
	public ArrayList<Payment> getPayments(String type) {
		ArrayList<Payment> payments = new ArrayList<Payment>();
		String query = "SELECT * FROM payment ";
		switch(type) {
		case "membership":
			query += " WHERE cause = 'membership fee' ";
			break;
		case "storage":
			query += " WHERE cause = 'storage fee' ";
			break;
		case "race":
			query += " WHERE cause = 'race fee' ";
			break;
		default:
			break;
		}
		query += " ORDER BY date DESC";
		
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					payments.add(new Payment(result.getInt("id"), result.getString("memberID"), result.getString("method"), 
							result.getInt("methodID_CC"), result.getInt("methodID_TR"), result.getString("cause"), 
							result.getInt("boatID"), result.getInt("raceID"), result.getDouble("amount"), result.getDate("date")));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		
		return payments;
	}
}
