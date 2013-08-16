/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import java.util.ArrayList;

/**
 *
 * @author CLV
 */
public class Config {

    private static int portefeuilleStart = 50;
    private static boolean useBoostPogne = false;
    private static boolean useavoid = false;
    private static ArrayList<Integer> mises = new ArrayList<>();
    private static double goalWin;
    private static final int MAX_RUNS = 100000;

    public static int getPortefeuilleStart() {
        return portefeuilleStart;
    }

    public static void setPortefeuilleStart(int portefeuilleStart) {
        Config.portefeuilleStart = portefeuilleStart;
    }

    public static boolean isUseBoostPogne() {
        return useBoostPogne;
    }

    public static void setUseBoostPogne(boolean useBoostPogne) {
        Config.useBoostPogne = useBoostPogne;
    }

    public static boolean isUseavoid() {
        return useavoid;
    }

    public static void setUseavoid(boolean useavoid) {
        Config.useavoid = useavoid;
    }

    public static ArrayList<Integer> getMises() {
        return mises;
    }

    public static void setMises(ArrayList<Integer> mises) {
        Config.mises = mises;
    }

    public static double getGoalWin() {
        return goalWin;
    }

    public static void setGoalWin(double goalWin) {
        Config.goalWin = goalWin;
    }

    public static int getMAX_RUNS() {
        return MAX_RUNS;
    }
}
