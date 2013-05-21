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

import clv.sub.RouletteNumber;

import third.EditListAction;
import third.ListAction;

public class Main extends JFrame {

	private static final long serialVersionUID = 2597779237651500313L;

	public static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();

	private JList misesBox;

	private String[] suitePlus1 = new String[] { "1", "3", "7", "15", "31", "63", "127", "255", "511", "1023", "2047" };
	private String[] suitePlusCroissante = new String[] { "1", "3", "8", "19", "42", "89", "184", "375", "758", "1525" };

	private final DefaultListModel model = new DefaultListModel();
	private JButton go, setNormal, setPlus1, setPlusCrois, setmultipl;
	private JTextField multip = new JTextField("2.000");
	private JTextField portefName = new JTextField("Nombre jetons start:");
	private JTextField portef = new JTextField("50      ");
	private JTextField goalName = new JTextField("condition win:");
	private JTextField goalf = new JTextField("2      ");
	private JCheckBox boost = new JCheckBox("BoostePogne", true);

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
		model.addElement("2");
		model.addElement("4");
		model.addElement("8");
		model.addElement("16");
		model.addElement("32");
		model.addElement("64");
		model.addElement("128");
		model.addElement("256");
		model.addElement("512");
		model.addElement("1024");
		model.addElement("2048");
		model.addElement("4096");
		misesBox = new JList(model);
		new ListAction(misesBox, new EditListAction());
		JScrollPane scrollList = new JScrollPane(misesBox);

		add(scrollList);
		go = new JButton("Launch");

		JPanel listButs = new JPanel();
		listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

		setNormal = new JButton("setnormal");
		setPlus1 = new JButton("set +1");
		setPlusCrois = new JButton("set +croiss");

		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new Player(Integer.parseInt("" + portef.getText().trim()), model, boost.isSelected(), Double.parseDouble(goalf.getText().trim()));
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

		setmultipl = new JButton("set multiplication");
		setmultipl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				double val = 1;
				for (int i = 0; i < 13; i++) {
					model.addElement("" + (int) val);
					val *= Double.parseDouble("" + multip.getText());
				}
			}
		});

		JPanel mulpanel = new JPanel();
		mulpanel.setLayout(new FlowLayout());
		mulpanel.add(multip);
		mulpanel.add(setmultipl);
		listButs.add(mulpanel);
		listButs.add(setNormal);

		listButs.add(setPlus1);
		listButs.add(setPlusCrois);
		listButs.add(boost);
		add(listButs);

		JPanel launchp = new JPanel();
		launchp.setLayout(new BoxLayout(launchp, BoxLayout.Y_AXIS));

		JPanel porte = new JPanel();
		porte.setLayout(new FlowLayout());
		portefName.setEditable(false);
		porte.add(portefName);
		porte.add(portef);
		launchp.add(porte);
		JPanel goalp = new JPanel();
		goalp.setLayout(new FlowLayout());
		portefName.setEditable(false);
		goalName.setEditable(false);
		goalp.add(goalName);
		goalp.add(goalf);
		launchp.add(goalp);
		launchp.add(go);
		add(launchp);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
