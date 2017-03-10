package Model;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Autogenerate {
	private int feld[][] = new int[9][9];
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

	public boolean reihe(int x, int y, int nummber) {
		for (int i = 0; i < 9; i++) {
			if (feld[i][y] == nummber) {
				imwegx = i;
				imwegy = y;
				return false;
			}
		}
		return true;
	}

	public boolean spalte(int x, int y, int nummber) {
		for (int i = 0; i < 9; i++) {
			if (feld[x][i] == nummber) {
				imwegx = x;
				imwegy = i;
				return false;
			}
		}
		return true;
	}

	// HIER WIRD DAS DREIER FELD GETESTET
	// Datt geht nicht so
	public boolean dreierfeld(int x, int y, int nummber) {
		if (y < 6) {
			if (y < 3) {
				if (x < 6) {
					if (x < 3) {
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {
								if (feld[j][i] == nummber) {
									return false;
								}
							}
						}
					} else {
						for (int i = 0; i < 3; i++) {
							for (int j = 3; j < 6; j++) {
								if (feld[j][i] == nummber) {
									return false;
								}
							}
						}
					}
				} else {
					for (int i = 0; i < 3; i++) {
						for (int j = 6; j < 9; j++) {
							if (feld[j][i] == nummber) {
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
								if (feld[j][i] == nummber) {
									return false;
								}
							}
						}
					} else {
						for (int i = 3; i < 6; i++) {
							for (int j = 3; j < 6; j++) {
								if (feld[j][i] == nummber) {
									return false;
								}
							}
						}
					}
				} else {
					for (int i = 3; i < 6; i++) {
						for (int j = 6; j < 9; j++) {
							if (feld[j][i] == nummber) {
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
							if (feld[j][i] == nummber) {
								return false;
							}
						}
					}
				} else {
					for (int i = 6; i < 9; i++) {
						for (int j = 3; j < 6; j++) {
							if (feld[j][i] == nummber) {
								return false;
							}
						}
					}
				}
			} else {
				for (int i = 6; i < 9; i++) {
					for (int j = 6; j < 9; j++) {
						if (feld[j][i] == nummber) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public boolean feldfollvuellbar(int xanf, int yanf, int xend, int yend) {
		for (; yanf < yend; yanf++) {
			for (; xanf < xend; xanf++) {
				if (feld[xanf][yanf] == 0) {
					for (int i = 1; i <= 9; i++) {
					}
				}
			}
		}
		return false;
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
			} else {
				//Backtracking
			}
		} else {
			//Backtracking
			Collections.shuffle(l, random);
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
			x = random.nextInt(9);
			y = random.nextInt(9);
			while (feld[x][y] != 0) {
				x = random.nextInt(9);
				y = random.nextInt(9);
			}
			wert = random.nextInt(9) + 1;
			if (reihe(x, y, wert) && spalte(x, y, wert)) {
//				if (dreierfeld(x, y, wert)) {
					feld[x][y] = wert;
					soll++;
					System.out.println(soll + ".: feld[" + x + "][" + y + "]" + wert);
//				}
					if(ist>=81) {
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
		Autogenerate ag = new Autogenerate(81);
		ag.print();
		System.out.println();
		System.out.println();
		System.out.println();
		//
		// Autogenerate agwithrows = new Autogenerate();
		// agwithrows.print();
	}
}
