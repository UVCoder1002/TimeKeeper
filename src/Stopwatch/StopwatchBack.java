package Stopwatch;

import Manager.StopwatchListener;
import Manager.TimeManager;

import java.util.ArrayList;
import java.util.UUID;

public class StopwatchBack {
    ArrayList<StopWatch> stopwatchList;
    TimeManager timeManager;
    StopwatchListener listner;
    StopWatchListener stopWatchListener;
    StopwatchBack(TimeManager timeManager){
         stopwatchList = new ArrayList<>();
         this.timeManager=timeManager;
    }

    void addStopwatch(StopWatch stopWatch){
      stopwatchList.add(stopWatch);

    }

    void startStopWatch(StopWatch stopWatch){
        addStopwatch(stopWatch);
        timeManager.addTimeListener(listner= new StopwatchListener(stopWatch) {
            @Override
            public void timeUpdated(int hr, int min, int sec,int milli) {
                stopWatchListener.updateui(stopWatch);
            }
        });
    }
    void PressedPause(UUID id){
//        stopwatchList.is
    }
}
