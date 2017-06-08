package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import Gui.Game;
import Gui.Timer;

public class Start {
	Autogenerate ag;
	Timer timer;
	Game game;
	
	private int fertigesfeld[][] = new int[9][9];
	private int feld[][] = new int[9][9];
	private boolean[][] anfang = new boolean[9][9];
	private String id = "";

	// Ein nicht Fertiges Spiel abschliesen
	public Start(File source, Game game) {
		this.game = game;
		try {
			load(source, game);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ag = new Autogenerate(fertigesfeld, feld);
	}

	// Neues Spiel
	public Start(int schwierigkeitsgrad, Game game) {
		this.game = game;
		setId();
		ag = new Autogenerate(schwierigkeitsgrad);
		setFeld(ag.getFeld());
		setFertigesfeld(ag.getFertigesfeld());
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (fertigesfeld[j][i] != 0) {
					anfang[j][i] = true;
				}
			}
		}
		timer = new Timer(game, 0, 0, 0);
	}

	public void setId() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm");
		LocalDateTime localDatetime = LocalDateTime.now();
		id = dtf.format(localDatetime);
	}

	public String getId() {
		return id;
	}

	// Um in das Array die zahl einzufügen
	public boolean zug(int x, int y, int move) {
		if (ag.dreierfeld(x, y, move) && ag.reihe(x, y, move) && ag.spalte(x, y, move)) {
			return true;
		}
		return false;
	}

	public boolean full() {
		boolean fertig = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (fertigesfeld[j][i] == 0) {
					fertig = false;
				}
			}
		}
		return fertig;
	}

	public boolean finished() {
		boolean fertig = false;
		int wieFrichtig = 0;
		if (full()) {
			// if(ag.feldIsValid(fertigesfeld)) {
			// fertig = true;
			// } ag.dreierfeld(j, i, fertigesfeld[j][i]) && ag.reihe(j, i,
			// fertigesfeld[j][i]) && ag.spalte(j, i, fertigesfeld[j][i])
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (fertigesfeld[j][i] == ag.getFeld(j, i)) {
						wieFrichtig++;
					}
				}
			}
			if (wieFrichtig == 81) {
				fertig = true;
			} else {
				fertig = false;
			}
		}
		return fertig;
	}

	public void fertig() {
		if (finished()) {
			String path = "savedGames/" + getId() + "_Sudoku.dat";
			try {
				Files.delete(Paths.get(path));
			} catch (NoSuchFileException x) {
				System.err.format("%s: no such" + " file or directory%n", path);
			} catch (DirectoryNotEmptyException x) {
				System.err.format("%s not empty%n", path);
			} catch (IOException x) {
				// File permission problems are caught here.
				System.err.println(x);
			}
		}
	}

	// Lädt ein selbst ausgewältes file
	public void load(File source, Game game) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(source));
		try {
			for (int i = 0; i < 81; ++i) {
				int x = dis.readInt();
				int y = dis.readInt();
				int wert = dis.readInt();
				fertigesfeld[x][y] = wert;
			}
			for (int i = 0; i < 81; ++i) {
				int x = dis.readInt();
				int y = dis.readInt();
				int wert = dis.readInt();
				feld[x][y] = wert;
			}
			for (int i = 0; i < 81; ++i) {
				int x = dis.readInt();
				int y = dis.readInt();
				boolean wert = dis.readBoolean();
				anfang[x][y] = wert;
			}
			timer = new Timer(game, dis.readInt(), dis.readInt(), dis.readInt());
		} catch (EOFException exc) {
			dis.close();
		}
	}

	public boolean exist(File file) {
		for (int i = 1; i <= 10; ++i) {
			String pathex = "savedGames/" + getId() + "_Sudoku_" + i + ".dat";
			Paths.get(pathex);
			if (new File(pathex).exists()) {
				return true;
			}
		}
		return false;
	}

	// speichern aller Arrays
	public void save() throws IOException {
		feld = ag.getFeld();
		String path = "savedGames/" + getId() + "_Sudoku.dat";
		Paths.get(path);
		File f = new File(path);
		f.getParentFile().mkdirs();
		f.createNewFile();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeInt(fertigesfeld[j][i]);
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeInt(feld[j][i]);
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeBoolean(anfang[j][i]);
			}
		}
		timer.terminate();
		dos.writeInt(timer.getSec());
		dos.writeInt(timer.getMin());
		dos.writeInt(timer.getHour());
		timer = new Timer(game, timer.getSec(),timer.getMin(), timer.getHour());
		dos.close();
	}
	
	

	public void save(String path) throws IOException {
		feld = ag.getFeld();
		Paths.get(path);
		File f = new File(path);
		f.getParentFile().mkdirs();
		f.createNewFile();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeInt(fertigesfeld[j][i]);
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeInt(feld[j][i]);
			}
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeInt(j);
				dos.writeInt(i);
				dos.writeBoolean(anfang[j][i]);
			}
		}
		timer.terminate();
		dos.writeInt(timer.getSec());
		dos.writeInt(timer.getMin());
		dos.writeInt(timer.getHour());
		timer = new Timer(game, timer.getSec(),timer.getMin(), timer.getHour());
		dos.close();
	}

	
	/**
	 * @return the fertigesfeld
	 */
	public int getFertigesfeld(int x, int y) {
		return fertigesfeld[x][y];
	}

	/**
	 * @param fertigesfeld
	 *            the fertigesfeld to set
	 */
	public void setFertigesfeld(int x, int y, int wert) {
		fertigesfeld[x][y] = wert;
	}

	/**
	 * @return the fertigesfeld
	 */
	public int[][] getFertigesfeld() {
		return fertigesfeld;
	}

	/**
	 * @param fertigesfeld
	 *            the fertigesfeld to set
	 */
	private void setFertigesfeld(int[][] fertigesfeld) {
		this.fertigesfeld = fertigesfeld;
	}

	/**
	 * @return the feld
	 */
	public int[][] getFeld() {
		return feld;
	}

	/**
	 * @param feld
	 *            the feld to sete
	 */
	private void setFeld(int[][] feld) {
		this.feld = feld;
	}

	/**
	 * @return the feld
	 */
	public boolean getAnfang(int x, int y) {
		return anfang[x][y];
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	
}
