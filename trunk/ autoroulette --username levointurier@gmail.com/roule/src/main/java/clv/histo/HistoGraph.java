package clv.histo;

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

public class HistoGraph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChartPanel ratiochart, failseriechart, winseriechart;
	private DefaultPieDataset ratiodataSet = new DefaultPieDataset();
	private DynamicPieDataSet failseriedataset = new DynamicPieDataSet("fails series"), winseriedataset = new DynamicPieDataSet("wins series");
	private int wins = 0, fails = 0;

	public HistoGraph(boolean switch0) {
		super("Histo, switch0=" + switch0);
		winseriechart = initChart(winseriedataset);
		failseriechart = initChart(failseriedataset);
		ratiochart = initChart(ratiodataSet, "ratio series");

		JPanel mainCharts = new JPanel();
		mainCharts.setLayout(new BoxLayout(mainCharts, BoxLayout.Y_AXIS));

		mainCharts.add(ratiochart);
		mainCharts.add(failseriechart);
		mainCharts.add(winseriechart);

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

	public void addFailSerie(int cpt) {
		failseriedataset.add(cpt);
		fails++;
		updateCharts();

	}

	public void addWinSerie(int cpt) {
		winseriedataset.add(cpt);
		wins++;
		updateCharts();

	}

	private void updateCharts() {
		// Creates a sample dataset
		ratiodataSet.setValue("wins", wins);
		ratiodataSet.setValue("fails", fails);
		ratiochart.getChart().setTitle(" Switchs=" + (wins + fails) + " ratio=" + (int) (((double) wins / (double) (wins + fails)) * 100) + "% wins");
		winseriechart.getChart().fireChartChanged();
		failseriechart.getChart().fireChartChanged();
		ratiochart.getChart().fireChartChanged();
		repaint();
	}

	public void removeEmptyParts() {
		winseriedataset.removeEmptyParts();
		failseriedataset.removeEmptyParts();
		updateCharts();
	}

	public void sort() {
		winseriedataset.sortByValues(SortOrder.DESCENDING);
		failseriedataset.sortByValues(SortOrder.DESCENDING);
		ratiodataSet.sortByValues(SortOrder.DESCENDING);
		updateCharts();
	}

}
