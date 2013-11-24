/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import clv.AbstractPlayer;
import clv.sub.RouletteNumber;
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
        if (!lastState.isPucelle() && lastState.getNumber().getValeur() != 0) {
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
            mise.setROUGE(miseAFaireROUGE);
            mise.setNOIR(miseAFaireNOIR);

        } else {
            lastState.setPucelle(false);
        }
        portefeuille -= mise.getNOIR();
        portefeuille -= mise.getROUGE();
    }

    @Override
    public String toString() {
        if (gameover()) {
            if (win) {
                return id + " " + portefeuille + "  is Win";
            }
            if (dead) {
                return id + " " + portefeuille + " is Dead";
            } else {
                return id + " " + portefeuille + " is bug";
            }
        } else {
            return id + " " + portefeuille + " " + mise.toString();
        }
    }

    @Override
    public void initValues() {
        Random r = new Random(System.currentTimeMillis());
        if (r.nextBoolean()) { //commence au hasard sur  noir ou rouge
            mise.setNOIR(1);
            System.out.println(" go for Black");
        } else {
            mise.setROUGE(1);
            System.out.println(" go for Red");
        }
    }
}
