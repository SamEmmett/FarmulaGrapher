import java.awt.Color;
import java.lang.Math;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Calculations {
	/* GLOBAL VARIABLES */

	DescriptiveStatistics statsPrep = new DescriptiveStatistics();
	Double[] xArr = new Double[101];

	public void Run() {

		CreateX();
		Double[] unaltered = StdCurve();
		Double[] salted = SaltedData();
		Double[] smooth = RollingAVG(salted);
		Double[] smoother = RollingAVG(smooth);

		checkArr(salted);
		
		genChart(unaltered, salted, smooth, smoother);
		

	}

	private void CreateX() { // done

		for (int i = 0; i < 100; i++) {

			this.xArr[i] = (double) i;

		}
	}

	private Double[] StdCurve() { // Done

		int counter = 0;

		Double[] storeArr = new Double[101];

		// THIS IS THE CODE THAT WILL CALCULATE THE NONSKEWED DATA AND CHART IT

		double val = 0;
		while (counter < 100) {

			if (xArr[counter] != null) {
				val = this.xArr[counter];
			}

			if (xArr[counter] != null) {
				storeArr[counter] = Calculate(val);
			}
			counter++;
		}

		return storeArr;
	}

	private Double[] SaltedData() { // Returns an array filled with salted data
		int counter = 0;

		Double[] saltedArr = new Double[101];

		// THIS IS THE CODE THAT WILL CALCULATE THE NONSKEWED DATA AND CHART IT

		double val = 0;
		while (counter < 100) {

			if (xArr[counter] != null) {
				val = this.xArr[counter];
			}

			if (xArr[counter] != null) {
				saltedArr[counter] = CalculateSalted(val);
			}
			counter++;
		}

		return saltedArr;
	}

	private Double[] RollingAVG(Double[] data) { /* ROLLING AVERAGE WITH INTERVAL OF 3 */
		DescriptiveStatistics statsAvg = new DescriptiveStatistics();

		int counter = 0;
		int meanCT = 0;
		Double[] meanArr = new Double[105];

		statsAvg.setWindowSize(3);

		// THIS IS THE CODE THAT WILL CALCULATE THE NONSKEWED DATA AND CHART IT
		long nLines = 0;
		

		while (data[counter] != null) {

			if (data[counter] != null) {
				statsAvg.addValue(data[counter]);

			}
			if (nLines == 3) {
				PrepMean(statsAvg);
				nLines = 2;
				meanArr[meanCT] = statsAvg.getMean();
				meanCT++;
			}

			counter++;
			nLines++;
		}

		return meanArr;

	}

	private double Calculate(double val2) { // calculates the unsalted value

		double val = 1;
		double sub1 = (Math.pow(val2, 3));
		double sub2 = (3 * (Math.pow(val2, 2)));
		double sub3 = val;

		double y = (sub1 + sub2 + sub3);

		return y;
	}

	private double CalculateSalted(double val2) { // calculates the salted value
		Random rand = new Random();
		Random op = new Random();
		double skew = rand.nextInt(100000) + 1;

		double val = 1;
		double sub1 = (Math.pow(val2, 3));
		double sub2 = (3 * (Math.pow(val2, 2)));
		double sub3 = val;

		if (op.nextInt(6) < 3) {
			double y = (sub1 + sub2 + sub3) + skew;
			return y;
		} else {
			double y = (sub1 + sub2 + sub3) - skew;
			return y;
		}

	}

	private DescriptiveStatistics PrepMean(DescriptiveStatistics stats) { // Used to store the last two values of the
																			// stats object to use them as the first two
																			// in the next

		statsPrep.clear();

		for (int i = 1; i <= 2; i++) {
			statsPrep.addValue(stats.getElement(i));
			// System.out.println(statsPrep);
		}

		return statsPrep;

	}

	private void genChart(Double[] unaltered, Double[] salted, Double[] smooth, Double[] smoother) {

		XYSeries unSaltedData = new XYSeries("Unsalted");
		XYSeries SaltedData = new XYSeries("Salted");
		XYSeries smoothData = new XYSeries("Smooth");
		XYSeries smootherData = new XYSeries("Smoother");
		XYSeriesCollection dataset = new XYSeriesCollection();

		for (int i = 0; unaltered[i] != null; i++) {

			unSaltedData.add(i, unaltered[i]);

		}

		for (int i = 0; salted[i] != null; i++) {

			SaltedData.add(i, salted[i]);

		}

		for (int i = 0; smooth[i] != null; i++) {

			smoothData.add(i, smooth[i]);

		}

		for (int i = 0; smoother[i] != null; i++) {

			System.out.println(smoother[i]);
			smootherData.add(i, smoother[i]);

		}

		dataset.addSeries(unSaltedData);
		dataset.addSeries(SaltedData);
		dataset.addSeries(smoothData);
		dataset.addSeries(smootherData);

		JFreeChart chart = ChartFactory.createXYLineChart("X^3 + 3X^2 + 1", "Input", "Output", dataset,
				PlotOrientation.VERTICAL, true, false, false);
		chart.setBackgroundPaint(Color.LIGHT_GRAY);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.GRAY);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		ChartFrame frame = new ChartFrame("X^3 + 3X^2 + 1", chart);

		frame.setVisible(true);
		frame.setSize(800, 500);
	}

	private void checkArr(Double[] array) {
		
		for (int i = 0; i <100; i++) {
			
			System.out.print(array[i] + "; ");
		}
	}
}
