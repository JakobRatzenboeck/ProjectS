package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Game {
	Autogenerate ag;
	private int fertigesfeld[][];
	private int feld[][];

	public Game(File source) {
		try {
			load(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Game(int schwierigkeitsgrad) {
		ag = new Autogenerate(schwierigkeitsgrad);
		fertigesfeld = ag.getFertigesfeld();
	}

	public void zug(int x, int y, int move) {
		if (ag.dreierfeld(x, y, move) && ag.reihe(x, y, move) && ag.spalte(x, y, move)) {
			ag.setFertigesfeld(x, y, move);
		}
	}

	public void load(File source) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(source));
		String filename = source.getName();
		String name = filename.substring(0, filename.length() - 4);
		DataInputStream disl = new DataInputStream(new FileInputStream(name + "_l.dat"));
		try {
			while (true) {
				fertigesfeld[dis.readByte()][dis.readByte()] = dis.readByte();
				fertigesfeld[disl.readByte()][disl.readByte()] = disl.readByte();
			}
		} catch (EOFException exc) {
			dis.close();
		}
	}

	public void save() throws IOException {
		feld = ag.getFeld();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(ag.getId() + ".dat")));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dos.writeByte(j);
				dos.writeByte(i);
				dos.writeByte(fertigesfeld[j][i]);
			}
		}
		DataOutputStream dosl = new DataOutputStream(new FileOutputStream(new File(ag.getId() + "_l.dat")));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				dosl.writeByte(j);
				dosl.writeByte(i);
				dosl.writeByte(feld[j][i]);
			}
		}
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
	public int[][] getFertigesfeld() {
		return fertigesfeld;
	}

	/**
	 * @param fertigesfeld the fertigesfeld to set
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
	 * @param feld the feld to set
	 */
	private void setFeld(int[][] feld) {
		this.feld = feld;
	}

	public static void main(String[] args) {
		Game ag = new Game(40);
		ag.print();
		Game ag1 = new Game(9);
		ag1.print();
	}
}
