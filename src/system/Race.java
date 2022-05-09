package system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import guiController.AlertDialogue;
import utility.ConnectDB;

/**
 * 
 * RACE CLASS
 *
 */
public class Race extends ConnectDB{

	private int id;
	private String name;
	private Timestamp date;
	private double fee;
	
	/**
	 *  constructor 
	 */
	public Race(){super();}
	/**
	 * constructor
	 * @param id race's id
	 * @param name race's name
	 * @param date race's date
	 * @param fee race's fee
	 */
	public Race(int id, String name, Timestamp date, double fee){
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.fee = fee;
	}
	/**
	 * constructor
	 * @param name race's name
	 * @param date race's date
	 * @param fee race's fee
	 */
	public Race(String name, Timestamp date, double fee){
		super();
		this.name = name;
		this.date = date;
		this.fee = fee;
	}
	
	/**
	 * gets the race id
	 * @return race id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * gets the race name
	 * @return race name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the race date
	 * @return race date
	 */
	public Timestamp getDate() {
		return date;
	}
	
	/**
	 * gets the race fee
	 * @return race fee
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * sets the race id
	 * @param id race id
	 */
	public void setName(int id) {
		this.id = id;
	}
	
	/**
	 * sets the race name
	 * @param name race name
	 */
	public void setSurname(String name) {
		this.name = name;
	}
	
	/**
	 * sets the race date
	 * @param date race date
	 */
	public void setAddress(Timestamp date) {
		this.date = date;
	}
	
	/**
	 * sets the race fee
	 * @param fee race fee
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	/**
	 * gets all the races
	 * @param type upcoming, past or cancelled
	 * @return ArrayList containing all the races
	 */
	public ArrayList<Race> getRaces(String type) {
		ArrayList<Race> races = new ArrayList<Race>();
		String query = "SELECT * FROM race ";
		
		switch(type) {
			case "upcoming":
				query += "WHERE date > NOW() AND  cancelled = false";
				break;
			case "past":
				query += "WHERE date < NOW() AND  cancelled = false";
				break;
			case "cancelled":
				query += "WHERE cancelled = true";
				break;
			default:
				break;
		}
		
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					races.add(new Race(result.getInt("id"), result.getString("name"), result.getTimestamp("date"), result.getDouble("fee")));
				}
				while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return races;
	}
	
	/**
	 * controls if race is cancelled given id
	 * @param idRace 
	 * @return true if the race is cancelled
	 */
	public boolean isCancelled(int idRace) {
		String query = "SELECT * FROM race 	WHERE id = " + idRace;
		ResultSet result = executeQuery(query);
		try {
			if (result != null && result.getBoolean("cancelled"))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * controls if race is cancelled
	 * @return true if the race is cancelled
	 */
	public boolean isCancelled() {
		return isCancelled(this.id);
	}
	
	/**
	 * controls if race is finished given id
	 * @param idRace the race id
	 * @return true if the race is finished
	 */
	public boolean isFinished(int idRace) {
		String query = "SELECT * FROM race 	WHERE id = " + idRace + " AND date < NOW()";
		ResultSet result = executeQuery(query);
		if (result != null)
			return true;
		return false;
	}
	
	/**
	 * controls if race is finished
	 * @return true if the race is finished
	 */
	public boolean isFinished() {
		return isFinished(this.id);
	}
	
	/**
	 * @return number of people enrolled in the race
	 */
	public int getNumberEnrolled() {
		String query = "SELECT count(*) FROM enrolment 	WHERE raceID = " + this.id + " AND cancelled = false";
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
	 * cancels race
	 * @return true if successful
	 */
	public boolean cancel() {
		String query = "UPDATE race SET cancelled = true WHERE id = "+this.id;
		return executeUpdate(query);
	}
	
	/**
	 * edits the race details
	 * @param name race's name
	 * @param date race's date
	 * @return true if successful
	 */
	public boolean changeRaceDetails(String name, Timestamp date) {
		String query = "UPDATE race SET name = '" + name + "', date = '" + date +"' WHERE id = "+this.id;
		return executeUpdate(query);
	}
	
	/**
	 * gets the race details given id
	 * @param idRace race id
	 * @return Race object
	 */
	public Race getRace(int idRace) {
		String query = "SELECT * FROM race 	WHERE id = " + idRace;
		ResultSet result = executeQuery(query);
		try {
			if (result != null)
				return new Race(result.getInt("id"), result.getString("name"), result.getTimestamp("date"), result.getDouble("fee"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * adds a race to the database
	 * @return id of the race added
	 */
	public Race addRace(Race race) {
		String query = "INSERT INTO race (name, date, fee, cancelled) VALUES ('"+race.getName()+"','"+race.getDate()+"','"+race.getFee()+"',false)";
		if (executeUpdate(query)) {
			query = "SELECT LAST_INSERT_ID()";
			ResultSet result = executeQuery(query);
			if (result != null)
				try {
					return getRace(result.getInt("LAST_INSERT_ID()"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
}
