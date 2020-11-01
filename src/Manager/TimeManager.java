package Manager;

import Stopwatch.StopWatch;
import Timer.Timer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TimeManager implements Runnable {
    ArrayList<TimeListener> listeners;
    HashMap<UUID, TimeListener> map;
    //ArrayList<TimerListener> listenersTimer;
    volatile boolean flag = true;

    Thread t1;
    Thread t2;
    Thread t3;

    public TimeManager() {
        map = new HashMap<>();
        t1 = new Thread() {
            @Override
            public void run() {

                try {
                    while (flag) {
                        ArrayList<TimeListener> listenersArr = new ArrayList<>(listeners);
                        for (TimeListener listener : listenersArr) {
                            if (listener instanceof AlarmListener) {
                                AlarmClock ac = ((AlarmListener) listener).alarmClock;
                                if (!ac.isfired && checkAlarm(listener)) {
                                    System.out.println("time match");
                                    ((AlarmListener) listener).alarmClock.isfired = true;
                                    ((AlarmListener) listener).fireAlarm(ac.id,ac.path);
                                }
                            }
                            Thread.sleep(100);
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
//                                    System.out.println("in");
                                    stopWatch.milli++;
                                    if (stopWatch.milli == 99) {
                                        stopWatch.sec++;
                                        stopWatch.milli = 0;
                                    }
                                    if (stopWatch.sec == 59) {
                                        stopWatch.min++;
                                        stopWatch.sec = 0;
                                    }
                                    if (stopWatch.min == 59) {
                                        stopWatch.hr++;
                                        stopWatch.min = 0;
                                    }

                                }
                                listener.timeUpdated();
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
//                        System.out.println("running");
                        ArrayList<TimeListener> array = new ArrayList<>(listeners);
                        //System.out.println("hi");
                        for (TimeListener listener : array) {
                            // System.out.println("in");
                            if (listener instanceof TimerListener) {
                                TimerListener timerListener = (TimerListener) listener;
                                Timer timer = timerListener.timer;
                                if (!timer.isPaused) {
                                    if (timer.sec == 0 && timer.min == 0 && timer.hr == 0 && timer.milli == 0) {
                                        continue;
                                    } else {
                                        if (timer.milli == 0) {
                                            if (timer.sec == 0) {
                                                if (timer.min == 0) {
                                                    timer.min = 59;
                                                    timer.sec = 59;
                                                    timer.milli = 100;
                                                    timer.hr--;
                                                } else {
                                                    timer.min--;
                                                    timer.sec = 59;
                                                    timer.milli = 100;
                                                }
                                            } else {
                                                timer.sec--;
                                                timer.milli = 100;
                                            }
                                        } else {
                                            timer.milli--;
                                        }

//
                                        listener.timeUpdated();

                                    }
                                }
//                               new Thread(){
//                                   @Override
//                                   public void run() {
//                                       notifyListenersTimer(timerListener.hr,timerListener.min,timerListener.sec);
//                                       notifyListenersTimer(timerListener.milli);
//                                   }
//                               }.start();
                            }
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
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
        if (listener instanceof StopwatchListener) {
            map.put(((StopwatchListener) listener).stopWatch.id, listener);
        } else if (listener instanceof AlarmListener) {
            map.put(((AlarmListener) listener).alarmClock.id, listener);
        } else {
            map.put(((TimerListener) listener).timer.id, listener);
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

        if (((AlarmListener) alarmListener).alarmClock.getDt().toEpochSecond() == ZonedDateTime.now().toEpochSecond()) {

            return true;
        }

        return false;
    }
}


