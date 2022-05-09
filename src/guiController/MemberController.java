package guiController;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import system.Boat;
import system.Member;
import system.Notification;
import system.Payment;
import system.Race;
import system.Staff;
import system.enrolment;

/**
 * Member GUI Controller class
 *
 */
public class MemberController {
	
	@FXML
	private AnchorPane root;
	@FXML
    private AnchorPane homeView;
	@FXML
	private AnchorPane servicesView;
	@FXML
	private Text eventTitleUpcoming;
	@FXML
	private Text eventTextUpcoming;
	@FXML
	private Text eventTitleOther;
	@FXML
	private Text eventTextOther;
	@FXML
	private SplitPane memberUpcomingRaces;
	@FXML
	private SplitPane otherRaces;
	@FXML
    private JFXButton user;
	@FXML
    private AnchorPane menuView;
	@FXML
    private AnchorPane profile;
	@FXML
    private Label id;
	@FXML
    private Label name;
	@FXML
    private Label surname;
	@FXML
    private Label address;
	@FXML
    private AnchorPane editProfile;
	@FXML
	private TextField changeName;
	@FXML
	private TextField changeSurname;
	@FXML
	private TextField changeAddress;
	@FXML
	private TextField changePassword1;
	@FXML
	private TextField changePassword2;
	@FXML
	private TextField oldPassword;
	@FXML
    private JFXButton membershipStatus;
	@FXML
    private Label image;
	@FXML
	private ImageView profilePicture;
	@FXML
	private ImageView profilePictureIcon;
	@FXML
    private JFXButton setName;
	@FXML
    private JFXButton setSurname;
	@FXML
    private JFXButton setAddress;
	@FXML
    private JFXButton setPassword;
	@FXML
    private AnchorPane boats;
	@FXML
	private TableView<Boat> boatsTableView;
	@FXML
	private TableColumn<Boat, String> nameCol;
	@FXML
	private TableColumn<Boat, Double> lengthCol;
	@FXML
    private AnchorPane addBoatView;
	@FXML
    private JFXButton addBoatBtn;
	@FXML
	private TextField newBoatName;
	@FXML
	private TextField newBoatLength;
	@FXML
	private Text newBoatStorageFee;
	@FXML
    private JFXButton addNewBoatBtn;
	@FXML
    private AnchorPane races;
	@FXML
	private TableView<Race> allRacesTableView;
	@FXML
	private TableColumn<Race, String> raceColAll;
	@FXML
	private TableColumn<Race, Date> dateColAll;
	@FXML
	private TableView<Race> enrolledRacesTableView;
	@FXML
	private TableColumn<Race, String> raceColEnrolled;
	@FXML
	private TableColumn<Race, Date> dateColEnrolled;
	@FXML
	private TableView<Race> pastRacesTableView;
	@FXML
	private TableColumn<Race, String> raceColPast;
	@FXML
	private TableColumn<Race, Date> dateColPast;
	@FXML
    private JFXRadioButton rbAllRaces;
    @FXML
    private JFXRadioButton rbEnrolledRaces;
    @FXML
    private JFXRadioButton rbPastRaces;
    @FXML
    private AnchorPane notificationsView;
    @FXML
    private VBox notifications;
    @FXML
    private VBox filterNotifications;
    @FXML
    private JFXButton filterNotificationsBtn;
    @FXML
    private CheckBox showNotificationsUnreadCB;
    @FXML
    private CheckBox showNotificationsReadCB;
    @FXML
    private AnchorPane notificationDetailsView;
    @FXML
    private Label notificationHeading;
    @FXML
    private Label notificationBody;
    @FXML
	private ImageView gotoNotificationFromProfile;
    @FXML
	private ImageView gotoNotificationFromBoats;
    @FXML
    private Label notificationLabel;
    
	private ArrayList<Race> memberUpcomingRacesList;
	private ArrayList<Race> otherRacesList;
	private int index1, index2;
	private Timeline timeline;
	private String profilePath;
	private Map<String, String> imagesPath;
	private double newBoatStorageFeeCalculated;
	private ToggleGroup tgRaces;
	private int temp;
	
	/**
	 * member logged
	 */
	public static Member member = new Member();
	
	/**
	 * initialises member home page window after log in
	 */
	@FXML
	void initialize() {
		if(!member.sessionOn) {
			AlertDialogue.error("Login error.");
			try {
			    Parent home = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
			    root.getChildren().setAll(home);
			} catch (IOException e) {
				e.printStackTrace();
				AlertDialogue.error("Unable to load HomePage");
			}
			return;
		}
		
		disableAll();
		timeline = new Timeline(new KeyFrame(Duration.millis(4000), e -> next()));
		timeline.setCycleCount(Animation.INDEFINITE); // loop forever
		loadHomeView();
		user.setText(member.getName());
		profilePath = System.getProperty("user.dir")+"\\src\\utility\\profile\\";
		if(new File(profilePath+member.getId()+".png").exists()){
			profilePicture.setImage(new Image(profilePath+member.getId()+".png"));
			profilePictureIcon.setImage(new Image(profilePath+member.getId()+".png"));
		}
		
		imagesPath = new HashMap<String, String>();
		imagesPath.put("remove", "/utility/images/remove.png");
		imagesPath.put("info", "/utility/images/info.png");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<Boat, String>("name"));
		lengthCol.setCellValueFactory(new PropertyValueFactory<Boat, Double>("length"));
		addColsToBoatTable(boatsTableView);
		
		tgRaces = new ToggleGroup();
		rbAllRaces.setToggleGroup(tgRaces);
		rbEnrolledRaces.setToggleGroup(tgRaces);
		rbPastRaces.setToggleGroup(tgRaces);
		
		allRacesTableView.setVisible(false);
		raceColAll.setCellValueFactory(new PropertyValueFactory<Race, String>("name"));
		dateColAll.setCellValueFactory(new PropertyValueFactory<Race, Date>("date"));
		addButtonToRaceTableAll(allRacesTableView);
		enrolledRacesTableView.setVisible(false);
		raceColEnrolled.setCellValueFactory(new PropertyValueFactory<Race, String>("name"));
		dateColEnrolled.setCellValueFactory(new PropertyValueFactory<Race, Date>("date"));
		addButtonToRaceTableEnrolled(enrolledRacesTableView);
		pastRacesTableView.setVisible(false);
		raceColPast.setCellValueFactory(new PropertyValueFactory<Race, String>("name"));
		dateColPast.setCellValueFactory(new PropertyValueFactory<Race, Date>("date"));
		
		
		addListener();
	}

	/**
	 * removes any visible view and displays homepage view
	 * @param event the ActionEvent
	 */
	@FXML
    void gotoHome(ActionEvent event) {
		disableAll();
		loadHomeView();
    }
	
	/**
	 * opens view containing the software description
	 * @param event the ActionEvent
	 */
	@FXML
    void services(ActionEvent event) {
		disableAll();
		servicesView.setVisible(true);
    }
	
	/**
	 * opens unipr link from browser
	 * @param event the ActionEvent
	 */
	@FXML
	void openUnipr(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://www.unipr.it/"));
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Error Redicting to Page");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			AlertDialogue.error("Error Redicting to Page");
		}
	}
	
	/**
	 * goes to the home page
	 * @param event the MouseEvent
	 */
	@FXML
    void gotoHomePage(MouseEvent event) {
		gotoHome(null);
    }
	
	/**
	 * toggle member's menu options
	 * @param event the ActionEvent
	 */
	@FXML
    void showMenu(ActionEvent event) {
		menuView.setVisible(!menuView.isVisible());
    }
	
	/**
	 * opens view displaying the account information
	 * @param event the ActionEvent
	 */
	@FXML
    void gotoProfile(ActionEvent event) {
		disableAll();
		gotoNotificationFromProfile.setVisible(false);
		setProfile();
		profile.setVisible(true);
    }
	
	/**
	 * opens view displaying the account information from the notification view
	 * @param event the ActionEvent
	 */
	@FXML
    void gotoProfileFromNotification(ActionEvent event) {
		gotoProfile(null);
		gotoNotificationFromProfile.setVisible(true);
    }
	
	/**
	 * allows the member to pay the membership fee calling the payment window
	 * @param event the ActionEvent
	 */
	@FXML
	void payMembershipFee(ActionEvent event) {
		if(member.isValidMembership())
			return;
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
				setProfile();
			}

		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Unable to Access Payment Service Area.");
		}
	}
	
	/**
	 * goes back to the profile view
	 * @param event the MouseEvent
	 */
	@FXML
    void gobackToProfile(MouseEvent event) {
	   editProfile.setVisible(false);
	   profile.setVisible(true);
    }
   
	/**
	 *  goes to the notification view from the profile view
	 * @param event the MouseEvent
	 */
	@FXML
   void gotoNotificationFromProfileClicked(MouseEvent event) {
	   profile.setVisible(false);
	   notificationDetailsView.setVisible(true);
   }
   
	/**
	 *  goes to the notification view from the boats view
	 * @param event the MouseEvent
	 */
	@FXML
   void gotoNotificationFromBoatsClicked(MouseEvent event) {
	   boats.setVisible(false);
	   notificationDetailsView.setVisible(true);
   }
	
	/**
	 *  opens the view for editing the profile details
	 * @param event the ActionEvent
	 */
	@FXML
	void editProfileDetails(ActionEvent event) {
		disableAll();
		changeName.setText(member.getName());
		changeSurname.setText(member.getSurname());
		changeAddress.setText(member.getAddress());
		image.setText("choose picture from file");
		editProfile.setVisible(true);
	}
	
	/**
	 *  edits the member's name
	 * @param event the ActionEvent
	 */
	@FXML
	void setName(ActionEvent event){
		String oldName = member.getName();
		String newName = changeName.getText();
		if(AlertDialogue.confirm("comfirm change of Name from '"+oldName+ "' to '"+newName+"'", "YES", "Cancel")) {
			if(member.setNewName(newName)) {
				AlertDialogue.info("Name successfully changed to "+newName);
				user.setText(member.getName());
			}
			else
				AlertDialogue.error("Error occured. (DB)");
		}
	}
	
	/**
	 *  edits the member's surname
	 * @param event the ActionEvent
	 */
	@FXML
	void setSurname(ActionEvent event){
		String oldSurname = member.getSurname();
		String newSurname = changeSurname.getText();
		if(AlertDialogue.confirm("comfirm change of Surname from '"+oldSurname+ "' to '"+newSurname+"'", "YES", "Cancel")) {
			if(member.setNewSurname(newSurname)) {
				AlertDialogue.info("Surname successfully changed to "+newSurname);
			}
			else
				AlertDialogue.error("Error occured. (DB)");
		}
	}
	
	/**
	 *  edits the member's address
	 * @param event the ActionEvent
	 */
	@FXML
	void setAddress(ActionEvent event){
		String oldAddress = member.getAddress();
		String newAddress = changeAddress.getText();
		if(AlertDialogue.confirm("comfirm change of Address from '"+oldAddress+ "' to '"+newAddress+"'", "YES", "Cancel")) {
			if(member.setNewAddress(newAddress)) {
				AlertDialogue.info("Address successfully changed to "+newAddress);
			}
			else
				AlertDialogue.error("Error occured. (DB)");
		}
	}
	
	/**
	 *  edits the member's password
	 * @param event the ActionEvent
	 */
	@FXML
	void setPassword(ActionEvent event){
		String newPassword1 = changePassword1.getText();
		String newPassword2 = changePassword2.getText();
		
		if(!newPassword1.equals(newPassword2)) {
			AlertDialogue.error("new password does not match!");
			return;
		}
		if(!member.getPassword().equals(oldPassword.getText())) {
			AlertDialogue.error("old password incorrect");
			return;
		}
		if(member.getPassword().equals(newPassword2)) {
			AlertDialogue.warning("new password can't be the same as the old one");
			return;
		}
		
		if(AlertDialogue.confirm("comfirm change of Password", "YES", "Cancel")) {
			if(member.setNewPassword(newPassword2)) {
				AlertDialogue.info("Password successfully changed");
			}
			else
				AlertDialogue.error("Error occured. (DB)");
		}
	}
	
	/**
	 *  allows the member to choose a new profile picture from file
	 * @param event the MouseEvent
	 */
	@FXML
	void chooseImage(MouseEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Image Files","*.jpeg","*.jpg","*.png"));
		
		File f = fc.showOpenDialog(null);
		
		if(f != null)
			image.setText(f.getAbsolutePath());
	}
	
	/**
	 *  sets member's profile image
	 * @param event the ActionEvent
	 */
	@FXML
	void setImage(ActionEvent event){
		if(!new File(profilePath).exists()) {
			AlertDialogue.error("Error Occured!");
			return;
		}
		String profileImagePath = profilePath +member.getId()+".png";
		File outputFile = new File(profileImagePath);
		File input = new File(image.getText());
		BufferedImage imageBuff = null;
		try {
			imageBuff = ImageIO.read(input);
		} catch (IOException e1) {
			AlertDialogue.error("Error! choose an image.");
			return;
		}
		
		try {
			if(!AlertDialogue.confirm("confirm change of profile picture?", "YES", "Cancel"))
				return;
			ImageIO.write(imageBuff,"png",outputFile);
			AlertDialogue.info("Profile picture uploaded successfully!");
			if(new File(profileImagePath).exists()) {
				profilePicture.setImage(new Image(profileImagePath));
				profilePictureIcon.setImage(new Image(profileImagePath));
				gotoProfile(null);
			}
			else
				AlertDialogue.error("Error Occured with uploaded image");
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Error Occured in uploading image");
		}
	}
	
	/**
	 *  goes to the boats view
	 * @param event the ActionEvent
	 */
	@FXML
	void gotoBoats(ActionEvent event) {
		disableAll();
		gotoNotificationFromBoats.setVisible(false);
		addBoatView.setVisible(false);
		addBoatBtn.setVisible(true);
		addNewBoatBtn.setDisable(newBoatName.getText().length() == 0 || newBoatLength.getText().length() == 0);
		setBoats();
		boats.setVisible(true);
	}
	
	/**
	 *  goes to the boats view from the notification view
	 * @param event the ActionEvent
	 */
	@FXML
    void gotoBoatsFromNotification(ActionEvent event) {
		gotoBoats(null);
		gotoNotificationFromBoats.setVisible(true);
    }
	
	/**
	 *  logs out the member's
	 * @param event the ActionEvent
	 */
    @FXML
	void logout(ActionEvent event) {
		if(!AlertDialogue.confirm("sign out?", "YES", "NO"))
			return;
		
		member.logout();
		try {
			Node node = (Node) event.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    
		    Parent root = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
			Scene scene = new Scene(root);
			
			thisStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Unable to load HomePage");
		}
	}
	
	/**
	 *  deletes the member's account
	 * @param event the ActionEvent
	 */
    @FXML
	void deleteAccount(ActionEvent event) {
		confirmDeleteAccount();
	}
	
	/**
	 *  allows the member to add a new boat 
	 * @param event the ActionEvent
	 */
   @FXML
    void addBoat(ActionEvent event) {
	   if(!member.isValidMembership()) {
		   AlertDialogue.warning("You do not have a valid membership.");
		   return;
	   }
	   addBoatBtn.setVisible(false);
	   addBoatView.setVisible(true);
    }

    /**
	 *  allows the member to add a new boat with payment verification across the payment window
	 * @param event the ActionEvent
	 */
   @FXML
    void addNewBoat(ActionEvent event) {
    	if(!member.isValidMembership()) {
 		   AlertDialogue.warning("You do not have a valid membership.");
 		   return;
 	   	}
    	if(Double.parseDouble(newBoatLength.getText()) < 15) {
    		newBoatStorageFee.setText("Minimum boat length required is 15ft.");
    		return;
    	}
    	Boat boat = new Boat(newBoatName.getText(),member.getId(),Double.parseDouble(newBoatLength.getText()));
    	int idNewBoat = member.addBoat(boat);
    	if(idNewBoat != 0) {		
    		try {
    			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/payment.fxml"));
    			
    			paymentController.payment = new Payment(member.getId(), "", null, null, "storage fee", idNewBoat, null, newBoatStorageFeeCalculated, null);
    			
    			Scene scene = new Scene(fxml);
    			Stage paymentWindow = new Stage();
    			
    			paymentWindow.setScene(scene);
    			paymentWindow.setResizable(false);
    			paymentWindow.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
    			paymentWindow.initModality(Modality.APPLICATION_MODAL);
    			paymentWindow.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
    			paymentWindow.showAndWait();
    			
    			if(paymentController.paidSuccessfully) {
    				setBoats();
    				newBoatName.setText("");
    	    		newBoatLength.setText("");
    			}else {
    				boat.deleteBoat(idNewBoat);
    			}

    		} catch (IOException e) {
    			e.printStackTrace();
    			AlertDialogue.error("Unable to Access Payment Service Area.");
    		}
    		
    	}else
    		AlertDialogue.error("Error occured while adding boat");
    }
    
	/**
	 *  opens the races view
	 * @param event the ActionEvent
	 */
   @FXML
    void gotoRaces(ActionEvent event) {
    	disableAll();
    	races.setVisible(true);
    	rbAllRaces.setSelected(true);
    	
    }
    
	/**
	 *  opens the notification view
	 * @param event the ActionEvent
	 */
   @FXML
    void gotoNotifications(ActionEvent event) {
    	disableAll();
    	showNotificationsUnreadCB.setSelected(false);
    	showNotificationsReadCB.setSelected(false);
    	filterNotifications.setVisible(false);
    	setNotifications();
    	notificationsView.setVisible(true);
    }
    
	/**
	 *  displays or hides the notifications filter
	 * @param event the ActionEvent
	 */
   @FXML
    void toggleFilterNotifications(ActionEvent event) {
    	filterNotifications.setVisible(!filterNotifications.isVisible());
    }
    
	/**
	 *  shows only unread notifications (checked box)
	 * @param event the ActionEvent
	 */
   @FXML
    void showNotificationsUnread(ActionEvent event) {
    	setNotifications();
    }
    
	/**
	 *  shows only read notifications (checked box)
	 * @param event the ActionEvent
	 */
   @FXML
    void showNotificationsRead(ActionEvent event) {
    	setNotifications();
    }
    
	/**
	 *  goes back to the notifications view, exiting from the notification details view
	 * @param event the MouseEvent
	 */
   @FXML
    void gobackToNotifications(MouseEvent event) {
    	notificationDetailsView.setVisible(false);
    	setNotifications();
    	notificationsView.setVisible(true);
    }
    
   /**
    * loads member home view, displaying messages like unread notifications and member's upcoming races details
    */
    private void loadHomeView() {
    	int noti = member.countUnreadNotification();
		notificationLabel.setText(noti == 0 ? "" : "YOU HAVE "+noti+" UNREAD NOTIFICATION/S");
		homeView.setVisible(true);
		memberUpcomingRacesList = member.getRaces("upcoming");
		for (int i = memberUpcomingRacesList.size()-1; i >= 0; i--) 
			if(memberUpcomingRacesList.get(i).isCancelled())
			memberUpcomingRacesList.remove(memberUpcomingRacesList.get(i));
		
		otherRacesList = member.getRaces("notEnrolled");
		memberUpcomingRaces.setVisible(!memberUpcomingRacesList.isEmpty());
		otherRaces.setVisible(!otherRacesList.isEmpty());
		index1 = index2 = -1;
		next();
		timeline.play();
    }
    
    /**
     * Transition to next next race in homepage display
     */
    private void next() {
    	if (index1 == memberUpcomingRacesList.size()-1)
			index1 = -1;
    	
    	if (index2 == otherRacesList.size()-1)
			index2 = -1;
    	
    	if (!memberUpcomingRacesList.isEmpty()) {
    		eventTitleUpcoming.setText(LocalDateTime.parse(memberUpcomingRacesList.get(++index1).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
    		eventTextUpcoming.setText(memberUpcomingRacesList.get(index1).getName());
		}
    	
    	if (!otherRacesList.isEmpty()) {
    		eventTitleOther.setText(LocalDateTime.parse(otherRacesList.get(++index2).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
    		eventTextOther.setText(otherRacesList.get(index2).getName());
		}
    }
	
    /**
     * set member's profile information for display
     */
	private void setProfile() {
		id.setText(member.getId());
		name.setText(member.getName());
		surname.setText(member.getSurname());
		address.setText(member.getAddress());
		if(member.isValidMembership()) {
			membershipStatus.setText("Valid");
			membershipStatus.setId("valid");
			membershipStatus.setDisable(true);
		}else {
			membershipStatus.setText("Pending");
			membershipStatus.setId("expired");
		}
	}
	
	/**
	 * disable all visible view(pane)
	 */
	private void disableAll() {
		homeView.setVisible(false);
		servicesView.setVisible(false);
		if(timeline != null)
			timeline.stop();
		menuView.setVisible(false);
		profile.setVisible(false);
		editProfile.setVisible(false);
		boats.setVisible(false);
		races.setVisible(false);
		notificationsView.setVisible(false);
		notificationDetailsView.setVisible(false);
	}
	
	/**
	 * adds an event listener to the text fields for avoiding the insertion of incorrect values
	 */
	private void addListener() {
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(newValue.trim().length() > 0) {
				if(textField == changeName || textField == changeSurname) {
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
				}else if(textField == changeAddress || textField == newBoatName) {
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
				}else if(textField == newBoatLength) {
					if(newValue.length() > 6)
						textField.setText(oldValue);
					else if (!newValue.matches("\\d+(\\.?\\d{2})?")) {
						newValue = newValue.replaceAll("[^\\d+(\\.?\\d{2})?]", "");
						if(( newValue.split( Pattern.quote("." ), -1).length) - 1 > 1)
							textField.setText(oldValue);
						else
							textField.setText(newValue);
					}
				}else {
					textField.setText(newValue.trim());
				}
			}else
				textField.setText(newValue.stripLeading());
			
			if(newBoatLength.getText().length() != 0 && Double.parseDouble(newBoatLength.getText()) >= 15) {
				newBoatStorageFeeCalculated = Double.parseDouble(String.format("%.2f", Double.parseDouble(newBoatLength.getText())*new Staff().getBoatStorageFeePerFoot()));
				newBoatStorageFee.setText("Annual Storage Fee: $"+newBoatStorageFeeCalculated);
				
			}else
				newBoatStorageFee.setText("");
			
			setName.setDisable((changeName.getText().trim().length() == 0) || (changeName.getText().trim().toLowerCase().equals(member.getName().toLowerCase())));
			setSurname.setDisable((changeSurname.getText().trim().length() == 0) || (changeSurname.getText().trim().toLowerCase().equals(member.getSurname().toLowerCase())));
			setAddress.setDisable((changeAddress.getText().trim().length() == 0) || (changeAddress.getText().trim().toLowerCase().equals(member.getAddress().toLowerCase())));
			setPassword.setDisable((oldPassword.getText().trim().length() == 0) || (changePassword1.getText().trim().length() == 0) || (changePassword2.getText().trim().length() == 0));
			addNewBoatBtn.setDisable(newBoatName.getText().length() == 0 || newBoatLength.getText().length() == 0);
		});
		
		changeName.textProperty().addListener(listener);
		changeSurname.textProperty().addListener(listener);
		changeAddress.textProperty().addListener(listener);
		oldPassword.textProperty().addListener(listener);
		changePassword1.textProperty().addListener(listener);
		changePassword2.textProperty().addListener(listener);
		newBoatName.textProperty().addListener(listener);
		newBoatLength.textProperty().addListener(listener);
		
		tgRaces.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
        {
			@Override
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
				if(tgRaces.getSelectedToggle() == rbAllRaces) {
					setAllRaces();
					enrolledRacesTableView.setVisible(false);
					pastRacesTableView.setVisible(false);
					allRacesTableView.setVisible(true);
				}else if(tgRaces.getSelectedToggle() == rbEnrolledRaces) {
					setEnrolledRaces();						
					allRacesTableView.setVisible(false);
					pastRacesTableView.setVisible(false);
					enrolledRacesTableView.setVisible(true);
				}else if(tgRaces.getSelectedToggle() == rbPastRaces) {
					setPastRaces();
					allRacesTableView.setVisible(false);
					enrolledRacesTableView.setVisible(false);
					pastRacesTableView.setVisible(true);
				}
			}
        });
	}
	
	
	private void addColsToBoatTable(TableView<Boat> table) {
		TableColumn<Boat, JFXButton> storageStatus = new TableColumn<Boat, JFXButton>("Storage Status");
        Callback<TableColumn<Boat, JFXButton>, TableCell<Boat, JFXButton>> cellFactoryStorage = new Callback<TableColumn<Boat, JFXButton>, TableCell<Boat, JFXButton>>() {
            @Override
            public TableCell<Boat, JFXButton> call(final TableColumn<Boat, JFXButton> param) {
                final TableCell<Boat, JFXButton> cell = new TableCell<Boat, JFXButton>() {

                    private final JFXButton btn = new JFXButton();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Boat boat = getTableView().getItems().get(getIndex());
                            if(boat.isValidStorage())
                            	return;
                            
                            try {
                    			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/payment.fxml"));
                    			
                    			paymentController.payment = new Payment(member.getId(), "", null, null, "storage fee", boat.getId(), null, boat.getLength()*new Staff().getBoatStorageFeePerFoot(), null);
                    			
                    			Scene scene = new Scene(fxml);
                    			Stage paymentWindow = new Stage();
                    			
                    			paymentWindow.setScene(scene);
                    			paymentWindow.setResizable(false);
                    			paymentWindow.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                    			paymentWindow.initModality(Modality.APPLICATION_MODAL);
                    			paymentWindow.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
                    			paymentWindow.showAndWait();
                    			
                    			if(paymentController.paidSuccessfully) {
                    				setBoats();
                    			}

                    		} catch (IOException e) {
                    			e.printStackTrace();
                    			AlertDialogue.error("Unable to Access Payment Service Area.");
                    		}
                        });
                    }

                    @Override
                    public void updateItem(JFXButton item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                            if(getTableView().getItems().get(getIndex()).isValidStorage()) {
                            	btn.setText("Valid");
                            	btn.setDisable(true);
                            	btn.setId("valid");
                            }else {
                            	btn.setText("Expired");
                            	btn.setId("expired");
                            }
                        }
                    }
                };
                return cell;
            }
        };
        storageStatus.setCellFactory(cellFactoryStorage);
        table.getColumns().add(storageStatus);
		
        TableColumn<Boat, JFXButton> colBtn = new TableColumn<Boat, JFXButton>();
        Callback<TableColumn<Boat, JFXButton>, TableCell<Boat, JFXButton>> cellFactory = new Callback<TableColumn<Boat, JFXButton>, TableCell<Boat, JFXButton>>() {
            @Override
            public TableCell<Boat, JFXButton> call(final TableColumn<Boat, JFXButton> param) {
                final TableCell<Boat, JFXButton> cell = new TableCell<Boat, JFXButton>() {

                    private final JFXButton btn = new JFXButton("remove");

                    {
                    	btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("remove")).toExternalForm(), 15, 15, true, true)));   
                        btn.setOnAction((ActionEvent event) -> {
                            Boat boat = getTableView().getItems().get(getIndex());
                            if(AlertDialogue.confirm("Confrim removal of boat: "+boat.getName()+" ("+boat.getLength()+"feet)", "Remove", "Cancel")) {
                            	if(boat.removeBoat(boat.getId())) {
                            		AlertDialogue.info("'"+boat.getName()+"' boat successfully removed.");
                            		setBoats();
                            	}
                            	else
                            		AlertDialogue.error("error occured!");
                            }
                        });
                    }

                    @Override
                    public void updateItem(JFXButton item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);

    }
	
	/**
	 * populate boat table view
	 */
	private void setBoats() {
		boatsTableView.getItems().clear();
		ArrayList<Boat> boatsArray = new Boat().getBoats(member.getId());
		if(!boatsArray.isEmpty())
			for (Boat boat : boatsArray)
				boatsTableView.getItems().add(boat);
		else
			boatsTableView.setPlaceholder(new Label("No boat stored."));
	}
	
	/**
	 * populate race table view
	 */
	private void setAllRaces() {
		allRacesTableView.getItems().clear();
		ArrayList<Race> racesArray = new Race().getRaces("all");
		if(!racesArray.isEmpty())
			for (Race race : racesArray)
				allRacesTableView.getItems().add(race);
		else
			allRacesTableView.setPlaceholder(new Label("No race available"));
	}
	
	/**
	 * populate member's enrolled race table view
	 */
	private void setEnrolledRaces() {
		enrolledRacesTableView.getItems().clear();
		ArrayList<Race> racesArray = member.getRaces("upcoming");
		if(!racesArray.isEmpty())
			for (Race race : racesArray)
				enrolledRacesTableView.getItems().add(race);
		else
			enrolledRacesTableView.setPlaceholder(new Label("No enrolled upcoming race"));
	}
	
	/**
	 * populate member's enrolled past race table view
	 */
	private void setPastRaces() {
		pastRacesTableView.getItems().clear();
		ArrayList<Race> racesArray = member.getRaces("past");
		if(!racesArray.isEmpty())
			for (Race race : racesArray)
				pastRacesTableView.getItems().add(race);
		else
			pastRacesTableView.setPlaceholder(new Label("No enrolled past race"));
	}
	
	
	private void addButtonToRaceTableAll(TableView<Race> table) {

		TableColumn<Race, JFXButton> colBtn = new TableColumn<Race, JFXButton>();

        Callback<TableColumn<Race, JFXButton>, TableCell<Race, JFXButton>> cellFactory = new Callback<TableColumn<Race, JFXButton>, TableCell<Race, JFXButton>>() {
            @Override
            public TableCell<Race, JFXButton> call(final TableColumn<Race, JFXButton> param) {
                final TableCell<Race, JFXButton> cell = new TableCell<Race, JFXButton>() {

                    private final JFXButton btn = new JFXButton();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	if(!member.isValidMembership()) {
                     		   AlertDialogue.warning("You do not have a valid membership.");
                     		   return;
                     	   }
                        	
                        	Race race = getTableView().getItems().get(getIndex());
                        	
                        	if(AlertDialogue.confirm("Race Enrolment Fee: $"+race.getFee(), "PAY", "Cancel")) {
                        		int idBoat = selectBoatForRace(race.getName());
                            	if(idBoat != 0) {
                                	try {
                            			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/payment.fxml"));
                            			
                            			paymentController.payment = new Payment(member.getId(), "", null, null, "Race Fee", idBoat, race.getId(), race.getFee(), null);
                            			
                            			Scene scene = new Scene(fxml);
                            			Stage paymentWindow = new Stage();
                            			paymentWindow.setScene(scene);
                            			paymentWindow.setResizable(false);
                            			paymentWindow.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                            			paymentWindow.initModality(Modality.APPLICATION_MODAL);
                            			paymentWindow.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
                            			paymentWindow.showAndWait();
                            			
                            			if(paymentController.paidSuccessfully) {
                            				if(member.enroll(new enrolment(member.getId(), race.getId(), idBoat)))
                            					setAllRaces();
                            				else
                            					AlertDialogue.error("Error Occured. (DB)");
                            					
                            			}

                            		} catch (IOException e) {
                            			e.printStackTrace();
                            			AlertDialogue.error("Unable to Access Payment Service Area.");
                            		}
                            	}
                        	}
                        });
                    }

                    @Override
                    public void updateItem(JFXButton item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                            Race race = getTableView().getItems().get(getIndex());
                            boolean enroll = true;
                            if(getTableRow() != null) {
                            	if(new enrolment().memberIsEnrolled(member.getId(), race.getId())) {
									btn.setId("enrolledRace");
									btn.setDisable(true);
									btn.setText("enrolled");
									enroll = false;
								}
                            	
                            	if(race.isCancelled(race.getId())) { // database control incase of live changes not yet registered in the value read.
                            		btn.setId("cancelledRace");
									btn.setDisable(true);
									btn.setText("cancelled");
									enroll = false;
								}
                            	
                            	if(race.isFinished(race.getId())) {
                            		btn.setId("finishedRace");
									btn.setDisable(true);
									btn.setText("Finished");
									enroll = false;
								}
                            	
                            	if(enroll) {
                            		btn.setId("enrollRace");
                            		btn.setDisable(false);
                            		btn.setText("enrol now!");
                            	}
                            }
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
	}
	
	/**
	 * member selects one of their boats to enrol in race
	 */
	private int selectBoatForRace(String race) {
		temp = 0;
		
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(allRacesTableView.getScene().getWindow());
        Text title = new Text("SELECT BOAT FOR RACE: '"+race+"'");
        title.setId("selectBoatText");
        VBox titleVbox = new VBox(title);
        titleVbox.setAlignment(Pos.TOP_CENTER);
        
        VBox dialogueVbox = new VBox();
        dialogueVbox.setAlignment(Pos.CENTER);
        dialogueVbox.setSpacing(20);
        dialogueVbox.setId("dialogVboxSelectBoat");
        
        ArrayList<Boat> boats = new Boat().getBoats(member.getId());
        VBox boatVbox = new VBox();
        boatVbox.setSpacing(10);
        boatVbox.setPadding(new Insets(10, 0, 10, 0));
        boatVbox.setAlignment(Pos.CENTER);
        boatVbox.setId("VboxSelectBoat");
        for (Boat boat : boats) {
        	JFXButton btn = new JFXButton(boat.getName()+" (Length: "+boat.getLength()+"ft)");
        	btn.setId(boat.isValidStorage() ? "selectBoatButtonValid" : "selectBoatButtonExpired");
        	boatVbox.getChildren().add(btn);
        	
        	btn.setOnAction(e -> {
        		if(!boat.isValidStorage()) {
        			AlertDialogue.warning("Storage for this boat is expired.");
        		}else {
	        		dialog.close();
	        		temp = boat.getId();
        		}
        	});
        }
        
        dialogueVbox.getChildren().addAll(titleVbox,boatVbox);
        ScrollPane pane = new ScrollPane(dialogueVbox);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        Scene dialogScene = new Scene(pane, 300, 300);
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialogScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
        dialog.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
        dialog.showAndWait();
        
        return temp;
	}
	
	private void addButtonToRaceTableEnrolled(TableView<Race> table) {
		TableColumn<Race, JFXButton> colBtn = new TableColumn<Race, JFXButton>();

        Callback<TableColumn<Race, JFXButton>, TableCell<Race, JFXButton>> cellFactory = new Callback<TableColumn<Race, JFXButton>, TableCell<Race, JFXButton>>() {
            @Override
            public TableCell<Race, JFXButton> call(final TableColumn<Race, JFXButton> param) {
                final TableCell<Race, JFXButton> cell = new TableCell<Race, JFXButton>() {

                    private final JFXButton btn = new JFXButton("details");

                    {
                    	btn.setGraphic(new ImageView(new Image(getClass().getResource(imagesPath.get("info")).toExternalForm(), 15, 15, true, true)));   
                    	btn.setId("enrolledRaceDetails");
                    	btn.setOnAction((ActionEvent event) -> {
                        	Race race = getTableView().getItems().get(getIndex());
                        	
                        	final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(table.getScene().getWindow());
                            VBox dialogVbox = new VBox();
                            dialogVbox.setSpacing(20);
                            
                            dialogVbox.getChildren().add(new Text("Race: "+race.getName()));
                            
                            LocalDateTime ldt = LocalDateTime.parse( race.getDate().toString().replace( " " , "T" )) ;
                            dialogVbox.getChildren().add(new HBox(10, new Text("Date: "+ldt.toLocalDate()), new Text("Time: "+ldt.toLocalTime())));
                            Boat boatRegistered = new Boat().getBoat(new enrolment().getBoat(member.getId(), race.getId()));
                            Text boatText = new Text("Boat: "+boatRegistered.getName()+" ("+boatRegistered.getLength()+"ft)");
                            boatText.setId("enrolledBoatText");
                            JFXButton changeBoat = new JFXButton("change boat");
                            changeBoat.setId("changeBoatOfRace");
                            dialogVbox.getChildren().add(new HBox(5, boatText, changeBoat));
                            JFXButton unEnroll = new JFXButton("un-enroll");
                            unEnroll.setId("unenrollFromEnrolledRace");
                            dialogVbox.getChildren().add(unEnroll);
                            
                            dialogVbox.setAlignment(Pos.CENTER);
                            dialogVbox.setId("viewEnrolledRaceDialog");
                            dialogVbox.setPadding(new Insets(0, 0, 0, 30));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialogScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
                            dialog.setScene(dialogScene);
                            
                            changeBoat.setOnAction(e -> {
                            	int idBoat = selectBoatForRace(race.getName());
                            	if(idBoat != 0 && idBoat != boatRegistered.getId()) {
                            		if(member.changeBoatForRace(race.getId(), idBoat)) {
                            			dialog.close();
                            			btn.fire();
                            		}
                            		else
                            			AlertDialogue.error("Error Ocurred while trying to change boat (DB)");
                            	}
                            });
                            unEnroll.setOnAction(e -> {
                            	int daysToRace = ((int) ChronoUnit.DAYS.between(LocalDateTime.now(), race.getDate().toLocalDateTime()));
                            	if(daysToRace <= 5)
                            		AlertDialogue.error("You cannot unenroll within 5 days before race date.");
                            	else {
                            		if(AlertDialogue.confirm("confirm unerolment from race '"+race.getName()+"'", "yes", "cancel")) {
                                		if(member.unenroll(race.getId())) {
                                			AlertDialogue.warning("You have un enrolled from the race '"+race.getName()+"'.\n You will be reimburse with a voucher within a week.");
                                			dialog.close();
                                			setEnrolledRaces();
                                		}
                                		else 
                                			AlertDialogue.error("Error occured. (DB)");
                                	}
                            	}
                            });
                            
                            dialog.setResizable(false);
                            dialog.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
                            dialog.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(JFXButton item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                            if(getTableRow() != null) {
                            	if(new Race().isCancelled(getTableView().getItems().get(getIndex()).getId())) { // database control incase of live changes not yet registered in the value read.
                            		btn.setId("cancelledRace");
									btn.setDisable(true);
									btn.setText("cancelled");
									
								}
                            }
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
	}

	/**
	 * populate member's notification view
	 */
	private void setNotifications() {
		notifications.getChildren().clear();
		ArrayList<Notification> notificationsArray = new ArrayList<Notification>();
		if((!showNotificationsUnreadCB.isSelected() && !showNotificationsReadCB.isSelected())
	    		|| (showNotificationsUnreadCB.isSelected() && showNotificationsReadCB.isSelected()))
			notificationsArray = member.getAllNotifications("all");
	    	else if(showNotificationsUnreadCB.isSelected())
	    		notificationsArray = member.getAllNotifications("notificationsUnread");
	    	else if(showNotificationsReadCB.isSelected()) 
	    		notificationsArray = member.getAllNotifications("notificationsRead");
		
		if(!notificationsArray.isEmpty())
			for (Notification notification : notificationsArray) {
				VBox vbox = new VBox();
				vbox.setId(notification.isRead() ? "notificationsRead" : "notificationsUnread");
				vbox.setPadding(new Insets(5));
				
				Label lbHead = new Label(notification.getHead());
				lbHead.setId("lbHead");
				Label lbBody = new Label(notification.getBody());
				lbBody.setId("lbBody");
				
				vbox.getChildren().addAll(lbHead, lbBody);
				vbox.setOnMouseClicked(e -> {
					notificationsView.setVisible(false);
					notificationHeading.setText(notification.getHead());
					notificationBody.setText(notification.getBody());
					
					if(!notification.isRead())
						notification.setRead();
					notificationDetailsView.setVisible(true);
				});
				notifications.getChildren().add(vbox);
		}else
			notifications.getChildren().add(new Label("NO NOTIFICATION"));
	}
	
	/**
	 * confirm and deletes member's account
	 */
	private void confirmDeleteAccount() {
		final Stage dialog = new Stage();

		PasswordField str = new PasswordField();
		str.setPromptText("Enter new PASSWORD");
		JFXButton delete = new JFXButton("DELETE");
		delete.setStyle("-fx-background-color: lightgreen; ");
		HBox feeBox = new HBox(5, new Text("password: "), str, delete);
		feeBox.setAlignment(Pos.CENTER);

		delete.setDisable(true);
		str.textProperty().addListener((observable, oldValue, newValue) -> { 
			str.setText(newValue.trim());
			delete.setDisable(str.getText().length() == 0);
		});

		delete.setOnAction(e1 -> {
			if(!str.getText().equals(member.getPassword())) {
				AlertDialogue.warning("Password Incorrect.");
				return;
			}
			dialog.close();
			if(!AlertDialogue.confirm("You are about to delete your membership account.\n"
					+ "All membership previleges will be revoked and any current subscription will be canceled without and form of reimbursement.", "DELETE", "Cancel"))
				return;
			else {
				if(member.disableAccount()) {
					AlertDialogue.warning("Account deleted.");
					member.logout();
					try {
					    Stage thisStage = (Stage) editProfile.getScene().getWindow();
					    
					    Parent root = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
						Scene scene = new Scene(root);
						
						thisStage.setScene(scene);
					} catch (IOException e) {
						e.printStackTrace();
						AlertDialogue.error("Unable to load HomePage");
					}
				}else 
					AlertDialogue.error("Error Occured. (DB)");
			}
		});

		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.initOwner(editProfile.getScene().getWindow());
		dialog.setScene(new Scene(feeBox, 300, 200));
		dialog.getIcons().add( new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
		dialog.showAndWait();
	}
}
