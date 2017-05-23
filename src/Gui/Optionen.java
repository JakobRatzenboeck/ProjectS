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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Optionen extends Dialog<ButtonType> {

	
	//Farbe
	String hNS = "";
	String bNS = "";
	String fNS = "";

	// Farbe in hexadezimal
	String hS = "";
	String bS = "";
	String fS = "";
	
	// Lautstaerke
	double sVal = 0;
	
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
	// Bilder für mute-Button
	Image muted = new Image("muted.png");
	Image unmuted = new Image("unmuted.png");
	Image unmuted0 = new Image("unmuted0.png");
	Image unmuted1 = new Image("unmuted1.png");
	Image unmuted2 = new Image("unmuted2.png");

	// fortgeschrittene sachen
	TextField hTf = new TextField();
	TextField bTf = new TextField();
	TextField fTf = new TextField();
	TextField lPath = new TextField();

	public Optionen(int height, int width) {
		try {
			load();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		www(hCb, hNS);
		www(bCb, bNS);
		www(fCb, fNS);

		//
		System.out.println(tVal);

		if (tVal) {
			slider.setValue(0);
			mute.setGraphic(new ImageView(muted));
		} else {
			slider.setValue(sVal);
		}
		mute.setGraphic(new ImageView(muted));
		hTf.setText(hS);
		bTf.setText(bS);
		fTf.setText(fS);

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
				if (newValue.equals("Schwarz")) {
					hNS = "Schwarz";
					hS = "000000";
					hTf.setText(hS);
				}
				if (newValue.equals("Grau")) {
					hNS = "Grau";
					hS = "C3C3C3";
					hTf.setText(hS);
				}
				if (newValue.equals("Weiß")) {
					hNS = "Weiß";
					hS = "FFFFFF";
					hTf.setText(hS);
				}
				if (newValue.equals("Blau")) {
					hNS = "Blau";
					hS = "00A2E8";
					hTf.setText(hS);
				}
				if (newValue.equals("Rot")) {
					hNS = "Rot";
					hS = "ED1C24";
					hTf.setText(hS);
				}
				if (newValue.equals("Gelb")) {
					hNS = "Gelb";
					hS = "FFF200";
					hTf.setText(hS);
				}
				if (newValue.equals("Gruen")) {
					hNS = "Grün";
					hS = "22B14C";
					hTf.setText(hS);
				}
			}
		});
		bCb.getSelectionModel().select(1);
		bCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if (newValue.equals("Schwarz")) {
					bNS = "Schwarz";
					bS = "000000";
					bTf.setText(bS);
				}
				if (newValue.equals("Grau")) {
					bNS = "Grau";
					bS = "C3C3C3";
					bTf.setText(bS);
				}
				if (newValue.equals("Weiß")) {
					bNS = "Weiß";
					bS = "FFFFFF";
					bTf.setText(bS);
				}
				if (newValue.equals("Blau")) {
					bNS = "Blau";
					bS = "00A2E8";
					bTf.setText(bS);
				}
				if (newValue.equals("Rot")) {
					bNS = "Rot";
					bS = "ED1C24";
					bTf.setText(bS);
				}
				if (newValue.equals("Gelb")) {
					bNS = "Gelb";
					bS = "FFF200";
					bTf.setText(bS);
				}
				if (newValue.equals("Grün")) {
					bNS = "Grün";
					bS = "22B14C";
					bTf.setText(bS);
				}
			}
		});
		fCb.getSelectionModel().select(2);
		fCb.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if (newValue.equals("Schwarz")) {
					fNS = "Schwarz";
					fS = "000000";
					fTf.setText(fS);
				}
				if (newValue.equals("Grau")) {
					fNS = "Grau";
					fS = "C3C3C3";
					fTf.setText(fS);
				}
				if (newValue.equals("Weiß")) {
					fNS = "Weiß";
					fS = "FFFFFF";
					fTf.setText(fS);
				}
				if (newValue.equals("Blau")) {
					fNS = "Blau";
					fS = "00A2E8";
					fTf.setText(fS);
				}
				if (newValue.equals("Rot")) {
					fNS = "Rot";
					fS = "ED1C24";
					fTf.setText(fS);
				}
				if (newValue.equals("Gelb")) {
					fNS = "Gelb";
					fS = "FFF200";
					fTf.setText(fS);
				}
				if (newValue.equals("Grün")) {
					fNS = "Grün";
					fS = "22B14C";
					fTf.setText(fS);
				}
			}
		});

		allgemeinP.add(h, 1, 1);
		allgemeinP.add(hCb, 2, 1);

		allgemeinP.add(b, 1, 2);
		allgemeinP.add(bCb, 2, 2);

		allgemeinP.add(f, 1, 3);
		allgemeinP.add(fCb, 2, 3);

		allgemeinP.add(m, 1, 4);
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
					mute.setGraphic(new ImageView(unmuted));
				} else if (slider.getValue() >= 50) {
					mute.setSelected(false);
					mute.setGraphic(new ImageView(unmuted2));
				} else if (slider.getValue() >= 25) {
					mute.setSelected(false);
					mute.setGraphic(new ImageView(unmuted1));
				} else if (slider.getValue() == 0) {
					mute.setSelected(false);
					mute.setGraphic(new ImageView(muted));
				} else {
					mute.setSelected(false);
					mute.setGraphic(new ImageView(unmuted0));
				}
			}
		});
		mute.setPrefHeight(10);
		mute.setPrefWidth(10);
		if (slider.getValue() >= 75) {
			mute.setSelected(false);
			mute.setGraphic(new ImageView(unmuted));
		} else if (slider.getValue() >= 50) {
			mute.setSelected(false);
			mute.setGraphic(new ImageView(unmuted2));
		} else if (slider.getValue() >= 25) {
			mute.setSelected(false);
			mute.setGraphic(new ImageView(unmuted1));
		} else if (slider.getValue() == 0) {
			mute.setSelected(false);
			mute.setGraphic(new ImageView(muted));
		} else {
			mute.setSelected(false);
			mute.setGraphic(new ImageView(unmuted0));
		}
		mute.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (mute.isSelected() && slider.getValue() != 0) {
					mute.setGraphic(new ImageView(muted));
					sVal = slider.getValue();
					slider.setValue(0);
				} else {
					slider.setValue(sVal);
					if (slider.getValue() >= 75) {
						mute.setGraphic(new ImageView(unmuted));
					} else if (slider.getValue() >= 50) {
						mute.setGraphic(new ImageView(unmuted2));
					} else if (slider.getValue() >= 25) {
						mute.setGraphic(new ImageView(unmuted1));
					} else if (slider.getValue() == 0) {
						mute.setGraphic(new ImageView(muted));
					} else {
						mute.setGraphic(new ImageView(unmuted0));
					}
				}
			}
		});
		allgemeinP.add(slider, 2, 4);
		allgemeinP.add(mute, 3, 4);

		fortgP.add(new Label("Hintergrund:	# "), 1, 1);
		fortgP.add(hTf, 2, 1);
		fortgP.add(new Label("Border:		# "), 1, 2);
		fortgP.add(bTf, 2, 2);
		fortgP.add(new Label("Felder:		# "), 1, 3);
		fortgP.add(fTf, 2, 3);

		tPane.setPrefHeight(height);
		tPane.setPrefWidth(width);
		this.setHeight(height);
		this.setWidth(width);
		this.getDialogPane().setContent(tPane);
		this.onCloseRequestProperty();
		this.getDialogPane().getButtonTypes().setAll(buttonTypeOk, buttonTypeReset, buttonTypeBack);
		Optional<ButtonType> result = this.showAndWait();
		if (result.get() == buttonTypeOk) {
			DataOutputStream dos;
			String path = "Settings/settings.dat";
			Paths.get(path);
			File f = new File(path);
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
				getColorFromText(hTf);
				getColorFromText(bTf);
				getColorFromText(fTf);
				dos = new DataOutputStream(new FileOutputStream(f));
				dos.writeUTF("" + hCb.getSelectionModel().getSelectedItem());
				dos.writeUTF("" + bCb.getSelectionModel().getSelectedItem());
				dos.writeUTF("" + fCb.getSelectionModel().getSelectedItem());
				dos.writeUTF(hTf.getText());
				dos.writeUTF(bTf.getText());
				dos.writeUTF(fTf.getText());
				if (slider.getValue() != 0) {
					dos.writeDouble(slider.getValue());
				} else {
					dos.writeDouble(sVal);
				}
				if (mute.getGraphic() == new ImageView(muted)) {
					dos.writeBoolean(true);
				} else {
					dos.writeBoolean(false);
				}
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
				dos.writeUTF("Weiß");
				dos.writeUTF("Schwarz");
				dos.writeUTF("Grau");
				dos.writeUTF("ffffff");
				dos.writeUTF("000000");
				dos.writeUTF("C3C3C3");
				dos.writeDouble(40);
				dos.writeBoolean(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hCb.getSelectionModel().select(3);
			bCb.getSelectionModel().select(1);
			fCb.getSelectionModel().select(2);
			slider.setValue(40);
			hTf.setText("ffffff");
			bTf.setText("000000");
			fTf.setText("C3C3C3");
			this.showAndWait();
		}
	}

	public void getColorFromText(TextField feld) {
		String farbe = "";
		String hexa = "";
		int aIndex = 0;
		if (feld.getText().length() == 6) {
			if (feld.getText().equalsIgnoreCase("000000")) {
				farbe = "Schwarz";
				hexa = "000000";
				aIndex = 1;
			} else if (feld.getText().equalsIgnoreCase("C3C3C3")) {
				farbe = "Grau";
				hexa = "C3C3C3";
				aIndex = 2;
			} else if (feld.getText().equalsIgnoreCase("FFFFFF")) {
				farbe = "Weiß";
				hexa = "FFFFFF";
				aIndex = 3;
			} else if (feld.getText().equalsIgnoreCase("00A2E8")) {
				farbe = "Blau";
				hexa = "00A2E8";
				aIndex = 4;
			} else if (feld.getText().equalsIgnoreCase("ED1C24")) {
				farbe = "Rot";
				hexa = "ED1C24";
				aIndex = 5;
			} else if (feld.getText().equalsIgnoreCase("FFF200")) {
				farbe = "Gelb";
				hexa = "FFF200";
				aIndex = 6;
			} else if (feld.getText().equalsIgnoreCase("22B14C")) {
				farbe = "Grün";
				hexa = "22B14C";
				aIndex = 7;
			} else {
				farbe = "";
				hexa = feld.getText();
				aIndex = 0;
			}
			if (feld == hTf) {
				hNS = farbe;
				hS = hexa;
				hCb.getSelectionModel().select(aIndex);
			} else if (feld == bTf) {
				bNS = farbe;
				bS = hexa;
				bCb.getSelectionModel().select(aIndex);
			} else if (feld == fTf) {
				fNS = farbe;
				fS = hexa;
				fCb.getSelectionModel().select(aIndex);
			}
		} else {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("Geht nicht");
			a.setHeaderText(feld.getText() + "muss genau 6 Zeichen lang sein!");
			a.setContentText("Zusätzlich dürfen nur A,B,C,D,E,F verwendet werden!");
			feld.requestFocus();
			a.showAndWait();
			this.showAndWait();
		}

	}

	public void www(ComboBox<String> setzen, String wert) {
		if (wert.equals("Schwarz")) {
			setzen.getSelectionModel().select(1);
		}
		if (wert.equals("Grau")) {
			setzen.getSelectionModel().select(2);
		}
		if (wert.equals("Weiß")) {
			setzen.getSelectionModel().select(3);
		}
		if (wert.equals("Blau")) {
			setzen.getSelectionModel().select(4);
		}
		if (wert.equals("Rot")) {
			setzen.getSelectionModel().select(5);
		}
		if (wert.equals("Gelb")) {
			setzen.getSelectionModel().select(6);
		}
		if (wert.equals("Gruen")) {
			setzen.getSelectionModel().select(7);
		}
	}

	public void load() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("Settings/settings.dat")));
		hNS = dis.readUTF();
		bNS = dis.readUTF();
		fNS = dis.readUTF();
		hS = dis.readUTF();
		bS = dis.readUTF();
		fS = dis.readUTF();
		sVal = dis.readDouble();
		tVal = dis.readBoolean();
		dis.close();
	}
}
