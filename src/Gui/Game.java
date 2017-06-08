package Gui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.sun.prism.paint.Color;

import Controler.AddsHandler;
import Controler.GameNUMHandler;
import Model.Start;
import Music.Player;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game extends Application {

	private MenuBar mBar = new MenuBar();
	private Menu file = new Menu("Spiel");
	private MenuItem neu = new MenuItem("Neu");
	private MenuItem save = new MenuItem("Speichern");
	private MenuItem load = new MenuItem("Laden");
	private MenuItem opt = new MenuItem("Optionen");

	private Label timerH = new Label("00");
	private Label timerM = new Label("00");
	private Label timerS = new Label("00");
	private ToggleButton hilfe;
	private Button reset;
	private Button[][] sSpiel = new Button[9][9];
	private ToggleButton[] butns = new ToggleButton[9];
	private int now;
	private Start st;
	private Player pl;

	final FileChooser fileChooser = new FileChooser();
	final AddsHandler gbh = new AddsHandler(this);
	final GameNUMHandler gh = new GameNUMHandler(this);
	private Stage stage;

	private String loadpath;
	private String hS = "";
	private String bS = "";
	private String fS = "";
	private String Mpath;
	private boolean mute;
	private double lautstaerke;
	private boolean mMode;

	public Game(int delete) {
		st = new Start(delete, this);
		st.getTimer().start();
		try {
			loadSettings();
			pl = new Player(Mpath, this);
			start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Game(File source) {
		st = new Start(source, this);
		st.getTimer().start();
		try {
			loadSettings();
			pl = new Player(Mpath, this);
			loadpath = source.getPath();
			start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		Player.sound(lautstaerke / 100);
		FlowPane root = new FlowPane();
		Scene scene = new Scene(root, 600, 800);
		primaryStage.setTitle("Sudoku");
		neu.setOnAction(new AddsHandler(this));

		save.setOnAction(new AddsHandler(this));

		load.setOnAction(new AddsHandler(this));

		opt.setOnAction(ActionEvent -> {
			new Optionen(800, 600);
			try {
				Player.close();
				if (loadpath != null) {
					st.save(loadpath);
				} else {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm");
					LocalDateTime localDatetime = LocalDateTime.now();
					st.save();
					loadpath = "savedGames/" + dtf.format(localDatetime) + "_Sudoku.dat";
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			st.getTimer().terminate();
			new Game(new File(loadpath));
			primaryStage.close();
			Player.close();
		});

		file.getItems().addAll(neu, save, load, opt);

		mBar.getMenus().addAll(file);
		mBar.setPrefHeight(10);
		mBar.setMinWidth(500);
		mBar.setPrefWidth(600);
		mBar.setMaxWidth(1000);

		// Zusatz
		HBox adds = new HBox();
		adds.setMinSize(92, 50);
		adds.setPrefSize(452, 50);
		adds.setMaxSize(677, 50);

		hilfe = new ToggleButton();
		reset = new Button();
		reset.setStyle("-fx-background-image: url('reset_50.png')");

		if (mMode) {
			hilfe.setOnAction(new AddsHandler(this));
		} else {
			hilfe.disarm();
		}

		reset.setOnAction(new AddsHandler(this));

		timerH.setMinSize(12, 50);
		timerM.setMinSize(12, 50);
		timerS.setMinSize(12, 50);
		hilfe.setMinSize(50, 50);
		reset.setMinSize(49, 50);

		Label d = new Label(":");
		Label d2 = new Label(":");
		d.setMinSize(2, 50);
		d2.setMinSize(2, 50);

		HBox.setMargin(timerS, new Insets(0, 90, 0, 0));
		HBox.setMargin(timerH, new Insets(0, 0, 0, 209));
		adds.setStyle("-fx-background-color: #FFFFFF");
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
				if (st.getFertigesfeld(j, i) != 0) {
					sSpiel[j][i].setText("" + st.getFertigesfeld(j, i));
				}
				if (st.getAnfang(j, i) == true) {
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
											if (st.getAnfang(j, i)) {
												sSpiel[j][i].setStyle("-fx-background-color: #" + fS + "");
											} else {
												sSpiel[j][i].setStyle("-fx-border-color: None");
											}
											st.setFertigesfeld(j, i, 0);
										} else {
											if (now != 0) {
												if (hilfe.isSelected()) {
													sSpiel[j][i].setStyle("-fx-color: #00A9D3");
												}
												sSpiel[j][i].setText("" + now);
											}
											st.setFertigesfeld(j, i, now);
										}
									}
								}
							}
							if (st.finished()) {
								st.getTimer().terminate();
								ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
								Alert meldung = new Alert(AlertType.INFORMATION);
								meldung.setTitle("Eilmeldung");
								meldung.setHeaderText("Sie haben gewonnen");
								if (timerS.getText().substring(0, 1).equals("0")) {
									meldung.setContentText("Gebrauchte Zeit:  " + timerH.getText() + ":"
											+ timerM.getText() + ":0" + (Integer.parseInt(timerS.getText()) + 1));
								} else {
									meldung.setContentText("Gebrauchte Zeit: " + timerH.getText() + ":"
											+ timerM.getText() + ":" + (Integer.parseInt(timerS.getText()) + 1));
								}
								meldung.getDialogPane().getButtonTypes().setAll(buttonTypeOk);
								Optional<ButtonType> result = meldung.showAndWait();
								if (result.get() == buttonTypeOk) {
									st.fertig();
									Main m = new Main(400, 400);
									m.start(new Stage());
									Player.close();
									primaryStage.close();
									st.fertig();
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
			if (mMode != true) {
				butns[i] = new ToggleButton("" + (i + 1));
			} else {
				butns[i].setStyle("-fx-background-image: url('src/Meme" + i + ".png')");
			}
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
		FlowPane.setMargin(adds, new Insets(5, 0, 2, 66));
		FlowPane.setMargin(sFeld, new Insets(3, 66, 10, 66));
		FlowPane.setMargin(num, new Insets(0, 75, 0, 75));

		root.setStyle("-fx-background-color: #" + hS + "");
		root.getChildren().addAll(mBar, adds, sFeld, num);

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new GameNUMHandler(this));
		primaryStage.setMinWidth(600);
		primaryStage.setMaxWidth(600);
		primaryStage.setMinHeight(700);
		primaryStage.setHeight(800);
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
						st.getTimer().terminate();
						Player.close();
						primaryStage.close();
					}
				});
			}
		});
	}

	public void loadSettings() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hS = dis.readUTF();
		bS = dis.readUTF();
		fS = dis.readUTF();
		mute = dis.readBoolean();
		if (mute) {
			lautstaerke = 0;
			dis.readDouble();
		} else {
			lautstaerke = dis.readDouble();
		}
		mMode = dis.readBoolean();
		Mpath = dis.readUTF();
		dis.close();
	}

	public void selectButns(int bunts) {
		now = bunts + 1;
		butns[bunts].setSelected(true);
		butns[bunts].requestFocus();
		if (hilfe.isSelected()) {
			for (int i = 0; i < 9; ++i) {
				for (int j = 0; j < 9; ++j) {
					if (st.getFertigesfeld(j, i) != 0) {
						if (mMode == false) {
							if (st.getFertigesfeld(j, i) == now) {
								sSpiel[j][i].setStyle("-fx-color: #00A9D3");
							} else {
								if (st.getAnfang(j, i)) {
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
	}

	public static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Sudokus");
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "savedGames"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Datei", "*.dat"));
	}

	public void openFile(File file) {
		new Game(file);
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

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return st.getTimer();
	}

	/**
	 * @param timer
	 *            the timer to set
	 */
	private void setTimer(Timer timer) {
		this.st.setTimer(timer);
	}

	/**
	 * @return the loadpath
	 */
	public String getLoadpath() {
		return loadpath;
	}

	/**
	 * @param loadpath
	 *            the loadpath to set
	 */
	public void setLoadpath(String loadpath) {
		this.loadpath = loadpath;
	}

	/**
	 * @return the neu
	 */
	public MenuItem getNeu() {
		return neu;
	}

	/**
	 * @param neu
	 *            the neu to set
	 */
	private void setNeu(MenuItem neu) {
		this.neu = neu;
	}

	/**
	 * @return the save
	 */
	public MenuItem getSave() {
		return save;
	}

	/**
	 * @param save
	 *            the save to set
	 */
	private void setSave(MenuItem save) {
		this.save = save;
	}

	/**
	 * @return the load
	 */
	public MenuItem getLoad() {
		return load;
	}

	/**
	 * @param load
	 *            the load to set
	 */
	private void setLoad(MenuItem load) {
		this.load = load;
	}

	/**
	 * @return the gm
	 */
	public Start getSt() {
		return st;
	}

	/**
	 * @param gm
	 *            the gm to set
	 */
	private void setSt(Start gm) {
		this.st = gm;
	}

	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	private void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return the fileChooser
	 */
	public FileChooser getFileChooser() {
		return fileChooser;
	}

	/**
	 * @return the hilfe
	 */
	public ToggleButton getHilfe() {
		return hilfe;
	}

	/**
	 * @param hilfe
	 *            the hilfe to set
	 */
	private void setHilfe(ToggleButton hilfe) {
		this.hilfe = hilfe;
	}

	/**
	 * @return the now
	 */
	public int getNow() {
		return now;
	}

	/**
	 * @param now
	 *            the now to set
	 */
	private void setNow(int now) {
		this.now = now;
	}

	public Button getSSpiel(int x, int y) {
		return sSpiel[x][y];
	}

	/**
	 * @return the reset
	 */
	public Button getReset() {
		return reset;
	}

	/**
	 * @param reset
	 *            the reset to set
	 */
	private void setReset(Button reset) {
		this.reset = reset;
	}

	/**
	 * @return the fS
	 */
	public String getfS() {
		return fS;
	}

	/**
	 * @return the pl
	 */
	public Player getPl() {
		return pl;
	}

	/**
	 * @param pl
	 *            the pl to set
	 */
	private void setPl(Player pl) {
		this.pl = pl;
	}

}
