module SailingClub {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires com.jfoenix;
	requires java.desktop;
	requires org.junit.jupiter.api;
	requires org.mockito;
	
	opens application to javafx.graphics, javafx.fxml;
	opens guiController to javafx.fxml ;
	opens system to javafx.base;
}
