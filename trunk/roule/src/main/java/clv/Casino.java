package clv;

import static clv.Croupier.runAllSessions;
import clv.view.CroupierView;
import javax.swing.JProgressBar;

public class Casino {

    private static final long serialVersionUID = 2597779237651500313L;
    public static JProgressBar bar = new JProgressBar();
    public static boolean batchMode = false;

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (!batchMode) {
            new CroupierView();
        }
        Croupier.run();
    }
}
