package clv.common;

import clv.Controller.SessionListener;
import clv.Main;
import java.util.ArrayList;
import java.util.List;

public class Report implements SessionListener {

    private List<Session> report = new ArrayList<>();
    private static Report instance;

    public static Report getInstance() {
        if (instance == null) {
            instance = new Report();
        }
        return instance;
    }

    public static List<Session> getReport() {
        return getInstance().report;
    }

    @Override
    public void updateInternalData(Session s) {
        report.add(s);
         Main.bar.setValue(report.size() * 100 / Config.getMAX_RUNS());
    }
}
