package Timer;

import Manager.TimeManager;
import Manager.TimerListener;

import java.io.*;
import java.util.ArrayList;

public class TimerBack {
    ArrayList<Timer> timerList;
    TimeManager timeManager;
     TimerListener listner;
    TimerBackEndListener timerBackEndListener;
    FileInputStream fis;
    ObjectInputStream ois;
    FileOutputStream fos;
    ObjectOutputStream oos;

    public TimerBack(TimeManager timeManager) {
        timerList = new ArrayList<>();
        this.timeManager=timeManager;
        try {
            fis = new FileInputStream("Timer.dat");
            ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                System.out.println("Reading obj");
                Timer timer=(Timer) ois.readObject();
                 timerList.add(timer);
                timeManager.addTimeListener(listner= new TimerListener(timer) {
                    @Override
                    public void timeUpdated(int hr, int min, int sec, int milli) {
//                        System.out.println("second:"+sec);
                        if(timerBackEndListener != null) {
                        timerBackEndListener.updateui(timer);
                        }
                    }
                });
            }
            ois.close();
        }catch (Exception e){
        e.printStackTrace();}
    }
    void addTimer(Timer timer){
        timerList.add(timer);

    }
    void startTimer(Timer timer) {
        addTimer(timer);


        timeManager.addTimeListener(listner = new TimerListener(timer) {
            @Override
            public void timeUpdated(int hr, int min, int sec, int milli) {
//                System.out.println("second:" + sec);
                timerBackEndListener.updateui(timer);
            }
        });
       writeTimerFile();
    }
  public  void writeTimerFile(){
        try {
            fos = new FileOutputStream("Timer.dat");
            oos = new ObjectOutputStream(fos);
            for (Timer timers : timerList) {
                oos.writeObject(timers);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
