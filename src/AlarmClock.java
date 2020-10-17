import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class AlarmClock implements Serializable {

    ZonedDateTime dt;
    UUID id;
    boolean isfired;

    AlarmClock(int year,int month,int day,int hr,int min,int sec,String zone,UUID uni){
        isfired=false;
        id=uni;
        dt= ZonedDateTime.of(year,month,day,hr,min,sec,0, ZoneId.of(zone));
    }

    public ZonedDateTime getDt() {
        return dt;
    }

    public boolean isIsfired() {
        return isfired;
    }
}
