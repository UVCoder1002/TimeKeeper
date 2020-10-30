package Timer;

import java.util.ArrayList;
import java.util.UUID;

public class Timer {
    public UUID id;
    public int hr, min, sec, milli;
    ArrayList<String> lap;
    public boolean isPaused = false;

    Timer(UUID id,int hr,int min,int sec,int milli) {
        lap = new ArrayList<>();
        this.id = id;
        this.hr = hr;
        this.min = min;
        this.sec = sec;
        this.milli = milli;

    }
