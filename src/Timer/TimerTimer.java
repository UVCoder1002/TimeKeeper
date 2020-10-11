package src.Timer;

public class TimerTimer implements Runnable{
    My_Timer stwt;
    volatile boolean flag=true;
    public TimerTimer(My_Timer sw){
        stwt = sw;
    }

    @Override
    public void run() {
        int hr=Integer.parseInt(stwt.getHr().getText());
        int min=Integer.parseInt(stwt.getMin().getText());
        int sec=Integer.parseInt(stwt.getSec().getText());;
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
                stwt.setCountdown(hr, min, sec);}
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
