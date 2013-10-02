/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv;

import clv.sub.Roulette;
import clv.sub.RouletteNumber;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLV
 */
public class Croupier {

    private static List<IPlayer> players = new ArrayList<>();

    public static void addPlayer(IPlayer p) {
        players.add(p);
    }

    public static void removePlayer(IPlayer p) {
        players.remove(p);
    }

    public static void run() {
        for (IPlayer p : players) {
            p.bet();
        }
        RouletteNumber number = Roulette.getNextNumber();
        for (IPlayer p : players) {
            pay(p, number);
        }
    }

    private static void pay(IPlayer p, RouletteNumber number) {
        //TODO
    }   
}
