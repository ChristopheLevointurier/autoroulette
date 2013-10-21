/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.Controller.SessionController;
import clv.Croupier;
import clv.common.Player;
import clv.common.Report;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author CLV
 */
public class CroupierView extends JFrame {

    private JCheckBoxMenuItem manualCheck = new JCheckBoxMenuItem("Mode manuel", Croupier.isManualMode());
    private JCheckBoxMenuItem HistoryVue = new JCheckBoxMenuItem("Historiques", false);
    private JCheckBoxMenuItem failwinsVue = new JCheckBoxMenuItem("Fails/Wins", false);

    // private Croupier model;
    public CroupierView() {
        super("Roulette");

        JMenuBar menu = new JMenuBar();
        JMenu configCroup = new JMenu("Croupier Config");
        JMenu configVue = new JMenu("Fenetres");
        configCroup.add(manualCheck);
        menu.add(configCroup);
        configVue.add(HistoryVue);
        configVue.add(failwinsVue);
        menu.add(configVue);
        setJMenuBar(menu);
        getContentPane().setLayout(new FlowLayout());
        JButton go = new JButton("Launch");
        JButton addPlayer = new JButton("AddPlayer");

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();
                Croupier.run();
            }
        });

        addPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Croupier.addPlayer(new Player());
            }
        });
        SessionController.addSessionListener(Report.getInstance());

        getContentPane().add(go, BorderLayout.SOUTH);
        getContentPane().add(addPlayer, BorderLayout.NORTH);

        setVisible(true);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Croupier.setManualMode(true);
                dispose();
                System.exit(0);
            }
        });

        JPanel content = (JPanel) getContentPane();
        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE");
        final JFrame frame = this;
        content.getActionMap().put("CLOSE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });


    }
}
