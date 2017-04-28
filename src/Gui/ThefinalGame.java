package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ThefinalGame extends Application {

	
		int e = 0;
		int m = 0;
		int h = 0;
		String s ="";
	
	
		
	
	
		@Override
		public void start(Stage primaryStage) {
			try {
				
				
				//HIER DEN SCHWIERIGKEITSGRAD AUSWÄHLBAR MACHEN
			
				
				
				
				
				
				BorderPane root = new BorderPane();
				Scene scene = new Scene(root,600,300);
				primaryStage.setTitle("SUDOKU GAME");
				
			
				
				
				
				
				
				
				
				
		 
				
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



		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		



		/**
		 * @return the e
		 */
		public int getE() {
			return e;
		}






		/**
		 * @return the m
		 */
		public int getM() {
			return m;
		}






		/**
		 * @return the h
		 */
		public int getH() {
			return h;
		}






		/**
		 * @param e the e to set
		 */
		public void setE(int e) {
			this.e = e;
		}






		/**
		 * @param m the m to set
		 */
		public void setM(int m) {
			this.m = m;
		}






		/**
		 * @param h the h to set
		 */
		public void setH(int h) {
			this.h = h;
		}






		/**
		 * @return the s
		 */
		public String getS() {
			return s;
		}






		/**
		 * @param s the s to set
		 */
		public void setS(String s) {
			this.s = s;
		}
		
		
		
		
	}
