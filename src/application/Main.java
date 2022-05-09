package application;
	
import guiController.AlertDialogue;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * 
 * the main class, where the software begins
 *
 */
public class Main extends Application {
	/**
	 * start function
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/homePage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        if(AlertDialogue.confirm("Exit System?", "YES", "NO"))
			        	System.exit(0);
			        t.consume();
			    }
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main function that launches the application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
