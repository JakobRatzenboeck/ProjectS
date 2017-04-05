package Model;

public class Start {
	Autogenerate ag;
	
	public Start(int schwierigkeitsgrad) {
		ag = new Autogenerate(schwierigkeitsgrad);
	}
	
	public void zug(int x, int y, int move) {
		if(ag.dreierfeld(x, y, move) && ag.reihe(x, y, move)&&ag.spalte(x, y, move)) {
			ag.setFertigesfeld(x, y, move);
		}
	}
}
