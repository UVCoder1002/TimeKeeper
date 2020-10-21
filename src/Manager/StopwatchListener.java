package Manager;

public abstract class StopwatchListener extends TimeListener {
    int  hr;
    int min;
    int sec;
    int milli;
    protected StopwatchListener(){
        this.hr=0;
        this.min=0;
        this.sec=0;
        this.milli=0;
    }
    public abstract void timeUpdated(int hr, int min, int sec);
    public abstract void timeUpdated(int milli);


}