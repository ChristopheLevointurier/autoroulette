package clv.view;

import clv.Controller.SessionController;
import clv.Controller.SessionListener;
import clv.common.Config;
import clv.common.Report;
import clv.common.Session;
import clv.view.sub.DynamicPieDataSet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;
import org.jfree.util.SortOrder;

public class CloudGraph extends JFrame implements SessionListener {//, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ChartPanel cloudchart;
    private XYSeriesCollection xyseriescollection = new XYSeriesCollection();
    private XYSeries cloudWinDataSet;
    private XYSeries cloudLooseDataSet;
    private int wins = 0, fails = 0;
    private JList liste;
    private double goal = 0;

    public CloudGraph() {
        super("Goal:" + Config.getGoalWin() + "  boost=" + Config.isUseBoostPogne());
        goal = Config.getGoalWin();
        liste = new JList(Config.getMises().toArray());


        cloudWinDataSet = new XYSeries(0, false, true);
        cloudLooseDataSet = new XYSeries(1, false, true);
        xyseriescollection.addSeries(cloudWinDataSet);
        xyseriescollection.addSeries(cloudLooseDataSet);

        cloudchart = new ChartPanel(ChartFactory.createScatterPlot("", "runs to the end", "final value", xyseriescollection, PlotOrientation.VERTICAL, false, false, false));
        XYPlot plot = cloudchart.getChart().getXYPlot();
        plot.setRenderer(new XYDotRenderer());
        plot.setRangePannable(true);
        plot.getRenderer().setSeriesPaint(0, Color.BLUE);
        plot.getRenderer().setSeriesPaint(1, Color.RED);
        cloudchart.setPreferredSize(new Dimension(200, 200));
        cloudchart.setDomainZoomable(true);
        cloudchart.setRangeZoomable(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, liste, cloudchart);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(20);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        final CloudGraph instance=this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SessionController.removeSessionListener(instance);
                dispose();
            }
        });

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

    @Override
    public void updateInternalData(Session s) {

        int indexSerie = 1;
        if (s.getLastPortefeuilleValue() < (Config.getPortefeuilleStart() * goal)) {
            fails++;
        }
        if (s.getLastPortefeuilleValue() >= (Config.getPortefeuilleStart() * goal)) {
            indexSerie = 0;
            wins++;
        }
        xyseriescollection.getSeries(indexSerie).add(s.getCptRuns(), s.getLastPortefeuilleValue());


        cloudchart.getChart().setTitle(" Jeux=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) (wins + fails)) * 100) + "% wins");
        cloudchart.getChart().fireChartChanged();
    }
}
