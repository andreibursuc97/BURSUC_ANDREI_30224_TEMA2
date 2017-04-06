import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
/*
 * Created by JFormDesigner on Sun Apr 02 11:51:18 EEST 2017
 */



/**
 * @author unknown
 */
public class View extends JFrame {

    private ArrayList<JProgressBar> progressBars;

    public View() {
        initComponents();
        startButton.addActionListener(new StartButtonListener());
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        progressBars=new ArrayList<JProgressBar>();
        progressBars.add(casaProgressBar1);
        progressBars.add(casaProgressBar2);
        progressBars.add(casaProgressBar3);
        progressBars.add(casaProgressBar4);
        progressBars.add(casaProgressBar5);
        for(JProgressBar bar:progressBars) {
            bar.setStringPainted(true);
        }
        this.initProgressBars();
    }

    public void setProgressBars(Map<Server,Integer> serverState)
    {
        int i=0;

        for(Map.Entry<Server,Integer> entry:serverState.entrySet())
        {
            progressBars.get(i).setValue(entry.getValue());
            progressBars.get(i).setString(entry.getValue().toString()+" clienti");
            i++;
        }
        //this.setVisible(false);
        //this.setVisible(true);
    }

    public void initProgressBars()
    {
        for(int i=0;i<5;i++)
        {
            progressBars.get(i).setValue(0);
            progressBars.get(i).setString("0 clienti");
        }
    }

    public synchronized void setLogger(String s)
    {
        loggerTextArea.setText(s);
    }

    public synchronized void setContorField(int n)
    {
        contorField.setText(Integer.toString(n));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Andrei Bursuc
        label1 = new JLabel();
        minTimeBeetween = new JLabel();
        minTimeBeetweenField = new JTextField();
        minServiceTime = new JLabel();
        minServiceTimeField = new JTextField();
        maxTimeBetween = new JLabel();
        maxTimeBetweenField = new JTextField();
        maxServiceTime = new JLabel();
        maxServiceTimeField = new JTextField();
        numberQueues = new JLabel();
        numberQueuesField = new JTextField();
        simulationTime = new JLabel();
        simulationTimeField = new JTextField();
        startButton = new JButton();
        label8 = new JLabel();
        contorLabel = new JLabel();
        contorField = new JTextField();
        casa1 = new JLabel();
        casaProgressBar1 = new JProgressBar();
        casa2 = new JLabel();
        casaProgressBar2 = new JProgressBar();
        casa3 = new JLabel();
        casaProgressBar3 = new JProgressBar();
        casa4 = new JLabel();
        casaProgressBar4 = new JProgressBar();
        casa5 = new JLabel();
        casaProgressBar5 = new JProgressBar();
        loggerLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        loggerTextArea = new JTextArea();

        //======== this ========
        setTitle("Minimum Time Beetween clients");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "filly,hidemode 3,aligny top",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Time is measured in miliseconds");
        label1.setFont(new Font("Times New Roman", Font.BOLD, 12));
        contentPane.add(label1, "cell 3 0");

        //---- minTimeBeetween ----
        minTimeBeetween.setText("Minimum Time Between clients");
        contentPane.add(minTimeBeetween, "cell 3 1,gapy 10");

        //---- minTimeBeetweenField ----
        minTimeBeetweenField.setText("Scrie aici...");
        contentPane.add(minTimeBeetweenField, "cell 5 1,width 50:100");

        //---- minServiceTime ----
        minServiceTime.setText("Minimum Service Time");
        contentPane.add(minServiceTime, "cell 7 1,gapx 10");

        //---- minServiceTimeField ----
        minServiceTimeField.setText("Scrie aici...");
        contentPane.add(minServiceTimeField, "cell 8 1,width 50:100");

        //---- maxTimeBetween ----
        maxTimeBetween.setText("Maximum Time Between clients");
        contentPane.add(maxTimeBetween, "cell 3 2");

        //---- maxTimeBetweenField ----
        maxTimeBetweenField.setText("Scrie aici...");
        contentPane.add(maxTimeBetweenField, "cell 5 2");

        //---- maxServiceTime ----
        maxServiceTime.setText("Maximum Service Time");
        contentPane.add(maxServiceTime, "cell 7 2,gapx 10");

        //---- maxServiceTimeField ----
        maxServiceTimeField.setText("Scrie aici...");
        contentPane.add(maxServiceTimeField, "cell 8 2");

        //---- numberQueues ----
        numberQueues.setText("Number of queues");
        contentPane.add(numberQueues, "cell 3 3");

        //---- numberQueuesField ----
        numberQueuesField.setText("Scrie aici...");
        contentPane.add(numberQueuesField, "cell 5 3");

        //---- simulationTime ----
        simulationTime.setText("Simulation Time");
        contentPane.add(simulationTime, "cell 7 3,gapx 10");

        //---- simulationTimeField ----
        simulationTimeField.setText("Scrie aici...");
        contentPane.add(simulationTimeField, "cell 8 3");

        //---- startButton ----
        startButton.setText("Start simulation");
        contentPane.add(startButton, "cell 3 4,gapy 15");

        //---- label8 ----
        label8.setText("Situatia la case");
        contentPane.add(label8, "cell 3 5");

        //---- contorLabel ----
        contorLabel.setText("Conometru:");
        contentPane.add(contorLabel, "cell 3 6");

        //---- contorField ----
        contorField.setText("Contor");
        contentPane.add(contorField, "cell 3 6,alignx left,grow 0 100,width 50:100");

        //---- casa1 ----
        casa1.setText("Casa 1");
        contentPane.add(casa1, "cell 3 7,gapy 10");
        contentPane.add(casaProgressBar1, "cell 3 7,gapy 10");

        //---- casa2 ----
        casa2.setText("Casa 2");
        contentPane.add(casa2, "cell 3 8");
        contentPane.add(casaProgressBar2, "cell 3 8");

        //---- casa3 ----
        casa3.setText("Casa 3");
        contentPane.add(casa3, "cell 3 9");
        contentPane.add(casaProgressBar3, "cell 3 9");

        //---- casa4 ----
        casa4.setText("Casa 4");
        contentPane.add(casa4, "cell 3 10");
        contentPane.add(casaProgressBar4, "cell 3 10");

        //---- casa5 ----
        casa5.setText("Casa 5");
        contentPane.add(casa5, "cell 3 11");
        contentPane.add(casaProgressBar5, "cell 3 11");

        //---- loggerLabel ----
        loggerLabel.setText("Logger");
        contentPane.add(loggerLabel, "cell 3 12,gapy 30");

        //======== scrollPane1 ========
        {

            //---- loggerTextArea ----
            loggerTextArea.setFont(new Font("Times New Roman", loggerTextArea.getFont().getStyle(), loggerTextArea.getFont().getSize() + 5));
            scrollPane1.setViewportView(loggerTextArea);
        }
        contentPane.add(scrollPane1, "cell 3 13 6 1,width 100:410:500,height 100:250:300");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Andrei Bursuc
    private JLabel label1;
    private JLabel minTimeBeetween;
    private JTextField minTimeBeetweenField;
    private JLabel minServiceTime;
    private JTextField minServiceTimeField;
    private JLabel maxTimeBetween;
    private JTextField maxTimeBetweenField;
    private JLabel maxServiceTime;
    private JTextField maxServiceTimeField;
    private JLabel numberQueues;
    private JTextField numberQueuesField;
    private JLabel simulationTime;
    private JTextField simulationTimeField;
    private JButton startButton;
    private JLabel label8;
    private JLabel contorLabel;
    private JTextField contorField;
    private JLabel casa1;
    private JProgressBar casaProgressBar1;
    private JLabel casa2;
    private JProgressBar casaProgressBar2;
    private JLabel casa3;
    private JProgressBar casaProgressBar3;
    private JLabel casa4;
    private JProgressBar casaProgressBar4;
    private JLabel casa5;
    private JProgressBar casaProgressBar5;
    private JLabel loggerLabel;
    private JScrollPane scrollPane1;
    private JTextArea loggerTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    class StartButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            try {

                int minTimeBetweenClients=new Integer(minTimeBeetweenField.getText());
                int maxTimeBetweenClients=new Integer(maxTimeBetweenField.getText());
                int minServiceTime=new Integer(minServiceTimeField.getText());
                int maxServiceTime=new Integer(maxServiceTimeField.getText());
                int numberQueues=new Integer(numberQueuesField.getText());
                if(numberQueues<3 || numberQueues>5) throw new NumberFormatException("Numarul de cozi trebuie sa fie intre 3 si 5!");
                int simulationTime=new Integer(simulationTimeField.getText());
                if(maxTimeBetweenClients<minTimeBetweenClients)
                    throw new NumberFormatException("Timpul maxim dintre clienti este mai mic decat cel minim!");
                if(maxServiceTime<minServiceTime)
                    throw new NumberFormatException("Timpul maxim de servire a unui client este mai mic decat timpul minim!");
                if(simulationTime<=maxServiceTime || simulationTime<=maxTimeBetweenClients)
                    throw new NumberFormatException("Timpul de simulare dat este prea mic");
                View.this.initProgressBars();
                //IEL listener=new EventListener();
                SimulationManager manager=new SimulationManager(minTimeBetweenClients,maxTimeBetweenClients,simulationTime,numberQueues,minServiceTime,maxServiceTime,View.this);

            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(null,e2.getMessage());
            }
        }
    }

}
