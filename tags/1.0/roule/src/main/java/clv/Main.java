package clv;

import clv.view.HistoryGraph;
import clv.view.CloudGraph;
import clv.Controller.SessionController;
import clv.common.Config;
import clv.common.Report;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import clv.sub.RouletteNumber;
import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

import third.EditListAction;
import third.ListAction;

public class Main extends JFrame {

    private static final long serialVersionUID = 2597779237651500313L;
    public static HashMap<Integer, RouletteNumber> table = new HashMap<>();
    private JList misesBox;
    private final DefaultListModel model = new DefaultListModel();
    private JButton go, calclist;
    private JSlider deb = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
    private JTextField multip = new JTextField("2.000");
    private JTextField debfield = new JTextField("miseInit");
    private JTextField multfield = new JTextField("multiplicateur");
    private JTextField runsName = new JTextField("Nombre sessions:");
    private JTextField runsf = new JTextField("100000   ");
    private JTextField portefName = new JTextField("Nombre jetons start:");
    private JSlider portef = new JSlider(JSlider.HORIZONTAL, 0, 250, 50);
    private JTextField goalName = new JTextField("condition win:");
    private JTextField goalf = new JTextField("1.83      "); // 1.28 pour 75%
    private JCheckBox boost = new JCheckBox("BoostePogne", false);
    private JCheckBox swi = new JCheckBox("Switch", true);
    private JCheckBox inc = new JCheckBox("inc fail", true);
    private JCheckBox drop = new JCheckBox("dropGetNull", false);
    private JCheckBox cloud = new JCheckBox("CloudGraph", false);
    private JCheckBox doublep = new JCheckBox("DoublePlayer", true);
    private JCheckBox history = new JCheckBox("HistoryGraph", false);
    private JTextField avoid = new JTextField("EchapFaibleProba");
    private JSlider avoidf = new JSlider(JSlider.HORIZONTAL, 0, 5, 1);
    private JRadioButton setPlus1 = new JRadioButton("+1", false), setPlus0 = new JRadioButton("+0", true), setPlusCrois = new JRadioButton("+1,2,3,4..", false);
    public static JProgressBar bar = new JProgressBar();

    static {
        for (int i = 0; i <= 36; i++) {
            table.put(i, new RouletteNumber(i));
        }

        System.out.println("init:");
        for (RouletteNumber r : table.values()) {
            System.out.println(r);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("Roulette");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().setLayout(new FlowLayout());
        model.addElement("                     ");
        misesBox = new JList(model);
        new ListAction(misesBox, new EditListAction());
        JScrollPane scrollList = new JScrollPane(misesBox);

        add(scrollList);

        ButtonGroup b = new ButtonGroup();
        b.add(setPlus1);
        b.add(setPlusCrois);
        b.add(setPlus0);

        go = new JButton("Launch");

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();
                Config.setMAX_RUNS(Integer.parseInt("" + runsf.getText().trim()));
                Config.setGoalWin(Double.parseDouble(goalf.getText().trim()));
                Config.setPortefeuilleStart(portef.getValue());
                Config.setAvoid(avoidf.getValue());
                Config.setUseBoostPogne(boost.isSelected());
                Config.setUseDropManagenull(drop.isSelected());
                Config.setUseswitch(swi.isSelected());
                Config.setDoubleOnFail(inc.isSelected());
                ArrayList<Integer> mises = new ArrayList<>();
                for (int i = 0; i < model.getSize(); i++) {
                    mises.add((Integer.parseInt(((String) model.getElementAt(i)).trim())));
                }
                Config.setMises(mises);
                if (cloud.isSelected()) {
                    SessionController.addSessionListener(new CloudGraph());
                }
                if (history.isSelected()) {
                    SessionController.addSessionListener(new HistoryGraph());
                }
                if (doublep.isSelected()) {
                    new DoublePlayer();
                } else {
                    new Player();
                }
            }
        });

        calclist = new JButton("calculer liste");
        calclist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeAllElements();
                for (int i = 0; i < avoidf.getValue(); i++) {
                    model.addElement("0");
                }
                double val = deb.getValue();
                for (int i = 0; i < 20; i++) {
                    model.addElement("" + (int) val);
                    val *= Double.parseDouble("" + multip.getText());
                    if (setPlus1.isSelected()) {
                        val += 1;
                    }
                    if (setPlusCrois.isSelected()) {
                        val += (i + 1);
                    }
                }
            }
        });
        JPanel debpanel = new JPanel();
        debpanel.setLayout(new FlowLayout());
        debpanel.add(deb);
        debpanel.add(debfield);
        listButs.add(debpanel);

        JPanel mulpanel = new JPanel();
        mulpanel.setLayout(new FlowLayout());
        mulpanel.add(multip);
        mulpanel.add(multfield);
        listButs.add(mulpanel);

        listButs.add(setPlus0);
        listButs.add(setPlus1);
        listButs.add(setPlusCrois);
        listButs.add(boost);
        listButs.add(drop);
        listButs.add(swi);
        listButs.add(inc);


        avoidf.setMajorTickSpacing(1);
        avoidf.setPaintTicks(true);
        avoidf.setPaintLabels(true);
        deb.setMajorTickSpacing(1);
        deb.setPaintTicks(true);
        deb.setPaintLabels(true);
        portef.setMajorTickSpacing(100);
        portef.setMinorTickSpacing(10);
        portef.setPaintTicks(true);
        portef.setPaintLabels(true);


        JPanel avoids = new JPanel();
        avoids.setLayout(new FlowLayout());
        avoid.setEditable(false);
        avoids.add(avoid);
        avoids.add(avoidf);

        listButs.add(avoids);
        listButs.add(calclist);
        add(listButs);

        JPanel launchp = new JPanel();
        launchp.setLayout(new BoxLayout(launchp, BoxLayout.Y_AXIS));

        JPanel runs = new JPanel();
        runs.setLayout(new FlowLayout());
        runsName.setEditable(false);
        runs.add(runsName);
        runs.add(runsf);
        launchp.add(runs);
        JPanel porte = new JPanel();
        porte.setLayout(new FlowLayout());
        portefName.setEditable(false);
        porte.add(portefName);
        porte.add(portef);
        launchp.add(porte);
        JPanel goalp = new JPanel();
        goalp.setLayout(new FlowLayout());
        portefName.setEditable(false);
        goalName.setEditable(false);
        multfield.setEditable(false);
        debfield.setEditable(false);
        goalp.add(goalName);
        goalp.add(goalf);
        launchp.add(goalp);
        launchp.add(cloud);
        launchp.add(history);
        launchp.add(doublep);
        launchp.add(go);
        launchp.add(bar);
        add(launchp);
        calclist.doClick();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SessionController.addSessionListener(Report.getInstance());
    }
}
