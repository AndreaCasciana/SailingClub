package guiController;

import javafx.fxml.FXML;
import utility.ConnectDB;

/**
 * 
 * opening scene GUI of application
 *
 */
public class OpeningSceneController {
	/**
	 * keeps track if application intro is loaded
	 */
	public static Boolean introLoaded = false;
	
	/**
	 * attempts to connect to the database
	 * if it fails, it displays an error message
	 * and the program halts
	 */
	@FXML
	void initialize() {
		if(new ConnectDB().getConnection() == null) { // creates static connection for sub-clases
			AlertDialogue.error("Database ERROR!");
			System.exit(0);
		}
	}
	
}
