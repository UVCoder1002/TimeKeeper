package Manager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class Alarm  {
    TimeManager tm ;
    AlarmFireListener afl;
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
    UUID id;

    Alarm(TimeManager tm,String zone) throws IOException, ClassNotFoundException {
        System.out.println(zone);
        this.tm = tm;
        this.zone=zone;
        UniqueCode uni=new UniqueCode();
        this.id=uni.generateunicode();
        alarmArr = new ArrayList<AlarmClock>();
        try {
            fis = new FileInputStream("Alarm.dat");
            ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                System.out.println("Reading obj");
                setA = (AlarmClock) ois.readObject();
                alarmArr.add(setA);
                System.out.println(setA.dt.getHour() + "" + setA.dt.getMinute() + "" + setA.dt.getSecond());
                tm.addTimeListener(new AlarmListener(setA) {
                    @Override
                    public void fireAlarm(UUID alarmid) {
                        System.out.println(alarmid);
                        System.out.println("Alarm Fired");
                    }

                    @Override
                    public void deleteListener(AlarmListener listener) {

                    }


                    @Override
                    public void timeUpdated(int hr, int min, int sec, int milli) {

                    }

                });
            }


            ois.close();
        } catch (EOFException | FileNotFoundException | ClassNotFoundException e) {
            System.out.println("Reach end of file");
        }
    }

   void setAlarm(AlarmClock setA){
       try {
           fos = new FileOutputStream("Alarm.dat");
           oos = new ObjectOutputStream(fos);
           alarmArr.add(setA);
           tm.addTimeListener(new AlarmListener(setA) {
               @Override
               public void fireAlarm(UUID alarmid) {
                   System.out.println("Alarm Fired");
                   afl.fire(alarmid);
               }

               @Override
               public void deleteListener(AlarmListener listener) {

               }


               @Override
               public void timeUpdated(int hr, int min, int sec, int milli) {

               }
           });
           for(AlarmClock alarm : alarmArr) {
               oos.writeObject(alarm);
           }
           oos.close();
       } catch (IOException ex) {
           ex.printStackTrace();
       }
   }
   void setAlarm(int year,int month,int day,int hr,int min,int sec)  {
       {
           try {
               UniqueCode uni = new UniqueCode();

               AlarmClock a = new AlarmClock(year, month, day, hr, min, sec, zone, uni.generateunicode());
               System.out.println(a.id);
               setAlarm(a);
           }catch (Exception e){
               e.printStackTrace();
           }
       }
   }
  void setSnooze(UUID alarmID)  {
      System.out.println("in");
        Iterator<AlarmClock> i=alarmArr.iterator();
        while(i.hasNext()){
            System.out.println("in2");
            AlarmClock clock=i.next();
            if(clock.id.toString().compareTo(alarmID.toString())==0){
//                System.out.println("here");
                clock.dt = ZonedDateTime.now().plusMinutes(1);
                System.out.println(clock.dt.getMinute());
                try {
                    fos = new FileOutputStream("Alarm.dat");
                    oos = new ObjectOutputStream(fos);
                    for(AlarmClock alarm:alarmArr){
                        oos.writeObject(alarm);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
    void deleteAlarm(UUID id){
        ArrayList<AlarmClock> arrayList=new ArrayList<>(alarmArr);
        Iterator<AlarmClock> i=arrayList.iterator();
        while(i.hasNext()) {
//            System.out.println("in2");
            AlarmClock clock = i.next();
            if(clock.id.toString().compareTo(id.toString())==0){
                alarmArr.remove(clock);
                try {
                    fos = new FileOutputStream("Alarm.dat");
                    oos = new ObjectOutputStream(fos);
                    for(AlarmClock alarm:alarmArr){
                        oos.writeObject(alarm);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
