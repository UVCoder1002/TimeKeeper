package Timer;

import java.util.EventListener;
public abstract class TimerListener implements EventListener {
    public int hr;
    public int min;
    public int sec;
    public TimerListener(int hr, int min, int sec) {
        this.hr = hr;
        this.min = min;
        this.sec = sec;
    }
    public abstract void updateTimer(int hr, int min, int sec);
}