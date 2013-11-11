/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view.sub;

import clv.sub.RouletteNumber;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author CLV
 */
public final class RouletteNumberView extends JTextField {

    private RouletteNumber toDisplay;

    public RouletteNumberView() {
        setToDisplay(RouletteNumber.getNumber(0));
    }

    public void setToDisplay(RouletteNumber _toDisplay) {
        toDisplay = _toDisplay;
        upd();
    }

    public void upd() {
        setBackground(toDisplay.getCoul().getRealColor());
        // setForeground(toDisplay.getCoul().getRealColor());
        setText(" __ " + toDisplay.getValeur() + " __ ");
        setSize(getPreferredSize());
    }
}
