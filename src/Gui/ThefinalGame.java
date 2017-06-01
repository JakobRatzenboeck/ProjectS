package Gui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Controler.GameHandler;
import Model.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ThefinalGame extends Application {

	private Label timerH = new Label("00");
	private Label timerM = new Label("00");
	private Label timerS = new Label("00");
	private ToggleButton hilfe;
	private Button[][] sSpiel = new Button[9][9];
	private ToggleButton[] butns = new ToggleButton[9];
	private int now;
	private Game gm;
	private Timer timer;

	final FileChooser fileChooser = new FileChooser();
	final GameHandler gh = new GameHandler(this);
	private Stage stage;

	private String loadpath;
	private String hS = "";
	private String bS = "";
	private String fS = "";

	public ThefinalGame(int delete, Stage primaryStage) {
		gm = new Game(delete);
		timer = new Timer(this, 0, 0, 0);
		try {
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ThefinalGame(File source, Stage primaryStage) {
		gm = new Game(source);
		try {
			loadpath = source.getPath();
			start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		load();
		timer.start();
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 600, 800);
		primaryStage.setTitle("Sudoku");

		MenuBar mBar = new MenuBar();
		Menu file = new Menu("Spiel");
		MenuItem neu = new MenuItem("Neu");
		MenuItem save = new MenuItem("Speichern");
		MenuItem load = new MenuItem("Laden");
		MenuItem opt = new MenuItem("Optionen");

		neu.setOnAction(ActionEvent -> {
			try {
				if (loadpath == null) {
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
							gm.save();
							timer.terminate();
							Spiel s = new Spiel();
							s.start(new Stage());
							primaryStage.close();
							info.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (result.get() == buttonTypeTwo) {
						timer.terminate();
						Spiel s = new Spiel();
						s.start(new Stage());
						primaryStage.close();
						info.close();
					} else if (result.get() == buttonTypeThree) {
						info.close();
					}
				} else {
					timer.terminate();
					Spiel s = new Spiel();
					s.start(new Stage());
					primaryStage.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		save.setOnAction(ActionEvent -> {
			try {
				if (loadpath != null) {
					gm.save(loadpath);
				} else {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm");
					LocalDateTime localDatetime = LocalDateTime.now();
					gm.save();
					loadpath = "savedGames/" + dtf.format(localDatetime) + "_Sudoku.dat";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		load.setOnAction(ActionEvent -> {
			System.out.println(loadpath);
			if (loadpath == null) {
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
						gm.save();
						stage = primaryStage;
						configureFileChooser(fileChooser);
						File file1 = fileChooser.showOpenDialog(primaryStage);
						if (file != null) {
							openFile(file1);
						}

						info.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (result.get() == buttonTypeTwo) {
					stage = primaryStage;
					configureFileChooser(fileChooser);
					File file1 = fileChooser.showOpenDialog(primaryStage);
					if (file != null) {
						openFile(file1);
					}

					info.close();
				} else if (result.get() == buttonTypeThree) {
					info.close();
				}
			} else {
				stage = primaryStage;
				configureFileChooser(fileChooser);
				File file1 = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					openFile(file1);
				}
			}
		});

		opt.setOnAction(ActionEvent -> {
			new Optionen(800, 600);
		});

		file.getItems().addAll(neu, save, load, opt);
		// Menu help = new Menu("Hilfe");
		// MenuItem quickHelp = new MenuItem("Quickfix");
		//
		// quickHelp.setOnAction(ActionEvent -> Platform.exit());
		//
		// MenuItem about = new MenuItem("Über");
		// help.getItems().addAll(quickHelp, about);

		mBar.getMenus().addAll(file);
		mBar.setPrefHeight(10);
		mBar.setMinWidth(500);
		mBar.setPrefWidth(600);
		mBar.setMaxWidth(1000);

		// Zusatz
		HBox adds = new HBox();
		adds.setMinSize(500, 50);
		adds.setPrefSize(600, 50);
		adds.setMaxSize(1000, 50);

		hilfe = new ToggleButton();
		Button reset = new Button();
		reset.setStyle("-fx-background-image: url('reset_50.png')");

		hilfe.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!hilfe.isSelected()) {
					for (int i = 0; i < 9; ++i) {
						for (int j = 0; j < 9; ++j) {
							if (gm.getAnfang(j, i)) {
								sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
							} else {
								sSpiel[j][i].setStyle("-fx-border-color: None");
							}
						}
					}
					hilfe.setStyle("-fx-color: #F3F3F3");
				} else {
					for (int i = 0; i < 9; ++i) {
						for (int j = 0; j < 9; ++j) {
							if (gm.getFertigesfeld(j, i) != 0) {
								if (gm.getFertigesfeld(j, i) == now) {
									sSpiel[j][i].setStyle("-fx-color: #00A9D3");
								}
							}
						}
					}
					hilfe.setStyle("-fx-color: #00A9D3");
				}
			}

		});

		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < 9; ++i) {
					for (int j = 0; j < 9; ++j) {
						if (gm.getAnfang(j, i) == false) {
							sSpiel[j][i].setText("");
							sSpiel[j][i].setStyle("-fx-border-color: None");
							gm.setFertigesfeld(j, i, 0);

						}
					}
				}
			}
		});
		timerH.setMinSize(12, 50);
		timerM.setMinSize(12, 50);
		timerS.setMinSize(102, 50);
		hilfe.setMinSize(50, 50);
		reset.setMinSize(49, 50);

		Label d = new Label(":");
		Label d2 = new Label(":");
		d.setMinSize(2, 50);
		d2.setMinSize(2, 50);

		adds.getChildren().add(timerH);
		adds.getChildren().add(d);
		adds.getChildren().add(timerM);
		adds.getChildren().add(d2);
		adds.getChildren().add(timerS);
		adds.getChildren().add(hilfe);
		adds.getChildren().add(reset);

		// Sudoku Feld
		FlowPane sFeld = new FlowPane();
		sFeld.setMinSize(274, 274);
		sFeld.setPrefSize(454, 454);
		sFeld.setMaxSize(630, 630);
		sFeld.setStyle("-fx-background-color: #" + bS + "");
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				sSpiel[j][i] = new Button();
				if (gm.getFertigesfeld(j, i) != 0) {
					sSpiel[j][i].setText("" + gm.getFertigesfeld(j, i));
				}
				if (gm.getAnfang(j, i) == true) {
					sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
				} else {
					sSpiel[j][i].setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							for (int i = 0; i < 9; ++i) {
								for (int j = 0; j < 9; ++j) {
									if (sSpiel[j][i].isFocused()) {
										if (sSpiel[j][i].getText().equals("" + now)) {
											sSpiel[j][i].setText("");
											if (gm.getAnfang(j, i)) {
												sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
											} else {
												sSpiel[j][i].setStyle("-fx-border-color: None");
											}
											gm.setFertigesfeld(j, i, 0);
										} else {
											if (now != 0) {
												if (hilfe.isSelected()) {
													sSpiel[j][i].setStyle("-fx-color: #00A9D3");
												}
												sSpiel[j][i].setText("" + now);
											}
											gm.setFertigesfeld(j, i, now);
										}
									}
								}
							}
							if (gm.finished()) {
								timer.terminate();
								ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
								Alert meldung = new Alert(AlertType.INFORMATION);
								meldung.setTitle("Eilmeldung");
								meldung.setHeaderText("Sie haben gewonnen");
								meldung.setContentText(
										"Gebrauchte Zeit || Stunde : Minute : Sekunde || " + timerH.getText() + ":"
												+ timerM.getText() + ":" + (Integer.parseInt(timerS.getText()) + 1));
								meldung.getDialogPane().getButtonTypes().setAll(buttonTypeOk);
								Optional<ButtonType> result = meldung.showAndWait();
								if (result.get() == buttonTypeOk) {
									gm.fertig();
									Main m = new Main(400, 400);
									m.start(new Stage());
									primaryStage.close();
								}
							}
						}
					});
				}
				sSpiel[j][i].setMinSize(30, 30);
				sSpiel[j][i].setPrefSize(50, 50);
				sSpiel[j][i].setMaxSize(70, 70);
				sFeld.getChildren().add(sSpiel[j][i]);
				if (j == 2 || j == 5) {
					FlowPane.setMargin(sSpiel[j][i], new Insets(0, 2, 0, 0));
					if (i == 2 || i == 5) {
						FlowPane.setMargin(sSpiel[j][i], new Insets(0, 2, 2, 0));
					}
				}
				if (i == 2 || i == 5) {
					FlowPane.setMargin(sSpiel[j][i], new Insets(0, 0, 2, 0));
					if (j == 2 || j == 5) {
						FlowPane.setMargin(sSpiel[j][i], new Insets(0, 2, 2, 0));
					}
				}
			}
		}

		HBox num = new HBox();
		ToggleGroup oneToNine = new ToggleGroup();
		for (int i = 0; i < 9; ++i) {
			for (int u = 0; u < 9; ++u) {
				for (int j = 0; j < 9; ++j) {
					for (int h = 0; h < 9; ++h) {
						if (gm.getFertigesfeld(j, u) == h) {

						}
					}

				}
			}
			butns[i] = new ToggleButton("" + (i + 1));
			butns[i].setToggleGroup(oneToNine);
			butns[i].setPrefSize(25, 25);
			butns[i].setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					now = 0;
					for (int i = 0; i < 9; ++i) {
						if (butns[i].isSelected()) {
							now = Integer.parseInt(butns[i].getText());
							selectButns(i);
						}
					}

				}
			});
			num.getChildren().add(butns[i]);
		}
		for (

				int i = 0; i < 9; ++i) {
			HBox.setMargin(butns[i], new Insets(50, 12, 0, 12));
		}
		FlowPane.setMargin(adds, new Insets(2.5, 0, 2.5, 275));
		FlowPane.setMargin(sFeld, new Insets(5, 66, 30, 66));
		FlowPane.setMargin(num, new Insets(0, 75, 0, 75));

		root.setStyle("-fx-background-color: #" + hS + "");
		root.getChildren().addAll(mBar, adds, sFeld, num);

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new GameHandler(this));
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(800);
		primaryStage.centerOnScreen();
		primaryStage.getIcons().add(new Image("JJGames.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						timer.terminate();
						primaryStage.close();
					}
				});
			}
		});
	}

	public void load() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hS = dis.readUTF();
		bS = dis.readUTF();
		fS = dis.readUTF();
		dis.readDouble();
		dis.close();
	}

	/**
	 * @return the sSpiel
	 */
	public Button[][] getsSpiel() {
		return sSpiel;
	}

	/**
	 * @return the butns
	 */
	public ToggleButton[] getButns() {
		return butns;
	}

	/**
	 * @param timerH
	 *            the timerH to set
	 */
	public void setTimerH(String h) {
		timerH.setText(h);
	}

	/**
	 * @param timerM
	 *            the timerM to set
	 */
	public void setTimerM(String m) {
		timerM.setText(m);
	}

	/**
	 * @param timerS
	 *            the timerS to set
	 */
	public void setTimerS(String s) {
		timerS.setText(s);
	}

	public void selectButns(int bunts) {
		now = bunts + 1;
		butns[bunts].setSelected(true);
		butns[bunts].requestFocus();
		if (hilfe.isSelected()) {
			for (int i = 0; i < 9; ++i) {
				for (int j = 0; j < 9; ++j) {
					if (gm.getFertigesfeld(j, i) != 0) {
						if (gm.getFertigesfeld(j, i) == now) {
							sSpiel[j][i].setStyle("-fx-color: #00A9D3");
						} else {
							if (gm.getAnfang(j, i)) {
								sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
							} else {
								sSpiel[j][i].setStyle("-fx-border-color: None");
							}
						}
					}
				}
			}
		}
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Sudokus");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "savedGames"));
	}

	private void openFile(File file) {
		new ThefinalGame(file, stage);
	}
}
