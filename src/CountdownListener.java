public abstract class CountdownListener extends TimeListener {
    public int hr;
    public int min;
    public int sec;
    boolean ispaused=false;

    public CountdownListener(int hr, int min, int sec) {
        this.hr = hr;
        this.min = min;
        this.sec = sec;

    }
    public abstract void timeUpdated(int hr, int min, int sec);
}
