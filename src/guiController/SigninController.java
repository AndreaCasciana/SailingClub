package guiController;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import system.Member;
import system.Payment;
import system.Staff;

/**
 * 
 * Signin GUI controller
 *
 */
public class SigninController {
	@FXML 
	private AnchorPane entryDetails;
	@FXML
	private AnchorPane newMemberSliderInfo;
	@FXML
	private AnchorPane memberSliderInfo;
	@FXML
	private AnchorPane newMemberLoginForm;
	@FXML
	private AnchorPane memberLoginForm;
	@FXML
	private AnchorPane layer1;
	@FXML
	private AnchorPane layer2;
	@FXML
	private TextField memberID;
	@FXML
	private TextField memberPassword;
	@FXML
	private TextField newMemberID;
	@FXML
	private TextField newMemberName;
	@FXML
	private TextField newMemberSurname;
	@FXML
	private TextField newMemberAddress;
	@FXML
	private TextField newMemberPassword;
	@FXML
	private TextField newMemberPasswordRepeat;
	@FXML
	private Label loginErrorMsg;
	@FXML
	private Button signinBtn;
	@FXML
	private Button signupBtn;
	@FXML
	private Label newMemberIDErrorMsg;
	@FXML
	private Label newMemberPasswordErrorMsg;
	@FXML
	private Label newMemberErrorMsg;
	@FXML
	private Label newMemberpasswordErrorMsg;
	@FXML
	private Label membershipFee;
	@FXML
	private Label membershipFeeLabel;
	@FXML
	private AnchorPane addMembershipFee;
	
	/**
	 * initialise the sign in window
	 */
	public void initialize() {
		layer2.setLayoutX(0);
		layer2.setLayoutY(0);
		newMemberSliderInfo.setVisible(false);
		newMemberLoginForm.setVisible(false);
		addMembershipFee.setVisible(false);
		membershipFeeLabel.setText("Membership Fee: $"+new Staff().getMembershipFee());
		memberID.requestFocus();
		signinBtn.setDisable(true);
		signupBtn.setDisable(true);
		
		addListener();
	}
	
	/**
	 * goes from the sign in to the sign up view
	 * @param event the ActionEvent
	 */
	@FXML
	void gotoSignup(ActionEvent event) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.seconds(0.7));
		slide.setNode(layer2);
		slide.setToX(295);
		slide.play();
		newMemberSliderInfo.setVisible(true);
		newMemberLoginForm.setVisible(true);
		memberSliderInfo.setVisible(false);
		memberLoginForm.setVisible(false);
		newMemberID.requestFocus();
	}
	
	/**
	 * goes from the sign up to the sign in view
	 * @param event the ActionEvent
	 */
	@FXML
	void gotoSignin(ActionEvent event) {
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.seconds(0.7));
		slide.setNode(layer2);
		slide.setToX(0);
		slide.play();
		newMemberSliderInfo.setVisible(false);
		newMemberLoginForm.setVisible(false);
		memberSliderInfo.setVisible(true);
		memberLoginForm.setVisible(true);
		memberID.requestFocus();
	}
	
	/**
	 * sign in the member, with credentials validation
	 * @param event the ActionEvent
	 */
	@FXML
	void signin(ActionEvent event) {
		if(!new Member().exists(memberID.getText())) {
			loginErrorMsg.setText("Account with id does not exit");
			return;
		}
		
		if(!MemberController.member.login(memberID.getText(), memberPassword.getText()))
			loginErrorMsg.setText("ID or Password Incorrect!");
		else {
			Node node = (Node) event.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.close();
		}
	}
	
	/**
	 * sign up the member, with credentials validation
	 * @param event the ActionEvent
	 */
	@FXML
	void signup(ActionEvent event) {
		if(new Member().exists(newMemberID.getText())) {
			newMemberErrorMsg.setText("Account with this ID already exist!");
		}
		else if(!newMemberPassword.getText().equals(newMemberPasswordRepeat.getText())){
			newMemberpasswordErrorMsg.setText("Passwords does not match!");
		}else if (!newMemberID.getText().matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) {
			newMemberErrorMsg.setText("Codice Fiscale not valid.");
		}else {
			if(MemberController.member.register(new Member(newMemberName.getText(), newMemberSurname.getText(), newMemberAddress.getText(), newMemberID.getText(), newMemberPassword.getText()))) {
				entryDetails.setVisible(false);
				membershipFee.setText(Double.toString(new Staff().getMembershipFee()));
				addMembershipFee.setVisible(true);
			}
			else{
				AlertDialogue.error("Account creation Error.");
			}
		}
	}
	
	/**
	 * shift the focus to the next text field (memberPassword)
	 * @param event the KeyEvent
	 */
	@FXML
	void requestMemberPasswordFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			memberPassword.requestFocus();
	}
	
	/**
	 * fires the sign in button
	 * @param event the KeyEvent
	 */
	@FXML
	void fireSigninBtn(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			signinBtn.fire();
	}
	
	/**
	 * shift the focus to the next text field (newMemberName)
	 * @param event the KeyEvent
	 */
	@FXML
	void requestNewMemberNameFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			newMemberName.requestFocus();
	}
	
	@FXML
	/**
	 * shift the focus to the next text field (newMemberSurname)
	 * @param event the KeyEvent
	 */
	void requestNewMemberSurnameFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			newMemberSurname.requestFocus();
	}
	
	/**
	 * shift the focus to the next text field (newMemberAddress)
	 * @param event the KeyEvent
	 */
	@FXML
	void requestNewMemberAddressFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			newMemberAddress.requestFocus();
	}
	
	/**
	 * shift the focus to the next text field (newMemberPassword)
	 * @param event the KeyEvent
	 */
	@FXML
	void requestNewMemberPasswordFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			newMemberPassword.requestFocus();
	}
	
	/**
	 * shift the focus to the next text field (newMemberPasswordRepeat)
	 * @param event the ActionEvent
	 */
	@FXML
	void requestNewMemberPasswordRepeatFocus(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			newMemberPasswordRepeat.requestFocus();
	}
	
	/**
	 * fires the sign up button
	 * @param event the KeyEvent
	 */
	@FXML
	void fireSignupBtn(KeyEvent event) {
		if( event.getCode() == KeyCode.ENTER )
			signupBtn.fire();
	}
	
	/**
	 * allows new member the choice to pay membership fee immediately
	 * @param event the ActionEvent
	 */
	@FXML
	void payMembershipFeeNow(ActionEvent event){
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/payment.fxml"));
			
			paymentController.payment = new Payment(MemberController.member.getId(), "", null, null, "membership fee", null, null, new Staff().getMembershipFee(), null);
			
			Scene scene = new Scene(fxml);
			Stage paymentWindow = new Stage();
			
			paymentWindow.setScene(scene);
			paymentWindow.setResizable(false);
			paymentWindow.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			paymentWindow.initModality(Modality.APPLICATION_MODAL);
			paymentWindow.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
			paymentWindow.showAndWait();
			
			if(paymentController.paidSuccessfully) {
				Stage thisStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			    thisStage.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Unable to Access Payment Service Area.");
		}
	}
	
	/**
	 * allows the user to delay membership fee payment
	 * @param event the ActionEvent
	 */
	@FXML
	void payMembershipFeeLater(ActionEvent event){
		Node node = (Node) event.getSource();
	    Stage thisStage = (Stage) node.getScene().getWindow();
	    thisStage.close();
	}
	
	/**
	 * opens the window for accessing staff area
	 * @param event the ActionEvent
	 */
	@FXML
	void gotoStaffArea(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    ((Stage) thisStage.getOwner()).close(); // close previous window
		    Parent fxml = FXMLLoader.load(getClass().getResource("/gui/Staff.fxml"));
		    Scene scene = new Scene(fxml);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
			thisStage.setScene(scene);
			thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        if(AlertDialogue.confirm("Exit System?", "YES", "NO"))
			        	System.exit(0);
			        t.consume();
			    }
			});
			thisStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Error Occured while accessing the Staff Area");
		}
	}
	
	/**
	 * adds an event listener to the text fields for avoiding the insertion of incorrect values
	 */
	private void addListener() {
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(newValue.trim().length() > 0) {
				if(textField == newMemberName || textField == newMemberSurname) { 
					newValue = newValue.stripLeading(); 
					if (!newValue.matches("\\sa-zA-Z*")) {
						newValue = newValue.replaceAll("[^\\sa-zA-Z]", "");
						String[] arrOfStr = newValue.split(" ");
						StringBuilder sb;
						for (int i=0; i<arrOfStr.length; i++ ) {
							sb = new StringBuilder(arrOfStr[i]);
				            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
				            arrOfStr[i] = sb.toString();
						}
			            sb = new StringBuilder(newValue);
			            if(sb.charAt(newValue.length()-1) == ' ')
			            	textField.setText(String.join(" ", arrOfStr)+" ");
			            else
			            	textField.setText(String.join(" ", arrOfStr));
					}
				}else if(textField == newMemberAddress) {
					textField.setText(newValue.stripLeading().replaceAll("\\s{2,}", " "));
					String[] arrOfStr = newValue.split(" ");
					StringBuilder sb;
					for (int i=0; i<arrOfStr.length; i++ ) {
						sb = new StringBuilder(arrOfStr[i]);
			            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			            arrOfStr[i] = sb.toString();
					}
		            sb = new StringBuilder(newValue);
		            if(sb.charAt(newValue.length()-1) == ' ')
		            	textField.setText(String.join(" ", arrOfStr)+" ");
		            else
		            	textField.setText(String.join(" ", arrOfStr));
				}else {
						newValue = newValue.trim(); 
						if(textField == memberID || textField == newMemberID) {
							if(newValue.length() > 16)
								newValue = oldValue;
							if (!newValue.matches("\\sa-zA-Z0-9*")) 
								newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
							textField.setText(newValue.toUpperCase());
						}else
							textField.setText(newValue);
					}
			}else
				textField.setText(newValue.stripLeading());
			
			signinBtn.setDisable((memberID.getText().trim().length() != 16) || (memberPassword.getText().trim().length() == 0));
			signupBtn.setDisable((newMemberID.getText().trim().length() != 16) || (newMemberName.getText().trim().length() == 0) || (newMemberSurname.getText().trim().length() == 0) || (newMemberAddress.getText().trim().length() == 0) || (newMemberPassword.getText().trim().length() == 0) || (newMemberPasswordRepeat.getText().trim().length() == 0));
			loginErrorMsg.setText("");
			newMemberErrorMsg.setText("");
			newMemberpasswordErrorMsg.setText("");
		});
		
		memberID.textProperty().addListener(listener);
		memberPassword.textProperty().addListener(listener);
		newMemberID.textProperty().addListener(listener);
		newMemberName.textProperty().addListener(listener);
		newMemberSurname.textProperty().addListener(listener);
		newMemberAddress.textProperty().addListener(listener);
		newMemberPassword.textProperty().addListener(listener);
		newMemberPasswordRepeat.textProperty().addListener(listener);
	}
}
