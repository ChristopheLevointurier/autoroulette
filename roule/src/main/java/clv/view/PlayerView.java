/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.AbstractPlayer;
import clv.Controller.EventController;
import clv.Croupier;
import clv.common.Utils;
import clv.sub.ValueSelectorMenuItem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

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
    private JMenuItem goalName = new JMenuItem("Coeff win");
    private int FRAME_WIDTH = 150, FRAME_HEIGHT = 80;

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
        goalName.setText("Coeff win:" + model.getConfig().getGoalWin());
        configPLay.add(portefStart);
        configPLay.add(goalName);
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
                portefStart.setText("NbrJetonsDebut:" + model.getConfig().getPortefeuilleStart());
            }
        });


        goalName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValueSelectorMenuItem selec = new ValueSelectorMenuItem("" + model.getConfig().getGoalWin(), "Coeff pour victoire:");
                model.getConfig().setGoalWin(selec.getDoubleValue());
                goalName.setText("Coeff win:" + model.getConfig().getGoalWin());
            }
        });


        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerConfigView(model);
            }
        });


        JPanel content = (JPanel) getContentPane();
        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE");
        final PlayerView frame = this;
        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        };
        content.getActionMap().put("CLOSE", actionListener);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                Croupier.removePlayer(model);
                EventController.broadcast(Utils.AppEvent.EXIT_PLAYER);
                //   ModelController.removeModelListener((IModelListener) e.getWindow());
                dispose();
            }
        });

        pack();
        FRAME_WIDTH = (int)getSize().getWidth();
        FRAME_HEIGHT = (int)getSize().getHeight();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int jou = Croupier.getPlayerAmount();
        int modulo = (int) d.getWidth() / FRAME_WIDTH;
        setLocation((jou % modulo) * FRAME_WIDTH, FRAME_HEIGHT * (jou / modulo));
    }

    public void maj() {
        multip.setText("" + model.getPortefeuille());
        if (model.isDead()) {
            multip.setBackground(Color.red);
        }
        if (model.isWin()) {
            multip.setBackground(Color.green);
        }
    }
}
