package Manager;

import Stopwatch.StopWatch;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TimeManager implements Runnable {
    ArrayList<TimeListener> listeners;
    HashMap<UUID,TimeListener> map;
    //ArrayList<TimerListener> listenersTimer;
    volatile boolean flag = true;
    Thread t1;
    Thread t2;
    Thread t3;

    public TimeManager() {
        map=new HashMap<>();
        t1 = new Thread() {
            @Override
            public void run() {

                try {
                    while (flag) {
                        ArrayList<TimeListener> listenersArr = new ArrayList<>(listeners);
                        for (TimeListener listener : listenersArr) {
//                    System.out.println("Checking listeners");

//                    System.out.println("Checking listeners");
                            if (!listener.ispaused) {
                                if (listener instanceof AlarmListener) {
                                    AlarmClock ac = ((AlarmListener) listener).alarmClock;
//                            System.out.println("Checking Alarm For : " + ac.getDt().toString());
                                    if (checkAlarm(listener)) {
                                        ((AlarmListener) listener).alarmClock.isfired = true;
                                        ((AlarmListener) listener).fireAlarm(ac.id);
//                                        listeners.remove(listener);
                                    }

//                                } else if (listener instanceof CountdownListener) {
//                                    CountdownListener cl = (CountdownListener) listener;
//                                    if (cl.sec == 0 && cl.min == 0 && cl.hr == 0) {
//                                        continue;
//                                    } else {
//                                        if (cl.sec == 0) {
//                                            if (cl.min == 0) {
//                                                cl.min = 59;
//                                                cl.sec = 59;
//                                                cl.hr--;
//                                            } else {
//                                                cl.min--;
//                                                cl.sec = 59;
//                                            }
//
//                                        } else {
//                                            cl.sec--;
//                                        }
//
//                                        notifyListeners(cl.hr, cl.min, cl.sec, 0);
//                                    }
//                                }
//                                if (listener instanceof StopwatchListener) {
//                                    StopwatchListener stopwatchListener = (StopwatchListener) listener;
//                                    System.out.println("in");
//                                    stopwatchListener.sec++;
//                                    if (stopwatchListener.sec == 59) {
//                                        stopwatchListener.min++;
//                                        stopwatchListener.sec = 0;
//                                    }
//                                    if (stopwatchListener.min == 59) {
//                                        stopwatchListener.hr++;
//                                        stopwatchListener.min = 0;
//                                    }
//                                    notifyListeners(stopwatchListener.hr,stopwatchListener.min,stopwatchListener.sec);
//                                }
                                }
                            }
                            Thread.sleep(1000);
                        }
                    }
                } catch (InterruptedException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }


        };
        t2 = new Thread() {
            @Override
            public void run() {

                try {
                    while (flag) {
                        ArrayList<TimeListener> listenersArr = new ArrayList<>(listeners);
                        for (TimeListener listener : listenersArr) {
//                    System.out.println("Checking listeners");

//                    System.out.println("Checking listeners");

                                if (listener instanceof StopwatchListener) {
                                    StopwatchListener stopwatchListener = (StopwatchListener) listener;

                                    StopWatch stopWatch = stopwatchListener.stopWatch;
                                    if (!stopWatch.isPaused) {
                                        System.out.println("in");
                                        stopWatch.milli++;
                                        if (stopWatch.milli == 99) {
                                            stopWatch.sec++;
                                            stopWatch.milli = 0;

                                            //System.out.println("in");
                                        }
                                        if (stopWatch.sec == 59) {
                                            stopWatch.min++;
                                            stopWatch.sec = 0;
                                        }
                                        if (stopWatch.min == 59) {
                                            stopWatch.hr++;
                                            stopWatch.min = 0;
                                        }


//                                            notifyListeners(stopwatchListener.milli);

                                    }
                                    notifyListeners(stopWatch.hr, stopWatch.min, stopWatch.sec, stopWatch.milli);
                                }


                        }
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        t3 = new Thread() {
            @Override
            public void run() {
                try {
                    while (flag) {
                        ArrayList<TimeListener> array=new ArrayList<>(listeners);
                        //System.out.println("hi");
                        for(TimeListener listener:array) {
                        // System.out.println("in");
                       if(!listener.ispaused) {
                           if (listener instanceof TimerListener) {
                               TimerListener timerListener = (TimerListener) listener;
                               if (timerListener.sec == 0 && timerListener.min == 0 && timerListener.hr == 0 && timerListener.milli==0) {
                                   continue;
                               } else {
                                   if(timerListener.milli == 0)
                                   {
                                        if (timerListener.sec == 0)
                                        {
                                            if (timerListener.min == 0)
                                            {
                                                timerListener.min = 59;
                                                timerListener.sec = 59;
                                                timerListener.milli = 100;
                                                timerListener.hr--;
                                            } else {
                                                timerListener.min--;
                                                timerListener.sec = 59;
                                                timerListener.milli = 100;
                                            }
                                        } else {
                                       timerListener.sec--;
                                       timerListener.milli = 100;
                                        }
                                    } else
                                        timerListener.milli--;
                               notifyListeners(timerListener.hr, timerListener.min, timerListener.sec,timerListener.milli);
//                                notifyListenersTimer(timerListener.milli);

                               }
//                               new Thread(){
//                                   @Override
//                                   public void run() {
//                                       notifyListenersTimer(timerListener.hr,timerListener.min,timerListener.sec);
//                                       notifyListenersTimer(timerListener.milli);
//                                   }
//                               }.start();
                           }
                       }
                            Thread.sleep(10);
                        }
                    }
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }


            }
        };

        listeners = new ArrayList<>();
        t1.start();
        t2.start();
        t3.start();
    }


    public void addTimeListener(TimeListener listener) {//add
        listeners.add(listener);
        if(listener instanceof StopwatchListener){
            map.put(((StopwatchListener) listener).stopWatch.id,listener);
        }
        else if(listener instanceof AlarmListener){
            map.put(((AlarmListener) listener).alarmClock.id,listener);
        }
        else
        {
            map.put(((CountdownListener) listener).timer.id,listener);
        }
    }


    private void notifyListeners(int hr, int min, int sec, int milli) {
        ArrayList<TimeListener> timeListeners=new ArrayList<>(listeners);
        for (TimeListener listener :
                timeListeners) {
            if (listener instanceof StopwatchListener||listener instanceof TimerListener) {
                listener.timeUpdated(hr, min, sec, milli);
            }
            else
            {
                listener.timeUpdated(hr,min,sec,0);
            }
        }
    }

    public void removeListener(UUID id) {//add
        listeners.remove(map.get(id));
        map.remove(id);
    }


    @Override
    public void run() {
        System.out.println("running");
    }

    boolean checkAlarm(TimeListener alarmListener) throws IOException, ClassNotFoundException {
        //if (time.getHr() == getA.getHr() && time.getMin() == getA.getMin() && time.getSec() == getA.getSec() && time.getAmpm().compareToIgnoreCase(getA.getAmpm()) == 0) {
        if (((AlarmListener) alarmListener).alarmClock.getDt().toEpochSecond() == ZonedDateTime.now().toEpochSecond()) {

//            System.out.println("matched successfully");
            return true;
        }
//        System.out.println("not matched");
        return false;
    }
}


