/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.AbstractPlayer;
import clv.Croupier;
import clv.common.Report;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author CLV
 */
public class PlayerView extends JFrame {

    private AbstractPlayer model;
    private JTextField multip = new JTextField("Portefeuille");
    private JButton config = new JButton("Config");

    public PlayerView(AbstractPlayer _model) {
        super("playerView");
        model = _model;
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(config);
        getContentPane().add(multip);
        setVisible(true);
        pack();


        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerConfigView(model);
            }
        });

    }

    public void maj() {
        multip.setText("" + model.getPortefeuille());
    }
}
