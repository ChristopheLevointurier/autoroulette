/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.AbstractPlayer;
import static clv.Casino.bar;
import clv.common.Report;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import third.EditListAction;
import third.ListAction;

/**
 *
 * @author CLV
 */
public class PlayerConfigView extends JFrame {

    private JList misesBox;
    private final DefaultListModel listModel = new DefaultListModel();
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
    private JTextField avoid = new JTextField("EchapFaibleProba");
    private JSlider avoidf = new JSlider(JSlider.HORIZONTAL, 0, 5, 1);
    private JRadioButton setPlus1 = new JRadioButton("+1", false), setPlus0 = new JRadioButton("+0", true), setPlusCrois = new JRadioButton("+1,2,3,4..", false);
    private AbstractPlayer model;

    public PlayerConfigView(AbstractPlayer _player) {
        super("tout est boiteux pour l'instant");
        model = _player;
        getContentPane().setLayout(new FlowLayout());
        listModel.addElement("                     ");
        misesBox = new JList(listModel);
        new ListAction(misesBox, new EditListAction());
        JScrollPane scrollList = new JScrollPane(misesBox);

        add(scrollList);

        ButtonGroup b = new ButtonGroup();
        b.add(setPlus1);
        b.add(setPlusCrois);
        b.add(setPlus0);

        go = new JButton("Ok");

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();
                model.getConfig().setMAX_RUNS(Integer.parseInt("" + runsf.getText().trim()));
                model.getConfig().setGoalWin(Double.parseDouble(goalf.getText().trim()));
                model.getConfig().setPortefeuilleStart(portef.getValue());
                model.getConfig().setAvoid(avoidf.getValue());
                model.getConfig().setUseBoostPogne(boost.isSelected());
                ArrayList<Integer> mises = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    mises.add((Integer.parseInt(((String) listModel.getElementAt(i)).trim())));
                }
                model.getConfig().setMises(mises);
                dispose();
            }
        });

        calclist = new JButton("calculer liste");
        calclist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                for (int i = 0; i < avoidf.getValue(); i++) {
                    listModel.addElement("0");
                }
                double val = deb.getValue();
                for (int i = 0; i < 20; i++) {
                    listModel.addElement("" + (int) val);
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
        launchp.add(go);
        launchp.add(bar);
        add(launchp);
        calclist.doClick();

        setVisible(true);
        pack();
    }
}
