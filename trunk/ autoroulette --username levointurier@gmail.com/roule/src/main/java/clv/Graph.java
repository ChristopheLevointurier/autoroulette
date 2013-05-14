package clv;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

public class Graph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartPanel ratiochart, maxfailschart, numRunsChart;
	private DefaultPieDataset ratiodataSet = new DefaultPieDataset(), maxfailsdataset = new DefaultPieDataset(), runsdataset = new DefaultPieDataset();
	private int wins = 0, fails = 0;

	private HashMap<Integer, Integer> failsMax = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> runs = new HashMap<Integer, Integer>();

	public Graph() {
		super("graphs");
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println(" Fails:" + fails + " wins=" + wins + " ratio=" + (((double) wins / (double) fails) * 100));
				System.exit(0);
			}
		});

		initCharts();
		getContentPane().add(maxfailschart, BorderLayout.CENTER);
		getContentPane().add(ratiochart, BorderLayout.NORTH);
		getContentPane().add(numRunsChart, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initCharts() {
		numRunsChart = initChart(runsdataset, "runs");
		maxfailschart = initChart(maxfailsdataset, "maxfails");
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart = initChart(ratiodataSet, "ratio");
	}

	private ChartPanel initChart(DefaultPieDataset d, String title) {
		ChartPanel c = new ChartPanel(ChartFactory.createPieChart3D(title, d, true, true, false));
		PiePlot plot = (PiePlot) c.getChart().getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		c.setPreferredSize(new java.awt.Dimension(500, 200));
		return c;
	}

	public void addData(int cptFailsMax, int portefeuille, int cptRuns) {
		// System.out.println(" Fails:" + cptFailsMax + "portefeuille=" +
		// portefeuille + "     lancé n°" + cptRuns);
		if (portefeuille < 0)
			fails++;
		if (portefeuille > 0)
			wins++;

		if (failsMax.containsKey(cptFailsMax)) {
			failsMax.put(cptFailsMax, failsMax.remove(cptFailsMax) + 1);
		} else {
			failsMax.put(cptFailsMax, 1);
		}

		int nbr = cptRuns / 50;
		int index = nbr * 50;
		if (runs.containsKey(index)) {
			runs.put(index, runs.remove(index) + 1);
		} else {
			runs.put(index, 1);
		}

		updateCharts();
	}

	private void updateCharts() {
		// Creates a sample dataset
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart.getChart().setTitle("Jeux=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) fails) * 100));
		ratiochart.getChart().fireChartChanged();

		for (Integer i : failsMax.keySet())
			maxfailsdataset.setValue(i, failsMax.get(i));
		maxfailschart.getChart().fireChartChanged();

		for (Integer i : runs.keySet())
			runsdataset.setValue(i, runs.get(i));
		numRunsChart.getChart().fireChartChanged();
		repaint();
	}

}
