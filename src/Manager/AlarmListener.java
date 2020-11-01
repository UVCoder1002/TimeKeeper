package Manager;

import java.util.UUID;

public abstract class AlarmListener extends TimeListener {
    //for updating backend from timemanager
    AlarmClock alarmClock;
    String path;
    public AlarmListener(AlarmClock alarmClock) {
        this.alarmClock = alarmClock;
    }

    public abstract void fireAlarm(UUID alarmid,String path);


}
