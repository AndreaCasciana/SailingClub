package guiController;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import system.Race;

/**
 * 
 * homePage GUI controller
 *
 */
public class homePageController {
	
	@FXML 
	private AnchorPane root;
	@FXML 
	private BorderPane centerView;
	@FXML
	private AnchorPane pane2;
	@FXML
	private AnchorPane pane3;
	@FXML
	private Text event;
	@FXML
	private Text eventTitle;
	@FXML
	private Text eventText;
	@FXML
    private JFXButton login;
    @FXML
    private JFXButton home;
    @FXML 
	private AnchorPane servicesView;
	
	private Timeline timeline;
	private int show = 0, index = -1;
	private double  width;
	private List<Race> raceList;
	
	/**
	 * initialize window
	 */
	@FXML
	void initialize() {
		if(!OpeningSceneController.introLoaded) {
			loadOpeningScene();	
		}
		
		
		setRaces();
		if (!raceList.isEmpty()) {
			eventTitle.setText(LocalDateTime.parse(raceList.get(++index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
			eventText.setText(raceList.get(index).getName());
		}
		width = 800;
		translateAnimation(1,pane2,0,width);
		translateAnimation(1,pane3,0,width);
		timeline = new Timeline(new KeyFrame(Duration.millis(4000), e -> next(null)));
		timeline.setCycleCount(Animation.INDEFINITE); // loop forever
	    
	    centerView.setVisible(true);
	    pane2.setVisible(true);
	    pane3.setVisible(true);
	    servicesView.setVisible(false);
	    timeline.play();
	}
	
	/**
	 * goes to the general homepage page
	 * @param event the MouseEvent
	 */
	@FXML
    void gotoHomePage(MouseEvent event) {
		servicesView.setVisible(false);
		centerView.setVisible(true);
	    pane2.setVisible(true);
	    pane3.setVisible(true);
	    timeline.play();
    }

	/**
	 * opens sign in window to permit member or staff login
	 * @param event the ActionEvent
	 */
	@FXML
	void gotoLogin(ActionEvent event)  {
		try {

			Parent fxml = FXMLLoader.load(getClass().getResource("/gui/Signin.fxml"));
			Scene scene = new Scene(fxml);
			Stage window = new Stage();
			window.setScene(scene);
			window.setResizable(false);
			window.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			window.initModality(Modality.APPLICATION_MODAL);
			window.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
			window.showAndWait();

			if(MemberController.member.sessionOn) {
				Node node = (Node) event.getSource();
			    Stage thisStage = (Stage) node.getScene().getWindow();
			    fxml = FXMLLoader.load(getClass().getResource("/gui/Member.fxml"));
				scene = new Scene(fxml);
				scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
				thisStage.setScene(scene);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Unable to Access Payment Service Area.");
		}
	}

	/**
	 * displays the next view of the carousel containing the upcoming races details
	 * @param event the ActionEvent
	 */
	@FXML
	void next(ActionEvent event) {
		if (index == raceList.size()-1)
			index = -1;
		
		if(show==0) {
			translateAnimation(1,pane2,width,0);
			show++;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(++index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}
		}else if(show==1) {
			translateAnimation(1,pane3,width,0);
			show++;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(++index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}
		}else if(show==2) {
			translateAnimation(1,pane2,0,-width);
			translateAnimation(1,pane3,0,-width);
			show = 0;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(++index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}
		}
	}

	/**
	 * displays the previous view of the carousel containing the upcoming races details
	 * @param event the ActionEvent
	 */
	@FXML
	void back(ActionEvent event) {
		if (--index < 0)
			index = raceList.size()-1;
		
		if(show==1) {
			translateAnimation(1,pane2,0,width);
			show--;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}
		}
		else if(show==2) {
			translateAnimation(1,pane3,0,width);
			show--;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}

		}else if(show==0) {
			translateAnimation(1,pane2,width,0);
			translateAnimation(1,pane3,width,0);
			show = 2;
			if (!raceList.isEmpty()) {
				eventTitle.setText(LocalDateTime.parse(raceList.get(index).getDate().toString().replace( " " , "T" )).toLocalDate().toString());
				eventText.setText(raceList.get(index).getName());
			}

		}
	}
	
	/**
	 * displays the view containing the software description
	 * @param event the ActionEvent
	 */
	@FXML
	void services(ActionEvent event) {
		centerView.setVisible(false);
	    pane2.setVisible(false);
	    pane3.setVisible(false);
	    servicesView.setVisible(true);
	    timeline.stop();
	}
	
	/**
	 * opens the UNIPR website on the user's browser
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
	 * displays the splash screen on application startup
	 */
	private void loadOpeningScene() {
		try {
			BorderPane intro = FXMLLoader.load(getClass().getResource("/gui/OpeningScene.fxml"));
			root.getChildren().setAll(intro);
			OpeningSceneController.introLoaded = true;
			
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), intro);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), intro);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			
			fadeIn.play();
			
			fadeIn.setOnFinished((e) -> fadeOut.play());
			
			fadeOut.setOnFinished((e) -> {
				try {
					AnchorPane home = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
					root.getChildren().setAll(home);
				} catch (IOException e1) {
					e1.printStackTrace();
					AlertDialogue.error("Unable to load HomePage");
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogue.error("Unable to load OpeningScene");
		}
	}
	
	/**
	 * animate the carousel's transition
	 * @param duration the speed
	 * @param node the element's node
	 * @param fromWidth starting point
	 * @param toWidth end point
	 */
	private void translateAnimation(double duration, Node node, double fromWidth, double toWidth) {

		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
		translateTransition.setFromX(fromWidth);
		translateTransition.setToX(toWidth);
		translateTransition.play();
	}
	
	/**
	 * displays the upcoming races on the homePage view
	 */
	private void setRaces() {
		raceList = new Race().getRaces("upcoming");
		if(raceList.isEmpty()) 
			event.setText("NO UPCOMING RACE");
		else
			event.setText("UPCOMING RACES");
		
	}

}
