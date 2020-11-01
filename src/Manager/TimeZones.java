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
    JScrollPane jScrollPaneTimeZOne;
    private JButton backButton;
    private JPanel jPanelTimeZone;
    FlowLayout flowLayout= new FlowLayout();
    String[] s;
    JFrame frame=null;
    JFrame jframe;
    Menu menu;
    TimeZones timezone;

    public TimeZones() throws IOException {

        setLayout(new GridBagLayout());
        dateFormat=new SimpleDateFormat("s");
        clocks = new JPanel();
        clocks.setLayout(new BoxLayout(clocks,BoxLayout.Y_AXIS));

        clocks.setBackground(Color.red);
        jScrollPaneTimeZOne=new JScrollPane(clocks);

        jScrollPaneTimeZOne.setPreferredSize(new Dimension(500,500));
        jScrollPaneTimeZOne.setBackground(Color.BLUE);

        this.add(jScrollPaneTimeZOne);

        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        File file=new File("TimeZones.txt");
        fWrite=new FileWriter(file,true);
        fRead=new FileReader(file);
        bRead=new BufferedReader(fRead);
        bWrite=new BufferedWriter(fWrite);


      while((line= bRead.readLine())!=null){
          stdclock = new StandardClock(line);
          clocks.add(stdclock);

          stdclock.start();
      }
        clocks.revalidate();
        jScrollPaneTimeZOne.revalidate();
      bRead.close();
        timezone=this;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timezone.jframe.setVisible(true);
                timezone.frame.setVisible(false);

            }
        });
    }

    void displayZones(){

        List<String> timezones = new ArrayList<>();
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();

        zoneIds.forEach((zoneId) -> {
            timezones.add( ""+ zoneId);
        });
        String[] array = timezones.toArray(new String[timezones.size()]);
        JComboBox<String> timeZonesList=new JComboBox<String>(array);
        timeZonesList.setPreferredSize(new Dimension(100,20));
        this.add(timeZonesList);
        this.setLayout(flowLayout);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        timeZonesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   bWrite.write(timeZonesList.getSelectedItem().toString());
                   bWrite.newLine();
                    bWrite.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                finally {
                    stdclock = new StandardClock(timeZonesList.getSelectedItem().toString());
                    clocks.add(stdclock);
                    stdclock.start();
                    clocks.revalidate();
                    jScrollPaneTimeZOne.revalidate();
                    jScrollPaneTimeZOne.repaint();

                }
            }
        });

    }
    

    public static void main(String[] args,JFrame jframe) throws IOException {
        TimeZones timeZone = new TimeZones();
        timeZone.frame=new JFrame("Time Keeper");
        timeZone.frame.add(timeZone.jPanelTimeZone,BorderLayout.WEST);
        timeZone.frame.add(timeZone,BorderLayout.CENTER);
        timeZone.frame.setLocation(0,0);
        timeZone.frame.setSize(1010, 500);
        timeZone.frame.setLocation(200,100);
        timeZone.displayZones();
        timeZone.frame.setVisible(true);
        timeZone.jframe=jframe;
    }

}
