package clv;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

public class Graph extends JFrame {

	private ChartPanel chart;
	private DefaultPieDataset dataSet;
	private int wins = 0, fails = 0;

	public Graph() {
		super("graphs");
			addWindowListener(new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		    	System.out.println(" Fails:" + fails + " wins="	+ wins+ " ratio="+(((double)wins/(double)fails)*100));
			System.exit(0);
		    }
		});
		dataSet = new DefaultPieDataset();
		dataSet.setValue("wins", wins);
		dataSet.setValue("fails", fails);
		// based on the dataset we create the chart
		chart = new ChartPanel(ChartFactory.createPieChart3D("pokpok", dataSet,
				true, true, false));
		chart.setPreferredSize(new java.awt.Dimension(500, 270));
		getContentPane().removeAll();
		setContentPane(chart);
    	PiePlot plot = (PiePlot) chart.getChart().getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		pack();
		setVisible(true);
	}

	public void addData(int cptFailsMax, int portefeuille, int cptRuns) {
		//System.out.println(" Fails:" + cptFailsMax + "portefeuille="	+ portefeuille + "     lancé n°" + cptRuns);
		if (portefeuille < 0)
			fails++;
		if (portefeuille > 0)
			wins++;
		updateChart();
	}

	private void updateChart() {
		// Creates a sample dataset
		dataSet.setValue("wins", wins);
		dataSet.setValue("fails", fails);
		chart.getChart().fireChartChanged();
		repaint();
	}

}
