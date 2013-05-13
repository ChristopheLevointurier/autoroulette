package clv;

import java.util.HashMap;
import java.util.Random;

public class Main {

	private static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private int[] mises = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512,1024,2048 };
	private int portefeuilleStart = 10000;
	private RouletteColor pari = RouletteColor.RED;
	private Graph g;
	private static Random r = new Random(System.currentTimeMillis());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// init

		for (int i = 0; i <= 36; i++) {
			table.put(i, new RouletteNumber(i));
		}

		System.out.println("init:");
		for (int i = 0; i <= 36; i++) {
			System.out.print(table.get(i));
		}
		Main m1 = new Main();
		m1.joue();

	}

	public enum RouletteColor {
		BLACK, RED, GREEN;

	}

	public Main() {
		System.out.println("***" + mises.length);
		g = new Graph();
	}

	private void joue() {

		while (true) {
			int cptFails = 0;
			int cptFailsMax = 0;
			int cptRuns = 0;
			int portefeuille = portefeuilleStart;
			while (cptFails < mises.length && portefeuille < portefeuilleStart * 2 && portefeuille > 0) {
				RouletteNumber lance = table.get(r.nextInt(37));
				cptRuns++;
				portefeuille -= mises[cptFails] * 200;
				if (lance.getCoul() == pari) {
					portefeuille += mises[cptFails] * 2 * 200;
					cptFails = 0;
					// System.out.println("Pari:" + pari + "-" + lance +
					// "=WIN, gain=" + (portefeuille - 10000) + "     lancé n°"
					// + cptRuns);
					switchh();
				} else {
					if (lance.getCoul() == RouletteColor.GREEN) {
						portefeuille += (mises[cptFails] / 2) * 200;
						// System.out.println("Pari:" + pari + "-" + lance +
						// "gain="+ (portefeuille - 10000) + "     lancé n°" +
						// cptRuns);
					} else {
						cptFails++;
						cptFailsMax = cptFails > cptFailsMax ? cptFails : cptFailsMax;
						// System.out.println("Pari:" + pari + "-" + lance +
						// "=FAIL, gain=" + (portefeuille - 10000) + " Fails:" +
						// cptFails + "     lancé n°" + cptRuns);
					}
				}
			}
			g.addData(cptFailsMax, portefeuille, cptRuns);
			/**
			 * try { Thread.sleep(100); } catch (InterruptedException e) { }
			 **/
		}
	}

	private void switchh() {
		if (pari == RouletteColor.RED) {
			pari = RouletteColor.BLACK;
		} else
			pari = RouletteColor.RED;
	}
}
