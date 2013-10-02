package clv.histo;

import java.util.Random;
import clv.Main;
import clv.sub.Roulette;
import clv.sub.RouletteNumber;
import clv.sub.RouletteNumber.RouletteColor;

public class Histo implements Runnable {

	private static final int MAX_RUNS = 100000;
	private RouletteColor pari = RouletteColor.RED;
	private boolean switchOnO = false;

	private HistoGraph g;
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
	}

	public void run() {
		int cptFails = 0;
		int cptWinws = 0;
		while (cptRuns < MAX_RUNS) {
			RouletteNumber lance = Roulette.getNextNumber();
			cptRuns++;

			if (lance.getCoul() == pari) {
				if (cptFails != 0)
					g.addFailSerie(cptFails);
				cptFails = 0;
				cptWinws++;
				switchh();
			} else {
				if (lance.getCoul() == RouletteColor.GREEN) {
					if (switchOnO) {
						switchh();
					}
				} else {
					if (cptWinws != 0)
						g.addWinSerie(cptWinws);
					cptWinws = 0;
					cptFails++;
				}
			}
		}
		// try { Thread.sleep(1); } catch (InterruptedException e) { }
		g.removeEmptyParts();
		g.sort();
	}

}
