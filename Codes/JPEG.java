import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Vector;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class JPEG {
	public static HuffmanCode huffmanCode;
	static String YchannelCode = "", CbChannelCode = "", CrChannelCode = "";
	// static PicRead pr;
	static int picWidth;
	static int picHeight;
	public static void encoding() {
		File filename = new File("../imgs/cartoon.jpg");
		if(!filename.exists()) {
			return; 
		}
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		picWidth = bi.getWidth();
		picHeight = bi.getHeight();
		Color[][] pic = new Color[picWidth%16==0?picWidth:(1+picWidth/16)*16][picHeight%16==0?picHeight:(1+picHeight/16)*16];
		
		for (int i = 0; i < picWidth ; i++) {
			for (int j = 0; j < picHeight; j++) {
				pic[i][j] = new Color(bi.getRGB(i, j));
			}
		}
		// System.out.println("encoding");
		// read thuffmanCode image and padding
		double [][] yChannel = new double[pic.length][pic[0].length];
		double [][] cbChannel = new double[pic.length/2][pic[0].length/2];
		double [][] crChannel = new double[pic.length/2][pic[0].length/2];
		// RGB to YCbCr
		YCbCr.RGBtoYCbCr(pic, yChannel, cbChannel, crChannel, picWidth, picHeight);


		double [][][] yAfterDCT = DCTcoding.dct(yChannel);
		int [][][]  yAfterQua = Quatization.quatizeY(yAfterDCT);

		double [][][] cbAfterDCT = DCTcoding.dct(cbChannel);
		int [][][]  cbAfterQua = Quatization.quatizeCbCr(cbAfterDCT);
		
		double [][][] crAfterDCT = DCTcoding.dct(crChannel);
		int [][][]  crAfterQua = Quatization.quatizeCbCr(crAfterDCT);
		
		int [][] yAfterZigZag = ZigZag.zigzag(yAfterQua);
		int [][] cbAfterZigZag = ZigZag.zigzag(cbAfterQua);
		int [][] crAfterZigZag = ZigZag.zigzag(crAfterQua);
		
		huffmanCode = new HuffmanCode();
		for(int i=0; i<yAfterZigZag.length; i++) {
			YchannelCode += huffmanCode.LumEncoding(yAfterZigZag[i]);
		}
		for(int i=0; i<cbAfterZigZag.length; i++) {
			CbChannelCode += huffmanCode.ColorEncoding(cbAfterZigZag[i]);
			CrChannelCode += huffmanCode.ColorEncoding(crAfterZigZag[i]);
		}
		System.out.print("ThuffmanCode length of 01 string after compression: ");
		System.out.println(YchannelCode.length() + CrChannelCode.length()+CbChannelCode.length());
	}
	
	public static void decoding() {
		System.out.println("begin decoding");

		Vector<int[]> yChannelHuff = new Vector<int[]>();
		Vector<int[]> CbChannelHuff = new Vector<int[]>();
		Vector<int[]> CrChannelHuff = new Vector<int[]>();
		
		while(!YchannelCode.equals("")) {
			int[] line = new int[64];
			YchannelCode = huffmanCode.LumDecoding(YchannelCode, line);
			yChannelHuff.addElement(line);
		}
		// yChannelHuff.addElement(line);
		while(!CbChannelCode.equals("")) {
			int[] line = new int[64];
			CbChannelCode = huffmanCode.ColorDecoding(CbChannelCode, line);
			CbChannelHuff.addElement(line);
		}
		// CbChannelHuff.addElement(line);
		while(!CrChannelCode.equals("")) {
			int[] line = new int[64];
			CrChannelCode = huffmanCode.ColorDecoding(CrChannelCode, line);
			CrChannelHuff.addElement(line);
		}

		int[][][] YMacroBlock = ZigZag.izigzag(yChannelHuff); // 1d -> 2d
		int[][][] CbMacroBlock = ZigZag.izigzag(CbChannelHuff);
		int[][][] CrMacroBlock = ZigZag.izigzag(CrChannelHuff);

		double[][][] yAfteriQuatize = Quatization.iQuatizeY(YMacroBlock);
		double[][] yAfteridct = DCTcoding.idct(yAfteriQuatize, picWidth%16==0?picWidth:(1+picWidth/16)*16,picHeight%16==0?picHeight:(1+picHeight/16)*16);

		double[][][] cbAfteriQuatize = Quatization.iQuatizeCbCr(CbMacroBlock);
		double[][] cbAfteridct = DCTcoding.idct(cbAfteriQuatize, (picWidth%16==0?picWidth:(1+picWidth/16)*16)/2,(picHeight%16==0?picHeight:(1+picHeight/16)*16)/2);

		double[][][] crAfteriQuatize = Quatization.iQuatizeCbCr(CrMacroBlock);
		double[][] crAfteridct = DCTcoding.idct(crAfteriQuatize, (picWidth%16==0?picWidth:(1+picWidth/16)*16)/2,(picHeight%16==0?picHeight:(1+picHeight/16)*16)/2);

		Color[][] pic = new Color[picWidth][picHeight];
		YCbCr.YCbCrtoRGB(pic, yAfteridct, cbAfteridct, crAfteridct);
		try {
			BufferedImage bi = new BufferedImage(pic.length, pic[0].length, BufferedImage.TYPE_INT_RGB);
			for(int i=0; i<pic.length; i++)
				for(int j=0; j<pic[0].length; j++) {
					bi.setRGB(i ,j, pic[i][j].getRGB());
				}
			Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = it.next();
			ImageOutputStream oStream;
			oStream = ImageIO.createImageOutputStream(new File("../Results/cartoonJPEG.jpg"));
			writer.setOutput(oStream);
			writer.write(bi);
			bi.flush();
			oStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finish");
	}
	
	
	public static void main(String[] args) {
		encoding();
		decoding();
	}

}
