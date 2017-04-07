package Gui;

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
			
			
			//HIER DEN SCHWIERIGKEITSGRAD AUSWÄHLBAR MACHEN
			int p = 10;
			
			
			
			
			
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			primaryStage.setTitle("SUDOKU");
			 Game x = new Game(p);
			 int[][] sudokuroh = new int[9][9];
			 sudokuroh = x.getFertigesfeld();
			
			 
			 
			 Object[][] sudoku = new Object[9][9];
			 
			 
			int b = 0;
			int c = 0;
			int z = sudokuroh[b][c];
			int helfer = -1;
			
			
			for(int i = 0;i < 81; i++)
			{
				Button a = new Button("" + z);
				sudoku[b][c] = a;
				
				if(helfer >0)
				{
					b++;
				}
				else
				{
					c++;
				}
				
				
			}
			
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
