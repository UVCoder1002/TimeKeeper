package Manager;

import java.util.UUID;

public abstract class AlarmListener extends TimeListener {
    public AlarmListener(AlarmClock alarmClock) {
        this.alarmClock = alarmClock;
    }

    AlarmClock alarmClock;
    public abstract void fireAlarm(UUID alarmid);
    public abstract void deleteListener(AlarmListener listener);
}
