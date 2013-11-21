/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.sub;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author CLV
 */
public class Roulette {

    public enum TypeRoulette {

        FR, EN, US, MEX
    };
    private static Random r = new Random(System.currentTimeMillis());
    private static HashMap<Integer, RouletteNumber> table = new HashMap<>();
    private static Roulette instance;
    private TypeRoulette type = TypeRoulette.EN;

    private Roulette(TypeRoulette t) {
        type = t;
        for (int i = 0; i <= 36; i++) {
            table.put(i, new RouletteNumber(i));
        }
        switch (t) {
            case FR:
            case EN:
                break;
            case US: {
                table.put(37, new RouletteNumber(37));
                break;
            }
            case MEX: {
                table.put(38, new RouletteNumber(37));
                break;
            }
        }
        System.out.println("init roulette " + t.name() + " done:");
        for (RouletteNumber r : table.values()) {
            System.out.println(r);
        }
    }

    private RouletteNumber getNext() {
        return table.get(r.nextInt(table.size()));
    }

    private static Roulette getInstance() {
        if (instance == null) {
            instance = new Roulette(TypeRoulette.FR);
        }
        return instance;
    }

    public static Roulette setType(TypeRoulette t) {
        instance = new Roulette(t);
        return instance;
    }

    public static TypeRoulette getType() {
        return getInstance().type;
    }

    public static RouletteNumber getNextNumber() {
        return getInstance().getNext();
    }

    public static RouletteNumber get(int v) {
        return getInstance().table.get(v);
    }
}
