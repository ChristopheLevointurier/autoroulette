package clv;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main extends JFrame {

	private static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private JComboBox misesBox;
	private DefaultComboBoxModel model = new DefaultComboBoxModel(new String[] { "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048", "4096", "9192" });

	private JButton go, addComboValue, delcombovalue;
	private ArrayList<Integer> mises = new ArrayList<Integer>();
	private int portefeuilleStart = 10000;
	private JTextField portef = new JTextField("" + portefeuilleStart);
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
		for (RouletteNumber r : table.values()) {
			System.out.println(r);
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
		majsettings();
		g = new Graph(portefeuilleStart, mises.size());
		misesBox = new JComboBox(model);
		misesBox.setEditable(true);
		misesBox.addActionListener(new ActionListener() {
			private int selectedIndex = -1;

			public void actionPerformed(ActionEvent e) {
				int index = misesBox.getSelectedIndex();
				if (index >= 0) {
					selectedIndex = index;
				} else if ("comboBoxEdited".equals(e.getActionCommand())) {
					Object newValue = model.getSelectedItem();
					model.removeElementAt(selectedIndex);
					model.addElement(newValue);
					misesBox.setSelectedItem(newValue);
					selectedIndex = model.getIndexOf(newValue);
				}
			}
		});

		add(misesBox);

		go = new JButton("Launch");
		addComboValue = new JButton("Ajout tour de mise");
		delcombovalue = new JButton("Suppression tour de mise");
		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				majsettings();
				g = new Graph(portefeuilleStart, mises.size());
			}
		});

		addComboValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addElement("000");
				misesBox.setSelectedIndex(model.getSize() - 1);
			}
		});
		delcombovalue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getSize() > 1) {
					model.removeElementAt(model.getSize() - 1);
					misesBox.setSelectedIndex(model.getSize() - 1);
				}
			}
		});

		add(addComboValue);
		add(delcombovalue);
		add(portef);
		add(go);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void majsettings() {
		mises.clear();
		for (int i = 0; i < model.getSize(); i++) {
			mises.add((Integer.parseInt((String) model.getElementAt(i))));
		}
		portefeuilleStart = Integer.parseInt("" + portef.getText());
	}

	private void joue() {
		while (true) {
			int cptFails = 0;
			int cptFailsMax = 0;
			int cptRuns = 0;
			int portefeuille = portefeuilleStart;
			while (cptFails < mises.size() && portefeuille < portefeuilleStart * 2 && portefeuille > 0) {
				RouletteNumber lance = table.get(r.nextInt(37));
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
