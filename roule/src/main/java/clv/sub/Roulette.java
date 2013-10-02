/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.sub;

import static clv.sub.typeRoulette.FR;
import static clv.sub.typeRoulette.US;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author CLV
 */
enum typeRoulette {

    FR, US, MEX
};

public class Roulette {

    private static Random r = new Random(System.currentTimeMillis());
    private static HashMap<Integer, RouletteNumber> table = new HashMap<>();
    private static Roulette instance;

    private Roulette(typeRoulette t) {
        switch (t) {
            case FR:
                for (int i = 0; i <= 36; i++) {
                    table.put(i, new RouletteNumber(i));
                }
            case US: {

                table.put(37, new RouletteNumber(37));
            }
            case MEX: {
                table.put(38, new RouletteNumber(37));
            }
        }
        System.out.println("init roulette " + t.name() + " done:");
        for (RouletteNumber r : table.values()) {
            System.out.print(r);
        }
    }

    private RouletteNumber getNext() {
        return table.get(r.nextInt(table.size()));
    }

    private static Roulette getInstance() {
        if (instance == null) {
            instance = new Roulette(FR);
        }
        return instance;
    }

    public static RouletteNumber getNextNumber() {
        return getInstance().getNext();
    }
}
