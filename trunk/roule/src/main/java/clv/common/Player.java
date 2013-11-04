/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import clv.AbstractPlayer;
import clv.sub.Etat;
import clv.sub.Mise;
import clv.sub.RouletteNumber.RouletteColor;
import clv.view.PlayerView;

/**
 *
 * @author CLV
 */
public class Player extends AbstractPlayer {

    //parie uniquement sur noir
    public Player() {

        lastState = new Etat();
        mise = new Mise();
        config = new PlayerConfig();
        view = new PlayerView(this);
    }

    @Override
    public void bet() {
        int miseAFaire = 1;
        if (lastState.getNumber().getCoul() == RouletteColor.RED) {
            miseAFaire = (lastState.getMise().getNOIR() * 2);
        }
        portefeuille -= miseAFaire;
        mise.setNOIR(miseAFaire);
        System.out.println(id + " " + portefeuille + " " + mise.toString());
    }
}
