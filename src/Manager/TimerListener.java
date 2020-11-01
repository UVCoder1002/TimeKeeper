package Manager;

import Timer.Timer;

public abstract class TimerListener extends TimeListener {
    int hr;
    int min;
    int sec;
    int milli;
    Timer timer;
    public TimerListener(Timer timer) {
        this.timer=timer;

    }

}