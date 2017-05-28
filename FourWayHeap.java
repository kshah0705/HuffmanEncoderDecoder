//package huffmancode;

import java.util.HashMap;
import java.util.Map;

public class FourWayHeap {

	TreeNode[] a;
	TreeNode min;
	int heapsize=0;

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//BinaryMinHeap b = new BinaryMinHeap();
		
		//int[] freq = {6,7,12,10,15,17,5};
		
		/*int[] a = b.build_tree_using_binary_heap(freq);
		
		for(int i=0;i<a.length;i++){
			
			System.out.println(a[i]);
		}*/
		
		/*Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("a", 2);
		map.put("b", 5);
		map.put("c", 4);
		map.put("d", 7);
		map.put("e", 9);
		//map.put('f', 6);
		
		FourWayHeap b = new FourWayHeap();
		
		TreeNode d = b.build_tree_using_4way_heap(map);
		
		b.print(d);
		
		System.out.println(d[0].frequency);
		System.out.println(d[1].frequency);
		System.out.println(d[2].frequency);
		System.out.println(d[3].frequency);
		System.out.println(d[4].frequency);
		System.out.println(d[5].frequency);*/

	}
	
	public TreeNode build_tree_using_4way_heap(Map<String,Integer> freqMap){
		
		this.a = new TreeNode[freqMap.size()];
		//System.out.println("length 4 way "+a.length);
		this.heapsize = freqMap.size();
		
		int i=0;
		for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
			
			
			a[i] = new TreeNode(entry.getKey(),entry.getValue());
			i++;
		}
		
		for(int j=0;j<a.length;j++){
			
			//System.out.println(a[j].frequency);
		}
		
		four_ary_heap();
		
		while(heapsize>1){
			TreeNode a = extractMin();
			//System.out.println("a "+a.frequency);
			
			TreeNode b = extractMin();
			//System.out.println("b "+b.frequency);
			
			TreeNode c = new TreeNode(a.frequency+b.frequency);
			c.left = a;
			c.right = b;
			insert(c);
		}
		
		
		return a[0];
	}
	
	public void four_ary_heap(){
		
		
		for(int i=(a.length-2)/4;i>=0; i--){
			minHeapify(i);
		}
		
		//return a;
	}
	
	public void minHeapify(int i){
		
		int c1 = child_1(i);
		int c2 = child_2(i);
		int c3 = child_3(i);
		int c4 = child_4(i);
		
		int smallest=0;
		
		if(c1<heapsize && a[c1].frequency<a[i].frequency)
			
			smallest = c1;
		else
			smallest = i;

		if(c2<heapsize && a[c2].frequency<a[smallest].frequency)
			smallest = c2;
		
		if(c3<heapsize && a[c3].frequency<a[smallest].frequency)
			smallest = c3;
		
		if(c4<heapsize && a[c4].frequency<a[smallest].frequency)
			smallest = c4;
		
			
		if(smallest!=i){
			
			swap(i,smallest);
			minHeapify(smallest);
		}
	}
	
	public void swap(int i, int smallest){
		TreeNode temp = a[i];
		a[i]=a[smallest];
		a[smallest] = temp;
		
	}
	
	
	public int parent(int i){
		
		return i-1/4;
		
	}
	
	public int child_1(int i){
		
		return 4*i+1;
	}
	
	public int child_2(int i){
		
		return 4*i+2;
		
	}
	
	public int child_3(int i){
		
		return 4*i+3;
	}

	public int child_4(int i){
		
		return 4*i+4;
	}
	
	public TreeNode extractMin(){
		
		min = a[0];
		a[0] = a[heapsize-1];
		heapsize = heapsize-1;
		minHeapify(0);
		
		return min;
		
	}
	
	public void insert(TreeNode c){
		heapsize = heapsize+1;
		a[heapsize-1] = c;
		
		//System.out.println("inserted value: "+a[heapsize-1].frequency);
        int current = heapsize-1;
        
        while (a[current].frequency < a[parent(current)].frequency)
        {
            swap(current,parent(current));
            current = parent(current);
        }
		
	}
	
	public void print(TreeNode root){
		
		
		if(root!=null){
			
			print(root.left);
			//System.out.println(root.frequency);
			print(root.right);
			
		}
		
	}

	
	
	


}
