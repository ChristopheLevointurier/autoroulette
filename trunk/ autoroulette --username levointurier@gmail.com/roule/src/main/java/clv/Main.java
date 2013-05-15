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

	private static final long serialVersionUID = 2597779237651500313L;

	public static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private JComboBox misesBox;
	private final DefaultComboBoxModel model = new DefaultComboBoxModel(new String[] { "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024"});
	private JButton go, addComboValue, delcombovalue;
	private JTextField portef = new JTextField("100");
	private final Player play = new Player();;

	static {
		for (int i = 0; i <= 36; i++) {
			table.put(i, new RouletteNumber(i));
		}

		System.out.println("init:");
		for (RouletteNumber r : table.values()) {
			System.out.println(r);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
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
					model.insertElementAt(newValue, selectedIndex);
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
				play.stop();
				play.start(Integer.parseInt("" + portef.getText()), model);
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

}
