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
    Menu menu;


    public TimeZones() throws IOException {

        setLayout(new GridBagLayout());
        dateFormat=new SimpleDateFormat("s");
        clocks = new JPanel();
        clocks.setLayout(new BoxLayout(clocks,BoxLayout.Y_AXIS));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.anchor = GridBagConstraints.NORTH;
//        gbc.weighty = 1;

//        clocks.setLayout(new GridLayout(0,1));
//        clocks.setPreferredSize(new Dimension(500,500));
        clocks.setBackground(Color.red);
        jScrollPaneTimeZOne=new JScrollPane(clocks);
//       jScrollPaneTimeZOne.setBounds(50, 30, 400, 400);
//        jScrollPaneTimeZOne.setViewport(clocks);
//        jScrollPaneTimeZOne.setLayout(new ScrollPaneLayout());
        jScrollPaneTimeZOne.setPreferredSize(new Dimension(500,500));
        jScrollPaneTimeZOne.setBackground(Color.BLUE);
//        jScrollPaneTimeZOne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        jScrollPaneTimeZOne.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(jScrollPaneTimeZOne);
//        this.add(jScrollPaneTimeZOne, gbc); // gbc is containing the GridBagConstraints
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        File file=new File("TimeZones.txt");
        fWrite=new FileWriter(file,true);
        fRead=new FileReader(file);
        bRead=new BufferedReader(fRead);
        bWrite=new BufferedWriter(fWrite);
//        jPanelTimeZone.add(this);

      while((line= bRead.readLine())!=null){
          stdclock = new StandardClock(line);
          clocks.add(stdclock);

          //timezone.revalidate();
          stdclock.start();
      }
        clocks.revalidate();
        jScrollPaneTimeZOne.revalidate();
      bRead.close();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                menu.frameVisible();
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
//        this.add(clocks);
//        jPanelTimeZone.add(this);
        this.setLayout(flowLayout);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //timezone = this;
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
                    stdclock.start();
                    clocks.revalidate();
                    jScrollPaneTimeZOne.revalidate();
                    jScrollPaneTimeZOne.repaint();
//                    jScrollBar.revalidate();

                }
            }
        });

        // Tz.setVisible(true);
    }

    public void displayFrame(TimeZones timeZone,JFrame jFrame)
    {

    }

    public static void main(String[] args) throws IOException {

        JFrame frame=new JFrame("Time Keeper");
        TimeZones timeZone = new TimeZones();
//        frame.add(timeZone.jPanelTimeZone,BorderLayout.WEST);
        frame.add(timeZone,BorderLayout.CENTER);
//        frame.add(timeZone.jScrollPaneTimeZOne);
//        timeZone.jPanelTimeZone.add(timeZone.timezone);
//        timeZone.jPanelTimeZone=new JPanel();
//        timeZone.jPanelTimeZone.add(timeZone.jScrollBar);
        frame.setLocation(0,0);
//        timeZone.jScrollBar = new JScrollBar();
//        timeZone.jScrollBar.setOrientation(Adjustable.VERTICAL);
//        frame.add(timeZone.jScrollBar,BorderLayout.EAST);
        frame.setSize(1010, 500);
        frame.setLocation(200,100);
        timeZone.displayZones();
        frame.setVisible(true);
    }

}
