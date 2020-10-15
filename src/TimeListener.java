
import java.util.EventListener;

public abstract class TimeListener implements EventListener {
    boolean ispaused=false;
    public abstract void timeUpdated(int hr, int min, int sec);
}
