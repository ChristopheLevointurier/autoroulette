package clv;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ListModel;
import clv.Main.RouletteColor;

public class Player implements Runnable {

	private ArrayList<Integer> mises = new ArrayList<Integer>();
	private int portefeuilleStart;
	private boolean running = true;
	private boolean useBoostPogne = true;
	private Graph g;
	private static Random r = new Random(System.currentTimeMillis());
	private RouletteColor pari = RouletteColor.RED;

	public Player() {
	}

	private void switchh() {
		if (pari == RouletteColor.RED) {
			pari = RouletteColor.BLACK;
		} else
			pari = RouletteColor.RED;
	}

	public void stop() {
		running = false;
	}

	public void start(int _portefeuilleStart, ListModel _mises, boolean _useBoostPogne) {
		mises.clear();
		for (int i = 0; i < _mises.getSize(); i++) {
			mises.add((Integer.parseInt(((String) _mises.getElementAt(i)).trim())));
		}
		portefeuilleStart = _portefeuilleStart;
		useBoostPogne = _useBoostPogne;
		new Thread(this).start();
	}

	public void run() {

		g = new Graph(portefeuilleStart, mises.size(), useBoostPogne);
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
					// System.out.println("Pari:" + pari + "-" + lance + "=WIN, gain=" + (portefeuille - 10000) + "     lanc� n�" + cptRuns);
					boostepogne = (portefeuille > portefeuilleStart) && useBoostPogne;
					switchh();
				} else {
					if (lance.getCoul() == RouletteColor.GREEN) {
						portefeuille += miseEnJeu / 2;
						// System.out.println("Pari:" + pari + "-" + lance + "gain="+ (portefeuille - 10000) + "     lanc� n�" + cptRuns);
					} else {
						cptFails++;
						cptFailsMax = cptFails > cptFailsMax ? cptFails : cptFailsMax;
						// System.out.println("Pari:" + pari + "-" + lance + "=FAIL, gain=" + (portefeuille - 10000) + " Fails:" + cptFails + "     lanc� n�" + cptRuns);
					}
				}
			}
			g.addData(cptFailsMax, portefeuille, cptRuns);
		//	 try { Thread.sleep(1000); } catch (InterruptedException e) { }
		}
	}

}
