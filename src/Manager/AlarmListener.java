package Manager;

import java.util.UUID;

public abstract class AlarmListener extends TimeListener {
    AlarmClock alarmClock;
    String path;
    public AlarmListener(AlarmClock alarmClock) {
        this.alarmClock = alarmClock;
    }

    public abstract void fireAlarm(UUID alarmid,String path);
    public abstract void deleteListener(AlarmListener listener);
}
