import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Vector;

public class ZigZag {
	static int[] ZigZagTable = {  
		     0, 1, 5, 6,14,15,27,28,
			 2, 4, 7,13,16,26,29,42,
			 3, 8,12,17,25,30,41,43,
			 9,11,18,24,31,40,44,53,
			10,19,23,32,39,45,52,54,
			20,22,33,38,46,51,55,60,
			21,34,37,47,50,56,59,61,
			35,36,48,49,57,58,62,63  };
	
	public static int[][] zigzag(int[][][] afterQua) {
		/**
	     * zigzag 2d->1d, dc diff 
		 */
		int[][] afterZigZag = new int[afterQua.length][8 * 8];
		for(int k = 0; k < afterQua.length; k++) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					afterZigZag[k][ZigZagTable[i*8+j]] = afterQua[k][i][j];
				}
			}
			if(k == 0) {
				afterZigZag[0][0] = afterQua[0][0][0];
			} else {
				afterZigZag[k][0] = afterQua[k][0][0] - afterQua[k - 1][0][0];
			}
		}
		return afterZigZag;
	}
	
	public static int[][][] izigzag(Vector<int[]> afterDeco) {
		/**
		 * reverse zigzag and dc difference
		 */
		int[][][] afteriZigZag = new int[afterDeco.size()][8][8];
		for (int k = 0; k < afteriZigZag.length; k++) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					afteriZigZag[k][i][j] = afterDeco.elementAt(k)[ZigZagTable[i*8+j]];
				}
			}
			if (k == 0)
				afteriZigZag[0][0][0] = afterDeco.elementAt(0)[0];
			else
				afteriZigZag[k][0][0] =afteriZigZag[k - 1][0][0]  + afterDeco.elementAt(k)[0];
		}
		return afteriZigZag;
	}
	
}
