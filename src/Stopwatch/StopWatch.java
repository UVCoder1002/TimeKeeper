package Stopwatch;

import java.util.UUID;

public class StopWatch {
    UUID id;
    int hr,min,sec,milli;
    StopWatch(UUID id){
      this.id=id;
    }
    void updateValues(int hr,int min,int sec,int milli){
        this.hr=hr;
        this.min=min;
        this.sec=sec;
        this.milli=milli;

    }

}
