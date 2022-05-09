package system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import guiController.AlertDialogue;
import utility.ConnectDB;

/**
 * 
 * MEMBER CLASS
 *
 */
public class Member extends ConnectDB{

	private String name;
	private String surname;
	private String address;
	private String id;
	private String password;
	/**
	 * flags if member is logged
	 */
	public boolean sessionOn = false;

	/**
	 *  constructor 
	 */
	public Member(){super();}
	/**
	 * constructor
	 * @param name member's name
	 * @param surname member's surname
	 * @param address member's address
	 * @param id member's id
	 * @param password member's password
	 */
	public Member(String name, String surname, String address, String id, String password){
		super();
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.id = id;
		this.password = password;
	}


	/**
	 * gets the member name
	 * @return member name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * gets the member surname
	 * @return member surname
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * gets the member address
	 * @return member address
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * gets the member id
	 * @return member id
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * gets the member password
	 * @return member password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * sets the member name
	 * @param n member name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * sets the member surname
	 * @param s member surname
	 */
	public void setSurname(String s) {
		this.surname = s;
	}
	
	/**
	 * sets the member address
	 * @param a member address
	 */
	public void setAddress(String a) {
		this.address = a;
	}
	
	/**
	 * sets the member id
	 * @param i member id
	 */
	public void setId(String i) {
		this.id = i;
	}
	
	/**
	 * sets the member password
	 * @param p member password
	 */
	public void setPassword(String p) {
		this.password = p;
	}
	
	/**
	 * logs in member
	 * @param id
	 * @param password
	 * @return true if login was successful
	 */
	public boolean login(String id, String password) {
		String query = "SELECT * FROM member WHERE id = '" + id + "' AND removed = false";
		ResultSet member = executeQuery(query);
		if (member != null) {
			try {
				if(!member.getString("password").equals(password))
					return false;
				this.setId(member.getString("id"));
				this.setName(member.getString("name"));
				this.setSurname(member.getString("surname"));
				this.setAddress(member.getString("address"));
				this.setPassword(member.getString("password"));
				sessionOn = true;
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * control the database if member exists with given id
	 * @param id
	 * @return true if member exists in database
	 */
	public boolean exists(String id) {
		String query = "SELECT * FROM member WHERE id = '" + id + "' AND removed = false";
		ResultSet member = executeQuery(query);

		if (member != null)
			return true;
		return false;
	}
	
	/**
	 * registers a member in database
	 * @param member to register
	 * @return true if registration was successful
	 */
	public boolean register(Member member) {
		String query = "INSERT INTO member (name, surname, address, id, password, removed) VALUES ("
				+ "'"+member.name+"','"+member.surname+"','"+member.address+"','"+member.id+"','"+member.password+"',false)";
		if(executeUpdate(query))
			return this.login(member.id, member.password); //double control of data in database
		return false;
	}
	
	/**
	 * method to change the name of the member
	 * @param newName
	 * @return true if successful
	 */
	public boolean setNewName(String newName) {
		String query = "UPDATE member SET name = '"+newName+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return this.login(this.id, this.password); //double control of data in database
		return false;
	}

	/**
	 * method to change the surname of the member
	 * @param newSurname
	 * @return  true if successful
	 */
	public boolean setNewSurname(String newSurname) {
		String query = "UPDATE member SET surname = '"+newSurname+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))     
			return login(this.id, this.password); //double control of data in database
		return false;
	}
	
	/**
	 * method to change the address of the member
	 * @param newAddress
	 * @return  true if successful
	 */
	public boolean setNewAddress(String newAddress) {
		String query = "UPDATE member SET address = '"+newAddress+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return login(this.id, this.password); //double control of data in database
		return false;
	}
	
	/**
	 *  method to change the password of the member
	 * @param newPassword
	 * @return true if successful
	 */
	public boolean setNewPassword(String newPassword) {
		String query = "UPDATE member SET password = '"+newPassword+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return this.login(this.id, newPassword); //double control of data in database
		return false;
	}
	
	/**
	 * get races concerning the member given type
	 * @param type (upcoming/past/notEnrolled)
	 * @return true if successful
	 */
	public ArrayList<Race> getRaces(String type) {
		ArrayList<Race> races = new ArrayList<Race>();
		String query = "";
		
		switch(type) {
			case "upcoming":
				query = "SELECT * FROM race, enrolment WHERE id = raceID AND memberID = '"+this.id+"' AND enrolment.cancelled = false AND race.date > NOW()";
				break;
			case "past":
				query = "SELECT * FROM race, enrolment WHERE id = raceID AND memberID = '"+this.id+"' AND enrolment.cancelled = false AND race.date < NOW()";
				break;
			case "notEnrolled":
				query = "SELECT * FROM race WHERE cancelled = false";
				break;
			default:
				break;
		}
	
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					races.add(new Race(result.getInt("id"), result.getString("name"), result.getTimestamp("race.date"), result.getDouble("fee")));
				}
				while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		return races;
	}
	
	/**
	 * stops session
	 */
	public void logout() {
		this.sessionOn = false;
		name = null;
		surname = null;
		address = null;
		id = null;
		password = null;
	}
	
	/**
	 * disable an account, flagging it as removed in the database
	 */
	public boolean disableAccount() {
		String query = "UPDATE member SET removed = true WHERE id = '"+this.id+"'";
		if(executeUpdate(query)) {
			logout();
			return true;
		}
		return false;
	}
	
	/**
	 * get member given id
	 * @param memberID
	 * @return member with id passed as argument
	 */
	public Member getMember(String memberID) {
		String query = "SELECT * FROM member WHERE id = '" + memberID + "' AND removed = false";
		ResultSet member = executeQuery(query);
		if (member != null)
			try {
				return new Member(member.getString("name"), member.getString("surname"), member.getString("address"), member.getString("id"), member.getString("password"));
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		return null;
	}
	
	/**
	 * @return true if membership fee is valid
	 */
	public boolean isValidMembership() {
		String query = "SELECT * FROM payment WHERE memberID = '"+this.id+"' AND cause = 'membership fee' AND DATEDIFF(NOW(), date) < 365";
		ResultSet result = executeQuery(query);
		if (result != null)
			return true;
		return false;
	}
	
	/**
	 * adds boat to member boats
	 * @param boat
	 * @return true if successful
	 */
	public int addBoat(Boat boat) {
		String query = "INSERT INTO boat (name, owner, lengthInFeet, removed) VALUES"
						+ "('"+boat.getName()+"','"+this.id+"','"+boat.getLength()+"',false)";
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
	 * get notifications of member
	 * @param type (notificationsUnread/notificationsRead)
	 * @return notifications of the member
	 */
	public ArrayList<Notification> getAllNotifications(String type) {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		String query = "SELECT * FROM notification WHERE memberID = '" + this.id + "' ";
		switch(type) {
		case "notificationsUnread":
			query += " AND opened = false ";
			break;
		case "notificationsRead":
			query += " AND opened = true ";
			break;
		default:
			break;
		}
		query += " ORDER BY date DESC";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					notifications.add(new Notification(result.getInt("id"), result.getString("memberID"), result.getString("head"), result.getString("body")));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		
		return notifications;
	}
	
	/**
	 * @return number of unread notification
	 */
	public int countUnreadNotification() {
		String query = "SELECT count(*) FROM notification WHERE memberID = '" + this.id + "' AND opened = false";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				return result.getInt(1);
			} catch (SQLException e) {
				AlertDialogue.error("Error Occured (DB)");
				e.printStackTrace();
			}
		return 0;
	}
	
	/**
	 * enrolls a user in a race
	 * @param enroll enrollment object
	 * @return true if successful
	 */
	public boolean enroll(enrolment enroll) {
		String query = "SELECT * FROM enrolment WHERE memberID = '" + this.id + "' AND raceID = " + enroll.getRaceID();
		ResultSet enrolled = executeQuery(query);
		if (enrolled != null) {
			query = "UPDATE enrolment SET cancelled = false WHERE memberID = '"+ this.id + "' AND raceID = " + enroll.getRaceID();
			return executeUpdate(query);
		}else {
			query = "INSERT INTO enrolment (memberID, raceID, boatID, date, cancelled) VALUES"
					+ "('"+this.id+"',"+enroll.getRaceID()+","+enroll.getBoatID()+",'"+LocalDate.now()+"',false)";
			return executeUpdate(query);
		}
	}
	
	/**
	 * changes the boat that is enrolled in a race
	 * @param raceID race id
	 * @param newBoatID the new boat id
	 * @return true if successful
	 */
	public boolean changeBoatForRace(int raceID, int newBoatID) {
		String query = "UPDATE enrolment SET boatID = "+newBoatID+" WHERE memberID = '"+this.id+"' AND raceID = "+raceID;
		if (executeUpdate(query)) {
			return true;
		}
		return false;
	}
	
	/**
	 * unenrolls a user from a race
	 * @param raceID race id
	 * @return true if successful
	 */
	public boolean unenroll(int raceID) {
		String query = "UPDATE enrolment SET cancelled = true WHERE memberID = '"+this.id+"' AND raceID = "+raceID;
		if (executeUpdate(query)) {
			return true;
		}
		return false;
	}
	
}