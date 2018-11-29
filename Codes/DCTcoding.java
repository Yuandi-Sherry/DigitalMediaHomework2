import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Vector;

public class DCTcoding {
	static int[] ZigZag = {  
		     0, 1, 5, 6,14,15,27,28,
			 2, 4, 7,13,16,26,29,42,
			 3, 8,12,17,25,30,41,43,
			 9,11,18,24,31,40,44,53,
			10,19,23,32,39,45,52,54,
			20,22,33,38,46,51,55,60,
			21,34,37,47,50,56,59,61,
			35,36,48,49,57,58,62,63  };

	static double [] dctM = {
		 0.35355,  0.35355,  0.35355,  0.35355,  0.35355,  0.35355,  0.35355,  0.35355,
         0.49039,  0.41573,  0.27779,  0.09755, -0.09755, -0.27779, -0.41573, -0.49039,
         0.46194,  0.19134, -0.19134, -0.46194, -0.46194, -0.19134,  0.19134,  0.46194,
         0.41573, -0.09755, -0.49039, -0.27779,  0.27779,  0.49039,  0.09755, -0.41573,
         0.35355, -0.35355, -0.35355,  0.35355,  0.35355, -0.35355, -0.35355,  0.35355,
         0.27779, -0.49039,  0.09755,  0.41573, -0.41573, -0.09755,  0.49039, -0.27779,
         0.19134, -0.46194,  0.46194, -0.19134, -0.19134,  0.46194, -0.46194,  0.19134,
         0.09755, -0.27779,  0.41573, -0.49039,  0.49039, -0.41573,  0.27779, -0.09755
	};

	static double [] dctTransM = {
		 0.3535,   0.4904,   0.4619,   0.4157,   0.3535,    0.2778,   0.1913,   0.0975,
	     0.3535,   0.4157,   0.1913,  -0.0975,  -0.3535,   -0.4904,  -0.4619,  -0.2778,
         0.3535,   0.2778,  -0.1913,  -0.4904,  -0.3535,    0.0975,   0.4619,   0.4157,
         0.3535,   0.0975,  -0.4619,  -0.2778,   0.3535,    0.4157,  -0.1913,  -0.4904,
         0.3535,  -0.0975,  -0.4619,   0.2778,   0.3535,   -0.4157,  -0.1913,   0.4904,
         0.3535,  -0.2778,  -0.1913,   0.4904,  -0.3535,   -0.0975,   0.4619,  -0.4157,
         0.3535,  -0.4157,   0.1913,   0.0975,  -0.3535,    0.4904,  -0.4619,   0.2778,
         0.3535,  -0.4904,   0.4619,  -0.4157,   0.3535,   -0.2778,   0.1913,  -0.0975
	};

	public static double [][][] dct(double[][] beforeDCT){
		// the first dimention of win_temp is width*height of beforeDCT, the next two dimension is the coordinate of macroblock
		double [][][] afterDCT = new double[beforeDCT.length/8*beforeDCT[1].length/8][8][8];
		for(int i=0; i<beforeDCT.length; i+=8) { // the relative two-dimension coordanate of each macroblock
			for(int j=0; j<beforeDCT[0].length; j+=8) { // // the relative two-dimension coordanate of each macroblock
				int index = i*beforeDCT[0].length/64 + j/8; // the relative one-dimension coordanate of each macroblock
				for(int k=0; k<8; k++) {// k^th row of all macroblock
					for(int l=0; l<8; l++) {// l^th column of all macroblock
						afterDCT[index][k][l] = beforeDCT[i + k][j + l];
					}
				}
			}
		}
		// traverse each pixel in each block
		// DCT
		for(int i=0; i<afterDCT.length; i++) {
			double[][] F = new double[8][8];
			double[][] temp = new double[8][8];
			for(int p = 0; p < 8; p++) {
				for(int q = 0; q < 8; q++) {
					temp[p][q] = 0;
					for(int k = 0; k < 8; k++) {
						temp[p][q] += dctM[p*8+k] * afterDCT[i][k][q];
					}
				}
			}

			for(int p = 0; p < 8; p++) {
				for(int q = 0; q < 8; q++) {
					F[p][q] = 0;
					for(int k = 0; k < 8; k++) {
						F[p][q] += temp[p][k] * dctTransM[k*8+q];
					}
				}
			}
			afterDCT[i] = F.clone();
				
		}
		// after DCT
		return afterDCT.clone(); 
	}

	public static double [][] idct(double[][][] afteriQua,  int padWidth, int padHeight){
		for(int i=0; i<afteriQua.length; i++) {
			double[][] F = new double[8][8];
			double[][] temp = new double[8][8];
			for(int p = 0; p < 8; p++) {
				for(int q = 0; q < 8; q++) {
					temp[p][q] = 0;
					for(int k = 0; k < 8; k++) {
						temp[p][q] += dctTransM[p*8+k] * afteriQua[i][k][q];
					}
				}
			}

			for(int p = 0; p < 8; p++) {
				for(int q = 0; q < 8; q++) {
					F[p][q] = 0;
					for(int k = 0; k < 8; k++) {
						F[p][q] += temp[p][k] * dctM[k*8+q];
					}
				}
			}
			afteriQua[i] = F.clone();
		}
		double[][] imgData = new double[padWidth][padHeight];
		System.out.print(padWidth);
		System.out.print(" ");
		System.out.println(padHeight);
		for(int i=0; i<imgData.length; i+=8) {
			for(int j=0; j<imgData[0].length; j+=8) {
				int index = i*imgData[0].length/64 + j/8;
				for(int k=0; k<8; k++)
					for(int l=0; l<8; l++) {
						imgData[i + k][j + l] = afteriQua[index][k][l];
					}
			}
		}
		return imgData.clone();
	}	
}
