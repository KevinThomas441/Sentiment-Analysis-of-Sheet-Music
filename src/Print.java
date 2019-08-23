
public class Print {
	
	int PrintNote(Integer[][][] ecnt) {
		int max=0;
		int i=-1;
		int r=-1;
		int c=-1;
		//int img=-1;
		for(int imgdb = 0;imgdb<9;imgdb++)
		{
			for(int rdb = 0;rdb<11;rdb++)
			{
				for(int cdb = 0;((cdb<3 && rdb<10)||(cdb<2 && rdb==10));cdb++)
				{
					//System.out.println("imgdb : "+imgdb+" rdb :"+rdb+" cdb :"+cdb+" max :"+ecnt[rdb][cdb]);
					/*if(imgdb==6 || imgdb==7 || imgdb==8)
					{
						if((rdb%2!=0 && cdb%2!=1) || (rdb%2!=1 && cdb%2!=0))
						{
							if(max<ecnt[imgdb][rdb][cdb])
							{
								max=ecnt[imgdb][rdb][cdb];
								i=imgdb%3;
								r=rdb;
								c=cdb;
							}
						}
					}
					if(imgdb==3 || imgdb==4 || imgdb==5)
					{
						if(rdb!=0 && rdb!=1 && rdb!=2 && rdb!=3 && rdb!=4 && rdb!=5)
						{
							if(max<ecnt[imgdb][rdb][cdb])
							{
								max=ecnt[imgdb][rdb][cdb];
								i=imgdb%3;
								r=rdb;
								c=cdb;
							}
						}
					}
					if(imgdb==0 || imgdb==1 || imgdb==2 || imgdb==3 || imgdb==4 || imgdb==5 || imgdb==6 || imgdb==7 || imgdb==8)
					{*/
						if(max<ecnt[imgdb][rdb][cdb])
						{
							max=ecnt[imgdb][rdb][cdb];
							i=imgdb%3;
							r=rdb;
							c=cdb;
							//img=imgdb;
						}
				}
			}
		}
		//System.out.println(" img :"+img+" r :"+r+" c :"+c);//+" max :"+ecnt[r][c]
		switch(i)
		{
			case 0:
					switch(r)
					{
						case 0 : 
							switch (c)
							{
								case 0:
									return 5;
								case 1:
									return 6;
								case 2:
									return 8;
								default: 
									return 0;
							}
							
						case 1 : 
							switch (c)
							{
								case 0:
									return 10;
								case 1:
									return 12;
								case 2:
									return 13;
								default: 
									  return 0;
							}
							
						case 2 : 
							switch (c)
							{
								case 0:
									return 15;
								case 1:
									return 17;
								case 2:
									return 18;
								default: 
									return 0;
							}
							
						case 3 : 
							switch (c)
							{
								case 0:
									return 20;
								case 1:
									return 22;
								case 2:
									return 24;
								default: 
									return 0;
							}
							
						case 4 : 
							switch (c)
							{
								case 0:
									return 25;
								case 1:
									return 27;
								case 2:
									return 29;
								default: 
									  return 0;
							}
							
						case 5 : 
							switch (c)
							{
								case 0:
									return 30;
								case 1:
									return 32;
								case 2:
									return 34;
								default: 
									return 0;
							}
							
						case 6 : 
							switch (c)
							{
								case 0:
									return 36;
								case 1:
									return 37;
								case 2:
									return 39;
								default: 
									return 0;
							}
							
						case 7 : 
							switch (c)
							{
								case 0:
									return 41;
								case 1:
									return 42;
								case 2:
									return 44;
								default: 
									return 0;
							}
							
						case 8 : 
							switch (c)
							{
								case 0:
									return 46;
								case 1:
									return 48;
								case 2:
									return 49;
								default: 
									return 0;
							}
							
						case 9 : 
							switch (c)
							{
								case 0:
									return 51;
								case 1:
									return 53;
								case 2:
									return 54;
								default: 
									return 0;
							}
							
						case 10 : 
							switch (c)
							{
								case 0:
									return 56;
								case 1:
									return 58;
								default: 
									return 0;
							}
							
					default: 
						return 0;
			}
			
			case 1:
				switch(r)
				{
					case 0 : 
						switch (c)
						{
							case 0:
								return 4;
							case 1:
								return 5;
							case 2:
								return 7;
							default: 
								return 0;
						}
						
					case 1 : 
						switch (c)
						{
							case 0:
								return 9;
							case 1:
								return 11;
							case 2:
								return 12;
							default: 
								return 0;
						}
						
					case 2 : 
						switch (c)
						{
							case 0:
								return 14;
							case 1:
								return 16;
							case 2:
								return 17;
							default: 
								return 0;
						}
						
					case 3 : 
						switch (c)
						{
							case 0:
								return 19;
							case 1:
								return 21;
							case 2:
								return 23;
							default: 
								return 0;
						}
						
					case 4 : 
						switch (c)
						{
							case 0:
								return 24;
							case 1:
								return 26;
							case 2:
								return 28;
							default: 
								return 0;
						}
						
					case 5 : 
						switch (c)
						{
							case 0:
								return 29;
							case 1:
								return 31;
							case 2:
								return 33;
							default: 
								return 0;
						}
						
					case 6 : 
						switch (c)
						{
							case 0:
								return 35;
							case 1:
								return 36;
							case 2:
								return 38;
							default: 
								return 0;
						}
						
					case 7 : 
						switch (c)
						{
							case 0:
								return 40;
							case 1:
								return 41;
							case 2:
								return 43;
							default: 
								return 0;
						}

					case 8 : 
						switch (c)
						{
							case 0:
								return 45;
							case 1:
								return 47;
							case 2:
								return 48;
							default: 
								return 0;
						}
						
					case 9 : 
						switch (c)
						{
							case 0:
								return 50;
							case 1:
								return 52;
							case 2:
								return 53;
							default: 
								return 0;
						}
						
					case 10 : 
						switch (c)
						{
							case 0:
								return 55;
							case 1:
								return 57;
							default: 
								return 0;
						}
						
				default: 
					return 0;
		}
	
	case 2:
		switch(r)
		{
			case 0 : 
				switch (c)
				{
					case 0:
						return 6;
					case 1:
						return 7;
					case 2:
						return 9;
					default: 
						return 0;
				}
				
			case 1 : 
				switch (c)
				{
					case 0:
						return 11;
					case 1:
						return 13;
					case 2:
						return 14;
					default: 
						return 0;
				}
				
			case 2 : 
				switch (c)
				{
					case 0:
						return 16;
					case 1:
						return 18;
					case 2:
						return 19;
					default: 
						return 0;
				}
				
			case 3 : 
				switch (c)
				{
					case 0:
						return 21;
					case 1:
						return 23;
					case 2:
						return 25;
					default: 
						return 0;
				}
				
			case 4 : 
				switch (c)
				{
					case 0:
						return 26;
					case 1:
						return 28;
					case 2:
						return 30;
					default: 
						return 0;
				}
				
			case 5 : 
				switch (c)
				{
					case 0:
						return 31;
					case 1:
						return 33;
					case 2:
						return 35;
					default: 
						return 0;
				}
				
			case 6 : 
				switch (c)
				{
					case 0:
						return 37;
					case 1:
						return 38;
					case 2:
						return 40;
					default: 
						return 0;
				}
				
			case 7 : 
				switch (c)
				{
					case 0:
						return 42;
					case 1:
						return 43;
					case 2:
						return 45;
					default: 
						return 0;
				}	
				
			case 8 : 
				switch (c)
				{
					case 0:
						return 47;
					case 1:
						return 49;
					case 2:
						return 50;
					default: 
						return 0;
				}
				
			case 9 : 
				switch (c)
				{
					case 0:
						return 52;
					case 1:
						return 54;
					case 2:
						return 55;
					default: 
						return 0;
				}
				
			case 10 : 
				switch (c)
				{
					case 0:
						return 57;
					case 1:
						return 59;
					default: 
						return 0;
				}
				
			default: 
				return 0;
		}
		
		default: 
			return 0;
		}
	}

}
