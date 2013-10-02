package clv.sub;

public class RouletteNumber {

    private RouletteSixain six;
    private RouletteColor coul = RouletteColor.BLACK;
    private int valeur;

    public RouletteNumber(int value) {
        valeur = value;
        if (valeur == 0 || valeur == 37 || valeur == 38) {
            coul = RouletteColor.GREEN;
        }
        if (valeur == 1 || valeur == 3 || valeur == 5 || valeur == 7 || valeur == 9 || valeur == 12 || valeur == 14 || valeur == 16 || valeur == 18 || valeur == 19 || valeur == 21 || valeur == 23 || valeur == 25 || valeur == 27 || valeur == 30 || valeur == 32 || valeur == 34 || valeur == 36) {
            coul = RouletteColor.RED;
        }

        switch (valeur / 6) {
            case 1: {
                six = RouletteSixain.UN;
                break;
            }
            case 2: {
                six = RouletteSixain.DEUX;
                break;
            }
            case 3: {
                six = RouletteSixain.TROIS;
                break;
            }
            case 4: {
                six = RouletteSixain.QUATRE;
                break;
            }
            case 5: {
                six = RouletteSixain.CINQ;
                break;
            }
            case 6: {
                six = RouletteSixain.SIX;
                break;
            }
            default: {
                six = RouletteSixain.ZERO;
                break;
            }
        }
    }

    public RouletteColor getCoul() {
        return coul;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return ("(" + valeur + "," + coul + ","+six+")");
    }

    public enum RouletteColor {

        BLACK, RED, GREEN;
    }

    public enum RouletteSixain {

        ZERO, UN, DEUX, TROIS, QUATRE, CINQ, SIX;
    }
}