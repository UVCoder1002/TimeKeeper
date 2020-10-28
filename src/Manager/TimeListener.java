package Manager;

import java.util.EventListener;

public abstract class TimeListener implements EventListener {
    public Boolean ispaused=false;
    public abstract void timeUpdated(int hr, int min, int sec);

    public abstract void timeUpdated(int milli);
}
