package system;

import java.sql.ResultSet;
import java.sql.SQLException;

import utility.ConnectDB;

/**
 * 
 * NOTIFICATION CLASS
 *
 */
public class Notification extends ConnectDB{
	private int id;
	private String memberID;
	private String head;
	private String body;
	
	/**
	 *  constructor 
	 */
	public Notification() {super();}
	/**
	 * constructor
	 * @param id notification id
	 * @param memberID member's id
	 * @param head notification's header
	 * @param body notification's body
	 */
	public Notification(int id, String memberID, String head, String body) {
		super();
		this.id = id;
		this.memberID = memberID;
		this.head = head;
		this.body = body;
	}
	public Notification(String memberID, String head, String body) {
		super();
		this.memberID = memberID;
		this.head = head;
		this.body = body;
	}
	
	
	/**
	 * gets the notification id
	 * @return notification id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * sets the notification id
	 * @param id notification id
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
	 * @param memberID member id
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	/**
	 * gets the notification header
	 * @return notification header
	 */
	public String getHead() {
		return head;
	}
	
	/**
	 * sets the notification header
	 * @param head notification header
	 */
	public void setHead(String head) {
		this.head = head;
	}
	
	/**
	 * gets the notification body
	 * @return notification body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * sets the notification body
	 * @param body notification body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * @return true if the notification message has been opened
	 */
	public boolean isRead() {
		String query = "SELECT * FROM notification WHERE id = "+this.id;
		ResultSet result = executeQuery(query);
		try {
			if (result != null && result.getBoolean("opened"))
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * flags a notification as opened in the database
	 */
	public void setRead() {
		String query = "UPDATE notification SET opened = true WHERE id = "+this.id;
		executeUpdate(query);
	}
}
