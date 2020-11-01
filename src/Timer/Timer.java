package Timer;

import java.io.Serializable;
import java.util.UUID;

public class Timer implements Serializable {

    public UUID id;
    public int hr, min, sec, milli;
    public boolean isPaused = false;

    Timer(UUID id,int hr,int min,int sec,int milli) {

        this.id = id;
        this.hr = hr;
        this.min = min;
        this.sec = sec;
        this.milli = milli;
    }

    }
