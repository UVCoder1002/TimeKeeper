import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

    public class TimeManager implements Runnable {
        ArrayList<TimeListener> listeners;
        volatile boolean flag = true;

        TimeManager() {
            listeners = new ArrayList<>();
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

            while (true) {
                for (TimeListener listener : listeners) {
                    try {
                        if (!listener.ispaused) {
                            if (listener instanceof AlarmListener) {
                              if(checkAlarm(listener)){
                                  ((AlarmListener)listener).alarmClock.isfired=true;
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
                        Thread.sleep(1000);}
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        boolean checkAlarm(TimeListener alarmListener) throws IOException, ClassNotFoundException {
            //if (time.getHr() == getA.getHr() && time.getMin() == getA.getMin() && time.getSec() == getA.getSec() && time.getAmpm().compareToIgnoreCase(getA.getAmpm()) == 0) {
            if(((AlarmListener)alarmListener).alarmClock.getDt().toEpochSecond()== ZonedDateTime.now().toEpochSecond())    {

                System.out.println("matched successfully");
                return true;
            }
//       System.out.println("not matched");
            return false;
        }
    }


