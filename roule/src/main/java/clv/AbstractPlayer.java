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
    protected boolean dead = false;
    protected boolean win = false;

    public boolean gameover() {
        if (!(dead || win)) {
            return isDead() || isWin();
        } else {
            return dead || win;
        }
    }

    public boolean isWin() {
        if (portefeuille >= config.getPortefeuilleStart() * config.getGoalWin()) {
            win = true;
        }
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isDead() {
        if (portefeuille <= 0) {
            dead = true;
        }
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

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

    public void raz() {
        lastState = new Etat();
        mise = new Mise();
        portefeuille = config.getPortefeuilleStart();
        dead = false;
        win = false;
        initValues();
    }

    public abstract void initValues();
}
