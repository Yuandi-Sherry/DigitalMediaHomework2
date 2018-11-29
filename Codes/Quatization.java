
public class Quatization {
	/**
	 */
	
	private static int[][] YchannelQua = { { 16, 11, 10, 16, 24, 40, 51, 61 }, { 12, 12, 14, 19, 26, 58, 60, 55 },
			{ 14, 13, 16, 24, 40, 57, 69, 56 }, { 14, 17, 22, 29, 51, 87, 80, 62 },
			{ 18, 22, 37, 56, 68, 109, 103, 77 }, { 24, 35, 55, 64, 81, 104, 113, 92 },
			{ 49, 64, 78, 87, 103, 121, 120, 101 }, { 72, 92, 95, 98, 112, 100, 103, 99 } },
			ColorChannelQua = { { 17, 18, 24, 47, 99, 99, 99, 99 }, { 18, 21, 26, 66, 99, 99, 99, 99 },
					{ 24, 26, 56, 99, 99, 99, 99, 99 }, { 47, 66, 99, 99, 99, 99, 99, 99 },
					{ 99, 99, 99, 99, 99, 99, 99, 99 }, { 99, 99, 99, 99, 99, 99, 99, 99 },
					{ 99, 99, 99, 99, 99, 99, 99, 99 }, { 99, 99, 99, 99, 99, 99, 99, 99 }, };

	public static int[][][] quatizeY(double[][][] afterDCT){
		int[][][] afterQua = new int[afterDCT.length][8][8];
		for(int k=0; k<afterQua.length; k++) {
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++)
					afterQua[k][i][j] = (int) Math.round(afterDCT[k][i][j]/YchannelQua[i][j]); 
		}
		return afterQua.clone();
	}
	
	public static int[][][] quatizeCbCr(double[][][] afterDCT){
		int[][][] afterQua = new int[afterDCT.length][8][8];
		for(int k=0; k<afterQua.length; k++) {
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++)
					afterQua[k][i][j] = (int) Math.round(afterDCT[k][i][j]/ColorChannelQua[i][j]);
		}
		return afterQua.clone();
	}
	
	public static double[][][] iQuatizeY(int[][][] afteriZigZag){
		double[][][] afteriQua = new double[afteriZigZag.length][afteriZigZag[0].length][afteriZigZag[0][0].length];
		for(int k=0; k<afteriZigZag.length; k++) {
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++)
					afteriQua[k][i][j] = afteriZigZag[k][i][j]*YchannelQua[i][j];
		}
		return afteriQua;
	}
	
	public static double[][][] iQuatizeCbCr(int[][][] afteriZigZag){
		double[][][] afteriQua = new double[afteriZigZag.length][afteriZigZag[0].length][afteriZigZag[0][0].length];
		for(int k=0; k<afteriZigZag.length; k++) {
			for(int i=0; i<8; i++)
				for(int j=0; j<8; j++)
					afteriQua[k][i][j] = afteriZigZag[k][i][j]*ColorChannelQua[i][j];
		}
		return afteriQua;
	}
}
