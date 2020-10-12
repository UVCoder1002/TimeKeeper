package Timer;

public class TimerTimer implements Runnable{
    My_Timer myTm;
    volatile boolean flag=true;
    public TimerTimer(My_Timer tm){
        myTm = tm;
    }

    @Override
    public void run() {
        int hr=Integer.parseInt(myTm.getHr().getText());
        int min=Integer.parseInt(myTm.getMin().getText());
        int sec=Integer.parseInt(myTm.getSec().getText());
        try {
            while (flag) {
                if(sec==0&&min==0&&hr==0){
                    flag=false;
                }
                else {
                    if (sec == 0) {
                        if (min == 0) {
                            min = 59;
                            sec = 59;
                            hr--;
                        } else {
                            min--;
                            sec = 59;
                        }

                    } else {
                        sec--;
                    }

                Thread.sleep(1000);
                myTm.setTime(hr, min, sec);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
