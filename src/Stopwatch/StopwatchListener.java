package Stopwatch;

import java.util.EventListener;

public abstract class StopwatchListener implements EventListener {
    int  hr;
    int min;
    int sec;
    StopwatchListener(){
        this.hr=0;
        this.min=0;
        this.sec=0;
    }
    public abstract void updateTime(int hr,int min,int sec);
}