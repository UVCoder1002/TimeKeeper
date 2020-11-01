package Manager;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class AlarmClock implements Serializable {

    ZonedDateTime dt;
    UUID id;
    String path;
    boolean isfired;

    AlarmClock(int year,int month,int day,int hr,int min,int sec,String zone,UUID uni, String path){
        isfired=false;
        id=uni;
        dt= ZonedDateTime.of(year,month,day,hr,min,sec,0, ZoneId.of(zone));
        this.path = path;
    }

    public ZonedDateTime getDt() {
        return dt;
    }

    public boolean isIsfired() {
        return isfired;
    }
}
