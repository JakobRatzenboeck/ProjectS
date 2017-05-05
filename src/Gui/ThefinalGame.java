package Gui;

import Model.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThefinalGame extends Application {

	int x = 0;
	int m = 0;
	int h = 0;

	String s = "";
	private Button[][] buttons = new Button[9][9];
	private Game gm = new Game(x);

	@Override
	public void start(Stage primaryStage) {
		try {

			// HIER DEN SCHWIERIGKEITSGRAD AUSWÄHLBAR MACHEN

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 600, 300);
			primaryStage.setTitle("SUDOKU GAME");

			FlowPane game = new FlowPane();
			game.setPrefSize(454, 454);
			game.setStyle("-fx-background-color: #000000");

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					buttons[j][i] = new Button();
					buttons[j][i].setPrefSize(50, 50);
					game.getChildren().add(buttons[j][i]);

					if (j == 2 || j == 5) {
						FlowPane.setMargin(buttons[j][i], new Insets(0, 2, 0, 0));
						if (i == 2 || i == 5) {
							FlowPane.setMargin(buttons[j][i], new Insets(0, 2, 2, 0));
						}
					}
					if (i == 2 || i == 5) {
						FlowPane.setMargin(buttons[j][i], new Insets(0, 0, 2, 0));
						if (j == 2 || j == 5) {
							FlowPane.setMargin(buttons[j][i], new Insets(0, 2, 2, 0));
						}
					}
				}
			}

			VBox box = new VBox(game);

			root.setCenter(box);

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
