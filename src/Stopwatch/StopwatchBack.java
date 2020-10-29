package Stopwatch;

import Manager.StopwatchListener;
import Manager.TimeManager;

import java.util.ArrayList;

public class StopwatchBack {
    ArrayList<StopWatch> stopwatchList;
    TimeManager timeManager;
    StopwatchListener listner;
    StopWatchListener stopWatchListener;
    public StopwatchBack(TimeManager timeManager){
         stopwatchList = new ArrayList<>();
         this.timeManager=timeManager;
    }

    void addStopwatch(StopWatch stopwatch){
      stopwatchList.add(stopwatch);

    }

    void startStopWatch(StopWatch stopWatch){
        addStopwatch(stopWatch);
        timeManager.addTimeListener(listner= new StopwatchListener() {
            @Override
            public void timeUpdated(int hr, int min, int sec,int milli) {
                stopWatch.updateValues(hr,min,sec,milli);
                stopWatchListener.updateui();
            }

            @Override
            public void timeUpdated(int hr, int min, int sec) {

            }

            @Override
            public void timeUpdated(int milli) {

            }
        });
    }
}
