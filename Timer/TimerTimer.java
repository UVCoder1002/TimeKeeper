package Timer;

public class TimerTimer implements Runnable{
    My_Timer stwt;
    public TimerTimer (My_Timer sw){
        stwt = sw;
    }

    @Override
    public void run() {
        int hr=Integer.parseInt(stwt.getHr().toString());
        int min=Integer.parseInt(stwt.getMin().toString());
        int sec=Integer.parseInt(stwt.getSec().toString());;
        try {
            while (sec!=0||min!=0||hr!=0) {
                Thread.sleep(1000);
                sec--;
                if(sec==0){
                    if(min==0) {
                        min=59;
                        sec=59;
                        hr--;
                    }
                    else{
                        min--;
                        sec=59;
                    }

                }
                else{
                       sec--;
                }
                stwt.setCountdown(hr, min, sec);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
