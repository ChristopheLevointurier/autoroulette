package clv.sub;

import clv.Main;
import clv.Main.RouletteColor;

public class RouletteNumber {

	private RouletteColor coul = RouletteColor.BLACK;
	private int valeur;

	public RouletteNumber(int value) {
		valeur = value;
		if (valeur == 0) {
			coul = RouletteColor.GREEN;
		}
		if (valeur == 1 || valeur == 3 || valeur == 5 || valeur == 7
				|| valeur == 9 || valeur == 12 || valeur == 14 || valeur == 16
				|| valeur == 18 || valeur == 19 || valeur == 21 || valeur == 23
				|| valeur == 25 || valeur == 27 || valeur == 30 || valeur == 32
				|| valeur == 34 || valeur == 36) {
			coul = RouletteColor.RED;
		}

	}

	public RouletteColor getCoul() {
		return coul;
	}

	public int getValeur() {
		return valeur;
	}

	public String toString() {
		return ("(" + valeur + "," + coul + ")");
	}

}
