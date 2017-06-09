package Controler;
import Gui.Game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameNUMHandler implements EventHandler<KeyEvent> {

	private final Game spiel;
	
	public GameNUMHandler(Game spiel) {
		this.spiel = spiel;
	}

	@Override
	public void handle(KeyEvent event) {
		if(event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.NUMPAD1) {
			spiel.selectButns(0);
		} else if(event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.NUMPAD2) {
			spiel.selectButns(1);
		} else if(event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.NUMPAD3) {
			spiel.selectButns(2);
		} else if(event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.NUMPAD4) {
			spiel.selectButns(3);
		} else if(event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.NUMPAD5) {
			spiel.selectButns(4);
		} else if(event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.NUMPAD6) {
			spiel.selectButns(5);
		} else if(event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.NUMPAD7) {
			spiel.selectButns(6);
		} else if(event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.NUMPAD8) {
			spiel.selectButns(7);
		} else if(event.getCode() == KeyCode.DIGIT9 || event.getCode() == KeyCode.NUMPAD9) {
			spiel.selectButns(8);
		} 
	}
}
