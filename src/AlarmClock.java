import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AlarmClock implements Serializable {
    int hr;
    int min;
    int sec;
    String ampm;
    ZonedDateTime dt;

    boolean isfired;
    AlarmClock(int hr,int min,int sec,int day,int month,int year,String zone){
        isfired=false;
        dt= ZonedDateTime.of(year,month,day,hr,min,sec,0, ZoneId.of(zone));
    }

    public ZonedDateTime getDt() {
        return dt;
    }

    public boolean isIsfired() {
        return isfired;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }
}
