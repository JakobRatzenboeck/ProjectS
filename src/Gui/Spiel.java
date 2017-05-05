package Gui;

import java.util.Random;

import Model.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Spiel extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			ThefinalGame hallo = new ThefinalGame();
			
			Random r = new Random();
			int q=r.nextInt(22)   +9 ;
			System.out.println(q);
			int m = r.nextInt(11) +31;
			int h = r.nextInt(22) + 42;
			System.out.println(m + "     " + h);
			
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,600,200);
			primaryStage.setTitle("SUDOKU GAME");
			
			Button easy = new Button("Einfach");
			Button normal = new Button("Normal");
			Button hard = new Button("Schwer");
			Button load = new Button("Spiel Laden");
			Button choose = new Button("EntwicklerModus");
			
			
			VBox oben = new VBox(easy,normal,hard);
			VBox unten = new VBox(load,choose)	;		
			easy.setMaxSize(90, 20);
			easy.setTranslateX(100);
			easy.setTranslateY(40);
			
			
			easy.setOnAction(ActionEvent -> hallo.start(primaryStage));
			normal.setOnAction(ActionEvent -> hallo.start(primaryStage));
			hard.setOnAction(ActionEvent -> hallo.start(primaryStage));
			
			
			
			
			
			normal.setMaxSize(90, 20);
			normal.setTranslateX(250);
			normal.setTranslateY(10);
			
			hard.setMaxSize(90, 20);
			hard.setTranslateX(400);
			hard.setTranslateY(-21);
			
			load.setMaxSize(210, 20);
			load.setTranslateX(190);
			load.setTranslateY(-40);
			
			
			root.setTop(oben);
			root.setBottom(unten);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

		

	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	
}
