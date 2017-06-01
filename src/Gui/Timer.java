package Gui;

import javafx.application.Platform;

public class Timer extends Thread {
	private boolean terminate;
	private int sec;
	private int min;
	private int hour;
	private String secS;
	private String minS;
	private String hourS;
	private ThefinalGame application;

	public Timer(ThefinalGame ap, int s, int m, int h) {
		this.application = ap;
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
	    				application.setTimerS("" + sec);
	    			} else {
	    				application.setTimerS("0" + sec);
	    			}
	    			if (minS.length() == 2) {
	    				application.setTimerM("" + min);
	    			} else {
	    				application.setTimerM("0" + min);
	    			}
	    			if (hourS.length() == 2) {
	    				application.setTimerH("" + hour);
	    			} else {
	    				application.setTimerH("0" + hour);
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
	private int getSec() {
		return sec;
	}

	/**
	 * @param sec
	 *            the sec to set
	 */
	private void setSec(int sec) {
		this.sec = sec;
	}

	/**
	 * @return the min
	 */
	private int getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	private void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the hour
	 */
	private int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	private void setHour(int hour) {
		this.hour = hour;
	}

}
