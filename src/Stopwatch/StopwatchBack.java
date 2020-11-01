package Stopwatch;

import Manager.StopwatchListener;
import Manager.TimeManager;

import java.util.ArrayList;
import java.util.Iterator;

public class StopwatchBack {
    ArrayList<StopWatch> stopwatchList;
    ArrayList<String> laps;
    TimeManager timeManager;
    StopwatchListener listner;
    StopWatchListener stopWatchListener;
    public StopwatchBack(TimeManager timeManager){
        laps=new ArrayList<>();
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
            public void timeUpdated() {
                stopWatchListener.updateUI(stopWatch);
            }
        });

    }
    void PressedPause(StopWatch stopWatch){
        if(stopWatch.isPaused){
            stopWatch.isPaused=false;
        }
        else
        {
            stopWatch.isPaused=true;
        }
//
    }
    //After pressing delete removing listener from time manager using id of the object
    void PressedDelete(StopWatch stopWatch){
        timeManager.removeListener(stopWatch.id);
        stopwatchList.remove(stopWatch);
    }
    StringBuilder AddLap(StopWatch stopWatch){
        StringBuilder laps=new StringBuilder();
        stopWatch.lap.add(stopWatch.hr+":"+stopWatch.min+":"+stopWatch.sec+":"+stopWatch.milli);
        Iterator<StopWatch> stopWatchIterator=stopwatchList.iterator();
        int OuterCount=1,innerCount;
        while(stopWatchIterator.hasNext()){
            innerCount=0;
            StopWatch stop=stopWatchIterator.next();
            Iterator<String> stopWatchLapiterator =stop.lap.iterator();
            while(stopWatchLapiterator.hasNext()) {
                innerCount++;
                laps.append("\n"+OuterCount+"."+innerCount+"."+stopWatchLapiterator.next());
            }
            OuterCount++;
        }
        return laps;
    }

}
