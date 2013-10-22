package clv.view;

import clv.Controller.SessionListener;
import clv.common.PlayerConfig;
import clv.common.Report;
import clv.common.Session;
import clv.view.sub.DynamicPieDataSet;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.jfree.util.SortOrder;

public class FailsWinsGraph extends JFrame implements SessionListener {//, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ChartPanel ratiochart, maxfailschart, runsChart, runsWhenWinChart, switchChart, switchWhenWinChart, failsMaxWhenWinChart;
    private ArrayList<ChartPanel> chartlist = new ArrayList<>();
    private ArrayList<DynamicPieDataSet> datasetlist = new ArrayList<>();
    private DefaultPieDataset ratiodataSet = new DefaultPieDataset();
    private JList liste;
    private DynamicPieDataSet failsMaxWhenWindataset = new DynamicPieDataSet("maxfails pdt win"), maxfailsWhenLoosedataset = new DynamicPieDataSet("maxfails pdt loose"), runsWhenWindataset = new DynamicPieDataSet("runs pdt win"), runsWhenLoosedataset = new DynamicPieDataSet("runs pdt loose"), switchWhenWindataset = new DynamicPieDataSet("switch pdt win"),
            switchWhenLoosedataset = new DynamicPieDataSet("switch pdt loose");
    private int wins = 0, fails = 0, drop = 0, portefeuilleStart = 0;
    private double goal = 0;

    public FailsWinsGraph() {
        super("Start=");// + ((PlayerConfig.getPortefeuilleStart() * 200) / 1000) + "kcfp, goal:" + PlayerConfig.getGoalWin() * ((PlayerConfig.getPortefeuilleStart() * 200) / 1000) + "kcfp.  boost=" + PlayerConfig.isUseBoostPogne());
        //   goal = PlayerConfig.getGoalWin();
        //   liste = new JList(PlayerConfig.getMises().toArray());
        //   portefeuilleStart = PlayerConfig.getPortefeuilleStart();
        runsChart = initChart(runsWhenLoosedataset);
        runsWhenWinChart = initChart(runsWhenWindataset);
        switchChart = initChart(switchWhenLoosedataset);
        switchWhenWinChart = initChart(switchWhenWindataset);
        maxfailschart = initChart(maxfailsWhenLoosedataset);
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
        datasetlist.add(maxfailsWhenLoosedataset);
        datasetlist.add(runsWhenWindataset);
        datasetlist.add(runsWhenLoosedataset);
        datasetlist.add(switchWhenWindataset);
        datasetlist.add(switchWhenLoosedataset);

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
        splitPane.setDividerLocation(200);


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


        for (Session s : Report.getReport()) {
            if (s.getLastPortefeuilleValue() < (portefeuilleStart)) {
                maxfailsWhenLoosedataset.add(s.getCptFailsMax());
                runsWhenLoosedataset.add(s.getCptRuns());
                switchWhenLoosedataset.add(s.getNbrswitch());
                fails++;
            }
            if (s.getLastPortefeuilleValue() >= (portefeuilleStart * goal)) {
                failsMaxWhenWindataset.add(s.getCptFailsMax());
                runsWhenWindataset.add(s.getCptRuns());
                switchWhenWindataset.add(s.getNbrswitch());
                wins++;
            }
            if ((s.getLastPortefeuilleValue() >= (portefeuilleStart)) && (s.getLastPortefeuilleValue() < (portefeuilleStart * goal))) {
                drop++;
            }
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
        ratiodataSet.setValue("drop", drop);
        ratiochart.getChart().setTitle(" Jeux=" + (wins + fails + drop) + " ratio=" + (int) (((double) wins / (double) (wins + fails + drop)) * 100) + "% wins," + (int) (((double) drop / (double) (wins + fails + drop)) * 100) + "% drop");

        for (ChartPanel c : chartlist) {
            c.getChart().fireChartChanged();
        }
        repaint();



        JPanel content = (JPanel) getContentPane();
        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE");
        final JFrame frame = this;
        content.getActionMap().put("CLOSE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

    }

    /**
     * @Override public void run() { while (RUNNING) { try { Thread.sleep((int)
     * (1000 / FPS)); } catch (InterruptedException p) { } updateCharts(); } }*
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

    @Override
    public void updateInternalData(Session s) {

        int indexSerie = 1;
        /**
         * if (s.getLastPortefeuilleValue() <
         * (PlayerConfig.getPortefeuilleStart() * goal)) { fails++; } if
         * (s.getLastPortefeuilleValue() >= (PlayerConfig.getPortefeuilleStart()
         * * goal)) { indexSerie = 0; wins++; }
         * xyseriescollection.getSeries(indexSerie).add(s.getCptRuns(),
         * s.getLastPortefeuilleValue());
         *
         *
         * cloudchart.getChart().setTitle(" Jeux=" + (wins + fails) + " ratio="
         * + (int) (((double) wins / (double) (wins + fails)) * 100) + "%
         * wins");
        cloudchart.getChart().fireChartChanged();*
         */
    }
}
