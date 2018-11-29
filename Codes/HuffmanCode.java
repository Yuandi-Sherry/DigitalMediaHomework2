import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javafx.util.Pair;

public class HuffmanCode {
	private String [] DCColorTable = { "00", "01", "10", "110", "1110", "11110", "111110",
		"1111110", "11111110", "111111110", "1111111110", "11111111110"};
	private String [] DCLumTable = { "00", "010", "011", "100", "101", "110", "1110", 
		"11110", "111110", "1111110", "11111110", "111111110"};
	private String [] ACTableKey = {
		"0/0","0/1","0/2","0/3","0/4","0/5","0/6","0/7","0/8","0/9","0/A",
		"1/1","1/2","1/3","1/4","1/5","1/6","1/7","1/8","1/9","1/A",
		"2/1","2/2","2/3","2/4","2/5","2/6","2/7","2/8","2/9","2/A",
		"3/1","3/2","3/3","3/4","3/5","3/6","3/7","3/8","3/9","3/A",
		"4/1","4/2","4/3","4/4","4/5","4/6","4/7","4/8","4/9","4/A",
		"5/1","5/2","5/3","5/4","5/5","5/6","5/7","5/8","5/9","5/A",
		"6/1","6/2","6/3","6/4","6/5","6/6","6/7","6/8","6/9","6/A",
		"7/1","7/2","7/3","7/4","7/5","7/6","7/7","7/8","7/9","7/A",
		"8/1","8/2","8/3","8/4","8/5","8/6","8/7","8/8","8/9","8/A",
		"9/1","9/2","9/3","9/4","9/5","9/6","9/7","9/8","9/9","9/A",
		"A/1","A/2","A/3","A/4","A/5","A/6","A/7","A/8","A/9","A/A",
		"B/1","B/2","B/3","B/4","B/5","B/6","B/7","B/8","B/9","B/A",
		"C/1","C/2","C/3","C/4","C/5","C/6","C/7","C/8","C/9","C/A",
		"D/1","D/2","D/3","D/4","D/5","D/6","D/7","D/8","D/9","D/A",
		"E/1","E/2","E/3","E/4","E/5","E/6","E/7","E/8","E/9","E/A",
		"F/0","F/1","F/2","F/3","F/4","F/5","F/6","F/7","F/8","F/9","F/A"};
	private String [] ACLumTableValue = {
		"1010", "00", "01", "100", "1011", "11010", "1111000", "11111000", "1111110110", "1111111110000010", "1111111110000011", "1100", "11011", "1111001", "111110110", "11111110110", "1111111110000100", "1111111110000101", "1111111110000110", "1111111110000111", "1111111110001000", "11100", "11111001", "1111110111", "111111110100", "1111111110001001", "1111111110001010", "1111111110001011", "1111111110001100", "1111111110001101", "1111111110001110", "111010", "111110111", "111111110101", "1111111110001111", "1111111110010000", "1111111110010001", "1111111110010010", "1111111110010011", "1111111110010100", "1111111110010101", "111011", "1111111000", "1111111110010110", "1111111110010111", "1111111110011000", "1111111110011001", "1111111110011010", "1111111110011011", "1111111110011100", "1111111110011101", "1111010", "11111110111", "1111111110011110", "1111111110011111", "1111111110100000", "1111111110100001", "1111111110100010", "1111111110100011", "1111111110100100", "1111111110100101", "1111011", "111111110110", "1111111110100110", "1111111110100111", "1111111110101000", "1111111110101001", "1111111110101010", "1111111110101011", "1111111110101100", "1111111110101101", "11111010", "111111110111", "1111111110101110", "1111111110101111", "1111111110110000", "1111111110110001", "1111111110110010", "1111111110110011", "1111111110110100", "1111111110110101", "111111000", "111111111000000", "1111111110110110", "1111111110110111", "1111111110111000", "1111111110111001", "1111111110111010", "1111111110111011", "1111111110111100", "1111111110111101", "111111001", "1111111110111110", "1111111110111111", "1111111111000000", "1111111111000001", "1111111111000010", "1111111111000011", "1111111111000100", "1111111111000101", "1111111111000110", "111111010", "1111111111000111", "1111111111001000", "1111111111001001", "1111111111001010", "1111111111001011", "1111111111001100", "1111111111001101", "1111111111001110", "1111111111001111", "1111111001", "1111111111010000", "1111111111010001", "1111111111010010", "1111111111010011", "1111111111010100", "1111111111010101", "1111111111010110", "1111111111010111", "1111111111011000", "1111111010", "1111111111011001", "1111111111011010", "1111111111011011", "1111111111011100", "1111111111011101", "1111111111011110", "1111111111011111", "1111111111100000", "1111111111100001", "11111111000", "1111111111100010", "1111111111100011", "1111111111100100", "1111111111100101", "1111111111100110", "1111111111100111", "1111111111101000", "1111111111101001", "1111111111101010", "1111111111101011", "1111111111101100", "1111111111101101", "1111111111101110", "1111111111101111", "1111111111110000", "1111111111110001", "1111111111110010", "1111111111110011", "1111111111110100", "11111111001", "1111111111110101", "1111111111110110", "1111111111110111", "1111111111111000", "1111111111111001", "1111111111111010", "1111111111111011", "1111111111111100", "1111111111111101", "1111111111111110"
	};

	private String [] ACColorTableValue = {
		"00", "01", "100", "1010", "11000", "11001", "111000", "1111000", "111110100", "1111110110", "111111110100", "1011", "111001", "11110110", "111110101", "11111110110", "111111110101", "1111111110001000", "1111111110001001", "1111111110001010", "1111111110001011", "11010", "11110111", "1111110111", "111111110110", "111111111000010", "1111111110001100", "1111111110001101", "1111111110001110", "1111111110001111", "1111111110010000", "11011", "11111000", "1111111000", "111111110111", "1111111110010001", "1111111110010010", "1111111110010011", "1111111110010100", "1111111110010101", "1111111110010110", "111010", "111110110", "1111111110010111", "1111111110011000", "1111111110011001", "1111111110011010", "1111111110011011", "1111111110011100", "1111111110011101", "1111111110011110", "111011", "1111111001", "1111111110011111", "1111111110100000", "1111111110100001", "1111111110100010", "1111111110100011", "1111111110100100", "1111111110100101", "1111111110100110", "1111001", "11111110111", "1111111110100111", "1111111110101000", "1111111110101001", "1111111110101010", "1111111110101011", "1111111110101100", "1111111110101101", "1111111110101110", "1111010", "11111111000", "1111111110101111", "1111111110110000", "1111111110110001", "1111111110110010", "1111111110110011", "1111111110110100", "1111111110110101", "1111111110110110", "11111001", "1111111110110111", "1111111110111000", "1111111110111001", "1111111110111010", "1111111110111011", "1111111110111100", "1111111110111101", "1111111110111110", "1111111110111111", "111110111", "1111111111000000", "1111111111000001", "1111111111000010", "1111111111000011", "1111111111000100", "1111111111000101", "1111111111000110", "1111111111000111", "1111111111001000", "111111000", "1111111111001001", "1111111111001010", "1111111111001011", "1111111111001100", "1111111111001101", "1111111111001110", "1111111111001111", "1111111111010000", "1111111111010001", "111111001", "1111111111010010", "1111111111010011", "1111111111010100", "1111111111010101", "1111111111010110", "1111111111010111", "1111111111011000", "1111111111011001", "1111111111011010", "111111010", "1111111111011011", "1111111111011100", "1111111111011101", "1111111111011110", "1111111111011111", "1111111111100000", "1111111111100001", "1111111111100010", "1111111111100011", "11111111001", "1111111111100100", "1111111111100101", "1111111111100110", "1111111111100111", "1111111111101000", "1111111111101001", "1111111111101010", "1111111111101011", "1111111111101100", "11111111100000", "1111111111101101", "1111111111101110", "1111111111101111", "1111111111110000", "1111111111110001", "1111111111110010", "1111111111110011", "1111111111110100", "1111111111110101", "1111111010", "111111111000011", "1111111111110110", "1111111111110111", "1111111111111000", "1111111111111001", "1111111111111010", "1111111111111011", "1111111111111100", "1111111111111101", "1111111111111110"
	};	

	private Vector<Pair<Integer, Integer>> runSize;
	public HuffmanCode() {
		runSize = new Vector<Pair<Integer, Integer>>(); // first -> runlength, second -> size
		for(int i = 0; i < ACTableKey.length; i++) {
			runSize.addElement(handleRS( ACTableKey[i]));
		}
	}

	public String LumEncoding(int[] array) {
		Vector<int[]> data = RLEencoding(array);
		data = entropyCoding(data);
		String result = "";
		for(int i=0; i<data.size(); i++) {
			int x = data.elementAt(i)[0],
					y = data.elementAt(i)[1],
					z = data.elementAt(i)[2];
			if(i==0) {//DC分量
				result += DCLumTable[x];
				result += HuffmanTable.getCode(y);
			} else {//AC分量
				result += getCodeWord(x, y, ACLumTableValue);
				result += HuffmanTable.getCode(z);
			}
		}
		return result;
	}

	public String ColorEncoding(int[] array) {
		Vector<int[]> data = RLEencoding(array);
		data = entropyCoding(data);
		String result = "";
		for(int i=0; i<data.size(); i++) {
			int x = data.elementAt(i)[0],
					y = data.elementAt(i)[1],
					z = data.elementAt(i)[2];
			if(i==0) {//DC分量
				result += DCColorTable[x];
				result += HuffmanTable.getCode(y);
			} else {//AC分量
				result += getCodeWord(x, y, ACColorTableValue);
				result += HuffmanTable.getCode(z);
			}
		}
		return result;
	}

	private Vector<int[]> RLEencoding(int[] array) {
		Vector<int[]> data = new Vector<int[]>(); // the vector of RL data pairs
		int zeroCnt = 0;
		for(int i=0; i<64; i++) {
			int[] current = new int[3];
			if(i==0) {
				current[0] = array[0];
				data.addElement(current);
			}
			else {
				boolean flag = true;
				for(int j=i; j<64; j++) {
					if(array[j] != 0)
						flag = false;
				}
				if(flag) {
					// judge if the following pixels are all 0
					current[0] = current[1] = 0;
					data.addElement(current);
					break;
				}
				// judge 16 0 for coding (15,0)
				if(zeroCnt == 16) {
					current[0] = 15;
					current[1] = 0;
					zeroCnt = 0;
					data.addElement(current);
				}
				if(array[i]==0)
					zeroCnt++;
				else {
					current[0] = zeroCnt; // length
					current[1] = array[i]; // next
					zeroCnt = 0;
					data.addElement(current);
				}
			}
		}
		return data;
	}

	private Vector<int[]> entropyCoding(Vector<int[]> data) {
		for(int i=0; i<data.size(); i++) {
			int[] current = data.elementAt(i); // get current number pair
			if(i==0) {
				int DC = current[0]; // run length
				data.elementAt(0)[0] = HuffmanTable.getIndex(DC); // size
				data.elementAt(0)[1] = DC; // amp
			} else if(data.elementAt(i)[0] !=0 || data.elementAt(i)[1]!=0){ // runlength 
				data.elementAt(i)[2] = data.elementAt(i)[1]; // amp
				data.elementAt(i)[1] = HuffmanTable.getIndex(data.elementAt(i)[2]); // runlength
			}
		}
		return data;
	}
	
	public String ColorDecoding(String data, int[] line) {
//		int[] line = new int[64];
		int DCHeadLen = -1;
		for(int rIndex=2; rIndex<11; rIndex++) {
			DCHeadLen = getKeyFromValue(DCColorTable, data.substring(0, rIndex));
			if(DCHeadLen>=0) {
				data = data.substring(rIndex);//去掉前面的头
				break;
			}
		}
		line[0] = HuffmanTable.getNum(data.substring(0, DCHeadLen));
		data = data.substring(DCHeadLen);
		int lineLen = 1;
		while(lineLen < 64) {
			Pair<Integer, Integer> curP = new Pair<Integer, Integer>(-1, -1);
			for(int rIndex = 2; rIndex <=16; rIndex++) {
				curP = getRunSize(data.substring(0, rIndex), ACColorTableValue);
				if(!(curP.getKey() == -1 && curP.getValue() == -1)) {
					data = data.substring(rIndex); // finish dealing with run and size
					break;
				}
			}
			int x = curP.getKey(); // runlength
			int y = HuffmanTable.getNum(data.substring(0, curP.getValue())); // get next y bit number for amp, y is amp 
			data = data.substring(curP.getValue()); // finish dealing with amp
			if(x==y && y==0) { // runlength == amp || amp == 0
				while(lineLen < 64) // compensate 0 
					line[lineLen++] = 0;
				break;
			}
			for(int i=0; i<x; i++)
				line[lineLen++] = 0; // compensate 0 
			line[lineLen++] = y; //next is the amp
		}
		return data;
	}
	
	public String LumDecoding(String data, int [] line) { // line is empty
//		int[] line = new int[64];
		int DCHeadLen = -1;
		for(int rIndex=2; rIndex<9; rIndex++) {
			DCHeadLen = getKeyFromValue(DCLumTable, data.substring(0, rIndex)); // value -> key
			if(DCHeadLen>=0) { // not return -1, 
				data = data.substring(rIndex);//去掉前面的头
				break;
			}
		}
		if(DCHeadLen == -1) {
			System.out.println(data.substring(0, 8));
		}
		line[0] = HuffmanTable.getNum(data.substring(0, DCHeadLen)); // decode huff -> num e.g. 011 -> 3
		data = data.substring(DCHeadLen); // 去掉dct size
		int lineLen = 1;
		while(lineLen < 64) {
			Pair<Integer, Integer> curP = new Pair<Integer, Integer>(-1, -1); // 
			for(int rIndex = 2; rIndex <=16; rIndex++) {
				curP = getRunSize(data.substring(0, rIndex), ACLumTableValue); // get runlength and size : value -> key
				if(!(curP.getKey() == -1 && curP.getValue() == -1)) { // 
					data = data.substring(rIndex);
					break;
				}
			}
			if(curP.getKey() == -1 && curP.getValue() == -1) {
				System.out.println("[INFO] ERROR: LumDecoding 2!");
			}
			int x = curP.getKey();
			int y = HuffmanTable.getNum(data.substring(0, curP.getValue()));
			data = data.substring(curP.getValue());
			if(x==y && y==0) {
				while(lineLen < 64)
					line[lineLen++] = 0;
				break;
			}
			for(int i=0; i<x; i++)
				line[lineLen++] = 0;
			line[lineLen++] = y;
		}
		return data;
	}
	private int getKeyFromValue(String[] array, String value) {
		for(int i = 0; i < array.length; i++) {
			if(value.equals(array[i]))
				return i;
		}
		return -1;
	}

	private Pair<Integer, Integer> handleRS(String s) {
		int x, y;
		String[] ss = s.split("/");
		if(ss[0].charAt(0) >= 'A')
			x = ss[0].charAt(0)-'A' +10;
		else 
			x = ss[0].charAt(0)-'0';
		if(ss[1].charAt(0) >= 'A')
			y = ss[1].charAt(0)-'A' +10;
		else 
			y = ss[1].charAt(0)-'0';
		return (new Pair<>(x, y));
	}
	

	public String getCodeWord(int x, int y, String[] array) {
		for(int i=0; i<runSize.size(); i++) {
			if(runSize.elementAt(i).getKey()==x && runSize.elementAt(i).getValue()==y)
				return array[i];
		}
		return "";
	}

	
	public Pair<Integer, Integer> getRunSize(String codeWord,  String[] array) {
		for(int i=0; i<array.length; i++) {
			if(array[i].equals(codeWord))
				return runSize.elementAt(i);
		}
		return (new Pair<Integer, Integer>(-1,-1));
	}
}


class HuffmanTable{

	/**
	 */
	public static int getIndex(int num) {
		if(num == 0) 
			return 0;
		if(num < 0) 
			num *= -1; // judge pos or neg
		double result = Math.log(num)/Math.log(2); //  size of bits
		for(int i=(int) (result-5); i <= result+5; i++) {
			if(Math.pow(2, i-1) <= num && num <=Math.pow(2, i)-1) // low bound:2^i-1 and up bound 2^i-1
				return i;
		}
		return 0;
	}

	/**
	 */
	public static String getCode(int num) {
		if(num ==0)
			return "";
		int index = getIndex(num);
		String result;
		if(num < 0){
			// transfor Binar
			result = Integer.toBinaryString((-num)^(int)(Math.pow(2, index)-1));
		} else {
			result = Integer.toBinaryString(num);
		}
		int p = index-result.length();
		for(int i=0; i<p; i++) {
			result = "0" + result;
		}
		return result;
	}
	
	public static int getNum(String code) {
		if(code.equals(""))
			return 0;
		int len = code.length();
		int num;
		if(code.charAt(0)=='1') {
			num = Integer.parseInt(code, 2);
		} else {
			String negCode = "";
			for(int i=0; i<len; i++) {
				negCode += code.charAt(i)=='0'?"1":"0";
			}
			num = -Integer.parseInt(negCode, 2);
		}
		return num;
	}
}
