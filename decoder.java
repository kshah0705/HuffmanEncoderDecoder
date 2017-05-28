//package huffmancode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class decoder {

	// private static final String FILENAME =
	// "//cise//homes//kvshah//Downloads//ADS//sample2//sample_input_large.txt";

	public static void main(String[] args) {

		/* decoder */

		String decodeFile = "decoded.txt";

		createDecoder(args[0], args[1], decodeFile);

		System.out.println("decoder written");

	}

	public static void createDecoder(String testEncode, String cTable,
			String decodeFile) {

		System.out.println("inside decoder");

		StringBuilder decoded = read(testEncode);
		System.out.println("read from encoded data");

		Map<String, String> ct = readMap(cTable);

		System.out.println("readfrom code table");

		TreeNode root = decodeTree(ct);

		// System.out.println("decoded");

		// System.out.println(decoded);

		// print(root);

		/* decoded msg */

		int j = 0;

		TreeNode pointer = root;
		ArrayList<String> msgDecode = new ArrayList<String>();
		while (j < decoded.length()) {

			while (!(pointer.left == null && pointer.right == null)) {

				if (decoded.charAt(j) == '0') {

					pointer = pointer.left;
				}

				else {
					pointer = pointer.right;
				}
				j++;
			}

			msgDecode.add(pointer.msg);
			pointer = root;

		}

		/*
		 * for(String m: msgDecode){
		 * 
		 * System.out.println(m); }
		 */

		writeDecode(msgDecode, decodeFile);

	}

	public static StringBuilder read(String aInputFileName) {

		StringBuilder decodedString = new StringBuilder();

		try {
			InputStream inputstream = new FileInputStream(aInputFileName);

			File file = new File(aInputFileName);

			byte[] data = new byte[(int) file.length()];
			int bytesRead = inputstream.read(data);
			try {
				while (bytesRead != -1) {
					// doSomethingWithData(data, bytesRead);
					// System.out.println("bytes read "+bytesRead);

					bytesRead = inputstream.read(data);

					// System.out.println(data.length);

					// System.out.println("bytes read "+bytesRead);
				}

				// String a = new String(data);

				// System.out.println("a :"+a);

				for (int i = 0; i < data.length; i++) {

					decodedString.append(Integer.toBinaryString(
							data[i] & 255 | 256).substring(1));

				}
				// System.out.println(decodedString);
			} finally {
				inputstream.close();
			}
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}

		return decodedString;

	}

	public static Map<String, String> readMap(String cTable) {

		BufferedReader br = null;
		FileReader fr = null;
		Map<String, String> ct = new HashMap<String, String>();
		String splitSpace[];

		try {
			fr = new FileReader(cTable);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {

				splitSpace = sCurrentLine.split(" ");
				ct.put(splitSpace[0], splitSpace[1]);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
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

		return ct;

	}

	public static TreeNode decodeTree(Map<String, String> ct) {

		TreeNode root = new TreeNode("t", 0);

		for (Map.Entry<String, String> entry : ct.entrySet()) {

			int i = 0;
			TreeNode current = root;

			String code = entry.getValue();

			while (i < code.length()) {

				if (code.charAt(i) == '0') {

					if (current.left == null) {

						TreeNode n = new TreeNode("t", 0);
						current.left = n;
						current = n;

					}

					else {

						current = current.left;
					}

				}

				else {

					if (current.right == null) {

						TreeNode n = new TreeNode("t", 0);
						current.right = n;
						current = n;
					}

					else {

						current = current.right;
					}
				}

				i++;
			}

			current.msg = entry.getKey();
		}

		return root;

	}

	public static void writeDecode(ArrayList<String> msgDecode,
			String decodeFile) {
		FileWriter writer = null;
		PrintWriter printWriter = null;

		try {
			writer = new FileWriter(decodeFile);

			printWriter = new PrintWriter(writer);

			for (String m : msgDecode) {

				printWriter.println(m);
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
