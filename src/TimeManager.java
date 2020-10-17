import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TimeManager implements Runnable {
    ArrayList<TimeListener> listeners;
    volatile boolean flag = true;
    Thread t1;

    TimeManager() {
        t1 = new Thread(this);
        listeners = new ArrayList<>();
        t1.start();
    }

    public void addTimeListener(TimeListener listener) {//add
        listeners.add(listener);
    }

    private void notifyListeners(int hr, int min, int sec) {
        for (TimeListener listener :
                listeners) {
            listener.timeUpdated(hr, min, sec);
        }
    }

    @Override
    public void run() {
        System.out.println("running");

        try {
            while (flag) {
                ArrayList<TimeListener> listenersArr =  new ArrayList<>(listeners);
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
                                listeners.remove(listener);
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

                                notifyListeners(cl.hr, cl.min, cl.sec);
                            }
                        }

                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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


