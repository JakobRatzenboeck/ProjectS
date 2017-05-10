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
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Game {
	Autogenerate ag;
	private int fertigesfeld[][] = new int[9][9];
	private int feld[][] = new int[9][9];
	private boolean[][] anfang = new boolean[9][9];
	private String id = "";

	// Ein nicht Fertiges Spiel abschliesen
	public Game(File source) {
		try {
			load(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ag = new Autogenerate(fertigesfeld, feld);
	}

	// Neues Spiel
	public Game(int schwierigkeitsgrad) {
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
	}

	public void setId() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy k:H:m");
		LocalDate localDate = LocalDate.now();
		id = dtf.format(localDate);
	}

	public String getId() {
		return id;
	}

	// Um in das Array die zahl einzufügen
	public void zug(int x, int y, int move) {
		if (ag.dreierfeld(x, y, move) && ag.reihe(x, y, move) && ag.spalte(x, y, move)) {
			ag.setFertigesfeld(x, y, move);
		}
	}

	public boolean notfinished() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (fertigesfeld[j][i] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void fertig() {
		if (notfinished()) {
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

	// Wenn mann sich fertahn hat
	public void zugzurück(int x, int y) {
		if (getFertigesfeld(x, y) != 0) {
			setFertigesfeld(x, y, 0);
		}
	}

	// Lädt ein selbst ausgewältes file
	public void load(File source) throws IOException {
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
		} catch (EOFException exc) {
			dis.close();
		}
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
		dos.close();
	}

	public void print() {
		System.out.println(" ╔═══╦═══╦═══╦╦═══╦═══╦═══╦╦═══╦═══╦═══╗");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 3 || j == 6 || j == 9) {
					System.out.print(" ║║ ");
				} else {
					System.out.print(" ║ ");
				}
				if (fertigesfeld[j][i] == 0) {
					System.out.print(" ");
				} else {
					System.out.print(fertigesfeld[j][i]);
				}
			}
			System.out.println(" ║");
			if (i != 8) {
				System.out.println(" ╠═══╬═══╬═══╬╬═══╬═══╬═══╬╬═══╬═══╬═══╣");
				if (i == 2 || i == 5) {
					System.out.println(" ╠═══╬═══╬═══╬╬═══╬═══╬═══╬╬═══╬═══╬═══╣");
				}
			}
		}
		System.out.println(" ╚═══╩═══╩═══╩╩═══╩═══╩═══╩╩═══╩═══╩═══╝");
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
	private void setFertigesfeld(int x, int y, int wert) {
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
	private int[][] getFeld() {
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
	private boolean[][] getAnfang() {
		return anfang;
	}

	public static void main(String[] args) throws IOException {
		Game ag = new Game(40);
		ag.print();
		Game ag1 = new Game(9);
		try {
			ag1.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ag1.print();
		Game ag2 = new Game(new File("savedGames/21.04.2017_Sudoku.dat"));
		ag2.print();
	}
}
