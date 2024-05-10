package view;

import javax.swing.*;
import java.awt.*;

public class CountdownTimer {

    private int timeRemaining;
    private JLabel countdownLabel;
    private Test test;
    private boolean running ;

    public CountdownTimer(int timeInSeconds, JLabel countdownLabel, Test test, Boolean isRunning) {
        this.timeRemaining = timeInSeconds;
        this.countdownLabel = countdownLabel;
        this.test = test;
        this.running= isRunning;
    }

    
    public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
	}


	public void stopTest() {
        running = false;  // Stop the timer
        if (timeRemaining <= 0) {
            test.endTest();
        }
    }
	
    public int getTimeRemaining() {
		return timeRemaining;
	}


	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}


	public void start() {
        new Thread(() -> {
            while (timeRemaining >= 0 && running) {  // Check if it should be running
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;

                String countdownText = String.format("%02d:%02d", minutes, seconds);
                SwingUtilities.invokeLater(() -> countdownLabel.setText(countdownText));

                timeRemaining--;
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopTest();
        }).start();
    }
}