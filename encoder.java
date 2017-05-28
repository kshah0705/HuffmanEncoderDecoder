//package huffmancode;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class encoder {

	//private static final String FILENAME = "//cise//homes//kvshah//Downloads//ADS//sample2//sample_input_large.txt";

	public static void main(String[] args) {

		BufferedReader br = null;
		FileReader fr = null;
		Map<String, Integer> smap;
		Map<String, String> ct;

		try {

			fr = new FileReader(args[0]);
			br = new BufferedReader(fr);
			smap = new HashMap<String, Integer>();
			ct = new HashMap<String, String>();

			String sCurrentLine;

			br = new BufferedReader(new FileReader(args[0]));

			while ((sCurrentLine = br.readLine()) != null) {

				if (smap.containsKey(sCurrentLine)) {

					smap.put(sCurrentLine, smap.get(sCurrentLine) + 1);
				}

				else {

					smap.put(sCurrentLine, 1);
				}
				// System.out.println(sCurrentLine);
			}

			/*
			 * binary heap
			 * 
			 * long bStartTime = System.nanoTime();
			 * 
			 * for (int i = 0; i < 10; i++) {
			 * 
			 * BinaryMinHeap b = new BinaryMinHeap(); TreeNode binary_heap =
			 * b.build_tree_using_binary_heap(smap); //b.print(binary_heap[0]);
			 * } long bEndTime = System.nanoTime();
			 * 
			 * long outputb = bEndTime - bStartTime;
			 * 
			 * System.out.println("Elapsed time in milliseconds binary heap: " +
			 * outputb / 10);
			 */

			/* 4 way heap */

			TreeNode four_way_heap = new TreeNode(0);

			long fStartTime = System.nanoTime();

			for (int i = 0; i < 1; i++) {

				FourWayHeap f = new FourWayHeap();
				four_way_heap = f.build_tree_using_4way_heap(smap);
				// f.print(four_way_heap);

			}

			long fEndTime = System.nanoTime();

			long outputf = fEndTime - fStartTime;

			System.out.println("Elapsed time in nanoseconds 4 way heap: "
					+ outputf);

			String str = "";

			/* code table */

			codeTable(four_way_heap, str, ct);

			System.out.println("code table created");

			/* encoder */

			String testEncode = "encoded.bin";

			String cTable = "code_table.txt";

			encodeTree(ct, args[0], testEncode, cTable);

			System.out.println("encoder created");

			/* decoder */

			//String decodeFile = "//cise//homes//kvshah//Downloads//ADS//decode.txt";

			//decoder(cTable, testEncode, decodeFile);

			//System.out.println("decoder written");

			/*
			 * pairing heap //MinPairingHeap p = new MinPairingHeap();
			 * 
			 * long pStartTime = System.nanoTime();
			 * 
			 * 
			 * 
			 * for(int i=0; i<10; i++){
			 * 
			 * // System.out.println("loop "+i); MinPairingHeap p = new
			 * MinPairingHeap(); TreeNode pairHeapTree =
			 * p.build_tree_using_pairing_heap(smap); //p.print(pairHeapTree);
			 * 
			 * }
			 * 
			 * long pEndTime = System.nanoTime();
			 * 
			 * long outputp = pEndTime - pStartTime;
			 * 
			 * System.out.println("Elapsed time in milliseconds pairing heap: "
			 * + outputp / 10);
			 */

			/*
			 * for (Map.Entry<String, Integer> entry : smap.entrySet()) {
			 * 
			 * System.out.println("k " + entry.getKey() + " " + "v " +
			 * entry.getValue()); }
			 */

			/*
			 * for (Map.Entry<String, String> entry : ct.entrySet()) {
			 * 
			 * System.out.println("symbol " + entry.getKey() + " " + "code " +
			 * entry.getValue()); }
			 */

		}

		catch (IOException e) {

			e.printStackTrace();

		}

		finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}

	public static void codeTable(TreeNode root, String str,
			Map<String, String> ct) {

		if (root == null) {

			return;
		}

		if (root.left == null && root.right == null) {

			ct.put(root.msg, str);
			return;

		}

		codeTable(root.left, str + "0", ct);
		codeTable(root.right, str + "1", ct);

		return;
	}

	public static void encodeTree(Map<String, String> ct, String fileEncode,
			String testEncode, String cTable) {

		// String fileEncode =
		// "F:\\GRE\\University\\UFL\\Courses\\Semester2\\ADS\\Project\\Sample1\\sample_input_small.txt";
		// String testEncode =
		// "F:\\GRE\\University\\UFL\\Courses\\Semester2\\ADS\\Project\\testEncode.bin";
		// String cTable =
		// "F:\\GRE\\University\\UFL\\Courses\\Semester2\\ADS\\Project\\code_table.txt";

		BufferedReader br = null;
		FileReader fr = null;

		StringBuilder str = new StringBuilder();

		try {
			fr = new FileReader(fileEncode);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileEncode));

			while ((sCurrentLine = br.readLine()) != null) {

				str.append(ct.get(sCurrentLine));

				// System.out.println(sCurrentLine);

				// System.out.println("making strings");
			}

			// System.out.println(str);

			byte b[] = new byte[str.length() / 8];

			int i = 0;
			int j = 0;

			for (i = 0; i < str.length(); i++) {

				if (i < 8) {

					if (str.charAt(i) == '1') {

						b[j] = (byte) (b[j] | (1 << 7 - i));
					}
				}

				else {

					if (i % 8 == 0) {

						j++;
					}

					if (str.charAt(i) == '1') {

						b[j] = (byte) (b[j] | (1 << 7 - i % 8));
					}

				}

			}

			// byte[] b = str.getBytes();

			write(b, testEncode);
			writeMap(ct, cTable);

		} catch (IOException e) {

			e.printStackTrace();

		}

		finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static void write(byte[] aInput, String aOutputFileName) {

		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(
						aOutputFileName));
				output.write(aInput);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	public static void writeMap(Map<String, String> ct, String cTable) {

		FileWriter writer = null;
		PrintWriter printWriter = null;

		try {
			writer = new FileWriter(cTable);

			printWriter = new PrintWriter(writer);

			for (Map.Entry<String, String> entry : ct.entrySet()) {

				// System.out.println("value"+entry.getValue());
				printWriter.println(entry.getKey() + " " + entry.getValue());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			printWriter.close();
		}

	}

	
	public static void print(TreeNode root) {

		if (root != null) {

			print(root.left);
			System.out.println(root.msg);
			print(root.right);

		}

	}
}
