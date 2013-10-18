package clv;

import clv.view.FailsWinsGraph;
import clv.Controller.SessionController;
import clv.common.Config;
import clv.common.Session;
import clv.sub.RouletteNumber;
import clv.sub.RouletteNumber.RouletteColor;
import java.util.ArrayList;
import java.util.Random;

public class DoublePlayer implements Runnable {

    private int amountData = 0;
    private static Random r = new Random(System.currentTimeMillis());
    private int pariRouge = 1;
    private int pariNoir = 1;

    public DoublePlayer() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (amountData < Config.getMAX_RUNS()) {
            boolean isdropped = false;
            int failRouge = 0;
            int failNoir = 0;
            ArrayList<Integer> hist = new ArrayList<>();
            int portefeuille = Config.getPortefeuilleStart();

            while (portefeuille < (Config.getPortefeuilleStart() * Config.getGoalWin()) && portefeuille > 2 && (!isdropped || (isdropped && portefeuille <= Config.getPortefeuilleStart()))) {
                RouletteNumber lance = Main.table.get(r.nextInt(37));
                if (Config.isDoubleOnFail()) {
                    pariRouge = Config.getMises().get(failRouge);
                    pariNoir = Config.getMises().get(failNoir);
                } else {
                    pariRouge = Config.getMises().get(failNoir);
                    pariNoir = Config.getMises().get(failRouge);
                }

                if (pariRouge + pariNoir > portefeuille) {
                    if (pariRouge > pariNoir) {
                        pariRouge = portefeuille - pariNoir;
                    } else {
                        pariNoir = portefeuille - pariRouge;
                    }
                    if (failRouge > failNoir) {
                        failRouge--;
                    } else {
                        failNoir--;
                    }
                }
                //System.out.println(" failRouge " + failRouge + " failNoir" + failNoir + " portefeuille" + portefeuille + " pariRouge" + pariRouge + " pariNoir" + pariNoir + "   tirage:" + lance.getCoul());


                portefeuille -= pariRouge;
                portefeuille -= pariNoir;
                isdropped = isdropped ? isdropped : (Config.isUseDropManagenull() && portefeuille < Config.getPortefeuilleStart() / 2);

                if (lance.getCoul() == RouletteColor.RED) {
                    portefeuille += pariRouge * 2;
                    failRouge = 0;
                    failNoir++;
                }
                if (lance.getCoul() == RouletteColor.BLACK) {
                    portefeuille += pariNoir * 2;
                    failRouge++;
                    failNoir = 0;
                }
                if (lance.getCoul() == RouletteColor.GREEN) {
                    portefeuille += (int) ((pariRouge + pariNoir) / 2);
                }
                hist.add(portefeuille);
            }
            amountData++;
            SessionController.addSession(new Session(hist, 0, 0));
        }
        new FailsWinsGraph();
    }
}
