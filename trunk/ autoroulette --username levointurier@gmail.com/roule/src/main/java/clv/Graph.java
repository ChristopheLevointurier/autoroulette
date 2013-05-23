package clv;

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

public class Graph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartPanel ratiochart, maxfailschart, runsChart, runsWhenWinChart, switchChart, switchWhenWinChart, failsMaxWhenWinChart;
	private ArrayList<ChartPanel> chartlist = new ArrayList<ChartPanel>();
	private ArrayList<DynamicPieDataSet> datasetlist = new ArrayList<DynamicPieDataSet>();
	private DefaultPieDataset ratiodataSet = new DefaultPieDataset();
	private JList liste;
	private DynamicPieDataSet failsMaxWhenWindataset = new DynamicPieDataSet("maxfails pendant victoire"), maxfailsdataset = new DynamicPieDataSet("maxfails"), runsWhenWindataset = new DynamicPieDataSet("runs pendant victoire"), runsdataset = new DynamicPieDataSet("runs"), switchWhenWindataset = new DynamicPieDataSet("switch pendant victoire"),
			switchdataset = new DynamicPieDataSet("switch");
	private int wins = 0, fails = 0, portefeuilleStart = 0;
	private double goal = 0;

	public Graph(int _portefeuilleStart, int nbrmisesmax, boolean boostpogne, ArrayList<Integer> mises, double _goal) {
		super("Start=" + ((_portefeuilleStart * 200) / 1000) + "kcfp, goal:" + _goal * ((_portefeuilleStart * 200) / 1000) + "kcfp.  boost=" + boostpogne);
		goal = _goal;

		liste = new JList(mises.toArray());
		portefeuilleStart = _portefeuilleStart;
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

		JPanel mainCharts = new JPanel();
		mainCharts.setLayout(new BoxLayout(mainCharts, BoxLayout.X_AXIS));

		// getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
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

		mainCharts.add(globalCharts);
		mainCharts.add(winsCharts);

		JPanel topCharts = new JPanel();
		topCharts.setLayout(new BoxLayout(topCharts, BoxLayout.X_AXIS));
		topCharts.add(ratiochart);
		topCharts.add(new JScrollPane(liste));

		getContentPane().add(topCharts, BorderLayout.NORTH);
		getContentPane().add(mainCharts, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

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

	public void addData(int cptFailsMax, int portefeuille, int cptRuns, int nbrswitch) {
		// System.out.println(" Fails:" + cptFailsMax + "portefeuille=" +portefeuille + "     lancé n°" + cptRuns);
		if (portefeuille < (portefeuilleStart * goal)) {
			fails++;
		}
		if (portefeuille >= (portefeuilleStart * goal)) {
			failsMaxWhenWindataset.add(cptFailsMax);
			runsWhenWindataset.add(cptRuns);
			switchWhenWindataset.add(nbrswitch);
			wins++;
		}
		maxfailsdataset.add(cptFailsMax);
		runsdataset.add(cptRuns);
		switchdataset.add(nbrswitch);
		updateCharts();
	}

	private void updateCharts() {
		// Creates a sample dataset
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart.getChart().setTitle(" Jeux=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) (wins + fails)) * 100) + "% wins");

		for (ChartPanel c : chartlist) {
			c.getChart().fireChartChanged();
		}
		repaint();
	}

	public void removeEmptyParts() {
		for (DynamicPieDataSet d : datasetlist) {
			d.removeEmptyParts();
		}
		updateCharts();
	}

	public void sort(boolean key, SortOrder order) {
		for (DynamicPieDataSet d : datasetlist) {
			d.sort(key, order);
		}

		ratiodataSet.sortByValues(SortOrder.DESCENDING);
		updateCharts();
	}

}
