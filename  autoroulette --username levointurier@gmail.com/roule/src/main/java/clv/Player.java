package clv;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import clv.Main.RouletteColor;

public class Player implements Runnable {

	private static final int MAX_RUNS=400000;
	private ArrayList<Integer> mises = new ArrayList<Integer>();
	private int portefeuilleStart;
	private boolean running = true;
	private int amountData = 0;
	private boolean useBoostPogne = true;
	private Graph g;
	private static Random r = new Random(System.currentTimeMillis());
	private RouletteColor pari = RouletteColor.RED;

	public Player(int _portefeuilleStart, ListModel _mises, boolean _useBoostPogne) {
		mises.clear();
		for (int i = 0; i < _mises.getSize(); i++) {
			mises.add((Integer.parseInt(((String) _mises.getElementAt(i)).trim())));
		}
		portefeuilleStart = _portefeuilleStart;
		useBoostPogne = _useBoostPogne;
		new Thread(this).start();
	}

	private void switchh() {
		if (pari == RouletteColor.RED) {
			pari = RouletteColor.BLACK;
		} else
			pari = RouletteColor.RED;
	}

	public void run() {

		StringBuilder misesString = new StringBuilder();
		for (int mi : mises) {
			misesString.append(mi).append("#");
		}

		g = new Graph(portefeuilleStart, mises.size(), useBoostPogne, misesString.toString());
		running = true;
		while (running) {
			int cptFails = 0;
			int cptFailsMax = 0;
			int cptRuns = 0;
			int portefeuille = portefeuilleStart;
			boolean boostepogne = false;
			while (cptFails < mises.size() && portefeuille < portefeuilleStart * 2 && portefeuille > 0) {
				RouletteNumber lance = Main.table.get(r.nextInt(37));
				cptRuns++;
				int miseEnJeu = mises.get(cptFails);
				if (boostepogne)
					miseEnJeu *= 2;
				portefeuille -= miseEnJeu;
				if (lance.getCoul() == pari) {
					portefeuille += miseEnJeu * 2;
					cptFails = 0;
					// System.out.println("Pari:" + pari + "-" + lance + "=WIN, gain=" + (portefeuille - 10000) + "     lancé n°" + cptRuns);
					boostepogne = (portefeuille > portefeuilleStart) && useBoostPogne;
					switchh();
				} else {
					if (lance.getCoul() == RouletteColor.GREEN) {
						portefeuille += miseEnJeu / 2;
						// System.out.println("Pari:" + pari + "-" + lance + "gain="+ (portefeuille - 10000) + "     lancé n°" + cptRuns);
					} else {
						cptFails++;
						cptFailsMax = cptFails > cptFailsMax ? cptFails : cptFailsMax;
						// System.out.println("Pari:" + pari + "-" + lance + "=FAIL, gain=" + (portefeuille - 10000) + " Fails:" + cptFails + "     lancé n°" + cptRuns);
					}
				}
			}
			amountData++;
			g.addData(cptFailsMax, portefeuille, cptRuns);
			running = amountData < MAX_RUNS;
			// try { Thread.sleep(1000); } catch (InterruptedException e) { }
		}
		amountData = 0;
	}

}
