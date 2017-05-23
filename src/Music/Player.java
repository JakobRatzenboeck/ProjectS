package Music;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class Player{
	
	private static String path;
	private static MediaPlayer mediaPlayer;
	
	public Player(String path) {
		if(new File(path).exists()) {
		Player.path = path; // "src/Julien Marchal - Insight XIV.mp3";
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Unzulässiger Pfad");
			a.setHeaderText(path +" ist ein unzulaesiger Pfad");
			a.setContentText("Bitte kontrolierren sie die Richtigkeit des Pfades");
		}
	}

	public static void sound() {
		Media media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
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
		if(mediaPlayer.getStatus() == Status.PLAYING) {
			close();
		}
		mediaPlayer.setVolume(volume);
		sound();
	}
	
	public static void close() {
		mediaPlayer.dispose();
	}
}
