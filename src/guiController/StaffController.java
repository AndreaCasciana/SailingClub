package guiController;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import system.Boat;
import system.CreditCard;
import system.Member;
import system.Notification;
import system.Payment;
import system.Race;
import system.Staff;
import system.TransferReciept;

/**
 * 
 * Staff GUI controller
 *
 */
public class StaffController {

	@FXML
	private AnchorPane loginView;
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private JFXButton signinBtn;
	@FXML
	private Label errMsg;
	@FXML
	private AnchorPane homeView;
	@FXML
	private AnchorPane feesView;
	@FXML
	private Label membershipFee;
	@FXML
	private Label BoatStorageFee;
	@FXML
	private Label lbIBAN;
	@FXML
	private Label lbBank;
	@FXML
	private Label lbBenefactor;
	@FXML
	private AnchorPane racesView;
	@FXML
	private VBox races;
	@FXML
	private JFXRadioButton rbAllRaces;
	@FXML
	private JFXRadioButton rbUpcomingRaces;
	@FXML
	private JFXRadioButton rbCancelledRaces;
	@FXML
	private JFXRadioButton rbPastRaces;
	@FXML
	private MenuButton menuStaff;
	@FXML
	private AnchorPane raceDetailsView;
	@FXML
	private Label raceName;
	@FXML
	private Label raceDate;
	@FXML
	private Label raceTime;
	@FXML
	private Label raceStatus;
	@FXML
	private Hyperlink raceNumberEnrolled;
	@FXML
	private JFXButton cancelRaceBtn;
	@FXML
	private JFXButton changeRaceButton;
	@FXML
	private JFXButton addRaceBtn;
	@FXML
	private AnchorPane membersView;
	@FXML
	private VBox members;
	@FXML
	private JFXButton filterMembersBtn;
	@FXML
	private VBox filterMembers;
	@FXML
	private CheckBox showMembershipPaidCB;
	@FXML
	private CheckBox showMembershipPendingCB;
	@FXML
	private Label lbMembershipPending;
	@FXML
	private AnchorPane memberDetailsView;
	@FXML
	private ImageView memberProfilePicture;
	@FXML
	private Label memberName;
	@FXML
	private Label memberSurname;
	@FXML
	private Label memberAddress;
	@FXML
	private Label memberID;
	@FXML
	private Label membershipStatus;
	@FXML
	private AnchorPane boatDetailsView;
	@FXML
	private Label boatStorageStatus;
	@FXML
	private Label boatName;
	@FXML
	private Label boatLength;
	@FXML
	private Label boatOwner;
	@FXML
	private AnchorPane boatsView;
	@FXML
	private JFXButton filterBoatsBtn;
	@FXML
	private VBox boats;
	@FXML
	private VBox filterBoats;
	@FXML
	private CheckBox showValidStorageBoatsCB;
	@FXML
	private CheckBox showInvalidStorageBoatsCB;
	@FXML
	private Label lbStorageBoatPending;
	@FXML
	private AnchorPane notificationBuilder;
	@FXML
	private TextField msgHead;
	@FXML
	private TextArea msgBody;
	@FXML
	private AnchorPane editProfileView;
	@FXML
	private TextField newName;
	@FXML
	private TextField newSurname;
	@FXML
	private TextField newUsername;
	@FXML
	private PasswordField oldPassword;
	@FXML
	private PasswordField newPassword1;
	@FXML
	private PasswordField newPassword2;
	@FXML
	private JFXButton setName;
	@FXML
	private JFXButton setSurname;
	@FXML
	private JFXButton setUsername;
	@FXML
	private JFXButton setPassword;
	@FXML
	private AnchorPane staffsView;
	@FXML
	private TableView<Staff> staffsTableView;
	@FXML
	private TableColumn<Staff, String> nameCol;
	@FXML
	private TableColumn<Staff, String> surnameCol;
	@FXML
	private TableColumn<Staff, String> usernameCol;
	@FXML
	private TableColumn<Staff, String> passwordCol;
	@FXML
	private TableColumn<Staff, Boolean> adminshipCol;
	@FXML
	private Label msgStaffs;
	@FXML
	private AnchorPane paymentsView;
	@FXML
	private JFXRadioButton rbAllPayments;
	@FXML
	private JFXRadioButton rbStoragePayments;
	@FXML
	private JFXRadioButton rbMembershipPayments;
	@FXML
	private JFXRadioButton rbRacePayments;
	@FXML
	private TableView<Payment> paymentsTable;
	@FXML
	private TableColumn<Payment, String> memberCol;
	@FXML
	private TableColumn<Payment, String> causeCol;
	@FXML
	private TableColumn<Payment, String> methodCol;
	@FXML
	private TableColumn<Payment, Double> amountCol;
	@FXML
	private TableColumn<Payment, Date> dateCol;
	@FXML
	private AnchorPane searchSuggestions;
	@FXML
	private TextField searchText;
	@FXML
	private AnchorPane paymentClickedView;
	@FXML
	private Label memberInfo;
	@FXML
	private Label causeInfo;
	@FXML
	private Label methodInfo;
	@FXML
	private Label amountInfo;
	@FXML
	private Label dateInfo;

	private Staff staff;
	private ToggleGroup tgRaces;
	private ToggleGroup tgPayments;
	private Race raceClicked;
	private Member memberClicked;
	private Boat boatClicked;
	private String goBackToView;
	private MenuItem m1 = new MenuItem("Fees");
	private MenuItem m2 = new MenuItem("Profile");
	private MenuItem m3 = new MenuItem("Logout");
	private MenuItem m4 = new MenuItem("STAFFS");
	private MenuItem m5 = new MenuItem("Payments");

	/**
	 * initializes the window
	 */
	@FXML
	void initialize() {
		staff = new Staff();

		loginView.setVisible(true);
		homeView.setVisible(false);
		disableAll();
		signinBtn.setDisable(true);

		tgRaces = new ToggleGroup();
		rbAllRaces.setToggleGroup(tgRaces);
		rbUpcomingRaces.setToggleGroup(tgRaces);
		rbCancelledRaces.setToggleGroup(tgRaces);
		rbPastRaces.setToggleGroup(tgRaces);

		tgPayments = new ToggleGroup();
		rbAllPayments.setToggleGroup(tgPayments);
		rbMembershipPayments.setToggleGroup(tgPayments);
		rbStoragePayments.setToggleGroup(tgPayments);
		rbRacePayments.setToggleGroup(tgPayments);

		menuStaff.getItems().clear();
		menuStaff.getItems().add(m1);
		menuStaff.getItems().add(m2);
		menuStaff.getItems().add(m5);
		menuStaff.getItems().add(m3);
		m1.setOnAction(e -> fees());
		m2.setOnAction(e -> editProfile());
		m3.setOnAction(e -> logout());
		m5.setOnAction(e -> payments());

		addListener();

		nameCol.setCellValueFactory(new PropertyValueFactory<Staff, String>("name"));
		surnameCol.setCellValueFactory(new PropertyValueFactory<Staff, String>("surname"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<Staff, String>("username"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<Staff, String>("password"));
		adminshipCol.setCellValueFactory(new PropertyValueFactory<Staff, Boolean>("isAdmin"));

		memberCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("memberID"));
		causeCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("cause"));
		methodCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("method"));
		amountCol.setCellValueFactory(new PropertyValueFactory<Payment, Double>("amount"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Payment, Date>("date"));
	}

	/**
	 * signs in the staff if the credentials are correct
	 */
	@FXML
	void signin(ActionEvent event) {
		if (!staff.exists(username.getText())) {
			errMsg.setText("Account with username does not exist");
			return;
		}
		if (!staff.login(username.getText(), password.getText()))
			errMsg.setText("ID or Password Incorrect!");
		else {
			loginView.setVisible(false);
			menuStaff.setText(staff.getUsername());
			if (staff.getIsAdmin()) {
				menuStaff.getItems().add(m4);
				m4.setOnAction(e -> showStaffs());
			}
			homeView.setVisible(true);
		}
	}

	/**
	 * shift the focus to the next text field (password)
	 * @param event the KeyEvent
	 */
	@FXML
	void requestPasswordFocus(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			password.requestFocus();
	}

	/**
	 * fires the sign in button
	 * @param event the KeyEvent
	 */
	@FXML
	void fireLogin(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			signinBtn.fire();
	}

	/**
	 * opens the boats view displaying all the boats in club
	 * @param event the ActionEvent
	 */
	@FXML
	void boatsBtn(ActionEvent event) {
		disableAll();
		showValidStorageBoatsCB.setSelected(false);
		showInvalidStorageBoatsCB.setSelected(false);
		setBoats();
		filterBoats.setVisible(false);
		boatsView.setVisible(true);
		goBackToView = "boat";
	}

	/**
	 * displays owner of specific boat
	 * @param event the ActionEvent
	 */
	@FXML
	void showBoatOwner(ActionEvent event) {
		setMemberClicked();
		boatDetailsView.setVisible(false);
		memberDetailsView.setVisible(true);
	}

	/**
	 * displays sailing club's members
	 * @param event the ActionEvent
	 */
	@FXML
	void membersBtn(ActionEvent event) {
		disableAll();
		showMembershipPaidCB.setSelected(false);
		showMembershipPendingCB.setSelected(false);
		setMembers();
		filterMembers.setVisible(false);
		membersView.setVisible(true);
		goBackToView = "member";
	}

	/**
	 * displays all the races oragnised by club
	 * @param event the ActionEvent
	 */
	@FXML
	void racesBtn(ActionEvent event) {
		disableAll();
		setRaces("all");
		rbAllRaces.setSelected(true);
		racesView.setVisible(true);
		goBackToView = "race";
	}

	/**
	 * goes back to the boat view from the boat's details view
	 * @param event the MouseEvent
	 */
	@FXML
	void goBackFromBoatView(MouseEvent event) {
		boatDetailsView.setVisible(false);
		if (goBackToView != null && goBackToView.equals("member")) {
			setMemberClicked();
			memberDetailsView.setVisible(true);
		} else
			boatsView.setVisible(true);
	}

	/**
	 * goes back to the races view from the race details view
	 * @param event the MouseEvent
	 */
	@FXML
	void goBackToRaces(MouseEvent event) {
		raceDetailsView.setVisible(false);

		if (rbAllRaces.isSelected()) {
			setRaces("all");
			rbAllRaces.setSelected(true);
		} else if (rbUpcomingRaces.isSelected()) {
			setRaces("upcoming");
			rbUpcomingRaces.setSelected(true);
		} else if (rbCancelledRaces.isSelected())
			rbCancelledRaces.setSelected(true);
		else if (rbPastRaces.isSelected())
			rbPastRaces.setSelected(true);

		racesView.setVisible(true);
	}

	/**
	 * goes back to the boats view or members view
	 * @param event the MouseEvent
	 */
	@FXML
	void goBackFromMemberView(MouseEvent event) {
		memberDetailsView.setVisible(false);
		if (goBackToView != null && goBackToView.equals("boat")) {
			setBoatClicked();
			boatDetailsView.setVisible(true);
		} else if (goBackToView != null && goBackToView.equals("race")) {
			setRaceClicked();
			raceDetailsView.setVisible(true);
		} else {
			setMembers();
			membersView.setVisible(true);
		}
	}

	/**
	 * goes back to the boats or members view from the notification builder view
	 * @param event the MouseEvent
	 */
	@FXML
	void goBackFromNotificationBuilderView(MouseEvent event) {
		notificationBuilder.setVisible(false);
		if (goBackToView != null && goBackToView.equals("boat")) {
			setBoatClicked();
			boatDetailsView.setVisible(true);
		} else if (goBackToView != null && goBackToView.equals("member")) {
			setMemberClicked();
			memberDetailsView.setVisible(true);
		} else {
			setMembers();
			membersView.setVisible(true);
		}
	}

	/**
	 * displays all the members enrolled in a specific race
	 * @param event the ActionEvent
	 */
	@FXML
	void viewMembersEnrolledInRace(ActionEvent event) {
		ArrayList<String> membersEnrolled = staff.getMembersEnrolled(raceClicked.getId());
		if (membersEnrolled.isEmpty())
			return;
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(raceDetailsView.getScene().getWindow());
		Text title = new Text("Members Enrolled in Race: '" + raceClicked.getName() + "'");
		title.setId("raceClicked");
		VBox titleVbox = new VBox(title);
		titleVbox.setAlignment(Pos.TOP_CENTER);

		VBox dialogueVbox = new VBox();
		dialogueVbox.setAlignment(Pos.CENTER);
		dialogueVbox.setSpacing(20);
		dialogueVbox.setId("dialogRaceClicked");

		VBox raceVbox = new VBox();
		raceVbox.setSpacing(10);
		raceVbox.setPadding(new Insets(10, 0, 10, 0));
		raceVbox.setAlignment(Pos.CENTER);
		raceVbox.setId("VboxRaceClicked");
		for (String memberID : membersEnrolled) {
			Member member = new Member().getMember(memberID);
			JFXButton btn = new JFXButton(
					member.getName() + " " + member.getSurname() + " (ID: " + member.getId() + ")");
			btn.setId("memberDetails");
			raceVbox.getChildren().add(btn);

			btn.setOnAction(e -> {
				dialog.close();
				raceDetailsView.setVisible(false);
				memberClicked = member;
				setMemberClicked();
				memberDetailsView.setVisible(true);
			});
		}

		dialogueVbox.getChildren().addAll(titleVbox, raceVbox);
		ScrollPane pane = new ScrollPane(dialogueVbox);
		pane.setFitToWidth(true);
		pane.setFitToHeight(true);
		Scene dialogScene = new Scene(pane, 300, 300);
		dialog.setScene(dialogScene);
		dialog.setResizable(false);
		dialogScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		dialog.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();

	}

	/**
	 * displays/hides members filters option
	 * @param event the ActionEvent
	 */
	@FXML
	void toggleFilterMembers(ActionEvent event) {
		filterMembers.setVisible(!filterMembers.isVisible());
	}

	/**
	 * displays/hides boats filters option
	 * @param event the ActionEvent
	 */
	@FXML
	void toggleFilterBoats(ActionEvent event) {
		filterBoats.setVisible(!filterBoats.isVisible());
	}

	/**
	 * displays only the members with membership fee paid
	 * @param event the ActionEvent
	 */
	@FXML
	void showMembershipPaid(ActionEvent event) {
		setMembers();
	}

	/**
	 * displays only the members with membership fee pending
	 * @param event the ActionEvent
	 */
	@FXML
	void showMembershipPending(ActionEvent event) {
		setMembers();
	}

	/**
	 * displays the boats with valid storage (storage fee paid)
	 * @param event the ActionEvent
	 */
	@FXML
	void showValidStorageBoats(ActionEvent event) {
		setBoats();
	}

	/**
	 * displays the boats with invalid storage (storage fee unpaid)
	 * @param event the ActionEvent
	 */
	@FXML
	void showInvalidStorageBoats(ActionEvent event) {
		setBoats();
	}

	/**
	 * edits club boat storage fee per foot
	 * @param event the ActionEvent
	 */
	@FXML
	void modifyBoatStorageFee(ActionEvent event) {
		getNewFee("storage");
		BoatStorageFee.setText("$" + staff.getBoatStorageFeePerFoot());
	}

	/**
	 * edits club membership fee
	 * @param event the ActionEvent
	 */
	@FXML
	void modifyMembershipFee(ActionEvent event) {
		getNewFee("membership");
		membershipFee.setText("$" + staff.getMembershipFee());
	}
	
	/**
	 * edits club bank's name
	 * @param event the ActionEvent
	 */
	@FXML
	void modifyBank(ActionEvent event) {
		getNewbankDetails("bank");
		lbBank.setText("$" + staff.getBank());
	}
	
	/**
	 * edits club bank's IBAN
	 * @param event the ActionEvent
	 */
	@FXML
	void modifyIBAN(ActionEvent event) {
		getNewbankDetails("IBAN");
		lbIBAN.setText("$" + staff.getIBAN());
	}

	/**
	 * allows staff to cancels a race
	 * @param event the ActionEvent
	 */
	@FXML
	void cancelRace(ActionEvent event) {
		if (AlertDialogue.confirm("Confirm Race Cancellation?", "confirm", "cancel")) {
			if (raceClicked.cancel()) {
				AlertDialogue.warning("Race '" + raceClicked.getName() + "' has been cancelled.");
				if (raceClicked.getNumberEnrolled() > 0) {
					AlertDialogue.info("Send Notification to members already enrolled");
					ArrayList<String> membersEnrolled = staff.getMembersEnrolled(raceClicked.getId());
					for (String id : membersEnrolled)
						staff.sendNotification(
								new Notification(id, "Race Cancellation", "This race << " + raceClicked.getName()
										+ " >> has been cancelled. Enrolment fee will be refunded within the week."));
				}
				raceClicked.getRace(raceClicked.getId());
				setRaceClicked();
			} else
				AlertDialogue.error("Error Occurred (DB)");
		}
	}

	/**
	 * allows the staff to edit a race details
	 * @param event the ActionEvent
	 */
	@FXML
	void modifyRace(ActionEvent event) {
		raceDetails("edit");
	}

	/**
	 * allows the staff to add an organised race
	 * @param event the ActionEvent
	 */
	@FXML
	void addRace(ActionEvent event) {
		raceDetails("add");
	}

	/**
	 * goes to the home page
	 * @param event the ActionEvent
	 */
	@FXML
	void gotohomepage(ActionEvent event) {
		homepage();
	}

	/**
	 * allows the staff to send a notification to a member about the membership fee
	 * @param event the ActionEvent
	 */
	@FXML
	void sendNotificationMember(ActionEvent event) {
		boatDetailsView.setVisible(false);
		memberDetailsView.setVisible(false);
		if (!memberClicked.isValidMembership()) {
			msgHead.setText("Annual Membership Due");
			msgBody.setText(
					"This is a reminder to renew  membership fee to access benefits and paticipate in future races.");
		} else {
			msgHead.setText("");
			msgBody.setText("");
		}
		notificationBuilder.setVisible(true);
	}

	/**
	 * allows the staff to send a notification to a member about the boat storage fee
	 * @param event the ActionEvent
	 */
	@FXML
	void sendNotificationBoat(ActionEvent event) {
		boatDetailsView.setVisible(false);
		memberDetailsView.setVisible(false);
		if (!boatClicked.isValidStorage()) {
			msgHead.setText("Boat Storage Fee Expired");
			msgBody.setText(
					"Annual storage fee for boat << " + boatClicked.getName() + " >> has expired. Please renew.");
		} else {
			msgHead.setText("");
			msgBody.setText("");
		}
		notificationBuilder.setVisible(true);
		memberClicked = memberClicked.getMember(boatClicked.getOwner());
	}

	/**
	 * allows the staff to send a custom notification to a member
	 * @param event the ActionEvent
	 */
	@FXML
	void sendNotification(ActionEvent event) {
		if (msgHead.getText().trim().length() == 0) {
			AlertDialogue.error("Please enter an heading/title of the message");
			return;
		}
		if (!AlertDialogue.confirm("Send Message?", "YES", "NO"))
			return;
		if (staff.sendNotification(new Notification(memberClicked.getId(), msgHead.getText(), msgBody.getText()))) {
			msgHead.setText("");
			msgBody.setText("");
			AlertDialogue.info("Notification Sent.");
			notificationBuilder.setVisible(false);
			goBackFromNotificationBuilderView(null);
		} else
			AlertDialogue.error("Error Occured (DB)");
	}

	/**
	 * displays all the boats of a member 
	 * @param event the ActionEvent
	 */
	@FXML
	void showMemberBoats(ActionEvent event) {
		ArrayList<Boat> boats = new Boat().getBoats(memberClicked.getId());
		if (boats.isEmpty()) {
			AlertDialogue.info("No Boat Registered for this member");
			return;
		}

		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(memberDetailsView.getScene().getWindow());
		Text title = new Text("Member Boats");
		title.setId("memberBoatsTitle");
		VBox titleVbox = new VBox(title);
		titleVbox.setAlignment(Pos.TOP_CENTER);

		VBox dialogueVbox = new VBox();
		dialogueVbox.setAlignment(Pos.CENTER);
		dialogueVbox.setSpacing(20);
		dialogueVbox.setId("dialogVboxSelectBoat");

		VBox boatVbox = new VBox();
		boatVbox.setSpacing(10);
		boatVbox.setPadding(new Insets(10, 0, 10, 0));
		boatVbox.setAlignment(Pos.CENTER);
		boatVbox.setId("VboxSelectBoat");

		for (Boat boat : boats) {
			JFXButton btn = new JFXButton(boat.getName() + " (Length: " + boat.getLength() + "ft)");
			btn.setId(boat.isValidStorage() ? "selectBoatButtonValid" : "selectBoatButtonNotValid");
			boatVbox.getChildren().add(btn);

			btn.setOnAction(e -> {
				memberDetailsView.setVisible(false);
				boatClicked = boat;
				setBoatClicked();
				dialog.close();
				boatDetailsView.setVisible(true);
			});
		}

		Label lb = new Label("*(boats in red) storage fee expired");
		lb.setId("lbExpStorageMsg");
		dialogueVbox.getChildren().addAll(titleVbox, boatVbox, lb);
		ScrollPane pane = new ScrollPane(dialogueVbox);
		pane.setFitToWidth(true);
		pane.setFitToHeight(true);
		Scene dialogScene = new Scene(pane, 300, 300);
		dialog.setScene(dialogScene);
		dialog.setResizable(false);
		dialogScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		dialog.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();
	}

	/**
	 * modify the staff's name
	 * @param event the ActionEvent
	 */
	@FXML
	void setName(ActionEvent event) {
		String oldName = staff.getName();
		String newNameToSet = newName.getText();

		if (newNameToSet.length() == 0) {
			AlertDialogue.error("Name field is empty.");
			return;
		}
		if (oldName.equals(newNameToSet)) {
			AlertDialogue.warning("Name is the same as the old one.");
			return;
		}

		if (AlertDialogue.confirm("comfirm change of Name from '" + oldName + "' to '" + newNameToSet + "'", "YES",
				"Cancel")) {
			if (staff.setNewName(newNameToSet)) {
				AlertDialogue.info("Name successfully changed to " + newNameToSet);
			} else
				AlertDialogue.error("Error occured. (DB)");
		}
	}

	/**
	 * modify the staff's surname
	 * @param event the ActionEvent
	 */
	@FXML
	void setSurname(ActionEvent event) {
		String oldSurname = staff.getSurname();
		String newSurnameToSet = newSurname.getText();

		if (newSurnameToSet.length() == 0) {
			AlertDialogue.error("Surname field is empty.");
			return;
		}
		if (oldSurname.equals(newSurnameToSet)) {
			AlertDialogue.warning("Surname is the same as the old one.");
			return;
		}

		if (AlertDialogue.confirm("comfirm change of Surname from '" + oldSurname + "' to '" + newSurnameToSet + "'",
				"YES", "Cancel")) {
			if (staff.setNewSurname(newSurnameToSet)) {
				AlertDialogue.info("Surname successfully changed to " + newSurnameToSet);
			} else
				AlertDialogue.error("Error occured. (DB)");
		}
	}

	/**
	 * modify the staff's username
	 * @param event the ActionEvent
	 */
	@FXML
	void setUsername(ActionEvent event) {
		String oldUsername = staff.getUsername();
		String newUsernameToSet = newUsername.getText();

		if (newUsernameToSet.length() == 0) {
			AlertDialogue.error("Username field is empty.");
			return;
		}
		if (oldUsername.equals(newUsernameToSet)) {
			AlertDialogue.warning("Username is the same as the old one.");
			return;
		}
		if (staff.exists(newUsernameToSet)) {
			AlertDialogue.warning("username '" + newUsernameToSet + "' already exists.");
			return;
		}
		if (AlertDialogue.confirm("comfirm change of Username from '" + oldUsername + "' to '" + newUsernameToSet + "'",
				"YES", "Cancel")) {
			if (staff.setNewUsername(newUsernameToSet)) {
				AlertDialogue.info("Address successfully changed to " + newUsernameToSet);
				menuStaff.setText(staff.getUsername());
			} else
				AlertDialogue.error("Error occured. (DB)");
		}
	}

	/**
	 * modify the staff's password
	 * @param event the ActionEvent
	 */
	@FXML
	void setPassword(ActionEvent event) {
		if (oldPassword.getText().trim().length() == 0 || newPassword1.getText().trim().length() == 0
				|| newPassword2.getText().trim().length() == 0) {
			AlertDialogue.error("Please fill all passwords field.");
			return;
		}

		String newPasswordToSet1 = newPassword1.getText();
		String newPasswordToSet2 = newPassword2.getText();

		if (!newPasswordToSet1.equals(newPasswordToSet2)) {
			AlertDialogue.error("new password does not match!");
			return;
		}
		if (!staff.getPassword().equals(oldPassword.getText())) {
			AlertDialogue.error("old password incorrect");
			return;
		}
		if (staff.getPassword().equals(newPasswordToSet1)) {
			AlertDialogue.warning("new password can't be the same as the old one");
			return;
		}

		if (AlertDialogue.confirm("comfirm change of Password", "YES", "Cancel")) {
			if (staff.setNewPassword(newPasswordToSet1)) {
				AlertDialogue.info("Password successfully changed");
			} else
				AlertDialogue.error("Error occured. (DB)");
		}
	}

	/**
	 * allows admin to add a staff
	 * @param event the ActionEvent
	 */
	@FXML
	void addStaff(ActionEvent event) {
		msgStaffs.setText("");

		addStaff();
		setStaffs();
	}

	/**
	 * allows admin to edit a staff's account
	 * @param event the ActionEvent
	 */
	@FXML
	void editStaff(ActionEvent event) {
		msgStaffs.setText("");
		try {
			Staff getSelectedStaff = staffsTableView.getItems()
					.get(staffsTableView.getSelectionModel().getSelectedIndex());

			editStaff(getSelectedStaff);
			setStaffs();
		} catch (IndexOutOfBoundsException e) {
			msgStaffs.setText("Select an account you wish to edit!  ");
		}
	}

	/**
	 * allows admin deletes the staff's account
	 * @param event the ActionEvent
	 */
	@FXML
	void removeStaff(ActionEvent event) {
		msgStaffs.setText("");

		try {
			Staff getSelectedStaff = staffsTableView.getItems()
					.get(staffsTableView.getSelectionModel().getSelectedIndex());
			if (staff.getUsername().equals(getSelectedStaff.getUsername())) {
				if (AlertDialogue.confirm(
						"Do you really want to remove YOUR account? \n(" + getSelectedStaff.getUsername() + ")", "YES",
						"NO")) {
					if (staff.countAdmins() == 1) {
						AlertDialogue.warning(
								"Request can not be granted because you are the only admin in system. \nMake another staff admin before removing your account.");
						return;
					}
					if (staff.removeStaff(getSelectedStaff)) {
						AlertDialogue.warning("Your account has been removed.");
						homepage();
					}
				}
			} else if (AlertDialogue.confirm("Confirm removal of " + getSelectedStaff.getUsername() + "'s account?",
					"YES", "NO")) {
				staff.removeStaff(getSelectedStaff);
				AlertDialogue.warning(getSelectedStaff.getUsername() + "'s has been removed.");
				setStaffs();
			}
		} catch (IndexOutOfBoundsException e) {
			msgStaffs.setText("Select an account you wish to remove!");
		}
	}

	/**
	 * refreshes visible view
	 * @param event the ActionEvent
	 */
	@FXML
	void refresh(ActionEvent event) {
		if (racesView.isVisible()) {
			if (rbAllRaces.isSelected()) {
				setRaces("all");
				rbAllRaces.setSelected(true);
			} else if (rbUpcomingRaces.isSelected()) {
				setRaces("upcoming");
				rbUpcomingRaces.setSelected(true);
			} else if (rbCancelledRaces.isSelected()) {
				setRaces("cancelled");
				rbCancelledRaces.setSelected(true);
			} else if (rbPastRaces.isSelected()) {
				setRaces("past");
				rbPastRaces.setSelected(true);
			}
		} else if (raceDetailsView.isVisible()) {
			raceClicked = raceClicked.getRace(raceClicked.getId());
			setRaceClicked();
		} else if (feesView.isVisible()) {
			fees();
		} else if (membersView.isVisible()) {
			setMembers();
		} else if (memberDetailsView.isVisible()) {
			memberClicked = memberClicked.getMember(memberClicked.getId());
			setMemberClicked();
		} else if (boatDetailsView.isVisible()) {
			boatClicked = boatClicked.getBoat(boatClicked.getId());
			setBoatClicked();
		} else if (boatsView.isVisible()) {
			setBoats();
		}
	}

	/**
	 * displays the specific payment details
	 * @param event the MouseEvent
	 */
	@FXML
	void showPaymentClicked(MouseEvent event) {
		try {
			Payment getSelectedPayment = paymentsTable.getItems()
					.get(paymentsTable.getSelectionModel().getSelectedIndex());

			Member member = new Member().getMember(getSelectedPayment.getMemberID());
			memberInfo.setText(member.getName() + " " + member.getSurname() + " (" + member.getId() + ")");

			if (getSelectedPayment.getCause().equals("storage fee")) {
				Boat boat = new Boat().getBoat(getSelectedPayment.getBoatID());
				if(boat != null)
					causeInfo.setText("Payment of Annual Storage Fee for boat: " + boat.getName());
			} else if (getSelectedPayment.getCause().equals("race fee")) {
				Race race = new Race().getRace(getSelectedPayment.getRaceID());
				if(race != null)
					causeInfo.setText("Payment of Enrolment Fee for race: " + race.getName());
			} else
				causeInfo.setText("Payment for Annual Membership Fee");
			
			if(getSelectedPayment.getMethod().equals("card")) {
				CreditCard card = new CreditCard().getCard(getSelectedPayment.getMethodID_CC());
				if(card != null)
					methodInfo.setText("Paid with card: "+card.getType()+" ••••"+card.getCardNumber().toString().substring (card.getCardNumber().toString().length()-4));
			}else {
				TransferReciept transfer = new TransferReciept().getTransfer(getSelectedPayment.getMethodID_TR());
				if(transfer != null)
					methodInfo.setText("Paid with tarnsfer provided by: "+transfer.getBank());
			}
			
			amountInfo.setText("Amount Paid: $"+getSelectedPayment.getAmount());
			
			dateInfo.setText("Date: "+getSelectedPayment.getDate());

			paymentsView.setVisible(false);
			paymentClickedView.setVisible(true);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			AlertDialogue.error("unable to view payment.");
		}
	}
	
	/**
	 * goes back to the payments view from the payment detail view
	 * @param event the MouseEvent
	 */
	@FXML
	void goBackToPayments(MouseEvent event) {
		paymentClickedView.setVisible(false);
		paymentsView.setVisible(true);
	}

	/**
	 * allows payment search
	 * @param event the MouseEvent
	 */
	@FXML
	void search(ActionEvent event) {
		String toSearch = searchText.getText().trim();
		if (toSearch.length() == 0)
			return;

		paymentsTable.getItems().clear();

		ArrayList<Payment> paymentsArray;

		if (tgPayments.getSelectedToggle() == rbMembershipPayments) {
			paymentsArray = new Payment().getPayments("membership");
		} else if (tgPayments.getSelectedToggle() == rbStoragePayments) {
			paymentsArray = new Payment().getPayments("storage");
		} else if (tgPayments.getSelectedToggle() == rbRacePayments) {
			paymentsArray = new Payment().getPayments("race");
		} else {
			paymentsArray = new Payment().getPayments("all");
		}

		if (new Member().exists(toSearch.toUpperCase())) {
			for (int i = paymentsArray.size() - 1; i >= 0; i--)
				if (!paymentsArray.get(i).getMemberID().equals(toSearch.toUpperCase()))
					paymentsArray.remove(paymentsArray.get(i));
		} else if (isValidDate(toSearch)) {
			try {
				Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(toSearch);
				for (int i = paymentsArray.size() - 1; i >= 0; i--)
					if (!paymentsArray.get(i).getDate().equals(date))
						paymentsArray.remove(paymentsArray.get(i));
			} catch (ParseException e) {
			}

		} else {
			Boat boat = new Boat();
			Race race = new Race();
			Member member = new Member();
			for (int i = paymentsArray.size() - 1; i >= 0; i--) {
				if(paymentsArray.get(i).getBoatID() != 0)
					boat = boat.getBoat(paymentsArray.get(i).getBoatID());
				if(paymentsArray.get(i).getRaceID() != 0)
					race = race.getRace(paymentsArray.get(i).getRaceID());
				member = member.getMember(paymentsArray.get(i).getMemberID());
				if (!((boat != null && boat.getId() != 0 && boat.getName().contains(toSearch))
						|| (race != null && race.getId() != 0  && race.getName().contains(toSearch)) || (member != null
								&& (member.getName().contains(toSearch) || member.getSurname().contains(toSearch)))))
					paymentsArray.remove(paymentsArray.get(i));
			}
		}

		if (!paymentsArray.isEmpty())
			for (Payment payment : paymentsArray)
				paymentsTable.getItems().add(payment);
		else
			paymentsTable.setPlaceholder(new Label("No Payment."));
	}

	/**
	 * shows search suggestions on mouse enter
	 * @param event the MouseEvent
	 */
	@FXML
	void searchInfoEntered(MouseEvent event) {
		searchSuggestions.setVisible(true);
	}

	/**
	 * hides search suggestions on mouse exit
	 * @param event the MouseEvent
	 */
	@FXML
	void searchInfoExited(MouseEvent event) {
		searchSuggestions.setVisible(false);
	}

	/**
	 * triggers the search when the staff presses enter 
	 * @param event the KeyEvent
	 */
	@FXML
	void gotoSearch(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			search(null);
	}

	/**
	 * displays the race details view
	 * @param type
	 */
	void raceDetails(String type) {
		final Stage dialog = new Stage();

		JFXDatePicker date = new JFXDatePicker();
		JFXTimePicker time = new JFXTimePicker();
		time.set24HourView(true);
		Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() { // callback function to disable
																						// past dates before now
			@Override
			public DateCell call(final DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty); // To change body of generated methods, choose Tools | Templates.
						LocalDate today = LocalDate.now();
						setDisable(empty || item.compareTo(today) < 0);
					}

				};
			}

		};
		date.setDayCellFactory(callB);

		TextField title = new TextField();
		TextField fee = new TextField();

		if (type.equals("edit")) {
			LocalDateTime ldt = LocalDateTime.parse(raceClicked.getDate().toString().replace(" ", "T"));
			date.setValue(ldt.toLocalDate());
			time.setValue(ldt.toLocalTime());
			title.setText(raceClicked.getName());
		}

		title.setPromptText("Enter new race title");
		JFXButton save = new JFXButton("save");
		save.setStyle("-fx-background-color: lightgreen; ");
		HBox hboxTitle = new HBox(5, new Text("Title: "), title);
		HBox hboxTime = new HBox(5, new Text("Date: "), date);
		HBox hboxDate = new HBox(5, new Text("Time: "), time);
		HBox hboxFee = new HBox(5, new Text("Fee: $"), fee);
		hboxTitle.setAlignment(Pos.CENTER_LEFT);
		hboxTime.setAlignment(Pos.CENTER_LEFT);
		hboxDate.setAlignment(Pos.CENTER_LEFT);
		hboxFee.setAlignment(Pos.CENTER_LEFT);
		Label errorMsg = new Label();
		errorMsg.setId("errMessageRaceDetails");
		errorMsg.setAlignment(Pos.CENTER_LEFT);
		VBox vbox = new VBox(10, hboxTitle, hboxTime, hboxDate);
		if (type.equals("edit")) {
			LocalDateTime ldt = LocalDateTime.parse(raceClicked.getDate().toString().replace(" ", "T"));
			date.setValue(ldt.toLocalDate());
			time.setValue(ldt.toLocalTime());
			title.setText(raceClicked.getName());

		} else {
			vbox.getChildren().add(hboxFee);
		}
		vbox.getChildren().addAll(errorMsg, save);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(0, 0, 0, 50));

		title.textProperty().addListener((Observable, oldValue, newValue) -> {
			newValue = (newValue.replaceAll("\\s{2,}", " "));
			String[] arrOfStr = newValue.split(" ");
			StringBuilder sb;
			for (int i = 0; i < arrOfStr.length; i++) {
				sb = new StringBuilder(arrOfStr[i]);
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
				arrOfStr[i] = sb.toString();
			}
			sb = new StringBuilder(newValue);
			if (sb.charAt(newValue.length() - 1) == ' ')
				title.setText(String.join(" ", arrOfStr) + " ");
			else
				title.setText(String.join(" ", arrOfStr));
		});

		fee.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d+(\\.?\\d{2})?")) {
				newValue = newValue.replaceAll("[^\\d+(\\.?\\d{2})?]", "");
				if ((newValue.split(Pattern.quote("."), -1).length) - 1 > 1)
					fee.setText(oldValue);
				else
					fee.setText(newValue);
			}
		});

		save.setOnAction(e1 -> {
			if (title.getText().trim().length() > 0 && date.getValue() != null && time.getValue() != null) {
				if (LocalDateTime.now().isAfter(LocalDateTime.of(date.getValue(), time.getValue()))) {
					errorMsg.setText("DateTime is Invalid.");
					return;
				}
				raceClicked = new Race();
				if (type.equals("edit")) {
					if (type.equals("edit") && !raceClicked.changeRaceDetails(title.getText().trim(),
							Timestamp.valueOf(LocalDateTime.of(date.getValue(), time.getValue())))) {
						AlertDialogue.error("Error Occurred (DB)");
						return;
					}
					raceClicked = raceClicked.getRace(raceClicked.getId());
				} else {
					raceClicked = raceClicked.addRace(new Race(title.getText(),
							Timestamp.valueOf(LocalDateTime.of(date.getValue(), time.getValue())),
							Double.parseDouble(fee.getText())));
					setRaceClicked();
					racesView.setVisible(false);
					raceDetailsView.setVisible(true);
				}

				if (raceClicked == null) {
					AlertDialogue.error("Error Occurred (DB)");
					return;
				}
				dialog.close();
			} else
				errorMsg.setText("Invalid Input.");
		});

		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(raceDetailsView.getScene().getWindow());
		Scene dialogScene = new Scene(vbox, 300, 250);
		dialog.setScene(dialogScene);
		dialogScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		dialog.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();
	}

	/**
	 * display fees view
	 */
	private void fees() {
		disableAll();
		membershipFee.setText("$" + staff.getMembershipFee());
		BoatStorageFee.setText("$" + staff.getBoatStorageFeePerFoot());
		lbBenefactor.setText(staff.getBenefactor());
		lbBank.setText(staff.getBank());
		lbIBAN.setText(staff.getIBAN());
		feesView.setVisible(true);
	}

	/**
	 * edit the profile of current staff logged
	 */
	private void editProfile() {
		disableAll();
		newName.setText(staff.getName());
		newSurname.setText(staff.getSurname());
		newUsername.setText(staff.getUsername());
		editProfileView.setVisible(true);
	}

	/**
	 * logs staff out and go to homepage
	 */
	private void logout() {
		if (AlertDialogue.confirm("Logout?", "yes", "no")) {
			staff.logout();
			homepage();
		}
	}

	/**
	 * loads main homepage of application
	 */
	private void homepage() {
		try {
			Stage thisStage = (Stage) menuStaff.getScene().getWindow();
			((Stage) thisStage.getOwner()).close(); // close previous window
			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
			Scene scene = new Scene(fxml);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
			thisStage.setScene(scene);
			thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					if (AlertDialogue.confirm("Exit System?", "YES", "NO"))
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
	 * dialog to obtain new fee
	 */
	private void getNewFee(String type) {
		final Stage dialog = new Stage();

		TextField fee = new TextField();
		fee.setPromptText("Enter new " + type + " fee");
		JFXButton change = new JFXButton("change");
		change.setStyle("-fx-background-color: lightgreen; ");
		HBox feeBox = new HBox(5, new Text("Fee: "), fee, change);
		feeBox.setAlignment(Pos.CENTER);

		change.setDisable(true);
		fee.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d+(\\.?\\d{2})?")) {
				newValue = newValue.replaceAll("[^\\d+(\\.?\\d{2})?]", "");
				if ((newValue.split(Pattern.quote("."), -1).length) - 1 > 1)
					fee.setText(oldValue);
				else
					fee.setText(newValue);
			}
			change.setDisable(fee.getText().length() == 0);
		});

		change.setOnAction(e1 -> {
			if (AlertDialogue.confirm("Confirm new " + type + " fee", "confirm", "cancel")) {
				dialog.close();
				switch (type) {
				case "membership":
					if (!staff.setNewMembershipFee(Double.parseDouble(fee.getText())))
						AlertDialogue.error("Error Occured (DB)");
					break;
				case "storage":
					if (!staff.setNewBoatStorageFee(Double.parseDouble(fee.getText())))
						AlertDialogue.error("Error Occured (DB)");
					break;
				}
			}
		});

		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(feesView.getScene().getWindow());
		dialog.setScene(new Scene(feeBox, 300, 200));
		dialog.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();
	}
	
	/**
	 * dialog to get new bank details of club
	 * @param type
	 */
	private void getNewbankDetails(String type) {
		final Stage dialog = new Stage();

		TextField str = new TextField();
		str.setPromptText("Enter new " + type + " fee");
		JFXButton change = new JFXButton("change");
		change.setStyle("-fx-background-color: lightgreen; ");
		HBox feeBox = new HBox(5, new Text(type+": "), str, change);
		feeBox.setAlignment(Pos.CENTER);

		change.setDisable(true);
		if(type.equals("IBAN")) {
			str.textProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue.length() > 34)
					str.setText(oldValue);
				else {
					newValue = newValue.trim();  
					if (!newValue.matches("\\a-zA-Z0-9*")) 
						str.setText(newValue.replaceAll("[^\\a-zA-Z0-9*]", "").toUpperCase());
				}
			});
		}

		change.setOnAction(e1 -> {
			if (AlertDialogue.confirm("Confirm new " + type, "confirm", "cancel")) {
				switch (type) {
				case "bank":
					if (!staff.setBank(str.getText()))
						AlertDialogue.error("Error Occured (DB)");
					break;
				case "IBAN":
					if(!isIbanValid(str.getText())) {
						AlertDialogue.warning("IBAN not Valid.");
						return;
					}
					if (!staff.setIBAN(str.getText()))
						AlertDialogue.error("Error Occured (DB)");
					break;
				}
				dialog.close();
			}
		});

		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(feesView.getScene().getWindow());
		dialog.setScene(new Scene(feeBox, 300, 200));
		dialog.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();
	}

	/**
	 * populate the view of races organised by club
	 * @param type
	 */
	private void setRaces(String type) {
		races.getChildren().clear();
		ArrayList<Race> racesArray = new Race().getRaces(type);
		if (!racesArray.isEmpty())
			for (Race race : racesArray) {
				Label lb = new Label(race.getName() + "   (" + race.getNumberEnrolled() + " enrolled)");
				lb.setId(race.isCancelled() ? "lbRacesCancelled" : race.isFinished() ? "lbRacesPast" : "lbRacesAll");
				lb.setPadding(new Insets(5));
				lb.setOnMouseClicked(e -> {
					racesView.setVisible(false);
					raceClicked = race;
					setRaceClicked();
					raceDetailsView.setVisible(true);
				});
				races.getChildren().add(lb);
			}
		else
			races.getChildren().add(new Label(type == "all" ? "No Race" : type));
	}

	/**
	 * display a specific race details
	 */
	private void setRaceClicked() {
		raceName.setText(raceClicked.getName());
		LocalDateTime ldt = LocalDateTime.parse(raceClicked.getDate().toString().replace(" ", "T"));
		raceDate.setText(ldt.toLocalDate().toString());
		raceTime.setText(ldt.toLocalTime().toString());
		raceNumberEnrolled.setText(raceClicked.getNumberEnrolled() + " enrolled");
		raceStatus
				.setText(raceClicked.isCancelled() ? "Cancelled" : raceClicked.isFinished() ? "Finished" : "Upcoming");
		cancelRaceBtn.setVisible(!raceClicked.isCancelled() && !raceClicked.isFinished());
		changeRaceButton.setVisible(cancelRaceBtn.isVisible());
	}

	/**
	 *  populate the view of members of club
	 */
	private void setMembers() {
		members.getChildren().clear();
		lbMembershipPending.setVisible(false);
		ArrayList<Member> membersArray = new ArrayList<Member>();
		if ((!showMembershipPaidCB.isSelected() && !showMembershipPendingCB.isSelected())
				|| (showMembershipPaidCB.isSelected() && showMembershipPendingCB.isSelected())) {
			membersArray = staff.getAllMembers("all");
			lbMembershipPending.setVisible(true);
		} else if (showMembershipPaidCB.isSelected())
			membersArray = staff.getAllMembers("membershipPaid");
		else if (showMembershipPendingCB.isSelected())
			membersArray = staff.getAllMembers("membershipPending");

		if (!membersArray.isEmpty())
			for (Member member : membersArray) {
				Label lb = new Label(member.getName() + " " + member.getSurname() + "   (" + member.getId() + ")");
				lb.setId(member.isValidMembership() ? "lbMembersAll" : "lbmembershipPending");
				lb.setPadding(new Insets(5));
				lb.setOnMouseClicked(e -> {
					membersView.setVisible(false);
					memberClicked = member;
					setMemberClicked();
					memberDetailsView.setVisible(true);
				});
				members.getChildren().add(lb);
			}
		else
			members.getChildren().add(new Label("No Members"));
	}

	/**
	 *  populate the view of boats of the members of club
	 */
	private void setBoats() {
		boats.getChildren().clear();
		lbStorageBoatPending.setVisible(false);
		ArrayList<Boat> boatsArray = new ArrayList<Boat>();
		if ((!showValidStorageBoatsCB.isSelected() && !showInvalidStorageBoatsCB.isSelected())
				|| (showValidStorageBoatsCB.isSelected() && showInvalidStorageBoatsCB.isSelected())) {
			boatsArray = new Boat().getAllBoats("all");
			lbStorageBoatPending.setVisible(true);
		} else if (showValidStorageBoatsCB.isSelected())
			boatsArray = new Boat().getAllBoats("storagePaid");
		else if (showInvalidStorageBoatsCB.isSelected())
			boatsArray = new Boat().getAllBoats("storagePending");

		if (!boatsArray.isEmpty())
			for (Boat boat : boatsArray) {
				Label lb = new Label(boat.getName() + " (" + boat.getLength() + "ft)");
				lb.setId(boat.isValidStorage() ? "lbStorageValid" : "lbStorageValidPending");
				lb.setPadding(new Insets(5));
				lb.setOnMouseClicked(e -> {
					boatsView.setVisible(false);
					boatClicked = boat;
					setBoatClicked();
					boatDetailsView.setVisible(true);
				});
				boats.getChildren().add(lb);
			}
		else
			boats.getChildren().add(new Label("No Boats"));
	}

	/**
	 * display a specific member details
	 */
	private void setMemberClicked() {
		membershipStatus.setText(memberClicked.isValidMembership() ? "Valid" : "Pending");
		String profilePath = System.getProperty("user.dir") + "\\src\\utility\\profile\\";
		if (new File(profilePath + memberClicked.getId() + ".png").exists())
			memberProfilePicture.setImage(new Image(profilePath + memberClicked.getId() + ".png"));
		else if (new File(profilePath + "default.png").exists())
			memberProfilePicture.setImage(new Image(profilePath + "default.png"));
		else
			memberProfilePicture.setImage(null);
		memberID.setText(memberClicked.getId());
		memberName.setText(memberClicked.getName());
		memberSurname.setText(memberClicked.getSurname());
		memberAddress.setText(memberClicked.getAddress());
	}

	/**
	 * display a specific boat details
	 */
	private void setBoatClicked() {
		boatStorageStatus.setText(boatClicked.isValidStorage() ? "Valid" : "Expired");
		boatName.setText(boatClicked.getName());
		boatLength.setText(boatClicked.getLength() + "ft");
		memberClicked = new Member().getMember(boatClicked.getOwner());
		boatOwner.setText(memberClicked.getName() + " " + memberClicked.getSurname());
	}

	/**
	 * disable visible view
	 */
	private void disableAll() {
		feesView.setVisible(false);
		racesView.setVisible(false);
		raceDetailsView.setVisible(false);
		membersView.setVisible(false);
		memberDetailsView.setVisible(false);
		boatDetailsView.setVisible(false);
		boatsView.setVisible(false);
		notificationBuilder.setVisible(false);
		editProfileView.setVisible(false);
		staffsView.setVisible(false);
		paymentsView.setVisible(false);
		searchSuggestions.setVisible(false);
		paymentClickedView.setVisible(false);
	}

	/**
	 * adds an event listener to the text fields for avoiding the insertion of incorrect values
	 */
	private void addListener() {
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable;
			TextField textField = (TextField) textProperty.getBean();
			if (newValue.trim().length() > 0) {
				newValue = newValue.trim();
				if (textField == username) {
					if (!newValue.matches("\\sa-zA-Z0-9*"))
						newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
					textField.setText(newValue);
				} else if (textField == searchText) {
					newValue = (newValue.replaceAll("\\s{2,}", " "));
					String[] arrOfStr = newValue.split(" ");
					StringBuilder sb;
					for (int i = 0; i < arrOfStr.length; i++) {
						sb = new StringBuilder(arrOfStr[i]);
						sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
						arrOfStr[i] = sb.toString();
					}
					sb = new StringBuilder(newValue);
					if (sb.charAt(newValue.length() - 1) == ' ')
						textField.setText(String.join(" ", arrOfStr) + " ");
					else
						textField.setText(String.join(" ", arrOfStr));
				} else
					textField.setText(newValue);
			} else
				textField.setText(newValue.stripLeading());
			errMsg.setText("");
			signinBtn
					.setDisable((username.getText().trim().length() == 0) || (password.getText().trim().length() == 0));
		});

		username.textProperty().addListener(listener);
		password.textProperty().addListener(listener);
		searchText.textProperty().addListener(listener);
		msgBody.textProperty().addListener((observable, oldValue, newValue) -> { // textarea
			if (newValue.length() > 300)
				msgBody.setText(oldValue);
		});
		msgHead.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 100)
				msgHead.setText(oldValue);
		});

		tgRaces.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
				if (tgRaces.getSelectedToggle() == rbAllRaces) {
					addRaceBtn.setVisible(true);
					setRaces("all");
				} else if (tgRaces.getSelectedToggle() == rbCancelledRaces) {
					addRaceBtn.setVisible(false);
					setRaces("cancelled");
				} else if (tgRaces.getSelectedToggle() == rbPastRaces) {
					addRaceBtn.setVisible(false);
					setRaces("past");
				} else if (tgRaces.getSelectedToggle() == rbUpcomingRaces) {
					addRaceBtn.setVisible(true);
					setRaces("upcoming");
				}
			}
		});

		tgPayments.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
				if (tgPayments.getSelectedToggle() == rbAllPayments) {
					setPayments("all");
				} else if (tgPayments.getSelectedToggle() == rbMembershipPayments) {
					setPayments("membership");
				} else if (tgPayments.getSelectedToggle() == rbStoragePayments) {
					setPayments("storage");
				} else if (tgPayments.getSelectedToggle() == rbRacePayments) {
					setPayments("race");
				}
			}
		});
	}

	/**
	 * display staffs in club
	 */
	private void showStaffs() {
		if (!staff.getIsAdmin()) {
			AlertDialogue.warning("No admin privilege to access");
			return;
		}
		disableAll();
		setStaffs();
		staffsView.setVisible(true);
		msgStaffs.setText("");
	}

	/**
	 * populate view of staffs in club
	 */
	private void setStaffs() {
		staffsTableView.getItems().clear();
		ArrayList<Staff> staffsArray = staff.getStaffs();
		if (!staffsArray.isEmpty())
			for (Staff staff : staffsArray)
				staffsTableView.getItems().add(staff);
		else
			staffsTableView.setPlaceholder(new Label("No Staff."));
		msgStaffs.setText("");
		staff = staffsArray.stream().filter(s -> staff.getId() == s.getId()).findAny().orElse(null);
		menuStaff.setText(staff.getUsername());
	}

	/**
	 * allows admin to edit a specific staff
	 * @param staffToEdit
	 */
	public void editStaff(Staff staffToEdit) {
		Stage alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("EDIT STAFF");
		alertWindow.setMinWidth(350);

		GridPane grid = new GridPane();
		grid.setId("edit");
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label output = new Label("");
		output.setId("editOutput");
		GridPane.setConstraints(output, 0, 11);

		Label nameLabel = new Label("Name:");
		nameLabel.setId("labelEdit");

		Label surnameLabel = new Label("Surname:");
		surnameLabel.setId("labelEdit");

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelEdit");

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelEdit");

		Label adminshipLabel = new Label("Adminship:");
		adminshipLabel.setId("labelEdit");

		TextField name = new TextField(staffToEdit.getName());
		TextField surname = new TextField(staffToEdit.getSurname());
		TextField username = new TextField(staffToEdit.getUsername());
		TextField password = new TextField(staffToEdit.getPassword());

		ChoiceBox<String> adminship = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));
		adminship.setValue(staffToEdit.getIsAdmin()? "true" : "false");

		JFXButton setNameBtn = new JFXButton("Change");
		setNameBtn.setId("btnEdit");

		JFXButton setSurnameBtn = new JFXButton("Change");
		setSurnameBtn.setId("btnEdit");

		JFXButton setUsernameBtn = new JFXButton("Change");
		setUsernameBtn.setId("btnEdit");

		JFXButton setPasswordBtn = new JFXButton("Change");
		setPasswordBtn.setId("btnEdit");

		JFXButton setAdminshipBtn = new JFXButton("Change");
		setAdminshipBtn.setId("btnEdit");

		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable;
			TextField textField = (TextField) textProperty.getBean();
			if (newValue.trim().length() > 0) {
				if (textField == name || textField == surname) {
					newValue = newValue.stripLeading();
					if (!newValue.matches("\\sa-zA-Z*")) {
						newValue = newValue.replaceAll("[^\\sa-zA-Z]", "");
						String[] arrOfStr = newValue.split(" ");
						StringBuilder sb;
						for (int i = 0; i < arrOfStr.length; i++) {
							sb = new StringBuilder(arrOfStr[i]);
							sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
							arrOfStr[i] = sb.toString();
						}
						sb = new StringBuilder(newValue);
						if (sb.charAt(newValue.length() - 1) == ' ')
							textField.setText(String.join(" ", arrOfStr) + " ");
						else
							textField.setText(String.join(" ", arrOfStr));
					}
				} else if (textField == username) {
					newValue = newValue.trim();
					if (!newValue.matches("\\sa-zA-Z0-9*"))
						newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
					if ((newValue.length() > 0) && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
						newValue = newValue.replaceAll("^[\\d]", "");
					textField.setText(newValue);
				} else if (textField == username) {
					newValue = newValue.trim();
					if (!newValue.matches("\\sa-zA-Z0-9*"))
						newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
					if ((newValue.length() > 0) && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
						newValue = newValue.replaceAll("^[\\d]", "");
					textField.setText(newValue);
				} else {
					textField.setText(newValue.replaceAll(" ", ""));
				}
			} else
				textField.setText(newValue.stripLeading());
		});
		name.textProperty().addListener(listener);
		surname.textProperty().addListener(listener);
		username.textProperty().addListener(listener);
		password.textProperty().addListener(listener);

		setNameBtn.setOnAction(e -> {

			if (!name.getText().trim().equals("")) {
				if (staffToEdit.getName().equals(name.getText().trim())) {
					GridPane.setConstraints(output, 2, 2);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Same as old one.");
				} else if (staff.setStaffName(staffToEdit, name.getText().trim())) {
					nameLabel.setText("Name:");
					nameLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 2, 2);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");
					staffToEdit.setName(name.getText().trim());
				} else
					AlertDialogue.error("Error Occured (DB)");
			} else {

				nameLabel.setText("This field cannot be empty.");
				nameLabel.setTextFill(Color.RED);

			}

		});

		setSurnameBtn.setOnAction(e -> {

			if (!surname.getText().trim().equals("")) {
				if (staffToEdit.getSurname().equals(surname.getText().trim())) {
					GridPane.setConstraints(output, 6, 2);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Same as old one.");
				} else if (staff.setStaffSurname(staffToEdit, surname.getText().trim())) {
					surnameLabel.setText("Surname:");
					surnameLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 6, 2);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");
					staffToEdit.setSurname(surname.getText().trim());
				} else
					AlertDialogue.error("Error Occured (DB)");
			} else {

				surnameLabel.setText("This field cannot be empty.");
				surnameLabel.setTextFill(Color.RED);

			}

		});

		setAdminshipBtn.setOnAction(e -> {

			if (staff.getUsername().equals(staffToEdit.getUsername()) && adminship.getValue().trim().equals("false")) {
				if (AlertDialogue.confirm("You are about to Relinquish your right to\nAdmin Privileges.\nPROCEED?",
						"PROCEED", "CANCEL")) {
					if (staff.countAdmins() == 1) {
						AlertDialogue.warning(
								"Request can not be granted because you are the only admin in system. \nMake another staff admin before revoking your previleges.");
						return;
					}
					if (staff.setStaffAdminship(staffToEdit, Boolean.parseBoolean(adminship.getValue().trim()))) {
						alertWindow.close();
						menuStaff.getItems().remove(m4);
						staffsView.setVisible(false);
						AlertDialogue.warning("Admin Privileges Revoked.");
					} else
						AlertDialogue.error("Error Occured (DB)");
				}
			} else {
				if (staffToEdit.getIsAdmin() == Boolean.parseBoolean(adminship.getValue().trim())) {
					GridPane.setConstraints(output, 4, 11);
					GridPane.setHalignment(output, HPos.CENTER);
					output.setText("Same as old one.");
				} else if (staff.setStaffAdminship(staffToEdit, Boolean.parseBoolean(adminship.getValue().trim()))) {
					GridPane.setConstraints(output, 4, 11);
					GridPane.setHalignment(output, HPos.CENTER);
					output.setText("Done!");
					staffToEdit.setIsAdmin(Boolean.parseBoolean(adminship.getValue().trim()));
				} else
					AlertDialogue.error("Error Occured (DB)");
			}

		});

		setPasswordBtn.setOnAction(e -> {

			if (!password.getText().trim().equals("")) {
				if (staffToEdit.getPassword().equals(password.getText().trim())) {
					GridPane.setConstraints(output, 6, 5);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Same as old one.");
				} else if (staff.setStaffPassword(staffToEdit, password.getText().trim())) {
					passwordLabel.setText("Password:");
					passwordLabel.setTextFill(Color.WHITE);
					GridPane.setConstraints(output, 6, 5);
					GridPane.setHalignment(output, HPos.LEFT);
					output.setText("Done!");
					staffToEdit.setPassword(password.getText().trim());
				} else
					AlertDialogue.error("Error Occured (DB)");
			} else {

				passwordLabel.setText("This field cannot be empty.");
				passwordLabel.setTextFill(Color.RED);

			}

		});

		setUsernameBtn.setOnAction(e -> {

			if (!username.getText().trim().equals(""))
				if (!staff.exists(username.getText().trim())) {
					if (staff.setStaffUsername(staffToEdit, username.getText().trim())) {
						usernameLabel.setText("Name:");
						usernameLabel.setTextFill(Color.WHITE);
						GridPane.setConstraints(output, 2, 5);
						GridPane.setHalignment(output, HPos.LEFT);
						output.setText("Done!");
						staffToEdit.setUsername(username.getText().trim());
					} else
						AlertDialogue.error("Error Occured (DB)");
				} else {

					usernameLabel.setText("Username already taken.");
					usernameLabel.setTextFill(Color.RED);

				}
			else {

				usernameLabel.setText("This field cannot be empty.");
				usernameLabel.setTextFill(Color.RED);

			}

		});

		GridPane.setConstraints(nameLabel, 1, 0);
		GridPane.setConstraints(surnameLabel, 5, 0);
		GridPane.setConstraints(usernameLabel, 1, 3);
		GridPane.setConstraints(adminshipLabel, 3, 9);
		GridPane.setConstraints(passwordLabel, 5, 3);
		GridPane.setConstraints(setNameBtn, 1, 2);
		GridPane.setConstraints(setSurnameBtn, 5, 2);
		GridPane.setConstraints(setUsernameBtn, 1, 5);
		GridPane.setConstraints(setAdminshipBtn, 4, 10);
		GridPane.setConstraints(setPasswordBtn, 5, 5);

		grid.add(name, 1, 1);
		grid.add(surname, 5, 1);
		grid.add(adminship, 5, 9);
		grid.add(username, 1, 4);
		grid.add(password, 5, 4);

		GridPane.setColumnSpan(name, 4);
		GridPane.setColumnSpan(nameLabel, 4);
		GridPane.setColumnSpan(surnameLabel, 4);
		GridPane.setColumnSpan(surname, 4);
		GridPane.setColumnSpan(username, 4);
		GridPane.setColumnSpan(adminship, 2);
		GridPane.setColumnSpan(adminshipLabel, 2);
		GridPane.setColumnSpan(setAdminshipBtn, 2);
		GridPane.setColumnSpan(password, 4);
		GridPane.setColumnSpan(output, 2);
		GridPane.setFillWidth(adminship, true);
		GridPane.setFillWidth(surname, true);
		GridPane.setHalignment(adminshipLabel, HPos.CENTER);
		GridPane.setHalignment(adminship, HPos.CENTER);
		GridPane.setHalignment(setAdminshipBtn, HPos.CENTER);

		grid.getChildren().addAll(output, setNameBtn, setSurnameBtn, setUsernameBtn, setAdminshipBtn, setPasswordBtn,
				nameLabel, surnameLabel, usernameLabel, adminshipLabel, passwordLabel);

		Scene scene = new Scene(grid, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.initOwner((Stage) staffsView.getScene().getWindow());
		alertWindow.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		alertWindow.showAndWait();
	}

	/**
	 * allow admin to add staff to club
	 */
	private void addStaff() {
		Stage alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.initStyle(StageStyle.UTILITY);
		alertWindow.setTitle("ADD NEW STAFF");
		alertWindow.setMinWidth(210);

		GridPane grid = new GridPane();
		grid.setId("add");
		grid.setPadding(new Insets(15, 15, 15, 15));
		grid.setVgap(8);
		grid.setHgap(8);
		grid.setAlignment(Pos.CENTER);

		Label usernameLabel = new Label("Username:");
		usernameLabel.setId("labelAdd");
		GridPane.setConstraints(usernameLabel, 1, 4);

		TextField username = new TextField();
		GridPane.setConstraints(username, 1, 5);

		Label nameLabel = new Label("Name:");
		nameLabel.setId("labelAdd");
		GridPane.setConstraints(nameLabel, 1, 0);

		TextField name = new TextField();
		GridPane.setConstraints(name, 1, 1);

		Label surnameLabel = new Label("Surname:");
		surnameLabel.setId("labelAdd");
		GridPane.setConstraints(surnameLabel, 1, 2);

		TextField surname = new TextField();
		GridPane.setConstraints(surname, 1, 3);

		Label passwordLabel = new Label("Password:");
		passwordLabel.setId("labelAdd");
		GridPane.setConstraints(passwordLabel, 1, 6);

		TextField password = new TextField();
		GridPane.setConstraints(password, 1, 7);

		ChoiceBox<String> adminship = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));
		adminship.setValue("false");
		GridPane.setConstraints(adminship, 1, 9);

		Label adminshipLabel = new Label("Adminship:");
		adminshipLabel.setId("labelAdd");
		GridPane.setConstraints(adminshipLabel, 1, 8);

		Label error = new Label("");
		error.setId("errorAdd");
		GridPane.setConstraints(error, 1, 13);
		error.setTextFill(Color.RED);

		JFXButton addBtn = new JFXButton("Add Staff");
		addBtn.setId("btnAdd");
		GridPane.setConstraints(addBtn, 1, 12);

		addBtn.setOnAction(e -> {

			if (!staff.exists(username.getText().trim()) && !username.getText().trim().equals("")
					&& !name.getText().trim().equals("") && !surname.getText().trim().equals("")
					&& !password.getText().trim().equals("") && !adminship.getValue().trim().equals("")
					&& (adminship.getValue().trim().equalsIgnoreCase("true")
							|| adminship.getValue().trim().equalsIgnoreCase("false"))) {
				if (staff.addStaff(new Staff(name.getText().trim(), surname.getText().trim(), username.getText().trim(),
						password.getText().trim(), Boolean.parseBoolean(adminship.getValue().trim())))) {
					AlertDialogue.info("Account Added Successfully.");
					alertWindow.close();
				} else
					AlertDialogue.error("Error Occurred. (DB)");
			} else if (staff.exists(username.getText().trim()))
				error.setText("Username already taken.");

			else
				error.setText("Please fill out all\nrequired fields.");

		});

		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable;
			TextField textField = (TextField) textProperty.getBean();
			if (newValue.trim().length() > 0) {
				if (textField == name || textField == surname) {
					newValue = newValue.stripLeading();
					if (!newValue.matches("\\sa-zA-Z*")) {
						newValue = newValue.replaceAll("[^\\sa-zA-Z]", "");
						String[] arrOfStr = newValue.split(" ");
						StringBuilder sb;
						for (int i = 0; i < arrOfStr.length; i++) {
							sb = new StringBuilder(arrOfStr[i]);
							sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
							arrOfStr[i] = sb.toString();
						}
						sb = new StringBuilder(newValue);
						if (sb.charAt(newValue.length() - 1) == ' ')
							textField.setText(String.join(" ", arrOfStr) + " ");
						else
							textField.setText(String.join(" ", arrOfStr));
					}
				} else if (textField == username) {
					newValue = newValue.trim();
					if (!newValue.matches("\\sa-zA-Z0-9*"))
						newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
					if ((newValue.length() > 0) && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
						newValue = newValue.replaceAll("^[\\d]", "");
					textField.setText(newValue);
				} else if (textField == username) {
					newValue = newValue.trim();
					if (!newValue.matches("\\sa-zA-Z0-9*"))
						newValue = newValue.replaceAll("[^\\sa-zA-Z0-9]", "");
					if ((newValue.length() > 0) && (!Character.toString(newValue.charAt(0)).matches("\\sa-zA-Z")))
						newValue = newValue.replaceAll("^[\\d]", "");
					textField.setText(newValue);
				} else {
					textField.setText(newValue.replaceAll(" ", ""));
				}
			} else
				textField.setText(newValue.stripLeading());
		});
		name.textProperty().addListener(listener);
		surname.textProperty().addListener(listener);
		username.textProperty().addListener(listener);
		password.textProperty().addListener(listener);

		grid.getChildren().addAll(nameLabel, name, surnameLabel, surname, usernameLabel, username, passwordLabel,
				password, adminshipLabel, adminship, addBtn, error);

		Scene scene = new Scene(grid, 210, 350);
		scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		alertWindow.setResizable(false);
		alertWindow.setScene(scene);
		alertWindow.initOwner((Stage) staffsView.getScene().getWindow());
		alertWindow.getIcons().add(
				new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		alertWindow.showAndWait();
	}

	/**
	 * display payments made to club
	 */
	private void payments() {
		disableAll();
		setPayments("all");
		rbAllPayments.setSelected(true);
		paymentsView.setVisible(true);
	}

	/**
	 * populate payment table view
	 * @param type
	 */
	private void setPayments(String type) {
		paymentsTable.getItems().clear();
		ArrayList<Payment> paymentsArray = new Payment().getPayments(type);
		if (!paymentsArray.isEmpty())
			for (Payment payment : paymentsArray)
				paymentsTable.getItems().add(payment);
		else
			paymentsTable.setPlaceholder(new Label("No Payment Made."));
	}

	/**
	 * validates a given date
	 * @param curDate
	 */
	private boolean isValidDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// don't allow dates out of range as 2015-18-33
		format.setLenient(false);

		Date parsedDate = null;
		try {
			parsedDate = (Date) format.parse(date);
		} catch (ParseException e) {
		}

		return parsedDate == null ? false : true;
	}
	
	/**
	 * validate IBAN
	 * @param iban
	 * @return true if IBAN is valid
	 */
	private boolean isIbanValid(String iban) {

	    int IBAN_MIN_SIZE = 15;
	    int IBAN_MAX_SIZE = 34;
	    long IBAN_MAX = 999999999;
	    long IBAN_MODULUS = 97;

	    String trimmed = iban.trim();

	    if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
	        return false;
	    }

	    String reformat = trimmed.substring(4) + trimmed.substring(0, 4);
	    long total = 0;

	    for (int i = 0; i < reformat.length(); i++) {

	        int charValue = Character.getNumericValue(reformat.charAt(i));

	        if (charValue < 0 || charValue > 35) {
	            return false;
	        }

	        total = (charValue > 9 ? total * 100 : total * 10) + charValue;

	        if (total > IBAN_MAX) {
	            total = (total % IBAN_MODULUS);
	        }
	    }

	    return (total % IBAN_MODULUS) == 1;
	}
}
