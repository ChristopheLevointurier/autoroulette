package clv;

import javax.swing.JProgressBar;

public class Casino {

    private static final long serialVersionUID = 2597779237651500313L;
    public static JProgressBar bar = new JProgressBar();

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Croupier(true);
    }
}
