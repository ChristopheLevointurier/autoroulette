package clv;

import clv.Controller.SessionController;
import clv.common.Config;
import clv.common.Report;
import clv.common.Session;
import java.util.Random;
import clv.sub.RouletteNumber;
import clv.sub.RouletteNumber.*;
import java.util.ArrayList;

public class Player implements Runnable {

    private boolean running = true;
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
        running = true;
        while (running) {
            int cptFails = 0;
            int cptFailsMax = 0;
            int nbrswitch = 0;
            ArrayList<Integer> hist = new ArrayList<>();
            int portefeuille = Config.getPortefeuilleStart();
            boolean boostepogne = false;
            int miseEnJeu = 0;
            while (portefeuille < (Config.getPortefeuilleStart() * Config.getGoalWin()) && portefeuille > 0) {
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
            running = amountData < Config.getMAX_RUNS();
        }
             System.out.println("Config.getMAX_RUNS()"+Config.getMAX_RUNS()+" amountData="+amountData+" Report="+ Report.getReport().size());
       new FailsWinsGraph();
    }
}
