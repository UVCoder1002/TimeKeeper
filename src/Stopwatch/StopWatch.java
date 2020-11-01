package Stopwatch;

import java.util.ArrayList;
import java.util.UUID;

public class StopWatch {
    public UUID id;
    public int hr,min,sec,milli;
    ArrayList<String> lap;
    public boolean isPaused=false;
    StopWatch(UUID id){
       lap=new ArrayList<>();
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
