package system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import guiController.AlertDialogue;
import utility.ConnectDB;

/**
 * 
 * BOAT CLASS
 *
 */
public class Boat extends ConnectDB{

	private int id;
	private String name;
	private String owner;
	private double length;

	/**
	 *  constructor 
	 */
	public Boat(){super();}
	/**
	 * constructor
	 * @param id boat's id
	 * @param name boat's name
	 * @param owner boat's owner
	 * @param length boat's length
	 */
	public Boat(int id, String name, String owner, double length){
		super();
		this.name = name;
		this.id = id;
		this.owner = owner;
		this.length = length;
	}
	/**
	 * constructor
	 * @param name boat's name
	 * @param owner boat's owner
	 * @param length boat's length
	 */
	public Boat(String name, String owner, double length){
		super();
		this.name = name;
		this.owner = owner;
		this.length = length;
	}


	/**
	 * gets the boat's name
	 * @return boat's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the boat's id
	 * @return boat's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * gets the boat's owner
	 * @return boat's owner
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * gets the boat's length
	 * @return boat's length
	 */
	public double getLength() {
		return length;
	}
	

	/**
	 * set the boat's name
	 * @param newName the boat's name
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * set the boat's id
	 * @param newId the boat's id
	 */
	public void setId(int newId) {
		this.id = newId;
	}
	
	/**
	 * set the boat's owner
	 * @param newName the owner's name
	 */
	public void setOwner(String newName) {
		this.owner = newName;
	}
	
	/**
	 * set the boat's length
	 * @param newId the boat's length
	 */
	public void setLength(int newId) {
		this.length = newId;
	}
	
	/**
	 * gets all boats of a specific member
	 * @param owner
	 * @return boats of a member(owner)
	 */
	public ArrayList<Boat> getBoats(String owner) {
		ArrayList<Boat> boats = new ArrayList<Boat>();
		String query = "SELECT * FROM boat WHERE owner = '" + owner + "' AND removed = false";
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					boats.add(new Boat(result.getInt("id"),result.getString("name"),result.getString("owner"),result.getDouble("lengthInFeet")));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return boats;
	}
	
	/**
	 * flag a boat as removed
	 * @param idBoat
	 * @return true if boat successfully removed
	 */
	public boolean removeBoat(int idBoat) {
		String query = "UPDATE boat SET removed = true WHERE id = "+idBoat+"";
		return executeUpdate(query);
	}
	
	/**
	 * deletes a boat from the database given the id
	 * @param idBoat
	 * @return true if successful
	 */
	public boolean deleteBoat(int idBoat) {
		String query = "DELETE FROM boat WHERE id = "+idBoat+"";
		return executeUpdate(query);
	}
	
	/**
	 * gets a specific boat with given id
	 * @param id boat's id
	 * @return boat's id, name, owner and length from the database
	 */
	public Boat getBoat(int id) {
		String query = "SELECT * FROM boat WHERE id = " + id;
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				return new Boat(result.getInt("id"), result.getString("name"), result.getString("owner"), result.getDouble("lengthInFeet"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	/**
	 * controls is boat storage is paid
	 * @return true if storage is valid
	 */
	public boolean isValidStorage() {
		String query = "SELECT * FROM payment WHERE memberID = '"+this.owner+"' AND boatID = "+this.id+ " AND cause = 'storage fee' AND DATEDIFF(NOW(), date) < 365";
		ResultSet result = executeQuery(query);
		if (result != null)
			return true;
		return false;
	}
	
	/**
	 * get all boats concerning type
	 * @param type "storagePaid" or "storagePending"
	 * @return ArrayList containing all the boats
	 */
	public ArrayList<Boat> getAllBoats(String type) {
		ArrayList<Boat> boats = new ArrayList<Boat>();
		String query = "SELECT * FROM boat WHERE removed = false ";
		ResultSet result = executeQuery(query);
		Boat boat;
		if (result != null)
			try {
				do {
					boat = new Boat(result.getInt("id"), result.getString("name"), result.getString("owner"), result.getDouble("lengthInFeet"));
					switch(type) {
						case "storagePaid":
							if(boat.isValidStorage())
								boats.add(boat);
							break;
						case "storagePending":
							if(!boat.isValidStorage())
								boats.add(boat);
							break;
						default:
							boats.add(boat);
							break;
					}
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
				AlertDialogue.error("Error Occured (DB)");
			}
		return boats;
	}
	
}

