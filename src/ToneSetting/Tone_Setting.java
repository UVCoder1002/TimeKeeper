package ToneSetting;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.String;
import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Tone_Setting extends JFrame{
    private JComboBox comboBox1;
    private JPanel settingJP;
    private JButton playBT;
    private JButton stopBT;
    private JButton chooseBT;
    //private String file;
    public static Tone_Setting instance;
    private URL urlFile;

    FileReader fileRead;
    FileWriter fileWrite;
    BufferedReader buffRead;
    BufferedWriter buffWrite;
    AudioInputStream audioTnSt;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    String path, tone;
    File file;

    public Tone_Setting() {


        /*filePath = "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles/alarm1.wav";
        audioInSt = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();*/


        //-----------to choose from system---------////IMPORTANT/////DON'T REMOVE
        /*chooseBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fD = new FileDialog(instance);
                fD.setMode(FileDialog.LOAD);
                fD.setVisible(true);
                String audio = fD.getDirectory() + fD.getFile();
                if (audio != null)
                    file = new File(audio);
                try {
                    urlFile = new URL("file:" + file);
                    //audioTnSt = AudioSystem.getAudioInputStream(new URL("file:" + file));
                } catch (MalformedURLException ex) {
                    System.err.println(ex);
                }
            }
        });*/

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tone = comboBox1.getSelectedItem().toString();
                if (tone.equals("Tone1")) {
                    path = "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\alarm2.wav";
                } else if (tone.equals("Tone2")) {
                    path = "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\ringtone1.wav";
                }
                file = new File(path);
                if(file.exists())
                {
                    try {
                    fileWrite=new FileWriter("F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\toneSetting.txt");
                    fileWrite.write(path);
                    fileWrite.close();
                     } catch (IOException ioException) {
                    ioException.printStackTrace();
                    }

                    try {
                    audioTnSt = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(audioTnSt);
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                    }
                } else
                 {
                    System.out.println("File doesn't exist");
                 }
            }
        });


        playBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Applet.newAudioClip(urlFile).play();
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                System.out.println("playing");
            }
        });

        stopBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Applet.newAudioClip(urlFile).stop();
                clip.stop();
                System.out.println("stopped");
            }
        });

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Tone_Setting toneS = new Tone_Setting();
        JFrame frame = new JFrame("Tone Setting");
        frame.setContentPane(new Tone_Setting().settingJP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocation(250,100);
        frame.setVisible(true);
    }

    /*public Music(String path) {

    }*/

    /*public void setTone(String tone)
    {
        if(tone.equals("Tone1"))
        {
            path= "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\alarm1.wav";
        } else if (tone.equals("Tone2"))
        {
            path= "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\alarm2.wav";
        } else if (tone.equals("Tone3"))
        {
            path= "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\ringtone1.wav";
        } else if (tone.equals("Tone4"))
        {
            path= "F:\\Coding\\Avishkar2020\\softablitz\\project\\TimeKeeper\\src\\ToneSetting\\sounds\\ringtone2.wav";
        }

    }*/
}
