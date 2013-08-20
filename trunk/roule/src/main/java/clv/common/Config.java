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
    private static int avoid = 0;
    private static ArrayList<Integer> mises = new ArrayList<>();
    private static double goalWin;
    private static int MAX_RUNS = 100000;

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

    public static void setMAX_RUNS(int MAX_RUNS) {
        Config.MAX_RUNS = MAX_RUNS;
    }

    public static int getAvoid() {
        return avoid;
    }

    public static void setAvoid(int avoid) {
        Config.avoid = avoid;
    }
    
    
}
