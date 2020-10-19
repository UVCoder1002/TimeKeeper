package Stopwatch;

import Timer.TimerListener;

import java.util.ArrayList;

public class StopwatchBackEnd implements Runnable{
    ArrayList<StopwatchListener> listeners;
    volatile boolean flag = true;
    StopwatchBackEnd() {
        listeners = new ArrayList<>();
    }

    public void addStopwatchListener(StopwatchListener listener) {
        //System.out.println("adding listener");
        listeners.add(listener);
    }
    public void removeListener(StopwatchListener listener)
    {
        listeners.remove(listener);
    }

    private void notifyListeners(int hr, int min, int sec) {
        for (StopwatchListener listener : listeners) {
            listener.updateTime(listener.hr,listener.min,listener.sec);
        }
    }
    @Override
    public void run() {
        while (flag) {
            ArrayList<StopwatchListener> array=new ArrayList<>(listeners);
            //System.out.println("hi");
            for(StopwatchListener listener:array) {
                //System.out.println("in");
                listener.sec++;
                if (listener.sec == 59) {
                    listener.min++;
                    listener.sec = 0;
                }
                if (listener.min == 59) {
                    listener.hr++;
                    listener.min = 0;
                }
                notifyListeners(listener.hr, listener.min, listener.sec);
            }
                try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
            }
        }
        /*int hr=0, min=0, sec=0;
        try {
            while (true) {
                Thread.sleep(1000);
                sec++;
                if(sec==60) {
                    min++;
                    sec=0;
                }
                if(min==60) {
                    hr++;
                    min=0;
                }
                stui.setTimer(hr, min, sec);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
