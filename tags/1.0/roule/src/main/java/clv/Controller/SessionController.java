/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.Controller;

import clv.common.Session;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLV
 */
public class SessionController {

    private static List<SessionListener> listeners = new ArrayList<>();

    public static void addSessionListener(SessionListener sl) {
        listeners.add(sl);
    }

    public static void removeSessionListener(SessionListener sl) {
        listeners.remove(sl);
    }  
    public static void addSession(Session s){
        for(SessionListener sl:listeners){
            sl.updateInternalData(s);
        }
    }
}
