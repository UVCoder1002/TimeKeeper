import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentTime {
    SimpleDateFormat dateFormat=new SimpleDateFormat("s");
   String zone;
    Date currentDate=new Date();
    CurrentTime(String zone){
        this.zone=zone;
    }
    void setCurrentTime(int hr,int min,int sec,String ampm) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        dateFormat.applyPattern("s");
        sec = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("m");
        min = Integer.parseInt(dateFormat.format(currentDate));
        System.out.println(min);
        dateFormat.applyPattern("h");
        hr = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("a");
        ampm = dateFormat.format(currentDate);
    }
}
