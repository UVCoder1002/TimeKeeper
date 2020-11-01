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
       // to show current date
        details=new JPanel(new GridLayout(1,2,5,5));

        //for manipulating buttons
        arrows=new JPanel(new GridLayout(1,2));
        prev=new Button("<");
        next=new Button(">");
        months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        daysOfMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        dayNames = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        currentDate = new Date();
        map=new HashMap<>();
        cal = new JPanel();
        task = new JPanel();
        lp = new JLayeredPane();
        lp.setLayout(new FlowLayout());
        lp.setPreferredSize(new Dimension(310, 310));
        calendar=this;

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

        this.setMaximumSize(new Dimension(300,300));
        this.setPreferredSize(new Dimension(300,300));

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

            }
        });
        int counter = 0;

        //checking if leap year
        if ((yr % 4 == 0 && yr % 100 != 0) || (yr % 400 == 0)) {
            daysOfMonths[1] = 29;
        }

        int space=calSpaces(yr,mon);
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
        //adding Daynames Labels
        for (int i = 0; i < 7; i++) {
            labels[i] = new JLabel("",SwingConstants.CENTER);
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.YELLOW);
            labels[i].setBorder(border);
            labels[i].setText(dayNames[i]);
            cal.add(labels[i]);
        }
        //adding initial Spaces
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

        //adding dates to calendar
        for (int i = 1; i <= daysOfMonths[mon - 1]; i++) {
            labels[counter] = new JLabel("",SwingConstants.CENTER);
            labels[counter].setOpaque(true);
            labels[counter].setBackground(Color.CYAN);
            labels[counter].setBorder(border);
            labels[counter].setText(" " + i + "  ");
            labels[counter].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
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
                return map.get(day);

    }
}
