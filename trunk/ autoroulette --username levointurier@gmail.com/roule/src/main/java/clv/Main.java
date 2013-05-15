package clv;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Main extends JFrame {

	private static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private JComboBox misesBox;
	private JButton go;

	private int[] mises = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
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
		Main m = new Main();
		m.joue();
	}

	public enum RouletteColor {
		BLACK, RED, GREEN;

	}

	public Main() {
		super("Roulette");
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		getContentPane().setLayout(new FlowLayout());
		g = new Graph();
		misesBox = new JComboBox(new String[] { "1", "2", "4" });
		misesBox.setEditable(true);
		misesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// misesBox.removeActionListener(this);
				misesBox.insertItemAt(misesBox.getSelectedItem(), 0);
				// misesBox.addActionListener(this);
			}
		});
		add(misesBox);

		go = new JButton("Launch");
		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				g = new Graph();
				// joue();
			}
		});
		add(go);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

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
