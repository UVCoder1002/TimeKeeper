import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentTime {
    int hr;
    int min;
    int sec;
    String ampm;
    SimpleDateFormat dateFormat=new SimpleDateFormat("s");
   String zone;
    Date currentDate=new Date();
    CurrentTime(String zone){
        this.zone=zone;
    }
    void setCurrentTime() {
        currentDate=new Date();
        dateFormat.setTimeZone(TimeZone.getTimeZone(zone));
        dateFormat.applyPattern("s");
        sec = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("m");
        min = Integer.parseInt(dateFormat.format(currentDate));
//        System.out.println(min);
        dateFormat.applyPattern("h");
        hr = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("a");
        ampm = dateFormat.format(currentDate);
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

    public static void main(String[] args) {
        CurrentTime time=new CurrentTime("Asia/Kolkata");
        time.setCurrentTime();
        System.out.println(time.getHr() + " : " + time.getMin() + " : " + time.getSec());

    }
}
