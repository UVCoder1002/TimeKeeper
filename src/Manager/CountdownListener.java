package Manager;

import Timer.Timer;

public abstract class CountdownListener extends TimeListener {
    Timer timer;
    public int hr;
    public int min;
    public int sec;
    boolean ispaused=false;


    public CountdownListener(Timer timer) {
        this.hr = timer.hr;
        this.min = timer.min;
        this.sec = timer.sec;

    }
    public abstract void timeUpdated(int hr, int min, int sec);
}
