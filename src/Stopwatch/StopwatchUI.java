package Stopwatch;

import Manager.Menu;
import Manager.TimeListener;
import Manager.TimeManager;
import Manager.UniqueCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.UUID;

public class StopwatchUI extends JPanel {
    private JTextArea hrTxt;
    private JTextArea minTxt;
    private JTextArea secTxt;
    private JButton startBT;
    private JButton pauseBT;
    private JButton lapBT;
    private JButton Back;
    public JTextArea lapOutput;
    private JLabel hrL;
    private JLabel minL;
    private JLabel secL;
    private JPanel stopwatchJp;
    //private JTextField millisec;
    private JTextArea milliTxt;
    int noOfClickLap = 0;
    int flag = 1;
    TimeManager timeManager;
    TimeListener listener;
    Thread thread = null;
    String[] s;
    Menu menu;
    JScrollPane OuterScrollPane;
    JPanel insideScroll;
    JFrame frame = null;
    JFrame jFrame;
    StopwatchUI stopWatchui;
    StopwatchBack stopWatchBack;
    HashMap<UUID, StopWatchItem> map;
    JPanel scrollPane=null;
    JPanel OutsidePane;

    public Integer getHr() {
        return Integer.parseInt(hrTxt.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(minTxt.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(secTxt.getText());
    }

    public Integer getmilli() {
        return Integer.parseInt(milliTxt.getText());
    }

    /*public JTextArea getMil() {
        return milTxt;
    }*/
    public void setTimer(int hr, int min, int sec) {
        hrTxt.setText("" + hr);
        minTxt.setText("" + min);
        secTxt.setText("" + sec);

    }

    public void setmilli(int milli) {
        milliTxt.setText("" + milli);
    }


    public StopwatchUI(StopwatchBack stopWatchBack) {
        if(scrollPane==null)
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
//        scrollPane.setLayout(new FlowLayout());
        OuterScrollPane=new JScrollPane(scrollPane);
        map = new HashMap<>();
        this.stopWatchBack = stopWatchBack;
        stopWatchui = this;
        //this.stopWatchBack = stopWatchBack;

        if (frame == null) {
            frame = new JFrame("Time Keeper");
            map = new HashMap<>();
            this.stopWatchBack = stopWatchBack;
            stopWatchui = this;

            //tCD.setLayout(new GridLayout());
            OutsidePane.add(OuterScrollPane);
            frame.setContentPane(stopwatchJp);
//        stopwatchJp.add(scrollPane,new GridConstraints());
            //JScrollPane jScrollPane = new JScrollPane(stopwatchJp);
            //scrollBar1.setOrientation(Adjustable.VERTICAL);
            //frame.getContentPane().add(jScrollPane);
//        stopwatchJp.add(scrollPane);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1010, 500);
            frame.setLocation(250, 100);
            //frame.add(tCD);
            frame.setVisible(true);

        } else
            frame.setVisible(true);
//        scrollPane = new JPanel();




        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UniqueCode id = new UniqueCode();
                StopWatch stopwatch = new StopWatch(id.generateunicode());
                //adding stopwatch in ui
                addStopwatch(stopwatch);
                //Adding stopwatch in backend
                stopWatchBack.startStopWatch(stopwatch);
            }
        });
        //joining ui and backend using stopWatchListener
        stopWatchui.stopWatchBack.stopWatchListener = new StopWatchListener() {
            @Override
            public void updateui(StopWatch stopWatch) {
                printStopWatch(stopWatch);
            }
        };

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                jFrame.setVisible(true);
            }
        });
    }
//    public void addActionListner(){
//        Iterator<Map.Entry<UUID, StopWatchItem>> iter=map.entrySet().iterator();
//        while(iter.hasNext()){
//            StopWatchItem stopWatchItem=iter.next().getValue();
//            stopWatchBack.isPressedPause(stopWatchItem);
//        }
//    }
    public void stopwatchFrameVisible() {
        frame.setVisible(true);
    }

    public void passFrame(JFrame jframe) {
        this.jFrame = jframe;
    }

    public static void main(String[] args) {
        TimeManager timeManager = new TimeManager();
        StopwatchBack stopwatchBack = new StopwatchBack(timeManager);
        StopwatchUI tCD = new StopwatchUI(stopwatchBack);

        //tCD.start();
    }


    void printStopWatch(StopWatch clock) {
        //System.out.println("id is" + clock.id);
        map.get(clock.id).updateui(clock);
    }


    void addStopwatch(StopWatch clock) {
        StopWatchItem stopWatchItem = new StopWatchItem(clock,stopWatchui);
        map.put(clock.id, stopWatchItem);
        scrollPane.add(stopWatchItem);
        scrollPane.repaint();
        scrollPane.revalidate();
//        revalidate();

    }
}

