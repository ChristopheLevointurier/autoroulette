package clv;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ListModel;
import clv.Main.RouletteColor;

public class Player implements Runnable {

	private ArrayList<Integer> mises = new ArrayList<Integer>();
	private int portefeuilleStart;
	private boolean running = true;
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

	public void start(int _portefeuilleStart, ListModel _mises) {
		for (int i = 0; i < _mises.getSize(); i++) {
			mises.add((Integer.parseInt((String) _mises.getElementAt(i))));
		}
		portefeuilleStart = _portefeuilleStart;
		new Thread(this).start();
	}

	public void run() {

		g = new Graph(portefeuilleStart, mises.size());
		running = true;
		while (running) {
			int cptFails = 0;
			int cptFailsMax = 0;
			int cptRuns = 0;
			int portefeuille = portefeuilleStart;
			while (cptFails < mises.size() && portefeuille < portefeuilleStart * 2 && portefeuille > 0) {
				RouletteNumber lance = Main.table.get(r.nextInt(37));
				cptRuns++;
				portefeuille -= mises.get(cptFails) * 200;
				if (lance.getCoul() == pari) {
					portefeuille += mises.get(cptFails) * 2 * 200;
					cptFails = 0;
					// System.out.println("Pari:" + pari + "-" + lance +
					// "=WIN, gain=" + (portefeuille - 10000) + "     lancé n°"
					// + cptRuns);
					switchh();
				} else {
					if (lance.getCoul() == RouletteColor.GREEN) {
						portefeuille += (mises.get(cptFails) / 2) * 200;
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

			// try { Thread.sleep(100); } catch (InterruptedException e) { }

		}
	}

}
