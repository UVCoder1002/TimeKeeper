import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Alarm extends JPanel {
    JLabel hourLabel;
    JLabel minLabel;
    JLabel secLabel;
    JComboBox<String> ampmDrop;
    Date date;
    String[] ampm;
    JLabel timeLeft;
    JTextField hrText;
    JTextField minText;
    JTextField secText;
    Button b1;
    AlarmClock setA,getA;
    JLayeredPane lp;
    CurrentTime time;
    ArrayList<AlarmClock> alarmArr;
    int hr=0,min=0,sec=0;
    String amOrpm;
    FileOutputStream fos;
    ObjectOutputStream oos;
    FileInputStream fis;
    ObjectInputStream ois;
    Iterator<AlarmClock> iter;
    Iterator<AlarmClock> iter2;
    Alarm(String zone) throws IOException, ClassNotFoundException {
        alarmArr=new ArrayList<AlarmClock>();
        try{
        fis = new FileInputStream("Alarm.dat");
        ois = new ObjectInputStream(fis);

            while(fis.available()>0){
                alarmArr.add((AlarmClock) ois.readObject());
                setA= alarmArr.get(0);
                System.out.println(setA.hr+""+setA.min+""+setA.sec);
            }


        ois.close();
        }
         catch(EOFException e){
            System.out.println("Reach end of file");
        }
        iter =alarmArr.iterator();
        Thread t1=new Thread(){
            public void run() {
                while(true){
                    while (iter.hasNext()) {
                        try {
                            if (checkAlarm()) {
                                JLabel alarm = new JLabel("alarm");
                                alarm.setText("Alarm");
                                alarm.setOpaque(true);
                                alarm.setBackground(Color.RED);
                                lp.add(alarm, 1);
                                break;
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        //this.setLayout();

        //TimeZones tzone=new TimeZones();
        time=new CurrentTime(zone);
        ampm= new String[]{"AM", "PM"};
        ampmDrop=new JComboBox<String>(ampm);
        setA=new AlarmClock();
        hourLabel=new JLabel();
        minLabel=new JLabel();
        secLabel=new JLabel();
        hrText=new JTextField();
        minText=new JTextField();
        secText=new JTextField();
        hrText.setPreferredSize(new Dimension(20,30));
        minText.setPreferredSize(new Dimension(20,30));
        secText.setPreferredSize(new Dimension(20,30));
        b1=new Button();
        hourLabel.setText("Hr :");
        minLabel.setText("Min :");
        secLabel.setText("Sec :");
        b1.setLabel("SET");
        this.add(hourLabel);
        this.add(hrText);
        this.add(minLabel);
        this.add(minText);
        this.add(secLabel);
        this.add(secText);
        this.add(ampmDrop);
        this.add(b1);
        time.setCurrentTime(hr,min,sec,amOrpm);
        iter2 = alarmArr.iterator();
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setA.hr = Integer.parseInt(hrText.getText().toString());
                setA.min = Integer.parseInt(minText.getText().toString());
                setA.sec = Integer.parseInt(secText.getText().toString());
                setA.ampm = ampmDrop.getSelectedItem().toString();
                try {
                    fos=new FileOutputStream("Alarm.dat");
                    oos=new ObjectOutputStream(fos);
                    oos.writeObject(setA);
                    oos.close();
                    alarmArr.add(setA);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
boolean checkAlarm() throws IOException, ClassNotFoundException {

//while(iter2.hasNext()){
//    AlarmClock getA=iter.next();
//    if(hr==getA.getHr()&&min==getA.getMin()&&sec==getA.getSec()&&amOrpm.compareTo(getA.getAmpm())==0)
//    {
//        return true;
//    }

//}
return false;
}




    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame jframe=new JFrame();
        TimeZones timezon= new TimeZones();
        Alarm alarm=new Alarm(timezon.dateFormat.getTimeZone().toString());
        jframe.add(alarm);
        jframe.setVisible(true);

    }
}
