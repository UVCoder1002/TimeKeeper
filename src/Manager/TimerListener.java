package Manager;

public abstract class TimerListener extends TimeListener {
    int hr;
    int min;
    int sec;
    int milli;

    protected TimerListener(int hr, int min, int sec, int milli){
        this.hr=hr;
        this.min=min;
        this.sec=sec;
        this.milli=milli;
    }
    public abstract void timeUpdated(int hr, int min, int sec);
    public abstract void timeUpdated(int milli);
}