package guiController;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * 
 * alert window class, for displaying quick messages to user
 *
 */
public class AlertDialogue {
	
	/**
	 * displays an alert window with a custom message
	 * @param msg the message to be displayed
	 */
	public static void info(String msg) {
      //Creating a dialog
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      //Setting the title
      alert.setTitle("Alert");
      //Setting the content of the dialog
      alert.setHeaderText(msg);
//      alert.setContentText(msg);

      alert.showAndWait();

	}
	
	/**
	 * displays a dialog box with a custom message and asks for confirmation
	 * @param msg the message to be displayed
	 * @param btnYes the text displayed on the confirm button 
	 * @param btnNo the message to be displayed on the cancel button
	 * @return returns the response of the dialog box
	 */
	 public static boolean confirm(String msg, String btnYes, String btnNo) {
		boolean response = false;
	
	  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	  ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(btnYes);
	  ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(btnNo);
	  
	  alert.setTitle("Alert");
	  alert.setHeaderText(msg);
	  alert.showAndWait();
	  response = alert.getResult() == ButtonType.OK;
	  
	  return response;
	}
	
	/**
	 * displays a custom error message
	 * @param msg the message to be displayed
	 */
	public static void error(String msg) {
	  Alert alert = new Alert(Alert.AlertType.ERROR);
	  alert.setTitle("Alert");
	  alert.setHeaderText(msg);
	  alert.showAndWait();
	}
	
	/**
	 * displays a custom warning message
	 * @param msg the message to be displayed
	 */
	public static void warning(String msg) {
	  Alert alert = new Alert(Alert.AlertType.WARNING);
	  alert.setTitle("Alert");
	  alert.setHeaderText(msg);
	  alert.showAndWait();
	}
	
}
