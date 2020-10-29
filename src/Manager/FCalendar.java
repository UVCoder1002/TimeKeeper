package Manager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FCalendar extends JPanel {
    String[] months;
    int[] daysOfMonths;
    String[] dayNames;
    int currentMonth;
    Date currentDate;
    int currentDateNum;
    int currentYear;
    String dayName;
    JLayeredPane lp;
    JPanel task,details,calendar;
    JPanel cal;
    JPanel arrows;
    JLabel monthLabel,yrLabel,dateLabel;
    JLabel labels[] = new JLabel[42];
    Thread thread = null;
    Button prev;
    Button next;
    HashMap<String,Integer> map;
    FCalendar(){
        details=new JPanel(new GridLayout(1,2,5,5));
        arrows=new JPanel(new GridLayout(1,2));
        prev=new Button("<");
        next=new Button(">");
        months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        daysOfMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        dayNames = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        currentDate = new Date();
        cal = new JPanel();
        task = new JPanel();
        lp = new JLayeredPane();
        lp.setLayout(new FlowLayout());
        lp.setPreferredSize(new Dimension(310, 310));
        calendar=this;
        map=new HashMap<>();
        map.put("MONDAY",0);
        map.put("TUESDAY",1);
        map.put("WEDNESDAY",2);
        map.put("THURSDAY",3);
        map.put("FRIDAY",4);
        map.put("SATURDAY",5);
        map.put("SUNDAY",6);
        this.drawCal();
    }

    public void drawCal() {
        System.out.println("drawcal");
        this.setMaximumSize(new Dimension(300,300)  );
        this.setPreferredSize(new Dimension(300,300));
        this.setBackground(Color.BLUE);
        cal.setLayout(new GridLayout(0, 7,5,5));
        cal.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        lp.add(details,-1);
        lp.add(arrows,0);
        lp.add(cal,0);
        lp.setBorder(BorderFactory.createTitledBorder("CALY"));
        this.add(lp);

        this.setPreferredSize(new Dimension(300,300));

        SimpleDateFormat dateFormat = new SimpleDateFormat("y", Locale.getDefault());
        dateFormat.applyPattern("y");

        currentYear = Integer.parseInt(dateFormat.format(currentDate));

        dateFormat.applyPattern("M");
        currentMonth = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("d");
        currentDateNum = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("E");
        dayName = dateFormat.format(currentDate);
        System.out.println(currentYear+""+currentMonth+""+currentDateNum+""+currentDate);

        showCal(currentMonth, currentYear);
    }

    public void showCal(int mon, int yr) {
        arrows.add(prev);
        arrows.add(next);
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cal.removeAll();
                details.removeAll();
                if(mon==1) {
                    showCal(12,yr-1);
                }else
                {
                    showCal(mon-1,yr);
                }
                cal.revalidate();
                cal.repaint();
                details.revalidate();
                arrows.revalidate();
                details.repaint();
                calendar.repaint();

            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cal.removeAll();
                details.removeAll();
                arrows.removeAll();
                if(mon==12){
                    System.out.println("yr increasing");
                    showCal(1,yr+1);
                }
                else{
                    showCal(mon+1,yr);
                }
                cal.revalidate();
                details.revalidate();
                arrows.revalidate();
                details.repaint();
                cal.repaint();
                arrows.repaint();
//                calendar.repaint();
            }
        });
        int counter = 0;
        if ((yr % 4 == 0 && yr % 100 != 0) || (yr % 400 == 0)) {
            daysOfMonths[1] = 29;
        }
//        System.out.println(months[mon - 1] + " " + yr);

//        int space = ((mon-1 + daysOfMonths[mon - 2]) % 7)/2 +1;
        int space=calSpaces(yr,mon);
        System.out.println(space);
        System.out.println();
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        dateLabel = new JLabel("",SwingConstants.CENTER);
        dateLabel.setOpaque(true);
        dateLabel.setBackground(new Color(0,204,0));
        dateLabel.setBorder(border);
        dateLabel.setText(currentDateNum+"");
        monthLabel = new JLabel("",SwingConstants.CENTER);
        monthLabel.setOpaque(true);
        monthLabel.setBackground(new Color(0,204,0));
        monthLabel.setBorder(border);
        monthLabel.setText(mon+"");
        yrLabel = new JLabel("",SwingConstants.CENTER);
        yrLabel.setOpaque(true);
        yrLabel.setBackground(new Color(0,204,0));
        yrLabel.setBorder(border);
        yrLabel.setText(yr+"");
        details.add(dateLabel);
        details.add(monthLabel);
        details.add(yrLabel);

        for (int i = 0; i < 7; i++) {
            labels[i] = new JLabel("",SwingConstants.CENTER);
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.YELLOW);
            labels[i].setBorder(border);
            labels[i].setText(dayNames[i]);
            cal.add(labels[i]);
        }
        for (int i = 0; i <space; i++) {
            labels[i] = new JLabel("",SwingConstants.CENTER);
            labels[counter].setOpaque(true);
            labels[counter].setBackground(Color.CYAN);
            labels[i].setBorder(border);
            labels[i].setText(" ");
            //System.out.print("    ");
            counter++;
            cal.add(labels[i]);
        }
        System.out.print(counter);
        //System.out.println();
        for (int i = 1; i <= daysOfMonths[mon - 1]; i++) {
            labels[counter] = new JLabel("",SwingConstants.CENTER);
            labels[counter].setOpaque(true);
            labels[counter].setBackground(Color.CYAN);
            labels[counter].setBorder(border);
//
            labels[counter].setText(" " + i + "  ");
            labels[counter].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
//                    cal.removeAll();
                    task.removeAll();
                    lp.add(task,1);
                    JLabel j1=new JLabel("Your scheduled Task");
                   task.add(j1);
                    task.setBackground(Color.RED);
                    lp.revalidate();

                }
            });
            cal.add(labels[counter]);
            counter++;

        }

    }
    int calSpaces(int yr,int mon){
        YearMonth ym = YearMonth.of(yr,mon);
        String day=ym.atDay(1).getDayOfWeek().name();
//        System.out.println(day);
//        if(day.compareToIgnoreCase("sunday")==0){
//            return 6;
//        }
//        else  if(day.compareToIgnoreCase("monday")==0){
//            return 0;
//        }
//        else if(day.compareToIgnoreCase("tuesday")==0){
//            return 1;
//        }
//        else if(day.compareToIgnoreCase("wednesday")==0){
//            return 2;
//        }
//        else if(day.compareToIgnoreCase("thursday")==0){
//            return 3;
//        }
//        else if(day.compareToIgnoreCase("friday")==0){
//            return 4;
//        }
//        else {
//            return 5;
//        }

                return map.get(day);

    }
//    public static void main(String[] args) {
//        JFrame window = new JFrame();
//        Color c = new Color(118, 73, 190);
//        window.setBackground(c);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(0, 0, 1000, 1000);
//        FCalendar fCal = new FCalendar();
//        window.add(fCal);
//        window.setVisible(true);
//    }
//
}
