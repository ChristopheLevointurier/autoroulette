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

    public Mise getMise() {
        return mise;
    }

    public Etat() {
        mise = new Mise();
        number =  Roulette.get(0);
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
}
