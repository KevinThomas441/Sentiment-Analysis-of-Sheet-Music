
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;

public class GreyscaleOMR {

   BufferedImage  image;
   int width;
   int height;
   
   public BufferedImage convert(BufferedImage b) {
   
      try {
    	 //File input = new File(name);
         image = b;//ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         for(int i=0; i<height; i++){
        	 
            for(int j=0; j<width; j++){
              
            	Color c = new Color(image.getRGB(j, i));
                int red = (int)(c.getRed());//0.299
                int green = (int)(c.getGreen());//0.587
                int blue = (int)(c.getBlue());//0.114
                if(red!=245 || blue!=245 || green!=245)//<180(||)
                {
             	   //System.out.println("red : "+red+" green : "+green+" blue :  "+blue);
                	Color newColor = new Color(0,0,0);
             	   image.setRGB(j,i,newColor.getRGB());
                }
                else
                {
             	   Color newColor = new Color(255,255,255);
             	   image.setRGB(j,i,newColor.getRGB());
                }
            }
         }

         //File ouptut = new File(name+"Grayscale.png");
         //ImageIO.write(image, "png", ouptut);
      } catch (Exception e) {}
	return image;
   }
      
   static public void main(String args[])
   {
      GreyscaleOMR obj = new GreyscaleOMR();
      BufferedImage  image=null;
      String fname = null;
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   System.out.println("Enter name of file to be scanned : ");
	   try {
		fname = br.readLine();
	} catch (IOException e) {
		e.printStackTrace();
	}
	   try {
		image=ImageIO.read(new File(fname+".png"));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	   image = obj.convert(image);
	   File ouptut = new File(fname+"Grayscale.png");
	   try {
		ImageIO.write(image, "png", ouptut);
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}