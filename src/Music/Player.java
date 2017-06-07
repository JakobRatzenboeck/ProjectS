package Music;

import java.io.File;

import Gui.Game;
import Gui.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {

	private static String path;
	private static MediaPlayer mediaPlayer;

	public Player(String path, Game game) {
		if (new File(path).exists()) {
			Player.path = path; // "src/Julien Marchal - Insight XIV.mp3";
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Unzulässiger Pfad");
			a.setHeaderText(path + " ist ein unzuläsiger Pfad");
			a.setContentText("Bitte kontrolierren sie die Richtigkeit des Pfades");
			a.showAndWait();
			game.getStage().close();
			new Main(400, 400);
		}
	}

	public static void sound(double volume) {
		Media media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		changeVolume(volume);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				mediaPlayer.play();
			}
		});
	}

	public static void changeVolume(double volume) {
		if (volume >= 0 && volume <= 1) {
			mediaPlayer.setVolume(volume);
		}
	}

	public static void close() {
		mediaPlayer.dispose();
	}
}
