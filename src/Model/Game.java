package Model;

import java.beans.FeatureDescriptor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

public class Game {
	Autogenerate ag;
	private int fertigesfeld[][] = new int[9][9];
	private int feld[][] = new int[9][9];
	private boolean[][] anfang = new boolean[9][9];
	private static int Staticid = 0;
	private int id = 0;

	// Ein nicht Fertiges Spiel abschliesen
	public Game(File source) {
		id = Staticid + 1;
		setId(id);
		try {
			load(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ag = new Autogenerate(fertigesfeld, feld);
		setFertigesfeld(fertigesfeld);
		setFeld(feld);
	}

	// Neues Spiel
	public Game(int schwierigkeitsgrad) {
		id = Staticid + 1;
		setId(id);
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

	/*
	 * SetId
	 */
	private void setId(int staticid) {
		this.id = staticid;

	}

	/*
	 * GetId
	 */
	public int getId() {
		return id;
	}

	// Um in das Array die zahl einzufügen
	public void zug(int x, int y, int move) {
		if (ag.dreierfeld(x, y, move) && ag.reihe(x, y, move) && ag.spalte(x, y, move)) {
			ag.setFertigesfeld(x, y, move);
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
			for (int i = 0; i > 81; ++i) {
				fertigesfeld[dis.readInt()][dis.readInt()] = dis.readInt();
			}
			for (int i = 0; i > 81; ++i) {
				feld[dis.readInt()][dis.readInt()] = dis.readInt();
			}
			for (int i = 0; i > 81; ++i) {
				anfang[dis.readInt()][dis.readInt()] = dis.readBoolean();
			}
		} catch (EOFException exc) {
			dis.close();
		}
	}

	// speichern beider Arrays
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

	public static void main(String[] args) throws IOException {
		Game ag = new Game(40);
		ag.print();
		Game ag1 = new Game(9);
		try {
			ag1.save();
			System.out.println("Saved");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ag1.print();
		System.out.println(new Game(new File("savedGames/1_Sudoku.dat")));
		Game ag2 = new Game(new File("savedGames/1_Sudoku.dat"));
		ag2.load(new File("savedGames/1_Sudoku.dat"));
		ag2.print();
	}
}
