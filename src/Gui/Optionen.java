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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.GridPane;

public class Optionen extends Dialog<ButtonType> {

	String notAllowed = "HIJKLMNOPQRSTUVWXYZÜÖÄ!\"§$%&/()=?`²³{[]}\\´^°<>|,.-;:_#+*'~@€";
	// Farbe in hexadezimal
	String hS = "FFFFFF";
	String bS = "000000";
	String fS = "C3C3C3";

	// Lautstaerke
	double sVal = 40;

	// Stummschaltung
	boolean tVal = false;

	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.LEFT);
	ButtonType buttonTypeReset = new ButtonType("Reset");
	ButtonType buttonTypeBack = new ButtonType("Back", ButtonData.CANCEL_CLOSE);
	TabPane tPane = new TabPane();
	Tab allgemein = new Tab("Allgemein");
	GridPane allgemeinP = new GridPane();
	Tab fortg = new Tab("Fortgeschrittene Einstellungen");
	GridPane fortgP = new GridPane();

	private ObservableList<String> options = FXCollections.observableArrayList("", "Schwarz", "Grau", "Weiß", "Blau",
			"Rot", "Gelb", "Grün");

	Label h = new Label("Hintergrund: ");
	ComboBox<String> hCb = new ComboBox<String>(options);

	Label b = new Label("Border: ");
	ComboBox<String> bCb = new ComboBox<String>(options);

	Label f = new Label("Felder: ");
	ComboBox<String> fCb = new ComboBox<String>(options);

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

		//
		System.out.println(tVal);

		if (tVal) {
			slider.setValue(0);
			mute.setStyle("-fx-background-image: url('muted.png')");
		} else {
			slider.setValue(sVal);
		}
		mute.setStyle("-fx-background-image: url('muted.png')");

		System.out
				.println(hCb.getSelectionModel().getSelectedIndex() + " " + hCb.getSelectionModel().getSelectedItem());
		System.out
				.println(bCb.getSelectionModel().getSelectedIndex() + " " + bCb.getSelectionModel().getSelectedItem());
		System.out
				.println(fCb.getSelectionModel().getSelectedIndex() + " " + fCb.getSelectionModel().getSelectedItem());

		this.setTitle("Optionen");
		this.setHeight(800);
		this.setWidth(600);
		this.setResizable(false);
		tPane.getTabs().addAll(allgemein, fortg);
		allgemein.setContent(allgemeinP);
		allgemein.setClosable(false);
		fortg.setContent(fortgP);
		fortg.setClosable(false);
		allgemeinP.setAlignment(Pos.CENTER);
		fortgP.setAlignment(Pos.CENTER);

		h.setPrefHeight(20);
		b.setPrefHeight(20);
		f.setPrefHeight(20);
		m.setPrefHeight(20);

		hCb.getSelectionModel().select(3);
		hCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				selectTf(hTf, newValue);
			}
		});
		bCb.getSelectionModel().select(1);
		bCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				selectTf(bTf, newValue);
			}
		});
		fCb.getSelectionModel().select(2);
		fCb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
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
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
		slider.setPrefHeight(20);
		// Change Voloume
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (slider.getValue() >= 75) {
					mute.setSelected(false);
					mute.setStyle("-fx-background-image: url('unmuted.png')");
				} else if (slider.getValue() >= 50) {
					mute.setSelected(false);
					mute.setStyle("-fx-background-image: url('unmuted2.png')");
				} else if (slider.getValue() >= 25) {
					mute.setSelected(false);
					mute.setStyle("-fx-background-image: url('unmuted1.png')");
				} else if (slider.getValue() == 0) {
					mute.setSelected(false);
					mute.setStyle("-fx-background-image: url('muted.png')");
				} else {
					mute.setSelected(false);
					mute.setStyle("-fx-background-image: url('unmuted0.png')");
				}
			}
		});
		mute.setPrefHeight(25);
		mute.setPrefWidth(25);
		mute.setMinSize(25, 25);
		if (slider.getValue() >= 75) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('unmuted.png')");
		} else if (slider.getValue() >= 50) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('unmuted2.png')");
		} else if (slider.getValue() >= 25) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('unmuted1.png')");
		} else if (slider.getValue() == 0) {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('muted.png')");
		} else {
			mute.setSelected(false);
			mute.setStyle("-fx-background-image: url('unmuted0.png')");
		}
		mute.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (mute.isSelected() && slider.getValue() != 0) {
					mute.setStyle("-fx-background-image: url('muted.png')");
					sVal = slider.getValue();
					slider.setValue(0);
				} else {
					slider.setValue(sVal);
					if (slider.getValue() >= 75) {
						mute.setStyle("-fx-background-image: url('unmuted.png')");
					} else if (slider.getValue() >= 50) {
						mute.setStyle("-fx-background-image: url('unmuted2.png')");
					} else if (slider.getValue() >= 25) {
						mute.setStyle("-fx-background-image: url('unmuted1.png')");
					} else if (slider.getValue() == 0) {
						mute.setStyle("-fx-background-image: url('muted.png')");
					} else {
						mute.setStyle("-fx-background-image: url('unmuted0.png')");
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
		fortgP.add(m, 1, 4);
		fortgP.add(slider, 2, 4);
		fortgP.add(mute, 3, 4);

		tPane.setPrefHeight(height);
		tPane.setPrefWidth(width);
		this.setHeight(height);
		this.setWidth(width);
		this.getDialogPane().setContent(tPane);
		this.onCloseRequestProperty();
		this.getDialogPane().getButtonTypes().setAll(buttonTypeOk, buttonTypeReset, buttonTypeBack);
		Optional<ButtonType> result = this.showAndWait();
		if (result.get() == buttonTypeOk && getColorFromText(hTf) && getColorFromText(bTf) && getColorFromText(fTf)) {
			DataOutputStream dos;
			String path = "Settings/settings.dat";
			Paths.get(path);
			File f = new File(path);
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				dos = new DataOutputStream(new FileOutputStream(f));
				dos.writeUTF(hTf.getText().toUpperCase());
				dos.writeUTF(bTf.getText().toUpperCase());
				dos.writeUTF(fTf.getText().toUpperCase());
				if (slider.getValue() != 0) {
					dos.writeDouble(slider.getValue());
				} else {
					dos.writeDouble(sVal);
				}
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
				dos.writeDouble(40);
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
			this.showAndWait();
		} else {
			if (getColorFromText(hTf) == false) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(hTf.getText() + " dürfen nur A,B,C,D,E,F beinhalten!");
				a.setContentText("");
				hTf.requestFocus();
				a.show();
			} else if (getColorFromText(bTf) == false) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(bTf.getText() + " dürfen nur A,B,C,D,E,F beinhalten!");
				a.setContentText("");
				bTf.requestFocus();
				a.show();
			} else if (getColorFromText(fTf) == false) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Geht nicht");
				a.setHeaderText(fTf.getText() + " dürfen nur A,B,C,D,E,F beinhalten!");
				a.setContentText("");
				fTf.requestFocus();
				a.show();
			}
		}
	}

	public boolean getColorFromText(TextField feld) {
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
		if (validHexacode(feld.getText()) == false) {
			return false;
		}
		return true;
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
					System.out.println(code);
					return false;
				}
			}
		}
		return true;
	}

	public void load() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hS = dis.readUTF();
		bS = dis.readUTF();
		fS = dis.readUTF();
		sVal = dis.readDouble();
		dis.close();
		hTf.setText(hS);
		bTf.setText(bS);
		fTf.setText(fS);
		getColorFromText(hTf);
		getColorFromText(bTf);
		getColorFromText(fTf);
	}
}
