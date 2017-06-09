package Gui;

import javafx.application.Platform;

public class Timer extends Thread {
	
	private Game game;
	
	private boolean terminate;
	private int sec;
	private int min;
	private int hour;
	private String secS;
	private String minS;
	private String hourS;

	/**
	 * 	Starts a new selfmade Thread
	 * @param game which game it has to up-date 
	 * @param s	the seconds to start with
	 * @param m the minutes to start with
	 * @param h the hours to start with
	 */
	public Timer(Game game, int s, int m, int h) {
		this.game = game;
		setSec(s);
		setMin(m);
		setHour(h);
	}

	public void run() {

		while (terminate == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++sec;
			if (sec >= 60) {
				sec = 0;
				min++;
				if (min >= 60) {
					min = 0;
					hour++;
				}
			}
			secS = "" + sec;
			minS = "" + min;
			hourS = "" + hour;
			Platform.runLater(new Runnable() {
	            @Override public void run() {
	    			if (secS.length() == 2) {
	    				game.setTimerS("" + sec);
	    			} else {
	    				game.setTimerS("0" + sec);
	    			}
	    			if (minS.length() == 2) {
	    				game.setTimerM("" + min);
	    			} else {
	    				game.setTimerM("0" + min);
	    			}
	    			if (hourS.length() == 2) {
	    				game.setTimerH("" + hour);
	    			} else {
	    				game.setTimerH("0" + hour);
	    			}
	            }
	        });

		}
	}

	public void terminate() {
		terminate = true;
	}

	/**
	 * @return the sec
	 */
	public int getSec() {
		return sec;
	}

	/**
	 * @param sec
	 *            the sec to set
	 */
	public void setSec(int sec) {
		this.sec = sec;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

}
