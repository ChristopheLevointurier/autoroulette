/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CLV
 */
public class Session {

    private List<Integer> portefeuilleHistory = new ArrayList<>();
    private int cptFailsMax = 0;
    private int cptRuns = 0;
    private int nbrswitch = 0;

    public Session(List<Integer> _portefeuilleHistory, int _cptFailsMax, int _nbrswitch) {
        portefeuilleHistory = _portefeuilleHistory;
        cptFailsMax = _cptFailsMax;
        cptRuns = portefeuilleHistory.size();
        nbrswitch = _nbrswitch;
    }

    public List<Integer> getPortefeuilleHistory() {
        return portefeuilleHistory;
    }

    public int getCptFailsMax() {
        return cptFailsMax;
    }

    public int getCptRuns() {
        return cptRuns;
    }

    public int getNbrswitch() {
        return nbrswitch;
    }
    
    
    public int getLastPortefeuilleValue(){
        return portefeuilleHistory.get(portefeuilleHistory.size()-1);
    }
}
