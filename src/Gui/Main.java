package Gui;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {

	private int height;
	private int width;

	public Main() {

	}

	public Main(int height, int width) {
		this.height = height;
		this.width = width;
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			primaryStage.setTitle("SUDOKU");

			Label top = new Label("Sudoku");
			top.setFont(new Font("Arial", 30));
			Button start = new Button("Start");
			Button end = new Button("Ende");
			Button credits = new Button("Credits");
			Button website = new Button("Website");
			Button settings = new Button("Einstellung");

			end.setOnAction(ActionEvent -> Platform.exit());
			Auswahl s = new Auswahl();
			start.setOnAction(ActionEvent -> s.start(primaryStage));

			credits.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle("JJ Games and more");
					info.setContentText("Made By Jakob Ratzenböck , pfusched by Johannes Strobl");
					info.show();
				}
			});

			settings.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					new Optionen(400, 400);
				}
			});

			website.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {

//					Uri location = new Uri("http://uhl17723.webspace.spengergasse.at/").toURI().toString();

					Desktop desktop = Desktop.getDesktop();	

					// Adresse mit Standardbrowser anzeigen
					URI uri;
					try {
						uri = new URI("http://uhl17723.webspace.spengergasse.at/");
						desktop.browse(uri);
					} catch (Exception oError) {
						// Hier Fehler abfangen
						System.out.println("Seite kann nicht geöffnet werden.");
					}

				}
			});

			// credits.setOnAction(value);

			settings.setPrefSize(90, 35);
			start.setPrefSize(90, 35);
			credits.setPrefSize(90, 35);
			end.setPrefSize(90, 35);
			website.setPrefSize(90, 35);
			website.setTranslateX(60);

			settings.setTranslateX(150);
			credits.setTranslateX(150);
			start.setTranslateX(150);
			end.setTranslateX(250);

			settings.setTranslateY(70);
			end.setTranslateY(-35);
			start.setTranslateY(35);
			credits.setTranslateY(105);

			// website.setTranslateY(-30);
			top.setTranslateY(20);
			top.setTranslateX(140);

			VBox boxbottom = new VBox(website, end);
			VBox box = new VBox();
			VBox boxtop = new VBox(top);

			box.getChildren().addAll(start, settings, credits);
			root.setLeft(box);

			root.setTop(boxtop);
			root.setBottom(boxbottom);

			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("JJGames.png"));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
