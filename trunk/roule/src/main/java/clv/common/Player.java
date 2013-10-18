/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import clv.AbstractPlayer;
import clv.sub.Etat;
import clv.sub.RouletteNumber.RouletteColor;
import clv.view.PlayerView;

/**
 *
 * @author CLV
 */
public class Player extends AbstractPlayer {

    //parie uniquement sur noir
    public Player() {
        
        lastState= new Etat();
        view = new PlayerView(this);
    }

    @Override
    public void bet() {
        if ( lastState.getNumber().getCoul() == RouletteColor.BLACK) {
            mise.setNOIR(1);
        } else {
            mise.setNOIR(lastState.getMise().getNOIR() * 2);
        }
    }
}
