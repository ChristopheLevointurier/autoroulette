/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.view.sub;

import clv.Controller.EventController;
import clv.Controller.EventListener;
import clv.Croupier;
import clv.common.Utils;
import clv.sub.RouletteNumber;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author CLV
 */
public class RouletteView extends JPanel implements EventListener {

    private RouletteNumberView number;

    public RouletteView() {
        number = new RouletteNumberView();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(number);
        EventController.addListener(this);
    }

    @Override
    public void updateInternalData(Utils.AppEvent s) {
        if (s.equals(Utils.AppEvent.NEW_NUMBER)) {
            number.setToDisplay(Croupier.getNumber());
        }
    }
}
