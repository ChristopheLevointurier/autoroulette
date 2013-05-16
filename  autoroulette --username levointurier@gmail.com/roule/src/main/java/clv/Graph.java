package clv;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import clv.sub.DynamicPieDataSet;

public class Graph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartPanel ratiochart, maxfailschart, numRunsChart, numRunsWhenWinChart, failsMaxWhenWinChart;
	private DefaultPieDataset ratiodataSet = new DefaultPieDataset();
	private DynamicPieDataSet failsMaxWhenWindataset = new DynamicPieDataSet(), maxfailsdataset = new DynamicPieDataSet(), runsWhenWindataset = new DynamicPieDataSet(), runsdataset = new DynamicPieDataSet();
	private int wins = 0, fails = 0, portefeuilleStart = 0, nbrmisesmax = 0;

	private HashMap<Integer, Integer> failsMax = new HashMap<Integer, Integer>();
	// private HashMap<Integer, Integer> failsMaxWhenWin = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> runs = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> runsWhenWin = new HashMap<Integer, Integer>();

	public Graph(int _portefeuilleStart, int _nbrmisesmax, boolean boostpogne) {
		super("Start=" + ((_portefeuilleStart * 200) / 1000) + "kcfp,  Nbr de mises:" + _nbrmisesmax + " boostpogne=" + boostpogne);
		portefeuilleStart = _portefeuilleStart;
		nbrmisesmax = _nbrmisesmax;
		numRunsChart = initChart(runsdataset, "runs");
		numRunsWhenWinChart = initChart(runsWhenWindataset, "runs pendant victoire");
		maxfailschart = initChart(maxfailsdataset, "maxfails");
		failsMaxWhenWinChart = initChart(failsMaxWhenWindataset, "maxfails pendant victoire");
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart = initChart(ratiodataSet, "ratio");

		JPanel mainCharts = new JPanel();
		mainCharts.setLayout(new BoxLayout(mainCharts, BoxLayout.X_AXIS));

		// getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		JPanel winsCharts = new JPanel();
		winsCharts.setLayout(new BoxLayout(winsCharts, BoxLayout.Y_AXIS));
		JPanel globalCharts = new JPanel();
		globalCharts.setLayout(new BoxLayout(globalCharts, BoxLayout.Y_AXIS));
		globalCharts.add(numRunsChart);
		globalCharts.add(maxfailschart);
		winsCharts.add(numRunsWhenWinChart);
		winsCharts.add(failsMaxWhenWinChart);

		mainCharts.add(globalCharts);
		mainCharts.add(winsCharts);

		getContentPane().add(ratiochart, BorderLayout.NORTH);
		getContentPane().add(mainCharts, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private ChartPanel initChart(DefaultPieDataset d, String title) {
		ChartPanel c = new ChartPanel(ChartFactory.createPieChart3D(title, d, true, true, false));
		PiePlot plot = (PiePlot) c.getChart().getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		c.setPreferredSize(new java.awt.Dimension(250, 200));
		return c;
	}

	public void addData(int cptFailsMax, int portefeuille, int cptRuns) {
		// System.out.println(" Fails:" + cptFailsMax + "portefeuille=" +portefeuille + "     lancé n°" + cptRuns);
		if (portefeuille < portefeuilleStart) {
			fails++;
		}
		if (portefeuille > portefeuilleStart) {
			failsMaxWhenWindataset.add(cptFailsMax, true);
			runsWhenWindataset.add(cptRuns, true);
			wins++;
		}
		maxfailsdataset.add(cptFailsMax, true);
		runsdataset.add(cptRuns, true);

		updateCharts();
	}

	private void updateCharts() {
		// Creates a sample dataset
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart.getChart().setTitle(" Jeux=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) (wins + fails)) * 100));
		ratiochart.getChart().fireChartChanged();

		maxfailschart.getChart().fireChartChanged();
		numRunsChart.getChart().fireChartChanged();
		numRunsWhenWinChart.getChart().fireChartChanged();
		failsMaxWhenWinChart.getChart().fireChartChanged();

		repaint();
	}

}
