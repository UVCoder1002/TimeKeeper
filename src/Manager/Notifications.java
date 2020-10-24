package Manager;

import javax.swing.*;
import java.util.HashMap;
import java.util.UUID;

public class Notifications implements Runnable{
    JLabel msg;
    volatile boolean flag;
    HashMap<UUID,AlarmItem> map;
    AlarmUI alarmUI;
    UUID id;
    Notifications(AlarmUI alarmUI ,UUID id, HashMap<UUID,AlarmItem> map){
      msg=new JLabel("Alarm snoozed for 1 minute");
      flag=true;
      this.alarmUI=alarmUI;
      this.map=map;
      this.id=id;
    }

    @Override
    public void run() {

            map.get(id).add(msg);
               alarmUI.revalidate();
               alarmUI.scrollPane.revalidate();
               alarmUI.repaint();
            try {
                Thread.sleep(10000);
                map.get(id).remove(msg);
                alarmUI.revalidate();
                alarmUI.scrollPane.revalidate();
                alarmUI.repaint();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
