package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class TimeZones extends JPanel{
    StandardClock stdclock;
    JPanel clocks;
    SimpleDateFormat dateFormat;
    FileReader fRead;
    FileWriter fWrite;
    BufferedReader bRead;
    BufferedWriter bWrite;
    String line;
    private JButton backButton;
    private JPanel jPanelTimeZone;
    FlowLayout flowLayout= new FlowLayout();
    String[] s;



    public TimeZones() throws IOException {



        setLayout(new GridBagLayout());
        dateFormat=new SimpleDateFormat("s");
        clocks = new JPanel();
        clocks.setLayout(new GridLayout(0,2));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        File file=new File("TimeZones.txt");
        fWrite=new FileWriter(file,true);
        fRead=new FileReader(file);
        bRead=new BufferedReader(fRead);
        bWrite=new BufferedWriter(fWrite);

      while((line= bRead.readLine())!=null){
          stdclock = new StandardClock(line);
          clocks.add(stdclock);
          //timezone.revalidate();
          stdclock.start();
      }
      bRead.close();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu.main(s);
            }
        });
    }

    void displayZones(){

        List<String> timezones = new ArrayList<>();
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();

        //LocalDateTime now = LocalDateTime.now();

        zoneIds.forEach((zoneId) -> {
            timezones.add( ""+ zoneId);
        });
        String[] array = timezones.toArray(new String[timezones.size()]);
        JComboBox<String> Tz=new JComboBox<String>(array);
        Tz.setPreferredSize(new Dimension(100,20));
        this.add(Tz);
        this.add(clocks);
        this.setLayout(flowLayout);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        TimeZones timezone = this;
        Tz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   bWrite.write(Tz.getSelectedItem().toString());
                   bWrite.newLine();
                    //bWrite.write("urvashi");
                    bWrite.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                finally {
                    stdclock = new StandardClock(Tz.getSelectedItem().toString());
                    clocks.add(stdclock);
                    timezone.revalidate();
                    stdclock.start();
                }
            }
        });

        // Tz.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        TimeZones timeZone = new TimeZones();
        JFrame frame=new JFrame("Time Keeper");
        frame.add(timeZone,BorderLayout.CENTER);
        frame.add(timeZone.jPanelTimeZone,BorderLayout.WEST);
        frame.setLocation(0,0);
        JScrollBar jScrollBar = new JScrollBar();
        jScrollBar.setOrientation(Adjustable.VERTICAL);
        frame.add(jScrollBar,BorderLayout.EAST);
        frame.setSize(1010, 500);
        frame.setLocation(200,100);
        timeZone.displayZones();
        frame.setVisible(true);

    }

}
