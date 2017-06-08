package Controler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Gui.Auswahl;
import Gui.Game;
import Gui.Timer;
import Music.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AddsHandler implements EventHandler<ActionEvent> {

	private final Game game;

	public AddsHandler(Game game) {
		this.game = game;
	}

	@Override
	public void handle(ActionEvent arg0) {
		Object source = arg0.getSource();

		// Neu Menu
		if (source == game.getNeu()) {

			try {
				if (game.getLoadpath() == null) {
					ButtonType buttonTypeOne = new ButtonType("Speichern");
					ButtonType buttonTypeTwo = new ButtonType("Nicht speichern");
					ButtonType buttonTypeThree = new ButtonType("Abbrechen");
					Alert info = new Alert(AlertType.CONFIRMATION);
					info.setTitle("Ungespeichertes Spiel");
					info.setHeaderText("Wollen sie das derzeitige Sudoku speichern?");
					info.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);
					Optional<ButtonType> result = info.showAndWait();
					if (result.get() == buttonTypeOne) {
						try {
							game.getTimer().terminate();
							game.getSt().save();
							Player.close();
							Auswahl s = new Auswahl();
							s.start(new Stage());
							game.getStage().close();
							info.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (result.get() == buttonTypeTwo) {
						game.getTimer().terminate();
						Player.close();
						Auswahl s = new Auswahl();
						s.start(new Stage());
						game.getStage().close();
						info.close();
					} else if (result.get() == buttonTypeThree) {
						info.close();
					}
				} else {
					game.getSt().save(game.getLoadpath());
					game.getTimer().terminate();
					Auswahl s = new Auswahl();
					s.start(new Stage());
					Player.close();
					game.getStage().close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Save Menu
		} else if (source == game.getSave()) {

			try {
				if (game.getLoadpath() != null) {
					game.getTimer().terminate();
					game.getSt().save(game.getLoadpath());
				} else {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm");
					LocalDateTime localDatetime = LocalDateTime.now();
					game.getSt().save();
					game.setLoadpath("savedGames/" + dtf.format(localDatetime) + "_Sudoku.dat");
					game.getSt().getTimer().start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Load Menu
		} else if (source == game.getLoad()) {

			if (game.getLoadpath() == null) {
				ButtonType buttonTypeOne = new ButtonType("Speichern");
				ButtonType buttonTypeTwo = new ButtonType("Nicht speichern");
				ButtonType buttonTypeThree = new ButtonType("Abbrechen");
				Alert info = new Alert(AlertType.CONFIRMATION);
				info.setTitle("Ungespeichertes Spiel");
				info.setHeaderText("Wollen sie das derzeitige Sudoku speichern?");
				info.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);
				Optional<ButtonType> result = info.showAndWait();
				if (result.get() == buttonTypeOne) {
					try {
						game.getTimer().terminate();
						game.getSt().save();
						Game.configureFileChooser(game.getFileChooser());
						File file = game.getFileChooser().showOpenDialog(game.getStage());
						if (file != null) {
							game.openFile(file);
						}
						Player.close();
						game.getStage().close();

						info.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (result.get() == buttonTypeTwo) {
					Game.configureFileChooser(game.getFileChooser());
					File file = game.getFileChooser().showOpenDialog(game.getStage());
					if (file != null) {
						game.openFile(file);
					}
					Player.close();
					game.getStage().close();

					info.close();
				} else if (result.get() == buttonTypeThree) {
					info.close();
				}
			} else {
				try {
					game.getSt().save(game.getLoadpath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Game.configureFileChooser(game.getFileChooser());
				File file = game.getFileChooser().showOpenDialog(game.getStage());
				if (file != null) {
					game.openFile(file);
				}
				Player.close();
				game.getStage().close();
			}

			// Hilfe EIN/AUS
		} else if (source == game.getHilfe() && game.getMMode() == false) {
			if (!game.getHilfe().isSelected()) {
				for (int i = 0; i < 9; ++i) {
					for (int j = 0; j < 9; ++j) {
						if (game.getSt().getAnfang(j, i)) {
							game.getSSpiel(j, i).setStyle("-fx-background-color: #" + game.getfS() + "");
						} else {
							game.getSSpiel(j, i).setStyle("-fx-border-color: None");
						}
					}
				}
				game.getHilfe().setStyle("-fx-color: #F3F3F3");
			} else {
				for (int i = 0; i < 9; ++i) {
					for (int j = 0; j < 9; ++j) {
						if (game.getSt().getFertigesfeld(j, i) != 0) {
							if (game.getSt().getFertigesfeld(j, i) == game.getNow()) {
								game.getSSpiel(j, i).setStyle("-fx-color: #00A9D3");
							}
						}
					}
				}
				game.getHilfe().setStyle("-fx-color: #00A9D3");
			}

			// Reset Handler
		} else if (source == game.getReset()) {

			for (int i = 0; i < 9; ++i) {
				for (int j = 0; j < 9; ++j) {
					if (game.getSt().getAnfang(j, i) == false) {
						game.getSSpiel(j, i).setText("");
						game.getSSpiel(j, i).setStyle("-fx-border-color: None");
						game.getSt().setFertigesfeld(j, i, 0);
					}
				}
			}
		}
	}
}