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
public class PlayerConfig {

    private int portefeuilleStart = 50;
    private boolean useBoostPogne = false;
    private boolean useswitch = true;
    private boolean doubleOnFail = true;
    private boolean useDropManagenull = false;
    private int avoid = 0;
    private ArrayList<Integer> mises = new ArrayList<>();
    private double goalWin = 1.83;
 
    public int getPortefeuilleStart() {
        return portefeuilleStart;
    }

    public void setPortefeuilleStart(int _portefeuilleStart) {
        portefeuilleStart = _portefeuilleStart;
    }

    public void setUseBoostPogne(boolean useBoostPogne) {
        this.useBoostPogne = useBoostPogne;
    }

    public boolean isUseBoostPogne() {
        return useBoostPogne;
    }

    public boolean isDoubleOnFail() {
        return doubleOnFail;
    }

    public boolean isUseswitch() {
        return useswitch;
    }

    public void setUseswitch(boolean useswitch) {
        this.useswitch = useswitch;
    }

    public void setDoubleOnFail(boolean incOnFail) {
        this.doubleOnFail = incOnFail;
    }

    public boolean isUseDropManagenull() {
        return useDropManagenull;
    }

    public void setUseDropManagenull(boolean useDropManagenull) {
        this.useDropManagenull = useDropManagenull;
    }

    public int getAvoid() {
        return avoid;
    }

    public void setAvoid(int avoid) {
        this.avoid = avoid;
    }

    public ArrayList<Integer> getMises() {
        return mises;
    }

    public void setMises(ArrayList<Integer> mises) {
        this.mises = mises;
    }

    public double getGoalWin() {
        return goalWin;
    }

    public void setGoalWin(double goalWin) {
        this.goalWin = goalWin;
    }

   
}
