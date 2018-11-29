import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class YCbCr {
	public static void RGBtoYCbCr(Color [][] img, double [][] Ychannel, double [][] Cbchannel, double [][] Crchannel, int picWidth, int picHeight) {
		for(int i=0; i<Ychannel.length; i++) {
			for(int j=0; j<Ychannel[1].length; j++) {
				int r;
				int g;
				int b;
				if(i >= picWidth || j >= picHeight) {
					r = 0;
					g = 0;
					b = 0;
				}
				else {
					// System.out.println(img[0].length);
					// System.out.println(img[i][j]);
					r = img[i][j].getRed();
					g = img[i][j].getGreen();
					b = img[i][j].getBlue();
				}
				Ychannel[i][j] = 0.299*r + 0.587*g + 0.114*b;
				Cbchannel[i/2][j/2] = -0.169*r - 0.331*g + 0.5*b + 128;
				Crchannel[i/2][j/2] = 0.5*r - 0.419*g - 0.081*b + 128;
			}
		}
	}
	
	public static void YCbCrtoRGB(Color[][] pic, double[][] Ychannel, double[][] Cbchannel, double[][] Crchannel) {
		for (int i = 0; i < pic.length; i++) {
			for (int j = 0; j < pic[0].length; j++) {
				double y = Ychannel[i][j];
				double cb = Cbchannel[i / 2][j / 2];
				double cr = Crchannel[i / 2][j / 2];
				
				int r,g,b;
				r = (int) (y+1.13983*(cr-128));
				g = (int) (y-0.39465*(cb-128)-0.58060*(cr-128));
				b = (int) (y+2.03211*(cb-128));
				r=r>255?255:r<0?0:r;
				g=g>255?255:g<0?0:g;
				b=b>255?255:b<0?0:b;
				pic[i][j] = new Color(r, g, b);
			}
		}
	}
}
