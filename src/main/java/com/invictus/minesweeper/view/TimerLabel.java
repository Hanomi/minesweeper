package com.invictus.minesweeper.view;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
// just try timer in first time

class TimerLabel extends JLabel { // label with stopwatch
    private Timer timer;

    TimerLabel() {
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 0, 1000);
    }

    void stopTimer() {
        timer.cancel();
    }

    void resetTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(getTimerTask(), 0, 1000);
    }

    private TimerTask getTimerTask() {
        return new TimerTask() {
            AtomicInteger time = new AtomicInteger(0);
            Runnable refresher = () -> TimerLabel.this.setText(String.format("%02d:%02d", time.get() / 60, time.get() % 60));

            public void run() {
                time.incrementAndGet();
                SwingUtilities.invokeLater(refresher);
            }
        };
    }
}
