package clv.histo;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clv.sub.RouletteNumber;

public class MainHisto extends JFrame {

    private static final long serialVersionUID = 25977765461500313L;
    public static HashMap<Integer, RouletteNumber> table = new HashMap<Integer, RouletteNumber>();
    private JButton go;
    private JCheckBox boost = new JCheckBox("switchOnGreen", false);

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
        new MainHisto();
    }

    public MainHisto() {
        super("Roulette");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getContentPane().setLayout(new FlowLayout());

        go = new JButton("Launch");

        JPanel listButs = new JPanel();
        listButs.setLayout(new BoxLayout(listButs, BoxLayout.Y_AXIS));

        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Histo(boost.isSelected());
            }
        });

        listButs.add(go);
        listButs.add(boost);
        add(listButs);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
