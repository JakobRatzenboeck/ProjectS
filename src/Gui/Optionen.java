package Gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Optionen extends Dialog<ButtonType> {

	private FileChooser fileChooser = new FileChooser();
	String notAllowed = "HIJKLMNOPQRSTUVWXYZÜÖÄ!\"§$%&/()=?`²³{[]}\\´^°<>|,.-;:_#+*'~@€";
	// Farbe in hexadezimal
	String hS = "FFFFFF";
	String bS = "000000";
	String fS = "C3C3C3";

	// Lautstaerke
	double sVal = 40;

	// Stummschaltung
	boolean tVal = false;
	boolean Memode = false;

	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.LEFT);
	ButtonType buttonTypeReset = new ButtonType("Zurücksetzen");
	ButtonType buttonTypeBack = new ButtonType("Zurück", ButtonData.CANCEL_CLOSE);
	TabPane tPane = new TabPane();
	Tab allgemein = new Tab("Allgemein");
	GridPane allgemeinP = new GridPane();
	Tab fortg = new Tab("Fortgeschrittene Einstellungen");
	GridPane fortgP = new GridPane();
	Tab me = new Tab("");
	GridPane meP = new GridPane();

	private ObservableList<String> options = FXCollections.observableArrayList("", "Schwarz", "Grau", "Weiß", "Blau",
			"Rot", "Gelb", "Grün");

	Label h = new Label("Hintergrund: ");
	ComboBox<String> hCb = new ComboBox<String>(options);

	Label b = new Label("3x3-Abgrenzung: ");
	ComboBox<String> bCb = new ComboBox<String>(options);

	Label f = new Label("Felder: ");
	ComboBox<String> fCb = new ComboBox<String>(options);

	ToggleButton MeMode = new ToggleButton("M");

	Label mString = new Label("MP3-Datei: ");
	TextField mStringTf = new TextField();
	Button mBut = new Button();

	Label m = new Label("Musiklautstärke:");
	Slider slider = new Slider();

	ToggleButton mute = new ToggleButton();

	// fortgeschrittene sachen
	TextField hTf = new TextField("FFFFFF");
	TextField bTf = new TextField("000000");
	TextField fTf = new TextField("C3C3C3");
	TextField lPath = new TextField();

	public Optionen(int height, int width) {
		try {
			load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (tVal) {
			slider.setValue(0);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");
		} else {
			slider.setValue(sVal);
		}
		mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");

		this.setTitle("Optionen");
		this.setHeight(800);
		this.setWidth(600);
		this.setResizable(false);
		tPane.getTabs().addAll(allgemein, fortg, me);
		allgemein.setContent(allgemeinP);
		allgemein.setClosable(false);
		fortg.setContent(fortgP);
		me.setContent(meP);
		me.setClosable(false);
		fortg.setClosable(false);
		allgemeinP.setAlignment(Pos.CENTER);
		fortgP.setAlignment(Pos.CENTER);
		meP.setAlignment(Pos.CENTER);

		meP.add(MeMode, 1, 1);

		h.setPrefHeight(20);
		b.setPrefHeight(20);
		f.setPrefHeight(20);
		m.setPrefHeight(20);

		// hCb.getSelectionModel().select(3);
		hCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, String oldValue, String newValue) {
				selectTf(hTf, newValue);
			}
		});
		// bCb.getSelectionModel().select(1);
		bCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, String oldValue, String newValue) {
				selectTf(bTf, newValue);
			}
		});
		// fCb.getSelectionModel().select(2);
		fCb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(@SuppressWarnings("rawtypes") ObservableValue observable, String oldValue, String newValue) {
				selectTf(fTf, newValue);
			}
		});

		allgemeinP.add(h, 1, 1);
		allgemeinP.add(hCb, 2, 1);

		allgemeinP.add(b, 1, 2);
		allgemeinP.add(bCb, 2, 2);

		allgemeinP.add(f, 1, 3);
		allgemeinP.add(fCb, 2, 3);

		// erweiterte Einstellungen
		slider.setMin(0);
		slider.setMax(100);
		slider.setShowTickLabels(false);
		slider.setShowTickMarks(false);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(1);
		slider.setBlockIncrement(1);
		slider.setPrefHeight(20);
		// Change Voloume
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (slider.getValue() >= 75) {
					mute.setSelected(false);
					tVal = false;
					mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted.png')");
				} else if (slider.getValue() >= 50) {
					mute.setSelected(false);
					tVal = false;
					mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted2.png')");
				} else if (slider.getValue() >= 25) {
					mute.setSelected(false);
					tVal = false;
					mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted1.png')");
				} else if (slider.getValue() == 0) {
					mute.setSelected(false);
					tVal = false;
					mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");
				} else {
					mute.setSelected(false);
					tVal = false;
					mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted0.png')");
				}
			}
		});
		mute.setPrefHeight(25);
		mute.setPrefWidth(25);
		mute.setMinSize(25, 25);
		if (slider.getValue() >= 75) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted.png')");
		} else if (slider.getValue() >= 50) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted2.png')");
		} else if (slider.getValue() >= 25) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted1.png')");
		} else if (slider.getValue() == 0) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");
		} else {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted0.png')");
		}
		mute.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (mute.isSelected() && slider.getValue() != 0) {
					mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");
					sVal = slider.getValue();
					slider.setValue(0);
					tVal = true;
				} else {
					slider.setValue(sVal);
					if (slider.getValue() >= 75) {
						mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted.png')");
						tVal = false;
					} else if (slider.getValue() >= 50) {
						mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted2.png')");
						tVal = false;
					} else if (slider.getValue() >= 25) {
						mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted1.png')");
						tVal = false;
					} else if (slider.getValue() == 0) {
						mute.setStyle("-fx-background-image: url('ImagesAndMore/muted.png')");
						tVal = false;
					} else {
						mute.setStyle("-fx-background-image: url('ImagesAndMore/unmuted0.png')");
						tVal = false;
					}
				}
			}
		});
		fortgP.add(new Label("Hintergrund:	# "), 1, 1);
		fortgP.add(hTf, 2, 1);
		fortgP.add(new Label("Border:		# "), 1, 2);
		fortgP.add(bTf, 2, 2);
		fortgP.add(new Label("Felder:		# "), 1, 3);
		fortgP.add(fTf, 2, 3);
		fortgP.add(mString, 1, 4);
		mStringTf.tooltipProperty().set(new Tooltip("MP3 datei ist nötig! (C:/User/music/Muster.mp3)"));
		fortgP.add(mStringTf, 2, 4);
		mBut.setMinSize(35, 35);
		mBut.setStyle("-fx-background-image: url('ImagesAndMore/ordner.jpg')");
		mBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				configureFileChooser(fileChooser);
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null) {
					mStringTf.setText(file.getPath());
				}
			}
		
		});
		fortgP.add(mBut, 3, 4);
		fortgP.add(m, 1, 5);
		fortgP.add(slider, 2, 5);
		fortgP.add(mute, 3, 5);

		tPane.setPrefHeight(height);
		tPane.setPrefWidth(width);
		this.setHeight(height);
		this.setWidth(width);
		this.getDialogPane().setContent(tPane);
		this.onCloseRequestProperty();
		this.getDialogPane().getButtonTypes().setAll(buttonTypeOk, buttonTypeReset, buttonTypeBack);
		handleButtonTypes();
	}
	
	public void handleButtonTypes() {
		Optional<ButtonType> result = this.showAndWait();
		if (result.get() == buttonTypeOk & validHexacode(hS) & validHexacode(bS) & validHexacode(fS)) {
			DataOutputStream dos;
			getColorFromText(hTf);
			getColorFromText(bTf);
			getColorFromText(fTf);
			String path = "Settings/settings.dat";
			Paths.get(path);
			File f = new File(path);
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				dos = new DataOutputStream(new FileOutputStream(f));
				dos.writeUTF(hS.toUpperCase());
				dos.writeUTF(bS.toUpperCase());
				dos.writeUTF(fS.toUpperCase());
				dos.writeBoolean(tVal);
				dos.writeDouble(sVal);
				dos.writeBoolean(MeMode.isSelected());
				dos.writeUTF(mStringTf.getText());
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result.get() == buttonTypeOk && (validHexacode(hS) == false | validHexacode(bS) == false
				| validHexacode(fS) == false)) {
			if (validHexacode(hS) == false ) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(hTf.getText() + " darf nur A,B,C,D,E,F beinhalten");
				a.setContentText(h.getText());
				a.showAndWait();
			} else if (validHexacode(bS) == false ) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(bTf.getText() + " darf nur A,B,C,D,E,F beinhalten!");
				a.setContentText(b.getText());
				a.showAndWait();
			} else if (validHexacode(fS) == false ) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(fTf.getText() + " darf nur A,B,C,D,E,F beinhalten!");
				a.setContentText(f.getText());
				a.showAndWait();
			}
			this.showAndWait();
		} else if (result.get() == buttonTypeReset) {
			DataOutputStream dos;
			String path = "Settings/settings.dat";
			Paths.get(path);
			File f = new File(path);
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				dos = new DataOutputStream(new FileOutputStream(f));
				dos.writeUTF("FFFFFF");
				dos.writeUTF("000000");
				dos.writeUTF("C3C3C3");
				dos.writeBoolean(false);
				dos.writeDouble(40);
				dos.writeBoolean(false);
				dos.writeUTF("Settings/Julien Marchal - Insight XIV.mp3");
			} catch (IOException e) {
				e.printStackTrace();
			}
			hCb.getSelectionModel().select(3);
			bCb.getSelectionModel().select(1);
			fCb.getSelectionModel().select(2);
			slider.setValue(40);
			hTf.setText("FFFFFF");
			bTf.setText("000000");
			fTf.setText("C3C3C3");
			MeMode.setSelected(false);
			mStringTf.setText("Settings/Julien Marchal - Insight XIV.mp3");
			this.showAndWait();
		}
	}

	public static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Sudokus");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
	}

	public void readPath(File file) {

	}

	public void getColorFromText(TextField feld) {
		String hexa = "";
		int aIndex = 0;
		if (feld.getText().length() < 6) {
			for (int i = feld.getText().length(); i < 6; ++i) {
				String hex = feld.getText();
				hex = hex + "0";
				feld.setText(hex);
			}
		} else if (feld.getText().length() > 6) {
			String hex = feld.getText().substring(0, 6);
			feld.setText(hex);
		} else if (feld.getText().length() == 6) {
			if (feld.getText().equalsIgnoreCase("000000")) {
				hexa = "000000";
				aIndex = 1;
			} else if (feld.getText().equalsIgnoreCase("C3C3C3")) {
				hexa = "C3C3C3";
				aIndex = 2;
			} else if (feld.getText().equalsIgnoreCase("FFFFFF")) {
				hexa = "FFFFFF";
				aIndex = 3;
			} else if (feld.getText().equalsIgnoreCase("00A2E8")) {
				hexa = "00A2E8";
				aIndex = 4;
			} else if (feld.getText().equalsIgnoreCase("ED1C24")) {
				hexa = "ED1C24";
				aIndex = 5;
			} else if (feld.getText().equalsIgnoreCase("FFF200")) {
				hexa = "FFF200";
				aIndex = 6;
			} else if (feld.getText().equalsIgnoreCase("22B14C")) {
				hexa = "22B14C";
				aIndex = 7;
			} else {
				hexa = feld.getText();
				aIndex = 0;
			}
			if (feld == hTf) {
				hS = hexa;
				hCb.getSelectionModel().select(aIndex);
			} else if (feld == bTf) {
				bS = hexa;
				bCb.getSelectionModel().select(aIndex);
			} else if (feld == fTf) {
				fS = hexa;
				fCb.getSelectionModel().select(aIndex);
			}
		}
	}

	public void selectTf(TextField tf, String newValue) {
		String hexwert = "";
		if (newValue.equals("Schwarz")) {
			hexwert = "000000";
		} else if (newValue.equals("Grau")) {
			hexwert = "C3C3C3";
		} else if (newValue.equals("Weiß")) {
			hexwert = "FFFFFF";
		} else if (newValue.equals("Blau")) {
			hexwert = "00A2E8";
		} else if (newValue.equals("Rot")) {
			hexwert = "ED1C24";
		} else if (newValue.equals("Gelb")) {
			hexwert = "FFF200";
		} else if (newValue.equals("Grün")) {
			hexwert = "22B14C";
		}
		if (tf == hTf) {
			hTf.setText(hexwert);
		} else if (tf == bTf) {
			bTf.setText(hexwert);
		} else if (tf == fTf) {
			fTf.setText(hexwert);
		}
	}

	public boolean validHexacode(String code) {
		for (int e = 0; e < code.length(); ++e) {
			for (int i = 0; i < notAllowed.length(); ++i) {
				if (notAllowed.substring(i, i + 1).equalsIgnoreCase(code.substring(e, e + 1))) {
					return false;
				}
			}
		}
		return true;
	}

	public void load() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hS = dis.readUTF().toUpperCase();
		bS = dis.readUTF().toUpperCase();
		fS = dis.readUTF().toUpperCase();
		tVal = dis.readBoolean();
		sVal = dis.readDouble();
		Memode = dis.readBoolean();
		mStringTf.setText(dis.readUTF());
		dis.close();
		hTf.setText(hS);
		bTf.setText(bS);
		fTf.setText(fS);
		getColorFromText(hTf);
		getColorFromText(bTf);
		getColorFromText(fTf);
		mute.setSelected(tVal);
		MeMode.setSelected(Memode);
	}
}
