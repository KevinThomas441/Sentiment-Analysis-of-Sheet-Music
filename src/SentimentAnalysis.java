/*import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.ui.RefineryUtilities;
*/

public class SentimentAnalysis {
	Integer[][] values= new  Integer[15][20];
	Integer[][] noteperbar= new Integer[20][5];
	Integer[] noteperrow= new Integer[100];
	Integer[] barsperrow=new Integer[20];
	float[][][] output = new float[20][5][2];
	int row=0;
	float tav=0;
	float tae=0;
	int timesig=4;
	int tempo=120;
	int energy=0;
	int totalbarno=0;
		
	public String calcSentiment(float totalavgvalence, float totalavgenergy)
	{
		//Quadrant 1
		if((totalavgvalence>=0 && totalavgvalence<5) && (totalavgenergy>=0 && totalavgenergy < 5))
		{
			return "Happy";
		}
		else if((totalavgvalence>=0 && totalavgvalence<5) && (totalavgenergy>=5 && totalavgenergy < 10))
		{
			return "Exciting";
		}
		else if((totalavgvalence>=5 && totalavgvalence<10) && (totalavgenergy>=5 && totalavgenergy < 10))
		{
			return "Ecstatic";
		}
		else if((totalavgvalence>=5 && totalavgvalence<10) && (totalavgenergy>=0 && totalavgenergy < 5))
		{
			return "Joyous";
		}
		//Quadrant 2
		else if((totalavgvalence<0 && totalavgvalence>=-5) && (totalavgenergy>=0 && totalavgenergy < 5))
		{
			return "Nervous";
		}
		else if((totalavgvalence<0 && totalavgvalence>=-5) && (totalavgenergy>=5 && totalavgenergy < 10))
		{
			return "Tense";
		}
		else if((totalavgvalence<-5 && totalavgvalence>=-10) && (totalavgenergy>=5 && totalavgenergy < 10))
		{
			return "Anger";
		}
		else if((totalavgvalence<-5 && totalavgvalence>=-10) && (totalavgenergy>=0 && totalavgenergy < 5))
		{
			return "Anxious";
		}
		//Quadrant 3
		else if((totalavgvalence<0 && totalavgvalence>=-5) && (totalavgenergy<0 && totalavgenergy >= -5))
		{
			return "Sad";
		}
		else if((totalavgvalence<0 && totalavgvalence>=-5) && (totalavgenergy<-5 && totalavgenergy >= -10))
		{
			return "Gloomy";
		}
		else if((totalavgvalence<-5 && totalavgvalence>=-10) && (totalavgenergy>-5 && totalavgenergy >= -10))
		{
			return "Depressing";
		}
		else if((totalavgvalence<-5 && totalavgvalence>=-10) && (totalavgenergy<0 && totalavgenergy >= -5))
		{
			return "Misery";
		}
		//Quadrant 4
		if((totalavgvalence>=0 && totalavgvalence<5) && (totalavgenergy<0 && totalavgenergy >=-5))
		{
			return "Satisfying";
		}
		else if((totalavgvalence>=0 && totalavgvalence<5) && (totalavgenergy<-5 && totalavgenergy>=-10))
		{
			return "Calming";
		}
		else if((totalavgvalence>=5 && totalavgvalence<10) && (totalavgenergy<-5 && totalavgenergy>=-10))
		{
			return "Serene";
		}
		else if((totalavgvalence>=5 && totalavgvalence<10) && (totalavgenergy<0 && totalavgenergy >=-5))
		{
			return "Relaxing";
		}
		return "void";
	}
	
	SentimentAnalysis(Integer[][] notevalue, Integer[][] noteperbarcnt,Integer[] barcnt, int rowcnt, Integer[] notecnt,int temp, int ts)
	{
		values=notevalue;
		noteperbar=noteperbarcnt;
		row=rowcnt;
		noteperrow=notecnt;
		barsperrow=barcnt;
		timesig=ts;
		tempo=temp;
	}

	float[][][] valenceFind()
	{
		int interval=0;
		int colno=0;
		int noteno=0;
		int n;
		int sum = 0;
		
		for(int rowno=0;rowno<row;rowno++)
		{	
			noteno=0;
			//System.out.println("barsperrow["+rowno+"] : "+(barsperrow[rowno]));
			for(int barno=0;barno<barsperrow[rowno];barno++)
			{
				sum=0;
				if(noteperbar[rowno][barno]!=0)
				totalbarno++;
				if(noteperbar[rowno][barno]>1)
				{
					for(colno=0;colno<noteperbar[rowno][barno]-1 && noteno<noteperrow[rowno];colno++)
					{
						//System.out.println("rowno : "+rowno+" barno : "+barno+" colno : "+colno+" values : "+values[rowno][noteno]);
						interval = Math.abs(((values[rowno][noteno+1])%12)-((values[rowno][noteno])%12));
						//System.out.println("Interval ="+interval);
						n = valenceValue(interval);
						sum = sum + n;
						noteno++;
					} 
					//System.out.println("Sum = "+ sum);
					output[rowno][barno][0] =(float) sum / noteperbar[rowno][barno];
					tav=tav+output[rowno][barno][0];
					noteno++;
					if(noteperbar[rowno][barno]!=0)
					System.out.println("Valence Per Bar ["+rowno+"]["+barno+"] = "+output[rowno][barno][0]);
				}
			}
		}
		tav=(float)tav/totalbarno;
		System.out.println("Total Average Valence : "+tav);
		energyFind();	
		return output;
	}

	int valenceValue(int interval) {
		switch(interval)
		{
			case 0:
				return 0;
			case 1:
				return -12;
			case 2:
				return -2;
			case 3:
				return -6;
			case 4:
				return 10;
			case 5:
				return 2;
			case 6:
				return -10;
			case 7:
				return 6;
			case 8:
				return -6;
			case 9:
				return -8;
			case 10:
				return 8;
			case 11:
				return 4;
			case 12:
				return -4;
			default:
				return 0;
		}
	}

	void energyFind() {
		if(tempo > 120)
		{
			if(tempo >= 150)
			{
				if(tempo >= 180)
				{
					if(tempo >= 210)
					{
						energy++;
					}
					energy++;
				}
				energy++;
			}
			energy++;
		}
		else
		{
			if(tempo<120)
			{
				if(tempo<=100)
				{
					if(tempo<=80)
					{
						if(tempo<=60)
						{
							energy--;
						}
						energy--;
					}
					energy--;
				}
				energy--;
			}
		}
		//System.out.println("Energy : "+energy);
		System.out.println();
		float sum=0;
		for(int rowno=0;rowno<row;rowno++)
		{
			for(int barno=0;barno<barsperrow[rowno];barno++)
			{
				output[rowno][barno][1]=energy + energyValue(((float)noteperbar[rowno][barno]/(float)timesig));
				//System.out.println("Div : "+((float)noteperbar[rowno][barno]/(float)timesig));
				//System.out.println("rowno : "+rowno+" barno : "+barno+" evalue  : "+energyValue((float)(noteperbar[rowno][barno]/timesig)));
				if(noteperbar[rowno][barno]!=0)
				{
					System.out.println("Energy Per Bar ["+rowno+"]["+barno+"] : "+output[rowno][barno][1]);
					sum = sum + output[rowno][barno][1];
				}
			}
		}
		tae=sum/totalbarno;
		System.out.println("Total Average Energy : "+tae);
		
		/*SwingUtilities.invokeLater(() -> {
		      SAGraph example = new SAGraph("Sentiment Analysis of Music Piece", output, row, barsperrow,totalavgvalence,totalavgenergy);
		      example.setSize(800, 400);
		      example.setLocationRelativeTo(null);
		      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		      example.setVisible(true);
		    });
		
		TSGraph tsg = new TSGraph("Sentiment analysis graph", output, row, barsperrow,tempo,timesig);
		tsg.pack( );         
	    RefineryUtilities.positionFrameRandomly( tsg );         
	    tsg.setVisible( true );
		TSGraph tsg= new TSGraph("Valence - Time Graph","Valence - Time Graph", output, row, barsperrow,tempo,timesig,totalbarno);
		tsg.pack( );          
	    RefineryUtilities.centerFrameOnScreen( tsg );          
	    tsg.setVisible( true );
	    TSGraph1 tsg1= new TSGraph1("Energy - Time","Energy - Time Graph", output, row, barsperrow,tempo,timesig,totalbarno);
		tsg1.pack( );          
	    RefineryUtilities.centerFrameOnScreen( tsg1 );          
	    tsg1.setVisible( true );*/
	}
	
	int energyValue(float emeasure)
	{
		if(emeasure==1)
		{
			return 0;
		}
		else if(emeasure>1 && emeasure<=1.16)
		{
			return 1;
		}
		else if(emeasure>1.16 && emeasure<=1.32)
		{
			return 2;
		}
		else if(emeasure>1.32 && emeasure<=1.48)
		{
			return 3;
		}
		else if(emeasure>1.48 && emeasure<=1.64)
		{
			return 4;
		}
		else if(emeasure>1.64 && emeasure<=1.80)
		{
			return 5;
		}
		else if(emeasure>1.80 && emeasure<=2.00)
		{
			return 6;
		}
		else if(emeasure>2)
		{
			return 7;
		}
		else if(emeasure<1 && emeasure>=0.84)
		{
			return -1;
		}
		else if(emeasure<0.84 && emeasure>=0.68)
		{
			return -2;
		}
		else if(emeasure<0.68 && emeasure>=0.52)
		{
			return -3;
		}
		else if(emeasure<0.52 && emeasure>=0.36)
		{
			return -4;
		}
		else if(emeasure<0.36 && emeasure>=0.20)
		{
			return -5;
		}
		else if(emeasure<0.20 && emeasure>=0.01)
		{
			return -6;
		}
		else if(emeasure==0.00)
		{
			return -7;
		}
		return 100;
	}

}

