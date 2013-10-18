/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view;

import clv.AbstractPlayer;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author CLV
 */
public class PlayerView extends JFrame {

    private AbstractPlayer model;
    private JTextField multip = new JTextField("Portefeuille");

    public PlayerView(AbstractPlayer _model) {
        super("playerView");
        model = _model;
        getContentPane().add(multip);
        setVisible(true);
        pack();
    }

    public void maj() {
        multip.setText("" + model.getPortefeuille());
    }
}
