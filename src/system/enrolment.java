package system;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import utility.ConnectDB;

/**
 * 
 * ENROLLMENT CLASS
 *
 */
public class enrolment extends ConnectDB{
	private String memberID;
	private int raceID;
	private int boatID;
	private Date date;
	
	/**
	 *  constructor 
	 */
	public enrolment() {super();}
	/**
	 * constructor
	 * @param memberID member's id
	 * @param raceID race's id
	 * @param boatID boat's id
	 * @param date race's date
	 */
	public enrolment(String memberID, int raceID, int boatID, Date date) {
		super();
		this.memberID = memberID;
		this.raceID = raceID;
		this.boatID = boatID;
		this.date = date;
	}
	/**
	 * constructor
	 * @param memberID member's id
	 * @param raceID race's id
	 * @param boatID boat's id
	 */
	public enrolment(String memberID, int raceID, int boatID) {
		super();
		this.memberID = memberID;
		this.raceID = raceID;
		this.boatID = boatID;
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
	 * gets the race id
	 * @return race id
	 */
	public int getRaceID() {
		return raceID;
	}

	/**
	 * sets the race id
	 * @param raceID 
	 */
	public void setRaceID(int raceID) {
		this.raceID = raceID;
	}

	/**
	 * gets the boat id
	 * @return boat id
	 */
	public int getBoatID() {
		return boatID;
	}

	/**
	 * sets the boat id
	 * @param boatID 
	 */
	public void setBoatID(int boatID) {
		this.boatID = boatID;
	}

	/**
	 * gets the race date
	 * @return race date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * sets the race date
	 * @param date race date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * controls if member is enrolled in race
	 * @param memberID 
	 * @param raceID
	 * @return true if member is enrolled
	 */
	public boolean memberIsEnrolled(String memberID, int raceID) {
		String query = "SELECT * FROM enrolment WHERE memberID = '" + memberID + "' AND raceID = " + raceID + " AND cancelled = false";
		ResultSet enrolled = executeQuery(query);
		if (enrolled != null)
			return true;
		return false;
	}

	
	/**
	 * get boat enrolled in given race by given member
	 * @param memberID 
	 * @param raceID 
	 * @return boat 
	 */
	public int getBoat(String memberID, int raceID) {
		String query = "SELECT * FROM enrolment WHERE memberID = '" + memberID + "' AND raceID = " + raceID;
		ResultSet enrolled = executeQuery(query);
		if (enrolled != null)
			try {
				return enrolled.getInt("boatID");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 0;
	}
	
}
