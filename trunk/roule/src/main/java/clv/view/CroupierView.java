/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.Casino;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author CLV
 */
public class CroupierView extends JFrame {

    private JCheckBoxMenuItem manualCheck = new JCheckBoxMenuItem("Mode manuel", Casino.batchMode);
    private JCheckBoxMenuItem HistoryVue = new JCheckBoxMenuItem("Historiques", false);
    private JCheckBoxMenuItem cloud = new JCheckBoxMenuItem("cloud", false);
    private JCheckBoxMenuItem failwinsVue = new JCheckBoxMenuItem("Fails/Wins", false);
    private JCheckBoxMenuItem tableVue = new JCheckBoxMenuItem("Voir la table", false);
    private JMenuItem addPlayer = new JMenuItem("Ajouter Joueur");
    private JMenuItem nbrS = new JMenuItem("Nbr sessions:" + Croupier.getNbrSessions());
    private JButton go = new JButton("Start session");
    private JButton goSession = new JButton("New group session");
    private JLabel nbrSrest = new JLabel("Sessions restantes:" + Croupier.getNbrSessions());

    // private Croupier model;
    public CroupierView() {
        super("Roulette");

        JMenuBar menu = new JMenuBar();
        JMenu configCroup = new JMenu("Croupier Config");
        JMenu configVue = new JMenu("Fenetres");
        JMenu joueurs = new JMenu("Joueurs");
        joueurs.add(addPlayer);
        configCroup.add(manualCheck);

        configCroup.add(nbrS);
        menu.add(configCroup);


        configVue.add(tableVue);
        configVue.addSeparator();
        configVue.add(HistoryVue);
        configVue.add(cloud);
        configVue.add(failwinsVue);
        menu.add(configVue);
        menu.add(joueurs);
        setJMenuBar(menu);
        getContentPane().setLayout(new FlowLayout());

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();

                if (cloud.isSelected()) {
                    SessionController.addSessionListener(new CloudGraph());
                }
                if (failwinsVue.isSelected()) {
                    SessionController.addSessionListener(new FailsWinsGraph());
                }
                if (HistoryVue.isSelected()) {
                    SessionController.addSessionListener(new HistoryGraph());
                }

                go.setText("continue");
                nbrSrest.setText("Sessions restantes:" + Croupier.getNbrSessions());
                Croupier.doClick();
            }
        });


        goSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();
                go.setText("Start session");
                nbrSrest.setText("Sessions restantes:" + Croupier.getNbrSessions());
                Croupier.newSessionGroup();
            }
        });



        manualCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Casino.batchMode = (manualCheck.isSelected());
            }
        });


        addPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Croupier.addPlayer(new Player());
            }
        });
        SessionController.addSessionListener(Report.getInstance());

        JPanel butPanel = new JPanel();
        butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.Y_AXIS));

        butPanel.add(go);
        butPanel.add(nbrSrest);
        butPanel.add(goSession);

        getContentPane().add(butPanel, BorderLayout.SOUTH);

        setVisible(true);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Casino.batchMode = true;
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
