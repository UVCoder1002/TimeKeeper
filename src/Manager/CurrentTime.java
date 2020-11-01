package Manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CurrentTime {
    int hr;
    int min;
    int sec;
    int mon;
    int yr;
    int day;

    String ampm;
    SimpleDateFormat dateFormat;
   String zone;
    Date currentDate;
    CurrentTime(String zone){

        this.zone=zone;
    }
    void setCurrentTime() {
       currentDate=new Date();
        dateFormat =new SimpleDateFormat("s", Locale.getDefault());

        dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        dateFormat.applyPattern("s");
        sec = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("m");
        min = Integer.parseInt(dateFormat.format(currentDate));

        dateFormat.applyPattern("H");
        hr = Integer.parseInt(dateFormat.format(currentDate));


        dateFormat.applyPattern("a");
        ampm = dateFormat.format(currentDate);

        dateFormat.applyPattern("M");
        mon = Integer.parseInt(dateFormat.format(currentDate));

        dateFormat.applyPattern("y");
        yr = Integer.parseInt(dateFormat.format(currentDate));

        dateFormat.applyPattern("d");
        day = Integer.parseInt(dateFormat.format(currentDate));
    }

    public int getHr() {
        return hr;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public String getAmpm() {
        return ampm;
    }

    public String getZone() {
        return zone;
    }

//    public static void main(String[] args) {
//        CurrentTime time=new CurrentTime("Asia/Kolkata");
//        time.setCurrentTime();
//        System.out.println(time.getHr() + " : " + time.getMin() + " : " + time.getSec());
//
//    }
}
