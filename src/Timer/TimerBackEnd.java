package Timer;

import java.util.ArrayList;

public class TimerBackEnd implements Runnable{
    ArrayList<TimerListener> listeners;
    volatile boolean flag = true;
    TimerBackEnd() {
        listeners = new ArrayList<>();
    }

    public void addTimeListener(TimerListener listener) {
        //System.out.println("adding listenre");
        listeners.add(listener);
    }
    public void removeListener(TimerListener listener)
    {
        listeners.remove(listener);
    }

    private void notifyListeners(int hr, int min, int sec) {
        for (TimerListener listener : listeners) {
            listener.updateTimer(hr, min, sec);
        }
    }
    @Override
    public void run() {
                while (flag) {
                    ArrayList<TimerListener> array=new ArrayList<>(listeners);
                    //System.out.println("hi");
                    for(TimerListener listener:array) {
                       // System.out.println("in");
                        try {
                        if (listener.sec == 0 && listener.min == 0 && listener.hr == 0) {
                            continue;
                        } else {
                            //System.out.println("hi");
                            if (listener.sec == 0) {
                                if (listener.min == 0) {
                                    listener.min = 59;
                                    listener.sec = 59;
                                    listener.hr--;
                                } else {
                                    listener.min--;
                                    listener.sec = 59;
                                }

                            } else {
                                listener.sec--;
                            }
                            notifyListeners(listener.hr, listener.min, listener.sec);
                            Thread.sleep(1000);

                        }
                    }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                }
            }
    }
}
