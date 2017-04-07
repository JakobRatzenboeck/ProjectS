package Model;

public class Start {
	Autogenerate ag;
	private int fertigesfeld[][];
	
	
	public Start(int schwierigkeitsgrad) {
		ag = new Autogenerate(schwierigkeitsgrad);
		fertigesfeld = ag.getFertigesfeld();
	}
	
	public void zug(int x, int y, int move) {
		if(ag.dreierfeld(x, y, move) && ag.reihe(x, y, move)&&ag.spalte(x, y, move)) {
			ag.setFertigesfeld(x, y, move);
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
	
	public static void main(String[] args) {
		Start ag = new Start(64);
		ag.print();
		Start ag1 = new Start(19);
		ag1.print();
		Start ag2 = new Start(42);
		ag2.print();
		Start ag3 = new Start(31);
		ag3.print();
		Start ag4 = new Start(9);
		ag4.print();
	}
}
