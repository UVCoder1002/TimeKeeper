

public abstract class AlarmListener extends TimeListener {
    AlarmClock alarmClock;
    public abstract void fireAlarm(int alarmId);
}
