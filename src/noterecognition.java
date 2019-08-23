import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class noterecognition {
	public static  BufferedImage  image;
	Integer[][][][] dbsegments = new Integer[9][20][50][4];
	Integer[][][] segments = new Integer[20][50][4];
	Integer[][][] barposn = new Integer[20][5][2];
	Integer[][][] ecnt = new Integer[9][11][3];
	float[][][] outputsa = new float[20][5][2];
	Integer[][] notevalue = new Integer[15][50];
	Integer[][] noteperbarcnt = new Integer[20][5];
	Integer[] barcnt = new Integer[20];								//bars per row
	Integer[] rowmap = new Integer[20];
	Integer[] notecnt = new Integer [100];
	int index=0;
	int rowcnt=0;
	int temp=120;
	int ts=4;
	int totalbars=0;
	int totalnotes=0;
	String emo;
	BufferedImage[]  rowdb=new BufferedImage[20];					//row segments
	BufferedImage[][]  bardb=new BufferedImage[20][5];				//bar segments [rowno][bar]
	BufferedImage[][]  imagedb=new BufferedImage[30][80];			//notes in the image [row][note]
	BufferedImage[][][]  dbimage=new BufferedImage[9][11][3];		//notes in the database [row][note]

	noterecognition(BufferedImage bi,int tsig, int tempo) throws IOException{	
		image=bi;
		ts=tsig;
		temp=tempo;
		
		GreyscaleOMR gs= new GreyscaleOMR();
		image=gs.convert(image);
		
		Database();
		Identification();
		Reworking();
		RowFind();
		RowSegment();
		BarSegment();
		NoteSegmentation();
		Subimaging();
		//ScanCall();
		//SentimentAnalyser();
	}

	void SentimentAnalyser() {
		SentimentAnalysis sa=new SentimentAnalysis(notevalue, noteperbarcnt,barcnt,rowcnt,notecnt,temp,ts);
		outputsa=sa.valenceFind();
		emo=sa.calcSentiment(sa.tav,sa.tae);
		System.out.println("The Emotion of this Music Piece is : "+emo);
	}

	void BarSegment() {
		for(int k=0;k<rowcnt;k++)
		{
			barcnt[k]=0;
			int w = rowdb[k].getWidth();
			int h = rowdb[k].getHeight();
			for(int y=0;y<h/2;y++)
			{
				for(int x=1;x<w-1;x++)
				{
					Color r1 = new Color(rowdb[k].getRGB(x,y));
		            int red1 = (int)(r1.getRed());
		            int green1 = (int)(r1.getGreen());
		            int blue1 = (int)(r1.getBlue());
		            if(red1==255 && green1==255 && blue1==255)
		            {
		            	int blackcount=0;
		            	int whitecount=0;
		            	for(int bl=1;bl<26;bl++)
		            	{
		            		Color r2 = new Color(rowdb[k].getRGB(x,(y+bl)));
		    		        int red2 = (int)(r2.getRed());
		    		        int green2 = (int)(r2.getGreen());
		    		        int blue2 = (int)(r2.getBlue());
		    		        if(red2==0 && green2==0 && blue2==0)
		    		        {
		    		        	blackcount++;
		    		        }
		    		        Color r4 = new Color(rowdb[k].getRGB(x+1,(y+bl)));
		    		        int red4 = (int)(r4.getRed());
		    		        int green4 = (int)(r4.getGreen());
		    		        int blue4 = (int)(r4.getBlue());
		    		        //System.out.println(x+1);
		    		        Color r5 = new Color(rowdb[k].getRGB(x-1,(y+bl)));
		    		        int red5 = (int)(r5.getRed());
		    		        int green5 = (int)(r5.getGreen());
		    		        int blue5 = (int)(r5.getBlue());
		    		        if(red4==255 && green4==255 && blue4==255 && red5==255 && green5==255 && blue5==255 )
		    		        {
		    		        	whitecount++;
		    		        }
		            	}
		            	if(blackcount==25 && whitecount==20)
		            	{
		            		Color r3 = new Color(rowdb[k].getRGB(x,y+26));
				            int red3 = (int)(r3.getRed());
				            int green3 = (int)(r3.getGreen());
				            int blue3 = (int)(r3.getBlue());
				            if(red3==255 && green3==255 && blue3==255)
				            {
				            	//Color newColor=new Color(0,255,0);
				    			//rowdb[k].setRGB(x,y,newColor.getRGB());
				    			barposn[k][barcnt[k]][0]=x;
				    			barposn[k][barcnt[k]][1]=y;
				    			//System.out.println("k : "+k+" barcnt : "+barcnt[k]+" x : "+barposn[k][barcnt[k]][0]+" y : "+barposn[k][barcnt[k]][1]);
				    			barcnt[k]++;
				    			totalbars++;
				            }
		            	}
		            }
				}
			}
			barcnt[k]--;
			totalbars--;
			/*File ouptut1 = new File("barid "+k+".png");
			try {
				ImageIO.write(rowdb[k], "png", ouptut1);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			for(int barno=0;barno<barcnt[k];barno++)
			{
				//System.out.println("barno : "+barno+" x : "+barposn[k][barno][0]+" x+1 : "+barposn[k][barno+1][0]);
				bardb[k][barno]=rowdb[k].getSubimage(barposn[k][barno][0],0,(barposn[k][barno+1][0]-barposn[k][barno][0]+1),h-1);
				/*File ouptut1 = new File("barid "+k+" "+barno+".png");
				try {
					ImageIO.write(bardb[k][barno], "png", ouptut1);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				
				int barwidth=bardb[k][barno].getWidth();
				int barheight=bardb[k][barno].getHeight();
				noteperbarcnt[k][barno]=0;
				for(int y=0;y<barheight;y++)
				{
					for(int x=0;x<barwidth;x++)
					{
						Color r1 = new Color(bardb[k][barno].getRGB(x,y));
			            int red1 = (int)(r1.getRed());
			            int green1 = (int)(r1.getGreen());
			            int blue1 = (int)(r1.getBlue());
			            if(red1==100 && green1==50 && blue1==80)
			            {
			            	noteperbarcnt[k][barno]++;
			            	totalnotes++;
			            }
					}
				}
				System.out.println("BarCount["+k+"]["+barno+"] : "+noteperbarcnt[k][barno]);
				
			}
		}
		System.out.println("Total number of bars : "+totalbars);
		System.out.println("Total number of notes : "+totalnotes);
	}
	
	Integer[][] ScanCall() throws IOException {
		//int flag=0;
		for(int rimg = 0;rimg<rowcnt;rimg++)
		{	
			for(int cimg = 0;cimg<notecnt[rimg];cimg++)
			{
				for(int imgdb = 0;imgdb<9;imgdb++)
				{
					for(int rdb = 0;rdb<11;rdb++)
					{
						for(int cdb = 0;((cdb<3 && rdb<10)||(cdb<2 && rdb==10));cdb++)
						{
							if(imgdb==0 || imgdb==1 || imgdb==2 || imgdb==3 || imgdb==4 || imgdb==5 || imgdb==6 || imgdb==7 || imgdb==8)
							{
								
								//System.out.println("here1");
								//System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb);
								ecnt[imgdb][rdb][cdb]=0;
								int widthimg = imagedb[rimg][cimg].getWidth();
							    int heightimg = imagedb[rimg][cimg].getHeight();
								int widthdb = (dbsegments[imgdb][rdb][cdb][2]-dbsegments[imgdb][rdb][cdb][0]);
							    int heightdb = dbsegments[imgdb][rdb][cdb][3]-dbsegments[imgdb][rdb][cdb][1];
							    //System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb+" widthimg : "+widthimg+" heightimg : "+ heightimg + " widthdb : "+ widthdb +" heightdb"+heightdb);
							    if(widthdb==widthimg && heightdb==heightimg)// ((float)widthdb/(float)heightdb)==(((float)(widthimg-1))/((float)(heightimg-1))) && 
							    Scan(imgdb,rdb,cdb,widthimg,heightimg,rimg,cimg);
							    /*if(rdb==10 && cdb==2)
								{
									cdb=1;
									flag=1;
								}*/
							}
							/*else if(imgdb==3 || imgdb==4 || imgdb==5)
							{
								if((rdb!=0) && (rdb!=1) && (rdb!=2) && (rdb!=3) && (rdb!=4) && (rdb!=5))
								{
									//System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb);
									//System.out.println("here2");
									ecnt[imgdb][rdb][cdb]=0;
									int widthimg = imagedb[rimg][cimg].getWidth();
								    int heightimg = imagedb[rimg][cimg].getHeight();
									int widthdb = (dbsegments[imgdb][rdb][cdb][2]-dbsegments[imgdb][rdb][cdb][0]);
								    int heightdb = dbsegments[imgdb][rdb][cdb][3]-dbsegments[imgdb][rdb][cdb][1];
								    //System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb+" widthimg : "+widthimg+" heightimg : "+ heightimg + " widthdb : "+ widthdb +" heightdb"+heightdb);
								    if(widthdb==widthimg && heightdb==heightimg)// ((float)widthdb/(float)heightdb)==(((float)(widthimg-1))/((float)(heightimg-1))) && 
								    Scan(imgdb,rdb,cdb,widthimg,heightimg,rimg,cimg);
								    if(rdb==10 && cdb==2)
									//{
										//cdb=1;
										//flag=1;
									//}
								}
							}*/
							/*else if(imgdb==6 || imgdb==7 || imgdb==8)
							{
								if((rdb%2!=0 && cdb%2!=1) || (rdb%2!=1 && cdb%2!=0))
								{
									//System.out.println("here3");
									//System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb);
									ecnt[imgdb][rdb][cdb]=0;
									int widthimg = imagedb[rimg][cimg].getWidth();
								    int heightimg = imagedb[rimg][cimg].getHeight();
									int widthdb = (dbsegments[imgdb][rdb][cdb][2]-dbsegments[imgdb][rdb][cdb][0]);
								    int heightdb = dbsegments[imgdb][rdb][cdb][3]-dbsegments[imgdb][rdb][cdb][1];
								    //System.out.println("rimg: "+rimg+" cimg: "+cimg+" imgdb: "+imgdb+" rdb : "+rdb+" cdb : "+cdb+" widthimg : "+widthimg+" heightimg : "+ heightimg + " widthdb : "+ widthdb +" heightdb"+heightdb);
								    if(widthdb==widthimg && heightdb==heightimg)// ((float)widthdb/(float)heightdb)==(((float)(widthimg-1))/((float)(heightimg-1))) && 
								    Scan(imgdb,rdb,cdb,widthimg,heightimg,rimg,cimg);
								    //if(rdb==10 && cdb==2)
									//{
										//cdb=1;
										//flag=1;
									//}
								}
							}*/
						}
					}
				}
				Print p=new Print();
				notevalue[rimg][cimg]=p.PrintNote(ecnt);
				System.out.print(notevalue[rimg][cimg]+" ");
			}
			System.out.println();
		}
		return notevalue;
	}

	@SuppressWarnings("unused")
	void Scan(int imgdb,int rdb, int cdb, int widthimg, int heightimg, int rimg, int cimg) throws IOException {
		//Q
		DatabaseImages di=new DatabaseImages();
		di.DatabaseFiles(dbimage);
		
		int ximg,yimg;
		int xdb,ydb;
		int ximgmid,yimgmid;
		int xdbmid,ydbmid;
		
		//System.out.println(" rdb :"+rdb+" cdb :"+cdb+" rimg :"+rimg+" cimg :"+cimg);
		
		ximgmid=widthimg/2;
		yimgmid=heightimg/2;
		//xdbmid=(dbsegments[rdb][cdb][2]-dbsegments[rdb][cdb][0])/2;
		//ydbmid=(dbsegments[rdb][cdb][3]-dbsegments[rdb][cdb][1])/2;
		//System.out.println("rdb: "+rdb+" cdb: "+cdb);
		int widthdb = dbimage[imgdb][rdb][cdb].getWidth();
	    int heightdb = dbimage[imgdb][rdb][cdb].getHeight();
	    //System.out.println("here");
		yimg=0; ydb=0;
		while(yimg<heightimg-1 && ydb<heightdb-1)
		{
			//System.out.println("here");
			ximg=0; xdb=0;
			while(ximg<widthimg-1 && xdb<widthdb-1)
			{
				//System.out.println(" ximg :"+ximg+" yimg :"+yimg+" height: "+ heightimg+" widthimg: "+widthimg);
				Color r1 = new Color(imagedb[rimg][cimg].getRGB(ximg,yimg));
	            int red1 = (int)(r1.getRed());
	            int green1 = (int)(r1.getGreen());
	            int blue1 = (int)(r1.getBlue());
	            
	            Color r2 = new Color(dbimage[imgdb][rdb][cdb].getRGB(xdb, ydb));
		        int red2 = (int)(r2.getRed());
		        int green2 = (int)(r2.getGreen());
		        int blue2 = (int)(r2.getBlue());
		        //System.out.println("here1");
		        if((red1==0 && red2==0 && green1==0 && green2==0 && blue1==0 && blue2==0))//||(red1==255 && red2==255 && green1==255 && green2==255 && blue1==255 && blue2==255)
		        {
		        	//System.out.println("here2");
		        	//System.out.println("red1: "+red1+" red2: "+red2+" green1: "+green1+" green2: "+green2+" blue1: "+blue1+" blue2: "+blue2);
		        	ecnt[imgdb][rdb][cdb]++;
		        }
		        ximg++;
		        xdb++;
			}
			yimg++;
			ydb++;
		}
		//System.out.println(" rdb :"+rdb+" cdb :"+cdb+" ecnt: "+ecnt[rdb][cdb]);
	}

	void Subimaging(){
		int x=0;
		for(int y=0;y<rowcnt;y++)
		{
			for(int i=0;i<50;i++)
			{
				if((segments[y][i][0]!=0) && (segments[y][i][1]!=0) && (segments[y][i][2]!=0) && (segments[y][i][3]!=0))
				{	
					x++;
					/*System.out.println("//y:"+y+"  x:"+x);
					System.out.println("dbsegments[4]["+y+"]["+i+"][0]= "+(segments[y][i][0])+";");
					System.out.println("dbsegments[4]["+y+"]["+i+"][1]= "+segments[y][i][1]+";");
					System.out.println("dbsegments[4]["+y+"]["+i+"][2]= "+(segments[y][i][2])+";");
					System.out.println("dbsegments[4]["+y+"]["+i+"][3]= "+segments[y][i][3]+";");*/
					imagedb[y][i]=rowdb[y].getSubimage(segments[y][i][0],segments[y][i][1],((segments[y][i][2]-segments[y][i][0])),(segments[y][i][3]-segments[y][i][1]));
					/*File ouptut1 = new File("XYZ "+y+" "+i+".png");
					try {
						ImageIO.write(imagedb[y][i], "png", ouptut1);
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				}
			}
			notecnt[y]=x;
			System.out.println("notecnt["+y+"] = "+notecnt[y]);
			x=0;
		}
	}

	void RowFind() {
		int width = image.getWidth();
	    int height = image.getHeight();
	    int cnt=0;
	    //UpperLimit
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				Color c1 = new Color(image.getRGB(j, i));
	            int red1 = (int)(c1.getRed());
	            int green1 = (int)(c1.getGreen());
	            int blue1 = (int)(c1.getBlue());
	            if(red1==0 && green1==0 && blue1==0)
	            {
	            	cnt=0;
	            	for(int i1=i;i1<(i+25);i1++)
	            	{
	            		Color c2 = new Color(image.getRGB(j, i1));
	    	            int red2 = (int)(c2.getRed());
	    	            int green2 = (int)(c2.getGreen());
	    	            int blue2 = (int)(c2.getBlue());
	    	            if(red2==0 && green2==0 && blue2==0)
	    	            {
	    	            	cnt++;
	    	            }
	            	}
	            }
	            //System.out.println(cnt);
	            if(cnt==25)
	            {
	            	cnt=0;
	            	Color newColor=new Color(50,50,200);
	    			image.setRGB(0,i-5,newColor.getRGB());
	    			rowmap[rowcnt]=i-5;
	    			i=i+50;
	    			rowcnt++;
	            }
			}
		}
		rowmap[rowcnt]=height;
		System.out.println("Rowcnt : "+rowcnt);
		for(int i=0;i<rowcnt+1;i++)
		{
			//System.out.println("Rowmap["+i+"] : "+rowmap[i]);
		}
		/*File ouptut1 = new File("RowMarking.png");
		try {
			ImageIO.write(image, "png", ouptut1);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	void RowSegment() {
		int width = image.getWidth();
		for(int i=0;i<rowcnt;i++)
		{
			//System.out.println("rowmap["+i+"] : "+rowmap[i]+"   rowmap["+(i+1)+"]: "+rowmap[i+1]);
			rowdb[i]=image.getSubimage(0,(rowmap[i]-18),width,((rowmap[i+1]-rowmap[i])+10));
			/*File ouptut1 = new File("row "+i+".png");
			try {
				ImageIO.write(rowdb[i], "png", ouptut1);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
	}
	
	void NoteSegmentation() {
		for(int y=0;y<rowcnt;y++)
		{
			for(int i=0;i<50;i++)
				{
					for(int j=0;j<4;j++)
					{
						segments[y][i][j]=0;
					}
				}
		}
		for(int y=0;y<rowcnt;y++)
		{
			
			int width = rowdb[y].getWidth();
		    int height = rowdb[y].getHeight();
		    int cnt=0;
		    
		    index=0;
		    for(int j=0;j<width;j++)
			{
		    	for(int i=0;i<height;i++)
		    	{
				
					Color c1 = new Color(rowdb[y].getRGB(j, i));
		            int red1 = (int)(c1.getRed());
		            int green1 = (int)(c1.getGreen());
		            int blue1 = (int)(c1.getBlue());
		            if(red1==100 && green1==50 && blue1==80)
		            {
		            	//xmin
			            for(int j1=j;j1>0;j1--)
			            	{
			            		//System.out.println("y : "+y);
			            		Color c2 = new Color(rowdb[y].getRGB(j1, i+1));
			            		int red2 = (int)(c2.getRed());
			            		int green2 = (int)(c2.getGreen());
			            		int blue2 = (int)(c2.getBlue());
			            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
			            		if((red2==0 && green2==0 && blue2==0))
			            		{
			            			//System.out.println("here");
			            			cnt=0;
			            			for(int j2=0;j2<7;j2++)
			            			{
			            				//System.out.println("y "+y+"       index "+index);
			            				Color c3 = new Color(rowdb[y].getRGB(j1-j2, i+1));
			            				int red3 = (int)(c3.getRed());
			            				int green3 = (int)(c3.getGreen());
			            				int blue3 = (int)(c3.getBlue());
			            				if(red3==255 && green3==255 && blue3==255)
			            				{
			            					//System.out.println("here");
			            					cnt++;
			            				}
			            			}
			            			//System.out.println("cnt : "+cnt);
			            			if(cnt==6)
			            			{
			            				segments[y][index][0]=j1-2;
			            			}
			            		}
			            		
			            		
			            		/*if(segments[y][index][0]!=0)
			            		{
			            			Color newColor=new Color(100,100,200);
			            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
			            			rowdb[y].setRGB(segments[y][index][0],i	+1,newColor.getRGB());
			            		}*/
			            		
				    			if(cnt==6)
		            			{
		            				break;
		            			}
			            	}
			            
			            //xmax
			            for(int j1=j;j1<width;j1++)
		            	{
		            		//System.out.println("i1 : "+i1);
		            		Color c2 = new Color(rowdb[y].getRGB(j1, i+1));
		            		int red2 = (int)(c2.getRed());
		            		int green2 = (int)(c2.getGreen());
		            		int blue2 = (int)(c2.getBlue());
		            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
		            		if((red2==0 && green2==0 && blue2==0))
		            		{
		            			//System.out.println("here");
		            			cnt=0;
		            			for(int j2=0;j2<8;j2++)
		            			{
		            				//System.out.println("y "+y+"       index "+index);
		            				Color c3 = new Color(rowdb[y].getRGB(j1+j2, i+1));
		            				int red3 = (int)(c3.getRed());
		            				int green3 = (int)(c3.getGreen());
		            				int blue3 = (int)(c3.getBlue());
		            				if(red3==255 && green3==255 && blue3==255)
		            				{
		            					//System.out.println("here");
		            					cnt++;
		            				}
		            			}
		            			//System.out.println("cnt : "+cnt);
		            			if(cnt==7)
		            			{
		            				//System.out.println("here");
		            				segments[y][index][2]=j1+2;
		            			}
		            		}
		            		
		            		
		            		/*if(segments[y][index][2]!=0)
		            		{
		            			//System.out.println("here");
		            			Color newColor=new Color(100,100,200);
		            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
		            			rowdb[y].setRGB(segments[y][index][2],i+1,newColor.getRGB());
		            		}*/
		            		
			    			if(cnt==7)
	            			{
	            				break;
	            			}
		            	}
			            
			          //ymin
		            	for(int i1=i+2;i1>0;i1--)
		            	{
		            		//System.out.println("y: "+y+"   i11 : "+i1);
		            		Color c2 = new Color(rowdb[y].getRGB(j, i1));
		            		int red2 = (int)(c2.getRed());
		            		int green2 = (int)(c2.getGreen());
		            		int blue2 = (int)(c2.getBlue());
		            		if((red2==0 && green2==0 && blue2==0) || (red2==100 && green2==50 && blue2==80))
		            		{
		            			cnt=0;
		            			for(int i2=0;i2<6;i2++)
		            			{
		            				if(i2<i1)
		            				{
		            					Color c3 = new Color(rowdb[y].getRGB(j, i1-i2));
		            					int red3 = (int)(c3.getRed());
		            					int green3 = (int)(c3.getGreen());
		            					int blue3 = (int)(c3.getBlue());
		            					if(red3==255 && green3==255 && blue3==255)
		            					{
		            						cnt++;
		            					}
		            				}
		            			}
		            			if(cnt==5)
		            			{
		            				segments[y][index][1]=i1-8;//8
		            			}
		            		}
		            		
		            		if(segments[y][index][1]!=0)
		            		{
		            			//Color newColor=new Color(200,100,100);
		            			//System.out.println("index: "+index+" i1= "+segments[index][1]+" j= "+j);
		            			//rowdb[y].setRGB(j,segments[y][index][1],newColor.getRGB());
		            		}
		            		
			    			if(cnt==7)
	            			{
	            				break;
	            			}
		            	}
		            	 
		            	//ymax
		            	for(int i1=i+1;i1<height;i1++)
		            	{
		            		//System.out.println("i1 : "+i1);
		            		Color c2 = new Color(rowdb[y].getRGB(j, i1));
		            		int red2 = (int)(c2.getRed());
		            		int green2 = (int)(c2.getGreen());
		            		int blue2 = (int)(c2.getBlue());
		            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
		            		if((red2==0 && green2==0 && blue2==0))
		            		{
		            			//System.out.println("here");
		            			cnt=0;
		            			for(int i2=0;i2<8;i2++)
		            			{
		            				//System.out.println("y "+y+"       index "+index);
		            				Color c3 = new Color(rowdb[y].getRGB(j, i1+i2));
		            				int red3 = (int)(c3.getRed());
		            				int green3 = (int)(c3.getGreen());
		            				int blue3 = (int)(c3.getBlue());
		            				if(red3==255 && green3==255 && blue3==255)
		            				{
		            					cnt++;
		            				}
		            			}
		            			//System.out.println("cnt : "+cnt);
		            			if(cnt==7)
		            			{
		            				segments[y][index][3]=i1+11;//11
		            			}
		            		}
		            		
		            		
		            		/*if(segments[y][index][3]!=0)
		            		{
		            			Color newColor=new Color(100,100,200);
		            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
		            			rowdb[y].setRGB(j,segments[y][index][3],newColor.getRGB());
		            		}*/
		            		
			    			if(cnt==7)
	            			{
	            				break;
	            			}
		            	}
			            index++;
		            }
		    	}
			}
		    
		    //xmax
		    /*index=0;
		    for(int j=0;j<width;j++)
			{
		    	for(int i=0;i<height;i++)
		    	{
				
					Color c1 = new Color(rowdb[y].getRGB(j, i));
		            int red1 = (int)(c1.getRed());
		            int green1 = (int)(c1.getGreen());
		            int blue1 = (int)(c1.getBlue());
		            if(red1==100 && green1==50 && blue1==80)
		            {
			            for(int j1=j;j1<width;j1++)
			            	{
			            		//System.out.println("i1 : "+i1);
			            		Color c2 = new Color(rowdb[y].getRGB(j1, i+1));
			            		int red2 = (int)(c2.getRed());
			            		int green2 = (int)(c2.getGreen());
			            		int blue2 = (int)(c2.getBlue());
			            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
			            		if((red2==0 && green2==0 && blue2==0))
			            		{
			            			//System.out.println("here");
			            			cnt=0;
			            			for(int j2=0;j2<8;j2++)
			            			{
			            				//System.out.println("y "+y+"       index "+index);
			            				Color c3 = new Color(rowdb[y].getRGB(j1+j2, i+1));
			            				int red3 = (int)(c3.getRed());
			            				int green3 = (int)(c3.getGreen());
			            				int blue3 = (int)(c3.getBlue());
			            				if(red3==255 && green3==255 && blue3==255)
			            				{
			            					//System.out.println("here");
			            					cnt++;
			            				}
			            			}
			            			//System.out.println("cnt : "+cnt);
			            			if(cnt==7)
			            			{
			            				//System.out.println("here");
			            				segments[y][index][2]=j1+2;
			            			}
			            		}
			            		
			            		
			            		if(segments[y][index][2]!=0)
			            		{
			            			//System.out.println("here");
			            			//Color newColor=new Color(100,100,200);
			            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
			            			//rowdb[y].setRGB(segments[y][index][2],i+1,newColor.getRGB());
			            		}
			            		
				    			if(cnt==7)
		            			{
		            				break;
		            			}
			            	}
			            index++;
		            }
		    	}
			}
		    
		  //ymin
		    index=0;
		    for(int j=0;j<width;j++)
			{
		    	for(int i=0;i<height;i++)
		    	{
					Color c1 = new Color(rowdb[y].getRGB(j, i));
		            int red1 = (int)(c1.getRed());
		            int green1 = (int)(c1.getGreen());
		            int blue1 = (int)(c1.getBlue());
		            if(red1==100 && green1==50 && blue1==80)
		            {
		            	//xmin
		            	//segments[y][index][0]=j-6;
		            	//xmax
		            	//segments[y][index][2]=j+10;
		            	
		            	//System.out.println("index:"+index);
		            	//System.out.println(segments[y][index][0]+"    "+segments[y][index][2]);
		            	
		            	//ymin
		            	for(int i1=i+2;i1>0;i1--)
		            	{
		            		//System.out.println("y: "+y+"   i11 : "+i1);
		            		Color c2 = new Color(rowdb[y].getRGB(j, i1));
		            		int red2 = (int)(c2.getRed());
		            		int green2 = (int)(c2.getGreen());
		            		int blue2 = (int)(c2.getBlue());
		            		if((red2==0 && green2==0 && blue2==0) || (red2==100 && green2==50 && blue2==80))
		            		{
		            			cnt=0;
		            			for(int i2=0;i2<8;i2++)
		            			{
		            				if(i2<i1)
		            				{
		            					Color c3 = new Color(rowdb[y].getRGB(j, i1-i2));
		            					int red3 = (int)(c3.getRed());
		            					int green3 = (int)(c3.getGreen());
		            					int blue3 = (int)(c3.getBlue());
		            					if(red3==255 && green3==255 && blue3==255)
		            					{
		            						cnt++;
		            					}
		            				}
		            			}
		            			if(cnt==7)
		            			{
		            				segments[y][index][1]=i1-8;
		            			}
		            		}
		            		
		            		if(segments[y][index][1]!=0)
		            		{
		            			Color newColor=new Color(200,100,100);
		            			//System.out.println("index: "+index+" i1= "+segments[index][1]+" j= "+j);
		            			rowdb[y].setRGB(j,segments[y][index][1],newColor.getRGB());
		            		}
		            		
			    			if(cnt==7)
	            			{
	            				break;
	            			}
		            	}
		            	 
		            	//ymax
		            	for(int i1=i+1;i1<height;i1++)
		            	{
		            		//System.out.println("i1 : "+i1);
		            		Color c2 = new Color(rowdb[y].getRGB(j, i1));
		            		int red2 = (int)(c2.getRed());
		            		int green2 = (int)(c2.getGreen());
		            		int blue2 = (int)(c2.getBlue());
		            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
		            		if((red2==0 && green2==0 && blue2==0))
		            		{
		            			//System.out.println("here");
		            			cnt=0;
		            			for(int i2=0;i2<8;i2++)
		            			{
		            				//System.out.println("y "+y+"       index "+index);
		            				Color c3 = new Color(rowdb[y].getRGB(j, i1+i2));
		            				int red3 = (int)(c3.getRed());
		            				int green3 = (int)(c3.getGreen());
		            				int blue3 = (int)(c3.getBlue());
		            				if(red3==255 && green3==255 && blue3==255)
		            				{
		            					cnt++;
		            				}
		            			}
		            			//System.out.println("cnt : "+cnt);
		            			if(cnt==7)
		            			{
		            				segments[y][index][3]=i1+11;
		            			}
		            		}
		            		
		            		
		            		if(segments[y][index][3]!=0)
		            		{
		            			Color newColor=new Color(100,100,200);
		            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
		            			rowdb[y].setRGB(j,segments[y][index][3],newColor.getRGB());
		            		}
		            		
			    			if(cnt==7)
	            			{
	            				break;
	            			}
		            	}
		            	index++;
		            }
		            
				}
			}
		  //ymax
		    index=0;
		    for(int j=0;j<width;j++)
			{
		    	for(int i=0;i<height;i++)
		    	{
				
					Color c1 = new Color(rowdb[y].getRGB(j, i));
		            int red1 = (int)(c1.getRed());
		            int green1 = (int)(c1.getGreen());
		            int blue1 = (int)(c1.getBlue());
		            if(red1==100 && green1==50 && blue1==80)
		            {
			            for(int i1=i+1;i1<height;i1++)
			            	{
			            		//System.out.println("i1 : "+i1);
			            		Color c2 = new Color(rowdb[y].getRGB(j, i1));
			            		int red2 = (int)(c2.getRed());
			            		int green2 = (int)(c2.getGreen());
			            		int blue2 = (int)(c2.getBlue());
			            		//System.out.println("red : "+red2+" green : "+green2+" blue : "+blue2);
			            		if((red2==0 && green2==0 && blue2==0))
			            		{
			            			//System.out.println("here");
			            			cnt=0;
			            			for(int i2=0;i2<8;i2++)
			            			{
			            				//System.out.println("y "+y+"       index "+index);
			            				Color c3 = new Color(rowdb[y].getRGB(j, i1+i2));
			            				int red3 = (int)(c3.getRed());
			            				int green3 = (int)(c3.getGreen());
			            				int blue3 = (int)(c3.getBlue());
			            				if(red3==255 && green3==255 && blue3==255)
			            				{
			            					cnt++;
			            				}
			            			}
			            			//System.out.println("cnt : "+cnt);
			            			if(cnt==7)
			            			{
			            				segments[y][index][3]=i1+11;
			            			}
			            		}
			            		
			            		
			            		if(segments[y][index][3]!=0)
			            		{
			            			Color newColor=new Color(100,100,200);
			            			//System.out.println("y: "+y+" index: "+index+" i1= "+segments[y][index][3]+" j= "+j);
			            			rowdb[y].setRGB(j,segments[y][index][3],newColor.getRGB());
			            		}
			            		
				    			if(cnt==7)
		            			{
		            				break;
		            			}
			            	}
			            index++;
		            }
		    	}
			}*/
		    /*File ouptut = new File("noterecog "+y+".png");
			try {
				ImageIO.write(rowdb[y], "png", ouptut);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
		}
		/*for(int y=0;y<rowcnt;y++)
		{
			for(int i=0;i<10;i++)
			{
				System.out.println("segments["+y+"]["+i+"][0]= "+segments[y][i][0]+";");
				System.out.println("segments["+y+"]["+i+"][1]= "+segments[y][i][1]+";");
				System.out.println("segments["+y+"]["+i+"][2]= "+segments[y][i][2]+";");
				System.out.println("segments["+y+"]["+i+"][3]= "+segments[y][i][3]+";");
			}
		}*/
	}

	void Reworking() {
		int width = image.getWidth();
	    int height = image.getHeight();
	    for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				Color c1 = new Color(image.getRGB(j, i));
	            int red1 = (int)(c1.getRed());
	            int green1 = (int)(c1.getGreen());
	            int blue1 = (int)(c1.getBlue());
	            if(red1==100 && green1==50 && blue1==80)
	            {
	            	Color c2 = new Color(image.getRGB(j, i+1));
    	            int red2 = (int)(c2.getRed());
    	            int green2 = (int)(c2.getGreen());
    	            int blue2 = (int)(c2.getBlue());
    	            if(red2==100 && green2==50 && blue2==80)
    	            {
    	            	Color newColor=new Color(0,0,0);
		    			image.setRGB(j,i+1,newColor.getRGB());
    	            }
    	            Color c3 = new Color(image.getRGB(j-1, i+1));
    	            int red3 = (int)(c3.getRed());
    	            int green3 = (int)(c3.getGreen());
    	            int blue3 = (int)(c3.getBlue());
    	            if(red3==100 && green3==50 && blue3==80)
    	            {
    	            	Color newColor=new Color(0,0,0);
		    			image.setRGB(j-1,i+1,newColor.getRGB());
    	            }
	            }
			}
		}
	    	
	    /*File ouptut = new File("ReWorking.png");
		try {
			ImageIO.write(image, "png", ouptut);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	void Identification() {
		int width = image.getWidth();
	    int height = image.getHeight();
	    int cnt=0;
	    int cnt1=0;
	    int cnt2=0;
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				Color c1 = new Color(image.getRGB(j, i));
	            int red1 = (int)(c1.getRed());
	            int green1 = (int)(c1.getGreen());
	            int blue1 = (int)(c1.getBlue());
	            if(red1==0 && green1==0 && blue1==0)
	            {
	            	cnt=0;
	            	for(int k=0;k<6;k++)
	            	{
	            		for(int l=0;l<6;l++)
	            		{
	            			Color c2 = new Color(image.getRGB(j+l, i+k));
	        	            int red2 = (int)(c2.getRed());
	        	            int green2 = (int)(c2.getGreen());
	        	            int blue2 = (int)(c2.getBlue());
	        	            if(red2==0 && green2==0 && blue2==0)
	        	            {
	        	            	cnt++;
	        	            }
	            		}
	            	}
	            	if(cnt==36)
	            	{
	            		Color newColor=new Color(100,50,80);
		    			image.setRGB(j,i,newColor.getRGB());
		    			j=j+3;
	            	}
	            }
			}
		}
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				Color c1 = new Color(image.getRGB(j, i));
	            int red1 = (int)(c1.getRed());
	            int green1 = (int)(c1.getGreen());
	            int blue1 = (int)(c1.getBlue());
	            if(red1==0 && green1==0 && blue1==0)
	            {
	            	cnt=0;
	            	for(int k=0;k<7;k++)
	            	{
	            		Color c2 = new Color(image.getRGB(j+k, i));
        	            int red2 = (int)(c2.getRed());
        	            int green2 = (int)(c2.getGreen());
        	            int blue2 = (int)(c2.getBlue());
        	            if(red2==0 && green2==0 && blue2==0)
        	            {
        	            	cnt++;
        	            }
	            	}
	            	if(cnt==7)
	            	{
	            		//System.out.println("here");
	            		cnt1=0;
		            	for(int k=0;k<5;k++)
		            	{
		            		Color c2 = new Color(image.getRGB((j-1)+k, i+1));
		        	        int red2 = (int)(c2.getRed());
		        	        int green2 = (int)(c2.getGreen());
		        	        int blue2 = (int)(c2.getBlue());
		        	        if(red2==0 && green2==0 && blue2==0)
		        	        {
		        	          	cnt1++;
		            		}
		            	}
		            	if(cnt1==5)
		            	{
		            		//System.out.println("here");
		            		Color c2 = new Color(image.getRGB((j-1)+5, i+1));
		        	        int red2 = (int)(c2.getRed());
		        	        int green2 = (int)(c2.getGreen());
		        	        int blue2 = (int)(c2.getBlue());
		        	        if(red2==255 && green2==255 && blue2==255)
		        	        {
		        	        	//System.out.println("here");
		        	        	Color c3 = new Color(image.getRGB((j-1)+6, i+1));
			        	        int red3 = (int)(c3.getRed());
			        	        int green3 = (int)(c3.getGreen());
			        	        int blue3 = (int)(c3.getBlue());
			        	        Color c4 = new Color(image.getRGB((j-1)+7, i+1));
			        	        int red4 = (int)(c4.getRed());
			        	        int green4 = (int)(c4.getGreen());
			        	        int blue4 = (int)(c4.getBlue());
			        	        if(red3==0 && green3==0 && blue3==0 && red4==0 && green4==0 && blue4==0)
			        	        {
			        	        	//System.out.println("here");
			        	        	cnt2=0;
					            	for(int k=0;k<4;k++)
					            	{
					            		Color c5 = new Color(image.getRGB((j-2)+k, i+2));
					        	        int red5 = (int)(c5.getRed());
					        	        int green5 = (int)(c5.getGreen());
					        	        int blue5 = (int)(c5.getBlue());
					        	        if(red5==0 && green5==0 && blue5==0)
					        	        {
					        	          	cnt2++;
					            		}
					            	}
					            	if(cnt2==4)
					            	{
					            		//System.out.println("here");
					            		Color c6 = new Color(image.getRGB((j-2)+4, i+2));
					        	        int red6 = (int)(c6.getRed());
					        	        int green6 = (int)(c6.getGreen());
					        	        int blue6 = (int)(c6.getBlue());
					        	        Color c7 = new Color(image.getRGB((j-2)+5, i+2));
					        	        int red7 = (int)(c7.getRed());
					        	        int green7 = (int)(c7.getGreen());
					        	        int blue7 = (int)(c7.getBlue());
					        	        Color c8 = new Color(image.getRGB((j-2)+6, i+2));
					        	        int red8 = (int)(c8.getRed());
					        	        int green8 = (int)(c8.getGreen());
					        	        int blue8 = (int)(c8.getBlue());
					        	        if(red6==255 && green6==255 && blue6==255 && red7==255 && green7==255 && blue7==255 && red8==255 && green8==255 && blue8==255)
					        	        {
					        	        	//System.out.println("here");
					        	        	Color c9 = new Color(image.getRGB((j-2)+7, i+2));
						        	        int red9 = (int)(c9.getRed());
						        	        int green9 = (int)(c9.getGreen());
						        	        int blue9 = (int)(c9.getBlue());
						        	        Color c10 = new Color(image.getRGB((j-2)+8, i+2));
						        	        int red10 = (int)(c10.getRed());
						        	        int green10 = (int)(c10.getGreen());
						        	        int blue10 = (int)(c10.getBlue());
						        	        if(red9==0 && green9==0 && blue9==0 && red10==0 && green10==0 && blue10==0)
						        	        {
						        	        	//System.out.println("here");
						        	        	Color newColor=new Color(100,50,80);
								    			image.setRGB(j,i,newColor.getRGB());
								    			j=j+3;
						            		}
					            		}
					            	}
			            		}
		            		}
		            	}
	            	}
	            }
			}
		}
		/*File ouptut = new File("Rearrange.png");
		try {
			ImageIO.write(image, "png", ouptut);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	void Database() throws NumberFormatException, IOException {
		
		/*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Tempo of music piece : ");
		temp = Integer.parseInt(br.readLine());
		System.out.println("Enter Time Signature of music piece : ");
		ts = Integer.parseInt(br.readLine());*/
		
		dbdecleration dbs=new dbdecleration();
		dbs.DatabaseDeclaration(dbsegments);
		
	}
		
	static public void main(String args[]) throws IOException {
		
		String fname = null;
		   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   System.out.println("Enter name of file to be scanned : ");
		   
		   try
		   {
			   fname = br.readLine();
		   }
		   catch (IOException e)
		   {
			   e.printStackTrace();
		   }
		   
		  try {
			image=ImageIO.read(new File(fname));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		  GreyscaleOMR gs= new GreyscaleOMR();
		  image=gs.convert(image);
		//@SuppressWarnings("unused")
		//noterecognition nr=new noterecognition();
	}

}