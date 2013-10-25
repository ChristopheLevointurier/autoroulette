/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.AbstractPlayer;
import clv.Croupier;
import clv.common.Report;
import clv.sub.ValueSelectorMenuItem;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 *
 * @author CLV TODO loading de la config sur widgets menu
 */
public class PlayerView extends JFrame {

    private AbstractPlayer model;
    private JTextField multip = new JTextField("Portefeuille");
    private JButton config = new JButton("Config");
    private JCheckBoxMenuItem doubleOnFail = new JCheckBoxMenuItem("doubleOnFail", true);
    private JCheckBoxMenuItem switchUse = new JCheckBoxMenuItem("switchOnFail", true);
    private JCheckBoxMenuItem drop = new JCheckBoxMenuItem("dropGetNull", false);
    private JCheckBoxMenuItem tableVue = new JCheckBoxMenuItem("Voir la table", false);
    private JMenuItem portefStart = new JMenuItem("NbrJetonsDebut");

    public PlayerView(AbstractPlayer _model) {
        super("playerView");
        model = _model;
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(config);
        getContentPane().add(multip);
        setVisible(true);
        pack();

        JMenuBar menu = new JMenuBar();
        JMenu configPLay = new JMenu("Joueur Config");
        JMenu configVue = new JMenu("Fenetres");
        configPLay.add(doubleOnFail);
        configPLay.add(switchUse);
        configPLay.add(drop);
        configPLay.addSeparator();
        portefStart.setText("NbrJetonsDebut:" + model.getConfig().getPortefeuilleStart());
        configPLay.add(portefStart);
        menu.add(configPLay);

        configVue.add(tableVue);
        configVue.addSeparator();
        menu.add(configVue);
        setJMenuBar(menu);
        getContentPane().setLayout(new FlowLayout());
        JButton go = new JButton("Launch");

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        doubleOnFail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getConfig().setDoubleOnFail(doubleOnFail.isSelected());
            }
        });

        switchUse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getConfig().setUseswitch(switchUse.isSelected());
            }
        });

        drop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getConfig().setUseDropManagenull(drop.isSelected());
            }
        });


        portefStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValueSelectorMenuItem selec = new ValueSelectorMenuItem("" + model.getConfig().getPortefeuilleStart(), "Nbr de jeton au début");
                model.getConfig().setPortefeuilleStart(selec.getIntValue());
            }
        });

        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerConfigView(model);
            }
        });
        pack();
    }

    public void maj() {
        multip.setText("" + model.getPortefeuille());
    }
}
