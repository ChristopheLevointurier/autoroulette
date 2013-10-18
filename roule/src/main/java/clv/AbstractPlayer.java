/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv;

import clv.sub.Mise;
import clv.common.PlayerConfig;
import clv.sub.Etat;
import clv.sub.RouletteNumber;
import clv.view.PlayerView;
import java.util.Random;

/**
 *
 * @author CLV
 */
public abstract class AbstractPlayer {

    protected String id = "Player_" + new Random(System.currentTimeMillis()).nextLong();

    public abstract void bet();
    protected PlayerConfig config;
    protected Mise mise;
    protected int portefeuille;
    protected Etat lastState;
    protected PlayerView view;

    public PlayerConfig getConfig() {
        return config;
    }

    public void addMoney(int p) {
        setPortefeuille(portefeuille + p);
    }

    public Mise getMise() {
        return mise;
    }

    public int getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(int portefeuille) {
        this.portefeuille = portefeuille;
        view.maj();
    }

    public void pushState(RouletteNumber r) {
        lastState.setNumber(r);
        lastState.setMise(mise);
    }
}