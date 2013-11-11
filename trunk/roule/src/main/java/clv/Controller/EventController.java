/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clv.Controller;

import clv.common.Utils.AppEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLV
 */
public class EventController {

    private static List<EventListener> listeners = new ArrayList<>();

    public static void addListener(EventListener sl) {
        listeners.add(sl);
    }

    public static void removeListener(EventListener sl) {
        listeners.remove(sl);
    }

    public static void broadcast(AppEvent e) {
        for (EventListener l : listeners) {
            l.updateInternalData(e);
        }
    }
}
