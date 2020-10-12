public class Timer implements Runnable{
    Integer hr;
    Integer min;
    Integer sec;

    public Timer(Alarm a){
      this.hr=Integer.parseInt(a.gettext1());
      this.min=Integer.parseInt(a.gettext2());
      this.sec=Integer.parseInt(a.gettext3());
      Thread t=new Thread();
    }
    @Override
    public void run() {
        int totaltime= this.hr*60*60 + this.min*60+this.sec;
        while(totaltime!=0){
           totaltime=totaltime-1;

        }
    }
}
