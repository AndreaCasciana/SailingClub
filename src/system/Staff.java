package system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import guiController.AlertDialogue;
import utility.ConnectDB;

/**
 * 
 * STAFF CLASS
 *
 */
public class Staff extends ConnectDB{

	private int id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private boolean sessionOn;
	private boolean isAdmin;

	/**
	 *  constructor 
	 */
	public Staff(){super();}
	/**
	 * constructor
	 * @param id staff's id
	 * @param name staff's name
	 * @param surname staff's surname
	 * @param username staff's username
	 * @param password staff's password
	 * @param isAdmin adminship: true or false
	 */
	public Staff(int id, String name, String surname, String username, String password, boolean isAdmin){
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	/**
	 * constructor
	 * @param name staff's name
	 * @param surname staff's surname
	 * @param username staff's username
	 * @param password staff's password
	 * @param isAdmin adminship: true or false
	 */
	public Staff(String name, String surname, String username, String password, boolean isAdmin){
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	/**
	 * gets the staff id
	 * @return staff id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * gets the staff name
	 * @return staff name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the staff surname
	 * @return staff surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * gets the staff username
	 * @return staff username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * gets the staff password
	 * @return staff password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return true if the staff is logged in
	 */
	public boolean getSessionOn() {
		return sessionOn;
	}
	
	/**
	 * @return true if the user is an admin
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * sets the staff id
	 * @param id 
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * sets the staff name
	 * @param newName 
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * sets the staff surname
	 * @param newSurname 
	 */
	public void setSurname(String newSurname) {
		this.surname = newSurname;
	}
	
	/**
	 * sets the staff username
	 * @param newUsername
	 */
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	/**
	 * sets the staff password
	 * @param newPassword
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	/**
	 * sets the session status
	 * @param bool true or false
	 */
	public void setSessionOn(boolean bool) {
		this.sessionOn = bool;
	}
	
	/**
	 * sets the adminship status
	 * @param bool true or false
	 */
	public void setIsAdmin(boolean bool) {
		this.isAdmin = bool;
	}
	
	/**
	 * signs in the staff
	 * @param username staff username
	 * @param password staff password
	 * @return true if successful
	 */
	public boolean login(String username, String password) {
		String query = "SELECT * FROM staff WHERE username = '" + username + "' AND removed = false";
		ResultSet staff = executeQuery(query);
		if (staff != null){
			try {
				if(!staff.getString("password").equals(password))
					return false;
				this.setID(staff.getInt("id"));
				this.setName(staff.getString("name"));
				this.setSurname(staff.getString("surname"));
				this.setUsername(staff.getString("username"));
				this.setPassword(staff.getString("password"));
				this.setIsAdmin(staff.getBoolean("isAdmin"));
				this.sessionOn = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	/**
	 *logs the staff out
	 */
	public void logout() {
		this.sessionOn = false;
		id = 0;
		name = null;
		surname = null;
		username = null;
		password = null;
		isAdmin = false;
	}
	
	/**
	 * changes the staff name
	 * @param newName
	 * @return true if successful
	 */
	public boolean setNewName(String newName) {
		String query = "UPDATE staff SET name = '"+newName+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return this.login(username, this.password); //double control of data in database
		return false;
	}

	/**
	 * changes the staff surname
	 * @param newSurname
	 * @return true if successful
	 */
	public boolean setNewSurname(String newSurname) {
		String query = "UPDATE staff SET surname = '"+newSurname+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))     
			return login(this.username, this.password); //double control of data in database
		return false;
	}
	
	/**
	 * changes the staff username
	 * @param newSurname
	 * @return true if successful
	 */
	public boolean setNewUsername(String newUsername) {
		String query = "UPDATE staff SET username = '"+newUsername+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return login(newUsername, this.password); //double control of data in database
		return false;
	}
	
	/**
	 * changes the staff password
	 * @param newSurname
	 * @return true if successful
	 */
	public boolean setNewPassword(String newPassword) {
		String query = "UPDATE staff SET password = '"+newPassword+"' WHERE id = '"+this.id+"'";
		if(executeUpdate(query))
			return this.login(this.username, newPassword); //double control of data in database
		return false;
	}
	
	/**
	 * @return the annual membership fee
	 */
	public double getMembershipFee() {
		double fee = 0;
		String query = "SELECT AnnualMembershipFee FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				fee = result.getDouble("AnnualMembershipFee");
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving annual membership fee.");
				e.printStackTrace();
			}
		return fee;
	}
	
	/**
	 * @return the boat storage fee per foot
	 */
	public double getBoatStorageFeePerFoot() {
		double fee = 0;
		String query = "SELECT AnnualBoatStoragePricePerFoot FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				fee = result.getDouble("AnnualBoatStoragePricePerFoot");
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving annual boat storage fee.");
				e.printStackTrace();
			}
		return fee;
	}
	
	/**
	 * @return benefactor's name used by the club
	 */
	public String getBenefactor() {
		String benefactor = "";
		String query = "SELECT benefactor FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				benefactor = result.getString("benefactor");
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving benefactor details.");
				e.printStackTrace();
			}
		return benefactor;
	}
	
	/**
	 * @return bank's name used by the club
	 */
	public String getBank() {
		String bank = "";
		String query = "SELECT bank FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				bank = result.getString("bank");
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving bank details.");
				e.printStackTrace();
			}
		return bank;
	}
	
	/**
	 * @return IBAN used by the club
	 */
	public String getIBAN() {
		String IBAN = "";
		String query = "SELECT IBAN FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				IBAN = result.getString("IBAN");
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving IBAN.");
				e.printStackTrace();
			}
		return IBAN;
	}
	
	/**
	 * @return benefactor's details
	 */
	public Map<String, String> getBenefactorDetails() {
		Map<String, String> details = new HashMap<String, String>();
		String query = "SELECT benefactor, bank, IBAN FROM club_details";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				details.put("benefactor", result.getString("benefactor"));
				details.put("bank", result.getString("bank"));
				details.put("IBAN", result.getString("IBAN"));
			} catch (SQLException e) {
				AlertDialogue.error("Error in retriving benefactor's details.");
				e.printStackTrace();
			}
		return details;
	}
	
	/**
	 * control if a staff exists given username
	 * @param username staff's username
	 * @return true if the staff exists
	 */
	public boolean exists(String username) {
		String query = "SELECT * FROM staff WHERE username = '" + username + "' and removed = false";
		ResultSet staff = executeQuery(query);
		if (staff != null)
			return true;
		return false;
	}
	
	/**
	 * changes the annual membership fee
	 * @param fee membership fee
	 * @return true if successful
	 */
	public boolean setNewMembershipFee(double fee) {
		String query = "UPDATE club_details SET AnnualMembershipFee = '"+fee+"'";
		return executeUpdate(query);
	}
	
	/**
	 * changes the boat storage fee
	 * @param fee boat storage fee per foot
	 * @return true if successful
	 */
	public boolean setNewBoatStorageFee(double fee) {
		String query = "UPDATE club_details SET AnnualBoatStoragePricePerFoot = '"+fee+"'";
		return executeUpdate(query);
	}
	
	/**
	 * changes the bank's name used by the club
	 * @param bank bank's name
	 * @return true if successful
	 */
	public boolean setBank(String bank) {
		String query = "UPDATE club_details SET bank = '"+bank+"'";
		return executeUpdate(query);
	}
	
	/**
	 * changes the IBAN used by the club
	 * @param IBAN
	 * @return true if successful
	 */
	public boolean setIBAN(String IBAN) {
		String query = "UPDATE club_details SET IBAN = '"+IBAN+"'";
		return executeUpdate(query);
	}
	
	/**
	 * @return ArrayList containing all the staffs
	 */
	public ArrayList<Staff>getStaffs() {
		ArrayList<Staff> staffs = new ArrayList<Staff>();
		String query = "SELECT * FROM staff WHERE removed = false";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					staffs.add(new Staff(result.getInt("id"), result.getString("name"), result.getString("surname"), 
							result.getString("username"), result.getString("password"), result.getBoolean("isAdmin")));
					
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		return staffs;
	}
	
	/**
	 * @return ArrayList containing all the members
	 */
	public ArrayList<Member> getAllMembers(String type) {
		ArrayList<Member> members = new ArrayList<Member>();
		String query = "SELECT * FROM member WHERE removed = false ";
		ResultSet result = executeQuery(query);
		Member member;
		if (result != null)
			try {
				do {
					member = new Member(result.getString("name"), result.getString("surname"), result.getString("address"), result.getString("id"), result.getString("password"));
					switch(type) {
						case "membershipPaid":
							if(member.isValidMembership())
								members.add(member);
							break;
						case "membershipPending":
							if(!member.isValidMembership())
								members.add(member);
							break;
						default:
							members.add(member);
							break;
					}
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		return members;
	}
	
	
	/**
	 * sets a new staff's name
	 * @param staff Staff Object
	 * @newName the staff's name
	 * @return true if successful
	 */
	public boolean setStaffName(Staff staff, String newName) {
		String query = "UPDATE staff SET name = '"+newName+"' WHERE id = "+staff.id;
		return executeUpdate(query);
	}

	/**
	 * sets a new staff's surname
	 * @param staff Staff Object
	 * @newSurname the staff's surname
	 * @return true if successful
	 */
	public boolean setStaffSurname(Staff staff, String newSurname) {
		String query = "UPDATE staff SET surname = '"+newSurname+"' WHERE id = "+staff.id;
		return executeUpdate(query);
	}
	
	/**
	 * sets a new staff's username
	 * @param staff Staff Object
	 * @newUsername the staff's username
	 * @return true if successful
	 */
	public boolean setStaffUsername(Staff staff, String newUsername) {
		String query = "UPDATE staff SET username = '"+newUsername+"' WHERE id = "+staff.id;
		return executeUpdate(query);
	}
	
	/**
	 * sets a new staff's password
	 * @param staff Staff Object
	 * @newName the staff's password
	 * @return true if successful
	 */
	public boolean setStaffPassword(Staff staff, String newPassword) {
		String query = "UPDATE staff SET password = '"+newPassword+"' WHERE id = "+staff.id;
		return executeUpdate(query);
	}
	
	/**
	 * sets a staff's adminship
	 * @param staff Staff Object
	 * @param bool admin: true or false
	 * @return true if successful
	 */
	public boolean setStaffAdminship(Staff staff, boolean bool) {
		String query = "UPDATE staff SET isAdmin = "+bool+" WHERE id = "+staff.id;
		return executeUpdate(query);
	}
	
	/**
	 * removes a staff from the database
	 * @param staff Staff Object
	 * @return true if successful
	 */
	public boolean removeStaff(Staff staff) {
		String query = "UPDATE staff SET removed = true WHERE id = "+staff.id;
		return executeUpdate(query);
	}
	
	/**
	 * adds a staff to the database
	 * @param staff Staff Object
	 * @return true if successful
	 */
	public boolean addStaff(Staff staff) {
		String query = "INSERT INTO staff (name, surname, username, password, isAdmin, removed) VALUES ('"
						+staff.name+"','"+staff.surname+"','"+staff.username+"','"+staff.password+"',"+staff.isAdmin+",false)";
		return executeUpdate(query);
	}
	
	/**
	 * @return the number of admins in database
	 */
	public int countAdmins() {
		String query = "SELECT count(*) FROM staff WHERE isAdmin = true AND removed = false" ;
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
	 * sends a notification
	 * @param notification Notification Object
	 * @return true if successful
	 */
	public boolean sendNotification(Notification notification) {
		String query = "INSERT INTO notification (memberID, head, body, date, opened) VALUES"
				+ "('"+notification.getMemberID()+"','"+notification.getHead()+"','"+notification.getBody()+"','"+LocalDate.now()+"',false)";
		return executeUpdate(query);
	}
	
	/**
	 * get members enrolled given race
	 * @param raceID the race id
	 * @return all the members enrolled in a race
	 */
	public ArrayList<String> getMembersEnrolled(int raceID) {
		ArrayList<String> membersEnrolled = new ArrayList<String>();
		String query = "SELECT * FROM enrolment WHERE raceID = " + raceID + " AND cancelled = false";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					membersEnrolled.add(result.getString("memberID"));
				}
				while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return membersEnrolled;
	}
	
}

