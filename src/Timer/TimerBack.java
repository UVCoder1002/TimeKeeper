package Timer;

import Manager.CountdownListener;
import Manager.TimeManager;

import java.util.ArrayList;

public class TimerBack {
    ArrayList<Timer> timerList;
    TimeManager timeManager;
   CountdownListener listner;
    TimerBackEndListener timerBackEndListener;
    public TimerBack(TimeManager timeManager){
        timerList = new ArrayList<>();
        this.timeManager=timeManager;
    }
    void addTimer(Timer timer){
        timerList.add(timer);

    }
    void startStopWatch(Timer timer){
        addTimer(timer);
        timeManager.addTimeListener(listner= new CountdownListener(timer) {
            @Override
            public void timeUpdated(int hr, int min, int sec) {

            }

            @Override
            public void timeUpdated(int hr, int min, int sec,int milli) {
               timerBackEndListener.updateui(timer);
            }
        });

    }
    void PressedPause(Timer timer){
        if(timer.isPaused){
            timer.isPaused=false;
        }
        else
        {
            timer.isPaused=true;
        }
//
    }
    //After pressing delete removing listener from time manager using id of the object
    void PressedDelete(Timer timer){
        timeManager.removeListener(timer.id);
        timerList.remove(timer);
    }
}
