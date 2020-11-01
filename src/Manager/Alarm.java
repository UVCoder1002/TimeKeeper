package Manager;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class Alarm {
    TimeManager tm;
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

    Alarm(TimeManager tm, String zone) {

        this.tm = tm;
        this.zone = zone;
        alarmArr = new ArrayList<AlarmClock>();
        UniqueCode uni = new UniqueCode();
        this.id = uni.generateunicode();

        try {
            fis = new FileInputStream("Alarm.dat");
            ois = new ObjectInputStream(fis);

            while (fis.available() > 0) {
                System.out.println("Reading obj");
                setA = (AlarmClock) ois.readObject();
                alarmArr.add(setA);
//                System.out.println(setA.dt.getHour() + " " + setA.dt.getMinute() + " " + setA.dt.getSecond()+" "+setA.path);

                //Passing alarmclock object to alarmlistener and adding timemanager listener to the object in file
                tm.addTimeListener(new AlarmListener(setA) {
                    @Override
                    public void fireAlarm(UUID alarmid, String path) {
                        afl.fire(alarmid, path);
                    }


                    @Override
                    public void timeUpdated() {

                    }

                });
            }


            ois.close();
        } catch (EOFException | FileNotFoundException | ClassNotFoundException e) {
            System.out.println("Reach end of file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setAlarm(AlarmClock setA) {
        //New Alarm Added to AlarmArr and in file
        try {
            fos = new FileOutputStream("Alarm.dat");
            oos = new ObjectOutputStream(fos);
            alarmArr.add(setA);
            tm.addTimeListener(new AlarmListener(setA) {
                @Override
                public void fireAlarm(UUID alarmid, String path) {

                    afl.fire(alarmid, path);
                }

                @Override
                public void timeUpdated() {

                }
            });
            for (AlarmClock alarm : alarmArr) {
                oos.writeObject(alarm);
            }
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void setAlarm(int year, int month, int day, int hr, int min, int sec, String message, String path) {
        {
            try {
                UniqueCode uni = new UniqueCode();

                AlarmClock a = new AlarmClock(year, month, day, hr, min, sec, zone, uni.generateunicode(), message, path);
                System.out.println(a.id);
                setAlarm(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void setSnooze(UUID alarmID) {
        //getting the alarm from the arrayList
        Iterator<AlarmClock> i = alarmArr.iterator();
        while (i.hasNext()) {
            AlarmClock clock = i.next();
            if (clock.id.toString().compareTo(alarmID.toString()) == 0) {
                clock.isfired = false;

                //snoozing by one minute from the time snooze is pressed
                clock.dt = ZonedDateTime.now().plusMinutes(1);
                writeAlarmFile();
            }
        }
    }

    void deleteAlarm(UUID id, Clip clip){
        ArrayList<AlarmClock> arrayList=new ArrayList<>(alarmArr);
        Iterator<AlarmClock> i=arrayList.iterator();
        while(i.hasNext()) {
            AlarmClock clock = i.next();
            if (clock.id.toString().compareTo(id.toString()) == 0) {
                //removing clock from array
                alarmArr.remove(clock);
       writeAlarmFile();
               if(clip!=null)
               clip.stop();

            }
        }
    }

    void writeAlarmFile() {
        try {
            fos = new FileOutputStream("Alarm.dat");
            oos = new ObjectOutputStream(fos);
            for (AlarmClock alarm : alarmArr) {
                oos.writeObject(alarm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
