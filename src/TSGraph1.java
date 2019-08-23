import java.awt.Color; 
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class TSGraph1 extends ApplicationFrame {

	private static final long serialVersionUID = -5649734434855247978L;
	Integer[] barsperrow=new Integer[20];
	float[][][] output = new float[20][5][2];
	int row=0;
	int tempo=0;
	int timesig=0;
	int totalbarno=0;
		
   public TSGraph1( String applicationTitle, String chartTitle, float[][][] op, int r, Integer[] bpr,int tem,int ts, int tbn) {
      super(applicationTitle);
      	output=op;
		row=r;
		barsperrow=bpr;
		tempo=tem;
		timesig=ts;
		totalbarno=tbn;    
      //TE
      JFreeChart xylineChart1 = ChartFactory.createXYLineChart(
    	         chartTitle ,
    	         "Time" ,
    	         "Energy" ,
    	         createDataset1() ,
    	         PlotOrientation.VERTICAL ,
    	         true , true , false);
    	         
    	      ChartPanel chartPanel1 = new ChartPanel( xylineChart1 );
    	      chartPanel1.setPreferredSize( new java.awt.Dimension( 800 , 600 ) );
    	      final XYPlot plot1 = xylineChart1.getXYPlot( );
    	      
    	      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer( );
    	      renderer1.setSeriesPaint( 0 , Color.RED );
    	      renderer1.setSeriesStroke( 0 , new BasicStroke( 2.0f ) );
    	      plot1.setRenderer( renderer1 ); 
    	      setContentPane( chartPanel1 ); 
   }
   
    private XYDataset createDataset1( ) {
	   float timeinterval=(float)(60*timesig)/(float)tempo;
	   
      final XYSeries TE = new XYSeries( "Energy" );
      float bar=0;
      for(int rowno=0;rowno<row;rowno++)
		{
			for(int barno=0;barno<barsperrow[rowno] && bar<totalbarno;barno++)
			{
				TE.add( (float)(bar+1)*timeinterval, output[rowno][barno][1] );
				bar=bar+1;
			}
		}
                 
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( TE );
      
      return dataset;
   }

   public static void main( String[ ] args ) {
      
   }
}
