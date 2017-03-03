import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Autogenerate {
	private int feld[][] = new int[9][9];
	private static int id;
	private int ist;
	private int soll;

	public Autogenerate(int wieViele) {
		fuellen(wieViele);
	}
	//
	// public Autogenerate() {
	// reihenfuellen(0, 0);
	// }

	public boolean reiheUSpalte(int x, int y, int nummber) {
		for (int i = 0; i < 9; i++) {
			if (feld[i][y] == nummber) {
				return false;
			}
		}
		for (int i = 0; i < 9; i++) {
			if (feld[x][i] == nummber) {
				return false;
			}
		}
		return true;
	}

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

	public boolean reihenfuellen(int x, int y) {
		if (y == 8 && x == 9) { // letzte zelle befuellt
			return true;
		}

		if (x == 9) {
			y++;
			x = 0;
		}

		Random random = new Random();

		List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Collections.shuffle(l, random);

		int zaehler = 0;
		int zaehlenbis = 500;
		int wert = 0;

		wert = random.nextInt(9 + 1);
		if (reiheUSpalte(x, y, wert)) {
			if (dreierfeld(x, y, wert)) {
				feld[x][y] = wert;
				soll++;
				System.out.print(wert);
			} else {
				x--;
			}
		} else {
			x--;
			zaehler++;
			if (x == 7 && zaehler > zaehlenbis) {
				System.out.println();
				System.out.println();
				zaehler = 0;
				for (int i = 0; i < 9; i++) {
					feld[i][y] = 0;
				}
				x = 0;
			}
		}

		ist++;
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
			if (reiheUSpalte(x, y, wert)) {
				if (dreierfeld(x, y, wert)) {
					feld[x][y] = wert;
					soll++;
					System.out.println(soll + ".: feld[" + x + "][" + y + "]" + wert);
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
		Autogenerate ag = new Autogenerate(50);
		ag.print();
		//
		// Autogenerate agwithrows = new Autogenerate();
		// agwithrows.print();
	}
}
