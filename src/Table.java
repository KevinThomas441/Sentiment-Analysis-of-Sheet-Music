import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JPanel{
	
	private static final long serialVersionUID = 1L;
	JTable jt;
    int k=0;
    		
    public Table(SentimentAnalysis sa){
    	String [] header={"Row Number","Bar Number","Valence","Energy","Mood"};
	    String [][] data = new String[52][5];
	    k=0;
	    for(Integer rowno=0;rowno<sa.row;rowno++)
		{
	    	for(Integer barno=0;barno<sa.barsperrow[rowno];barno++)
			{
	    		Float val=sa.output[rowno][barno][0];
	    		Float eng=sa.output[rowno][barno][1];
	    		String mood=sa.calcSentiment(sa.output[rowno][barno][0], sa.output[rowno][barno][1]);
                data[k][0]= rowno.toString();
                data[k][1]= barno.toString();
                data[k][2]=val.toString();
                data[k][3]=eng.toString();
                data[k][4]=mood;          
	    		k++;
			}
		}


        DefaultTableModel model = new DefaultTableModel(data,header);

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(614,346));
        table.setFillsViewportHeight(true);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        add(js);

    }

    public static void main(String [] a) {

        /*JFrame jf=new JFrame();
        Table tab= new Table();
        jf.setTitle("Table");
        jf.setSize(500, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(tab);*/



    }

}