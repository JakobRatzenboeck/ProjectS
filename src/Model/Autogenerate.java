package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Autogenerate {
	private int feld[][] = new int[9][9];
	private int falsch[][] = new int[9][3];
	private static int Staticid;
	private int id;
	private int ist;
	private int soll;
	private int imwegx;
	private int imwegy;
	private int x;
	private int y;

	public Autogenerate(int wieViele) {
		setId(Staticid);
		Staticid++;
		fuellen(wieViele);
	}

	public Autogenerate() {
		setId(Staticid);
		Staticid++;
		reihenfuellen();
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
				imwegx = i;
				imwegy = y;
				return false;
			}
		}
		return true;
	}

	public boolean spalte(int x, int y, int number) {
		for (int i = 0; i < 9; i++) {
			if (feld[x][i] == number) {
				imwegx = x;
				imwegy = i;
				return false;
			}
		}
		return true;
	}

	public boolean notfinished() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean backtracking() {
		Random random = new Random();
		int wert = 0;
		if (notfinished()) {
			wert = random.nextInt(8) + 1;
		}
		// if (grid is empty) {
		// assign the empty grid with values (i)
		// if (no numbers exists in same rows & same columns same as (i) & 3x3
		// square (i) is currently in)
		// fill in the number
		// if (numbers exists in same rows | same columns same as (i) | 3x3
		// square (i) is currently in)
		// discard (i) and repick other values (i++)
		// }
		// else {
		// while (nx < 9) {
		// Proceed to next row grid(nx++, ny)
		// if (nx equals 9) {
		// reset nx = 1
		// proceed to next column grid(nx,ny++)
		// if (ny equals 9) {
		// print solution
		// }
		// }
		// }
		// }
		return true;
	}

	public boolean dreierfeld(int x, int y, int number) {
		if (y < 6) {
			if (y < 3) {
				if (x < 6) {
					if (x < 3) {
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {
								if (feld[j][i] == number) {
									return false;
								}
							}
						}
					} else {
						for (int i = 0; i < 3; i++) {
							for (int j = 3; j < 6; j++) {
								if (feld[j][i] == number) {
									return false;
								}
							}
						}
					}
				} else {
					for (int i = 0; i < 3; i++) {
						for (int j = 6; j < 9; j++) {
							if (feld[j][i] == number) {
								return false;
							}
						}
					}
				}
			} else {
				if (x < 6) {
					if (x < 3) {
						for (int i = 3; i < 6; i++) {
							for (int j = 0; j < 3; j++) {
								if (feld[j][i] == number) {
									return false;
								}
							}
						}
					} else {
						for (int i = 3; i < 6; i++) {
							for (int j = 3; j < 6; j++) {
								if (feld[j][i] == number) {
									return false;
								}
							}
						}
					}
				} else {
					for (int i = 3; i < 6; i++) {
						for (int j = 6; j < 9; j++) {
							if (feld[j][i] == number) {
								return false;
							}
						}
					}
				}
			}
		} else {
			if (x < 6) {
				if (x < 3) {
					for (int i = 6; i < 9; i++) {
						for (int j = 0; j < 3; j++) {
							if (feld[j][i] == number) {
								return false;
							}
						}
					}
				} else {
					for (int i = 6; i < 9; i++) {
						for (int j = 3; j < 6; j++) {
							if (feld[j][i] == number) {
								return false;
							}
						}
					}
				}
			} else {
				for (int i = 6; i < 9; i++) {
					for (int j = 6; j < 9; j++) {
						if (feld[j][i] == number) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public int[][] dreierfeld(int x, int y) {
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
				if (feld[j][i] == feld[y][x]) {
					if (i != y && j != x) {
						for (int c = 0; c < 9; c++) {
							if (falsch[c][0] != feld[x][y]) {
								falsch[i][0] = feld[x][y];
								falsch[i][1] = x;
								falsch[i][2] = y;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i <= y; i++) {
			for (int j = newx; j < newx + 2; j++) {
				if (feld[j][i] == feld[y][x]) {
					if (i != y && j != x) {

					}
				}
			}
		}
		return falsch;
	}

	public boolean reihenfuellen() {
		if (y == 8 && x == 9) { // letzte zelle befuellt
			return true;
		}

		if (x == 9) {
			y++;
			x = 0;
		}

		Random random = new Random();

		List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		if (x == 0) {
			Collections.shuffle(l, random);
		}

		if (dreierfeld(x, y, l.get(x))) {
			if (spalte(x, y, l.get(x))) {
				if (reihe(x, y, l.get(x))) {
					feld[x][y] = l.get(x);
					x++;
				} else {
					Collections.shuffle(l, random);
				}
			} else

			{
				// Backtracking
			}
		} else {
			// Backtracking
			Collections.shuffle(l, random);
		}
		for (int i = 0; i < 9; i++) {
			if (dreierfeld(i, y) == null) {

			}
		}
		reihenfuellen();
		return false;
	}

	public void fuellen(int wieViele) {
		Random random = new Random();
		int x = 0;
		int y = 0;
		int wert = 0;
		while (soll != wieViele) {
			x = random.nextInt(8) + 1;
			y = random.nextInt(8) + 1;
			while (feld[x][y] != 0) {
				x = random.nextInt(8) + 1;
				y = random.nextInt(8) + 1;
			}
			wert = random.nextInt(9) + 1;
			if (reihe(x, y, wert) && spalte(x, y, wert)) {
				if (dreierfeld(x, y, wert)) {
					feld[x][y] = wert;
					soll++;
					System.out.println(soll + ".: feld[" + x + "][" + y + "]" + wert);
				}
				if (ist >= 81) {
					print();
				}
			}
			ist++;
		}
	}

	public void print() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (feld[j][i] == 0) {
					System.out.println("X: " + j + ", Y: " + i);
				}
			}
		}
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
		// Autogenerate ag = new Autogenerate(54);
		// ag.print();
		// System.out.println();
		// System.out.println();
		// System.out.println();
		//
		// Autogenerate agwithrows = new Autogenerate();
		// agwithrows.print();
		Random random = new Random();
		while (true) {
			System.out.println(random.nextInt(9));
		}
	}
}
