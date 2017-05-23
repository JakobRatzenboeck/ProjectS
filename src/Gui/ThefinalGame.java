package Gui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Model.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlendMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ThefinalGame extends Application {

	private ToggleButton hilfe;
	private Button[][] sSpiel = new Button[9][9];
	private ToggleButton[] butns = new ToggleButton[9];
	private int now;
	private Game gm;

	final FileChooser fileChooser = new FileChooser();
	final GameHandler gh = new GameHandler(this);
	private Stage stage;

	private String loadpath;
	private String hNS = "";
	private String bNS = "";
	private String fNS = "";
	private String hS = "";
	private String bS = "";
	private String fS = "";

	public ThefinalGame(int delete, Stage primaryStage) {
		gm = new Game(delete);
		try {
			start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ThefinalGame(File source, Stage primaryStage) {
		gm = new Game(source);
		try {
			loadpath = source.getPath();
			start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		load();
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
			Spiel s = new Spiel();
			try {
				s.start(new Stage());
				primaryStage.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
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
		Image resetP = new Image("reset_45.png");
		Button reset = new Button();
		reset.setGraphic(new ImageView(resetP));

		hilfe.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!hilfe.isSelected()) {
					for (int i = 0; i < 9; ++i) {
						for (int j = 0; j < 9; ++j) {
							if (gm.getAnfang(j, i)) {
								sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
							} else {
								sSpiel[j][i].setStyle("-fx-border-color: #");
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
							sSpiel[j][i].setStyle("-fx-background-color: #");
							gm.setFertigesfeld(j, i, 0);

						}
					}
				}
			}
		});

		hilfe.setMinSize(50, 50);
		reset.setMinSize(50, 50);

		adds.getChildren().addAll(hilfe, reset);

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
							int full = 0;
							for (int i = 0; i < 9; ++i) {
								for (int j = 0; j < 9; ++j) {
									if (sSpiel[j][i].isFocused()) {
										if (sSpiel[j][i].getText().equals("" + now)) {
											sSpiel[j][i].setText("");
											if (gm.getAnfang(j, i)) {
												sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
											} else {
												sSpiel[j][i].setStyle("-fx-border-color: #");
											}
											gm.setFertigesfeld(j, i, 0);
										} else {
											if (now != 0) {
												sSpiel[j][i].setStyle("-fx-color: #00A9D3");
												sSpiel[j][i].setText("" + now);
											}
											gm.setFertigesfeld(j, i, now);
										}
									}
								}
							}
							if (gm.finished()) {

								ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
								Alert meldung = new Alert(AlertType.INFORMATION);
								meldung.setTitle("Eilmeldung");
								meldung.setHeaderText("Sie haben gewonnen");
								meldung.setContentText("Zurück zum Startscreen");
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
		FlowPane.setMargin(adds, new Insets(2.5, 0, 2.5, 486));
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
	}

	public void load() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hNS = dis.readUTF();
		bNS = dis.readUTF();
		fNS = dis.readUTF();
		hS = dis.readUTF();
		bS = dis.readUTF();
		fS = dis.readUTF();
		dis.readDouble();
		dis.readBoolean();
		dis.close();
	}

	/**
	 * @return the sSpiel
	 */
	public Button[][] getsSpiel() {
		return sSpiel;
	}

	/**
	 * @param sSpiel
	 *            the sSpiel to set
	 */
	private void setsSpiel(Button[][] sSpiel) {
		this.sSpiel = sSpiel;
	}

	/**
	 * @return the butns
	 */
	public ToggleButton[] getButns() {
		return butns;
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

	/**
	 * @param butns
	 *            the butns to set
	 */
	private void setButns(ToggleButton[] butns) {
		this.butns = butns;
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
