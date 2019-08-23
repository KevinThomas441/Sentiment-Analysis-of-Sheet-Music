import java.awt.Color;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
//import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SAGraph extends JFrame {
	private static final long serialVersionUID = 6294689542092367723L;
  	Integer[] barsperrow=new Integer[20];
	float[][][] output = new float[20][5][2];
	int row=0;
	float totalavgvalence=0;
	float totalavgenergy=0;
	
	public SAGraph(String title, float[][][] op, int r, Integer[] bpr, float tav,float tae) {
    super(title);
    output=op;
	row=r;
	barsperrow=bpr;
	totalavgvalence=tav;
	totalavgenergy=tae;
    
    // Create dataset
    XYDataset dataset = createDataset();

    // Create chart
    JFreeChart chart = ChartFactory.createScatterPlot(
        "Sentiment Analysis for Sheet Music", 
        "Valence", "Energy", dataset, PlotOrientation.VERTICAL ,
         true , true , false);

    
    //Changes background color
    XYPlot plot = (XYPlot)chart.getPlot();
    
    plot.setBackgroundPaint(new Color(255,228,220));
        
    ValueMarker marker = new ValueMarker(0);  // position is the value on the axis
    marker.setPaint(Color.black);
    //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes
    ValueMarker marker1 = new ValueMarker(-5);  // position is the value on the axis
    marker1.setPaint(Color.red);
    ValueMarker marker2 = new ValueMarker(5);  // position is the value on the axis
    marker2.setPaint(Color.red);
    ValueMarker marker3 = new ValueMarker(-10);  // position is the value on the axis
    marker3.setPaint(Color.red);
    ValueMarker marker4 = new ValueMarker(-10);  // position is the value on the axis
    marker4.setPaint(Color.red);
    
    plot.addDomainMarker(marker);
    plot.addDomainMarker(marker1);
    plot.addDomainMarker(marker2);
    plot.addDomainMarker(marker3);
    plot.addDomainMarker(marker4);
    plot.addRangeMarker(marker);
    plot.addRangeMarker(marker1);
    plot.addRangeMarker(marker2);
    plot.addRangeMarker(marker3);
    plot.addRangeMarker(marker4);
    
    
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private XYDataset createDataset() {
    XYSeriesCollection dataset = new XYSeriesCollection();

    //Boys (Age,weight) series
    XYSeries series1 = new XYSeries("Sentiment Analysis");
    for(int rowno=0;rowno<row;rowno++)
	{
		for(int barno=0;barno<barsperrow[rowno];barno++)
		{
			series1.add( output[rowno][barno][0] , output[rowno][barno][1]);
		}
	}
    XYSeries series2 = new XYSeries("Average Valence and Energy");
    series2.add(totalavgvalence,totalavgenergy);
    dataset.addSeries(series1);
    dataset.addSeries(series2);
    return dataset;
  }

  public static void main(String[] args) {
  }

}
