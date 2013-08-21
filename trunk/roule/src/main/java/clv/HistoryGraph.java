package clv;

import clv.Controller.SessionController;
import clv.Controller.SessionListener;
import clv.common.Config;
import clv.common.Report;
import clv.common.Session;
import clv.sub.DynamicPieDataSet;
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

public class HistoryGraph extends JFrame implements SessionListener {//, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ChartPanel histchart;
    private XYSeriesCollection xyseriescollection = new XYSeriesCollection();
    private int wins = 0, fails = 0;
    private JList liste;
    private double goal = 0;

    public HistoryGraph() {
        super("Goal:" + Config.getGoalWin() + "  boost=" + Config.isUseBoostPogne());
        goal = Config.getGoalWin();
        liste = new JList(Config.getMises().toArray());

        histchart = new ChartPanel(ChartFactory.createXYLineChart("", "runs", "value", xyseriescollection, PlotOrientation.VERTICAL,false, false, false));
        XYPlot plot = histchart.getChart().getXYPlot();
        // plot.setRenderer(new XYDotRenderer());
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRangePannable(true);
        histchart.setPreferredSize(new Dimension(200, 200));
        histchart.setDomainZoomable(true);
        histchart.setRangeZoomable(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, liste, histchart);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(20);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        final HistoryGraph instance=this;
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
        int index = xyseriescollection.getSeriesCount();
        XYSeries temp = new XYSeries(index, false, true);
        for (int i = 0; i < s.getPortefeuilleHistory().size(); i++) {
            temp.add(i, s.getPortefeuilleHistory().get(i));
        }
        xyseriescollection.addSeries(temp);
        //  histchart.getChart().getXYPlot().getRenderer().setSeriesPaint(index, Color.BLUE);
        histchart.getChart().fireChartChanged();
    }
}
