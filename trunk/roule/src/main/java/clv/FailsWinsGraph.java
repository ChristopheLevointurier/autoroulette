package clv;

import clv.Controller.SessionListener;
import clv.common.Config;
import clv.common.Report;
import clv.common.Session;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.jfree.util.SortOrder;

import clv.sub.DynamicPieDataSet;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSplitPane;

public class FailsWinsGraph extends JFrame{//, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ChartPanel ratiochart, maxfailschart, runsChart, runsWhenWinChart, switchChart, switchWhenWinChart, failsMaxWhenWinChart;
    private ArrayList<ChartPanel> chartlist = new ArrayList<>();
    private ArrayList<DynamicPieDataSet> datasetlist = new ArrayList<>();
    private DefaultPieDataset ratiodataSet = new DefaultPieDataset();
    private JList liste;
    private DynamicPieDataSet failsMaxWhenWindataset = new DynamicPieDataSet("maxfails pendant victoire"), maxfailsdataset = new DynamicPieDataSet("maxfails"), runsWhenWindataset = new DynamicPieDataSet("runs pendant victoire"), runsdataset = new DynamicPieDataSet("runs"), switchWhenWindataset = new DynamicPieDataSet("switch pendant victoire"),
            switchdataset = new DynamicPieDataSet("switch");
    private int wins = 0, fails = 0, portefeuilleStart = 0;
    private double goal = 0;

    public FailsWinsGraph() {
        super("Start=" + ((Config.getPortefeuilleStart() * 200) / 1000) + "kcfp, goal:" + Config.getGoalWin() * ((Config.getPortefeuilleStart() * 200) / 1000) + "kcfp.  boost=" + Config.isUseBoostPogne() + " avoid=" + Config.isUseavoid());
        goal = Config.getGoalWin();
        liste = new JList(Config.getMises().toArray());
        portefeuilleStart = Config.getPortefeuilleStart();
        runsChart = initChart(runsdataset);
        runsWhenWinChart = initChart(runsWhenWindataset);
        switchChart = initChart(switchdataset);
        switchWhenWinChart = initChart(switchWhenWindataset);
        maxfailschart = initChart(maxfailsdataset);
        failsMaxWhenWinChart = initChart(failsMaxWhenWindataset);
        ratiochart = initChart(ratiodataSet, "ratio");

        chartlist.add(runsChart);
        chartlist.add(runsWhenWinChart);
        chartlist.add(switchChart);
        chartlist.add(switchWhenWinChart);
        chartlist.add(maxfailschart);
        chartlist.add(failsMaxWhenWinChart);
        chartlist.add(ratiochart);
        datasetlist.add(failsMaxWhenWindataset);
        datasetlist.add(maxfailsdataset);
        datasetlist.add(runsWhenWindataset);
        datasetlist.add(runsdataset);
        datasetlist.add(switchWhenWindataset);
        datasetlist.add(switchdataset);

        JPanel winsCharts = new JPanel();
        winsCharts.setLayout(new BoxLayout(winsCharts, BoxLayout.Y_AXIS));
        JPanel globalCharts = new JPanel();
        globalCharts.setLayout(new BoxLayout(globalCharts, BoxLayout.Y_AXIS));
        globalCharts.add(runsChart);
        globalCharts.add(maxfailschart);
        globalCharts.add(switchChart);
        winsCharts.add(runsWhenWinChart);
        winsCharts.add(failsMaxWhenWinChart);
        winsCharts.add(switchWhenWinChart);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, globalCharts, winsCharts);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);


        JPanel topCharts = new JPanel();
        topCharts.setLayout(new BoxLayout(topCharts, BoxLayout.X_AXIS));
        topCharts.add(ratiochart);
        topCharts.add(new JScrollPane(liste));

        getContentPane().add(topCharts, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               dispose();
            }
        });
        
        
        for (Session s:Report.getReport()){
               if (s.getLastPortefeuilleValue() < (portefeuilleStart * goal)) {
            fails++;
        }
        if (s.getLastPortefeuilleValue() >= (portefeuilleStart * goal)) {
            failsMaxWhenWindataset.add(s.getCptFailsMax());
            runsWhenWindataset.add(s.getCptRuns());
            switchWhenWindataset.add(s.getNbrswitch());
            wins++;
        }
        maxfailsdataset.add(s.getCptFailsMax());
        runsdataset.add(s.getCptRuns());
        switchdataset.add(s.getNbrswitch());
        }
         for (DynamicPieDataSet d : datasetlist) {
            d.removeEmptyParts();
        }
       for (DynamicPieDataSet d : datasetlist) {
            d.sort(false, SortOrder.DESCENDING);
        }
        ratiodataSet.sortByValues(SortOrder.DESCENDING);
        ratiodataSet.setValue("wins", wins);
        ratiodataSet.setValue("fails", fails);
        ratiochart.getChart().setTitle(" Jeux=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) (wins + fails)) * 100) + "% wins");

        for (ChartPanel c : chartlist) {
            c.getChart().fireChartChanged();
        }
        repaint();
    }

    /**
     * @Override public void run() { while (RUNNING) { try { Thread.sleep((int)
     * (1000 / FPS)); } catch (InterruptedException p) { } updateCharts(); }
    }*
     */
    private ChartPanel initChart(DynamicPieDataSet dataset) {
        return initChart(dataset, dataset.getTitle());
    }

    private ChartPanel initChart(DefaultPieDataset d, String title) {
        ChartPanel c = new ChartPanel(ChartFactory.createPieChart3D(title, d, true, true, false));
        PiePlot plot = (PiePlot) c.getChart().getPlot();
        plot.setLabelGenerator(null);
        plot.setStartAngle(90);
        plot.setDirection(Rotation.ANTICLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        c.setPreferredSize(new java.awt.Dimension(200, 200));
        return c;
    }
}
