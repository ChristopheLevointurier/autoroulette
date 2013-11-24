/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.sub;

/**
 *
 * @author CLV
 */
public class Etat {

    private Mise mise;
    private RouletteNumber number;
    private boolean pucelle = true;

    public Mise getMise() {
        return mise;
    }

    public Etat() {
        mise = new Mise();
        number = Roulette.get(0);
        pucelle = true;
    }

    public void setMise(Mise mise) {
        this.mise = mise;
    }

    public RouletteNumber getNumber() {
        return number;
    }

    public void setNumber(RouletteNumber number) {
        this.number = number;
    }

    public boolean isPucelle() {
        return pucelle;
    }

    public void setPucelle(boolean pucelle) {
        this.pucelle = pucelle;
    }
}
