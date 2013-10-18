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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author CLV
 */
public class CroupierView extends JFrame {

    // private Croupier model;
    public CroupierView() {
        super("Roulette");
        // model = new Croupier();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
    }
}
