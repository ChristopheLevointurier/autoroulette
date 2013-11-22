/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import clv.AbstractPlayer;
import clv.sub.RouletteNumber.RouletteColor;
import clv.view.PlayerView;
import java.util.Random;

/**
 *
 * @author CLV
 */
public class Player extends AbstractPlayer {

    public Player() {
        config = new PlayerConfig();
        raz();
        view = new PlayerView(this);
    }

    @Override
    public void bet() {
        int miseAFaireROUGE = 0;
        int miseAFaireNOIR = 0;
        if (lastState.getMise().getNOIR() > 0 && lastState.getNumber().getCoul() == RouletteColor.RED) {
            miseAFaireNOIR = (lastState.getMise().getNOIR() * 2);
        }
        if (lastState.getMise().getROUGE() > 0 && lastState.getNumber().getCoul() == RouletteColor.BLACK) {
            miseAFaireROUGE = (lastState.getMise().getROUGE() * 2);
        }

        if (lastState.getMise().getNOIR() > 0 && lastState.getNumber().getCoul() == RouletteColor.BLACK) {
            if (config.isUseswitch()) {
                miseAFaireROUGE = 1;
                miseAFaireNOIR = 0;
            } else {
                miseAFaireNOIR = 1;
                miseAFaireROUGE = 0;
            }
        }
        if (lastState.getMise().getROUGE() > 0 && lastState.getNumber().getCoul() == RouletteColor.RED) {
            if (config.isUseswitch()) {
                miseAFaireNOIR = 1;
                miseAFaireROUGE = 0;
            } else {
                miseAFaireROUGE = 1;
                miseAFaireNOIR = 0;
            }
        }

        portefeuille -= miseAFaireROUGE;
        portefeuille -= miseAFaireNOIR;
        mise.setROUGE(miseAFaireROUGE);
        mise.setNOIR(miseAFaireNOIR);
    }

    @Override
    public String toString() {
        return id + " " + portefeuille + " " + mise.toString();
    }

    @Override
    public void initValues() {
        Random r = new Random(System.currentTimeMillis());
        if (r.nextBoolean()) { //commence au hasard sur  noir ou rouge
            mise.setNOIR(1);
        } else {
            mise.setROUGE(1);
        }
    }
}
