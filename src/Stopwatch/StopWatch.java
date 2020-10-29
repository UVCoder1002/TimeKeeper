package Stopwatch;

import java.util.UUID;

public class StopWatch {
    UUID id;
    public int hr,min,sec,milli;
    StopWatch(UUID id){
      this.id=id;
      this.hr = 0;
      this.min = 0;
      this.sec = 0;
      this.milli = 0;
    }
    void updateValues(int hr,int min,int sec,int milli){
        this.hr=hr;
        this.min=min;
        this.sec=sec;
        this.milli=milli;

    }

}
