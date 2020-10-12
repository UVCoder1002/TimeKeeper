package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class TimeZones extends JPanel{
    src.StandardClock stdclock;
    JPanel clocks;
    SimpleDateFormat dateFormat;
    TimeZones(){
        setLayout(new GridBagLayout());
        dateFormat=new SimpleDateFormat("s");
        clocks = new JPanel();
        clocks.setLayout(new GridLayout(0,2));
    }


    void displayZones(){

        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

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
        TimeZones timezone = this;
        Tz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stdclock = new src.StandardClock(Tz.getSelectedItem().toString());
                clocks.add(stdclock);
                timezone.revalidate();
                stdclock.start();
            }
        });

        // Tz.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame();
        TimeZones timez=new TimeZones();
        frame.add(timez);
        timez.displayZones();
        frame.setVisible(true);
    }
}
