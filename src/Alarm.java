import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Alarm  {
    Date date;
    String[] ampm;
    JLabel timeLeft;
    JTextField hrText;
    JTextField minText;
    JTextField secText;
    Button b1;
    AlarmClock setA, getA;
    JLayeredPane lp;
    CurrentTime time;
    ArrayList<AlarmClock> alarmArr;
    int hr = 0, min = 0, sec = 0;
    Button snooze;
    String amOrpm;
    FileOutputStream fos;
    ObjectOutputStream oos;
    FileInputStream fis;
    ObjectInputStream ois;
    Iterator<AlarmClock> iter;
    Iterator<AlarmClock> iter2;
    String zone;

    Alarm(TimeManager tm,String zone) throws IOException {
        System.out.println(zone);
        this.zone=zone;
        alarmArr = new ArrayList<AlarmClock>();
        try {
            fis = new FileInputStream("Alarm.dat");
            ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                System.out.println("Reading obj");
                alarmArr.add((AlarmClock) ois.readObject());
                setA = alarmArr.get(0);
                System.out.println(setA.hr + "" + setA.min + "" + setA.sec);
            }


            ois.close();
        } catch (EOFException | FileNotFoundException | ClassNotFoundException e) {
            System.out.println("Reach end of file");
        }

        time = new CurrentTime(zone);
        iter = alarmArr.iterator();
    }

   void setAlarm(AlarmClock setA){
       try {
           fos = new FileOutputStream("Alarm.dat");
           oos = new ObjectOutputStream(fos);
           oos.writeObject(setA);
           oos.close();
           alarmArr.add(setA);
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }
   void setAlarm(int day,int month,int year,int hr,int min,int sec){
       {
           AlarmClock a=new AlarmClock(year,month,day,hr,min,sec,zone);
           setAlarm(a);
       }
   }
}
