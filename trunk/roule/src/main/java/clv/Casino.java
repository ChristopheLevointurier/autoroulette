package clv;

import clv.view.CroupierView;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Casino extends JFrame {

    private static final long serialVersionUID = 2597779237651500313L;
     public static JProgressBar bar = new JProgressBar();


    /**
     * @param args
     */
    public static void main(String[] args) {
        new CroupierView();
    }

}
