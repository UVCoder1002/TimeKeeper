package Manager;

import Stopwatch.StopWatch;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TimeManager implements Runnable {
    ArrayList<TimeListener> listeners;
    volatile boolean flag = true;
    Thread t1;
    Thread t2;

    public TimeManager() {
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

                                } else if (listener instanceof CountdownListener) {
                                    CountdownListener cl = (CountdownListener) listener;
                                    if (cl.sec == 0 && cl.min == 0 && cl.hr == 0) {
                                        continue;
                                    } else {
                                        if (cl.sec == 0) {
                                            if (cl.min == 0) {
                                                cl.min = 59;
                                                cl.sec = 59;
                                                cl.hr--;
                                            } else {
                                                cl.min--;
                                                cl.sec = 59;
                                            }

                                        } else {
                                            cl.sec--;
                                        }

                                        notifyListeners(cl.hr, cl.min, cl.sec, 0);
                                    }
                                }
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
                            if (!listener.ispaused) {
                                if (listener instanceof StopwatchListener) {
                                    StopwatchListener stopwatchListener = (StopwatchListener) listener;
                                    StopWatch stopWatch = stopwatchListener.stopWatch;
                                    System.out.println("in");
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

                                    notifyListeners(stopWatch.hr, stopWatch.min, stopWatch.sec, stopWatch.milli);
//                                            notifyListeners(stopwatchListener.milli);

                                }

                            }
                        }
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        listeners = new ArrayList<>();
        t1.start();
        t2.start();

    }

    public void addTimeListener(TimeListener listener) {//add
        listeners.add(listener);
    }

    private void notifyListeners(int hr, int min, int sec, int milli) {
        ArrayList<TimeListener> timeListeners=new ArrayList<>(listeners);
        for (TimeListener listener :
                timeListeners) {
            if (listener instanceof StopwatchListener) {
                listener.timeUpdated(hr, min, sec, milli);
            }
        }
    }

    public void removeListener(TimeListener listener) {//add
        listeners.remove(listener);
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


