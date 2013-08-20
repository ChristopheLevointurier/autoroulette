package clv;

import clv.Controller.SessionController;
import clv.common.Config;
import clv.common.Session;
import clv.sub.RouletteNumber;
import clv.sub.RouletteNumber.RouletteColor;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Runnable {

    private int amountData = 0;
    private static Random r = new Random(System.currentTimeMillis());
    private RouletteColor pari = RouletteColor.RED;

    public Player() {
        new Thread(this).start();
    }

    private void switchh() {
        if (pari == RouletteColor.RED) {
            pari = RouletteColor.BLACK;
        } else {
            pari = RouletteColor.RED;
        }
    }

    @Override
    public void run() {
        while (amountData < Config.getMAX_RUNS()) {
            int cptFails = 0;
            boolean isdropped = false;
            int cptFailsMax = 0;
            int nbrswitch = 0;
            ArrayList<Integer> hist = new ArrayList<>();
            int portefeuille = Config.getPortefeuilleStart();
            boolean boostepogne = false;
            int miseEnJeu = 0;
            while (portefeuille < (Config.getPortefeuilleStart() * Config.getGoalWin()) && portefeuille > 0 && (!isdropped ||(isdropped && portefeuille <= Config.getPortefeuilleStart()))) {
                isdropped = isdropped ? isdropped : (Config.isUseDropManagenull() && portefeuille < Config.getPortefeuilleStart() / 2);
                RouletteNumber lance = Main.table.get(r.nextInt(37));
                miseEnJeu = Config.getMises().get(cptFails);
                if (boostepogne) {
                    miseEnJeu *= 2;
                }
                if (miseEnJeu > portefeuille) {
                    miseEnJeu = portefeuille;
                }
                portefeuille -= miseEnJeu;

                if (lance.getCoul() == pari) {
                    portefeuille += miseEnJeu * 2;
                    cptFails = 0;
                    //         System.out.println("Pari:" + pari + "-" + lance + "=WIN, mise=" + miseEnJeu+ " Fails:" + cptFails);
                    boostepogne = (Config.isUseBoostPogne() && portefeuille > Config.getPortefeuilleStart() / 2);
                    switchh();
                    nbrswitch++;
                } else {
                    if (lance.getCoul() == RouletteColor.GREEN) {
                        portefeuille += miseEnJeu / 2;
                        //         System.out.println("Pari:" + pari + "-" + lance + "=VERT, mise=" + miseEnJeu+ " Fails:" + cptFails);
                    } else {

                        cptFails++;
                        cptFailsMax = cptFails > cptFailsMax ? cptFails : cptFailsMax;
                        //           System.out.println("Pari:" + pari + "-" + lance + "=FAIL, mise=" + miseEnJeu+ " Fails:" + cptFails);
                    }
                }
                hist.add(portefeuille);
            }
            amountData++;
            SessionController.addSession(new Session(hist, cptFailsMax, nbrswitch));
        }
        new FailsWinsGraph();
    }
}
