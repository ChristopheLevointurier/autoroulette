/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.Casino;
import clv.Controller.EventController;
import clv.Controller.EventListener;
import clv.Controller.SessionController;
import clv.Croupier;
import clv.common.Player;
import clv.common.Report;
import clv.common.Utils;
import clv.sub.ValueSelectorMenuItem;
import clv.view.sub.RouletteView;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author CLV
 */
public class CroupierView extends JFrame implements EventListener {

    private JCheckBoxMenuItem manualCheck = new JCheckBoxMenuItem("Mode manuel", Casino.batchMode);
    private JCheckBoxMenuItem historyVue = new JCheckBoxMenuItem("Historiques", true);
    private JCheckBoxMenuItem cloud = new JCheckBoxMenuItem("cloud", false);
    private JCheckBoxMenuItem failwinsVue = new JCheckBoxMenuItem("Fails/Wins", false);
    private JCheckBoxMenuItem tableVue = new JCheckBoxMenuItem("Voir la table", false);
    private JMenuItem addPlayer = new JMenuItem("Ajouter Joueur");
    private JMenuItem addPlayers = new JMenuItem("Ajouter Joueurs");
    private JMenuItem nbrS = new JMenuItem("Nbr sessions:" + Croupier.getNbrSessions());
    private JButton go = new JButton("Spin");
    private JButton finishSession = new JButton("Finish session");
    private JButton resetSessions = new JButton("New group session");
    private JTextField nbrSpin = new JTextField("Spins :" + Croupier.getNbrSpins());
    private JTextField nbrSrest = new JTextField("Sessions restantes:" + Croupier.getNbrSessions());
    private JTextField nbrJoueurs = new JTextField("Joueurs :" + Croupier.getPlayerAmount());
    private RouletteView roul = new RouletteView();
    private HistoryGraph historyVueFrame = new HistoryGraph();

    // private Croupier model;
    public CroupierView() {
        super("Roulette");

        JMenuBar menu = new JMenuBar();
        JMenu configCroup = new JMenu("Croupier Config");
        JMenu configVue = new JMenu("Fenetres");
        JMenu joueurs = new JMenu("Joueurs");
        joueurs.add(addPlayer);
        joueurs.add(addPlayers);
        configCroup.add(manualCheck);

        configCroup.add(nbrS);
        menu.add(configCroup);


        configVue.add(tableVue);
        configVue.addSeparator();
        configVue.add(historyVue);
        configVue.add(cloud);
        configVue.add(failwinsVue);
        menu.add(configVue);
        menu.add(joueurs);
        setJMenuBar(menu);
        getContentPane().setLayout(new FlowLayout());

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));
        SessionController.addSessionListener(historyVueFrame);

        historyVue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                historyVueFrame.setVisible(historyVue.isSelected());
            }
        });


        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Croupier.doClickSpin();
                refreshData();
            }
        });
        finishSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Croupier.doClicksession();
                refreshData();
            }
        });

        resetSessions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report.getReport().clear();
                Croupier.newSessionGroup();
                refreshData();
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
                EventController.broadcast(Utils.AppEvent.NEW_PLAYER);
            }
        });

        addPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ValueSelectorMenuItem selec = new ValueSelectorMenuItem("1", "Nouveaux joueurs:");
                int nbr = selec.getIntValue();
                for (int i = 0; i < nbr; i++) {
                    Croupier.addPlayer(new Player());
                }
                EventController.broadcast(Utils.AppEvent.NEW_PLAYER);
            }
        });

        SessionController.addSessionListener(Report.getInstance());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

        JPanel session = new JPanel();
        session.setLayout(new BoxLayout(session, BoxLayout.X_AXIS));

        session.add(go);
        session.add(finishSession);
        session.add(roul);
        session.add(resetSessions);

        nbrSpin.setEditable(false);
        nbrSrest.setEditable(false);
        nbrJoueurs.setEditable(false);

        infoPanel.add(nbrSpin);
        infoPanel.add(nbrSrest);
        infoPanel.add(nbrJoueurs);

        mainPanel.add(session);
        mainPanel.add(infoPanel);

        getContentPane().add(mainPanel);

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
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

        EventController.addListener(this);
    }

    private void refreshData() {
        nbrSpin.setText("Spins :" + Croupier.getNbrSpins());
        nbrJoueurs.setText("Joueurs :" + Croupier.getPlayerAmount());
        nbrSrest.setText("Sessions restantes:" + Croupier.getNbrSessions());
    }

    @Override
    public void updateInternalData(Utils.AppEvent s) {
        refreshData();
    }
}
