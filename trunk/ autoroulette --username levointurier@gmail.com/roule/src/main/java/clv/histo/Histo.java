package clv.histo;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.util.SortOrder;

import clv.Graph;
import clv.Main;
import clv.sub.RouletteNumber;
import clv.sub.RouletteNumber.RouletteColor;

public class Histo implements Runnable {

	private static final int MAX_RUNS = 100000;
	private static Random r = new Random(System.currentTimeMillis());
	private RouletteColor pari = RouletteColor.RED;
	private boolean switchOnO = false;

	private HistoGraph g;
	private int nbrswitch = 0;
	private int cptRuns = 0;

	public Histo(boolean selected) {
		switchOnO = selected;
		g = new HistoGraph(switchOnO);
		new Thread(this).start();

	}

	private void switchh() {
		if (pari == RouletteColor.RED) {
			pari = RouletteColor.BLACK;
		} else {
			pari = RouletteColor.RED;
		}
		nbrswitch++;
	}

	public void run() {
		int cptFails = 0;
		int cptWinws = 0;
		while (nbrswitch < MAX_RUNS) {
			RouletteNumber lance = Main.table.get(r.nextInt(37));
			cptRuns++;

			if (lance.getCoul() == pari) {
				g.addFailSerie(cptFails, nbrswitch);
				cptFails = 0;
				cptWinws++;
				switchh();
			} else {
				if (lance.getCoul() == RouletteColor.GREEN) {
					if (switchOnO) {
						switchh();
					}
				} else {
					g.addWinSerie(cptFails, nbrswitch);
					cptWinws = 0;
					cptFails++;
				}
			}
		}
		// try { Thread.sleep(1); } catch (InterruptedException e) { }
		// g.removeEmptyParts();
		// g.sort(false, SortOrder.DESCENDING);
	}

}
