package Model;

import java.util.Random;

public class Autogenerate {
	private int fertigesfeld[][] = new int[9][9];
	private int feld[][] = new int[9][9];
	private static int Staticid;
	private int id;
	private int ist;
	private int soll;
	private int x = 0;
	private int y = 0;

	public Autogenerate(int wieViele) {
		setId(Staticid);
		Staticid++;
		fuellen(wieViele);
	}

	public Autogenerate() {
		setId(Staticid);
		Staticid++;
	}

	/**
	 * @return the id
	 */
	private int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	private void setId(int id) {
		this.id = id;
	}

	public boolean reihe(int x, int y, int number) {
		for (int i = 0; i < 9; i++) {
			if (feld[i][y] == number) {
				return false;
			}
		}
		return true;
	}

	public boolean spalte(int x, int y, int number) {
		for (int i = 0; i < 9; i++) {
			if (feld[x][i] == number) {
				return false;
			}
		}
		return true;
	}

	public boolean notfinished() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (feld[j][i] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean backtracking() {
		Random random = new Random();
		int oldx = x;
		int oldy = y;
		if (notfinished()) {
			int wert = random.nextInt(8) + 1;

			for (int versuch = 0; versuch < 9; ++versuch) {
				if (dreierfeld(x, y, wert) && reihe(x, y, wert) == true && spalte(x, y, wert)) {
					feld[x][y] = wert;
					if (x + 1 >= 9) {
						x = 0;
						y++;
					} else {
						x++;
					}
					if (backtracking()) {
						return true;
					}
					// wenn nicht geklappt rueckgaengig
					x = oldx;
					y = oldy;
					feld[x][y] = 0;
				}
				// neuer wert
				if (wert + 1 > 9) {
					wert = 1;
				} else {
					wert++;
				}

			}
		} else {
			return true;
		}
		return false;
	}

	public boolean dreierfeld(int x, int y, int number) {
		int newx = 0;
		int newy = 0;
		if (x >= 0 && x <= 2) {
			newx = 0;
		}
		if (x >= 3 && x <= 5) {
			newx = 3;
		}
		if (x >= 6 && x <= 8) {
			newx = 6;
		}

		if (y >= 0 && y <= 2) {
			newy = 0;
		}
		if (y >= 3 && y <= 5) {
			newy = 3;
		}
		if (y >= 6 && y <= 8) {
			newy = 6;
		}
		for (int i = newy; i < newy + 3; i++) {
			for (int j = newx; j < newx + 3; j++) {
				if (feld[j][i] == number) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean minifeldNL(int x, int y) {
		int newx = 0;
		int newy = 0;
		if (x >= 0 && x <= 2) {
			newx = 0;
		}
		if (x >= 3 && x <= 5) {
			newx = 3;
		}
		if (x >= 6 && x < 8) {
			newx = 6;
		}
		if (y >= 0 && y <= 2) {
			newy = 0;
		}
		if (y >= 3 && y <= 5) {
			newy = 3;
		}
		if (y >= 6 && y < 8) {
			newy = 6;
		}
		for (int i = newy; i < newy + 2; i++) {
			for (int j = newx; j < newx + 2; j++) {
				if (feld[j][i] != 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void fuellen(int wieViele) {
		fertigesfeld = feld;
		backtracking();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				feld[i][j] = 0;
			}
		}
//		Random random = new Random();
//		int howmany1 = wieViele / 9;
//		int x = 0;
//		int y = 0;
//		while (soll != wieViele) {
//			x = random.nextInt(2);
//			y = random.nextInt(2);
//			if (feld[x][y] == 0) {
//				while (feld[x][y] == 0) {
//					x = random.nextInt(2);
//					y = random.nextInt(2);
//				}
//			} else {
//				if (minifeldNL(x, y)) {
//					feld[x][y] = 0;
//					soll++;
//				}
//			}
//		}
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
				if (feld[j][i] == 0) {
					System.out.print(" ");
				} else {
					System.out.print(feld[j][i]);
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
		System.out.println("Soll: " + soll);
		System.out.println("Ist: " + ist);
	}

	public static void main(String[] args) {
		Autogenerate ag = new Autogenerate();

		ag.fuellen(10);
		ag.print();
	}
}
