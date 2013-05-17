package clv;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import third.EditListAction;
import third.ListAction;

public class Main extends JFrame {

	private static final long serialVersionUID = 2597779237651500313L;

	public static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private JList misesBox;

	private String[] suiteNormale = new String[] { "1", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024" };
	private String[] suitePlus1 = new String[] { "1", "3", "7", "15", "31", "63", "127", "255", "511", "1023" };
	private String[] suitePlusCroissante = new String[] { "1", "3", "8", "19", "42", "89", "184", "375", "758" };

	private final DefaultListModel model = new DefaultListModel();
	private JButton go, addComboValue, delcombovalue, setNormal, setPlus1, setPlusCrois, setmultipl;
	private JTextField multip = new JTextField("2.000");
	private JTextField portefName = new JTextField("Nombre jetons start:");
	private JTextField portef = new JTextField("50");
	private JCheckBox boost = new JCheckBox("BoostePogne", true);
	private Player play;

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
		model.addElement("1                     ");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		model.addElement("1");
		misesBox = new JList(model);
		new ListAction(misesBox, new EditListAction());
		JScrollPane scrollList = new JScrollPane(misesBox);

		add(scrollList);
		add(boost);

		go = new JButton("Launch");

		JPanel listButs = new JPanel();
		listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

		addComboValue = new JButton("Ajout tour de mise");
		delcombovalue = new JButton("Suppression tour de mise");

		setNormal = new JButton("setnormal");
		setPlus1 = new JButton("set +1");
		setPlusCrois = new JButton("set +croiss");

		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				play = new Player(Integer.parseInt("" + portef.getText()), model, boost.isSelected());
			}
		});

		setNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				for (String s : suiteNormale)
					model.addElement(s);
			}
		});
		setPlus1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				for (String s : suitePlus1)
					model.addElement(s);
			}
		});
		setPlusCrois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				for (String s : suitePlusCroissante)
					model.addElement(s);
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

		setmultipl = new JButton("set multiplication");
		setmultipl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cap = model.capacity();
				model.removeAllElements();
				double val = 1;
				for (int i = 0; i < cap; i++) {
					model.addElement("" + (int) val);
					val *= Double.parseDouble("" + multip.getText());
				}
			}
		});

		JPanel mulpanel = new JPanel();
		mulpanel.setLayout(new FlowLayout());
		mulpanel.add(multip);
		mulpanel.add(setmultipl);
		add(mulpanel);

		listButs.add(addComboValue);
		listButs.add(delcombovalue);
		listButs.add(setNormal);

		listButs.add(setPlus1);
		listButs.add(setPlusCrois);
		add(listButs);

		JPanel porte = new JPanel();
		porte.setLayout(new FlowLayout());
		portefName.setEditable(false);
		porte.add(portefName);
		porte.add(portef);
		add(porte);
		add(go);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}