//package huffmancode;

public class TreeNode {

	String msg;
	int frequency;
	TreeNode left;
	TreeNode right;
	
	TreeNode(String msg, int frequency){
		
		this.msg=msg;
		
		this.frequency = frequency;
	}
	
	TreeNode(int frequency){
		
		this.frequency = frequency;
	}
	

}
