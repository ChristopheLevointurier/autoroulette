package clv;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.jfree.util.SortOrder;

import clv.Main.RouletteColor;
import clv.sub.RouletteNumber;

public class Player implements Runnable {

	private static final int MAX_RUNS = 100000;
	private ArrayList<Integer> mises = new ArrayList<Integer>();
	private int portefeuilleStart;
	private boolean running = true;
	private int amountData = 0;
	private double goalWin;
	private boolean useBoostPogne = true;
	private Graph g;
	private static Random r = new Random(System.currentTimeMillis());
	private RouletteColor pari = RouletteColor.RED;

	public Player(int _portefeuilleStart, ListModel _mises, boolean _useBoostPogne, double _goalWin) {
		mises.clear();
		for (int i = 0; i < _mises.getSize(); i++) {
			mises.add((Integer.parseInt(((String) _mises.getElementAt(i)).trim())));
		}
		portefeuilleStart = _portefeuilleStart;
		useBoostPogne = _useBoostPogne;
		goalWin = _goalWin;
		new Thread(this).start();
	}

	private void switchh() {
		if (pari == RouletteColor.RED) {
			pari = RouletteColor.BLACK;
		} else
			{pari = RouletteColor.RED;}
	}

	public void run() {


		g = new Graph(portefeuilleStart, mises.size(), useBoostPogne, mises, goalWin);
		running = true;
		while (running) {
			int cptFails = 0;
			int cptFailsMax = 0;
			int nbrswitch=0;
			int cptRuns = 0;
			int portefeuille = portefeuilleStart;
			boolean boostepogne = false;
			while (portefeuille < (portefeuilleStart * goalWin) && portefeuille > 0) {
				RouletteNumber lance = Main.table.get(r.nextInt(37));
				cptRuns++;
				int miseEnJeu = mises.get(cptFails);
				if (boostepogne) {
					miseEnJeu *= 2;
				}
				if (miseEnJeu > portefeuille) {
					miseEnJeu = portefeuille;
				}
				portefeuille -= miseEnJeu;

				if (lance.getCoul() == pari) {
					portefeuille += miseEnJeu * 2;
					cptFails = 0;
					// System.out.println("Pari:" + pari + "-" + lance + "=WIN, gain=" + (portefeuille - 10000) + "     lancé n°" + cptRuns);
					boostepogne = (portefeuille > portefeuilleStart / 2) && useBoostPogne;
					switchh();
					nbrswitch++;
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
			g.addData(cptFailsMax, portefeuille, cptRuns,nbrswitch);
			running = amountData < MAX_RUNS;
			// try { Thread.sleep(1); } catch (InterruptedException e) { }
		}
		g.removeEmptyParts();
		g.sort(false, SortOrder.DESCENDING);
	}

}
