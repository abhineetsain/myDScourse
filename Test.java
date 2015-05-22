import java.io.*;
import java.lang.*;
import java.util.*;
import java.lang.Integer;
public class graph {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		Scanner a = new Scanner(new FileReader("input.txt"));
		
		int numcities = a.nextInt();
		int numflights = a.nextInt();
		System.out.println(numcities);
		System.out.println(numflights);
		
		mylinkedlist[] AL = new mylinkedlist[numflights + 2];
		
		for(int i = 1; i<numflights; i++){
			String F = a.nextLine();
			//System.out.println(F);
			String[] Graphnodes = new String[6];
			
			int l=0;
			int k = 0;
			
			for (int j = 0; j < F.length(); j++) {
				if (F.substring(j, j+1).equals(" ") || (j == F.length()-1)){
					String num = F.substring(k, j);
					//System.out.println(num);
					Graphnodes[l] = num;
					l++;
					k = j+1;
				}
			}
			//System.out.println(Graphnodes[5]);
			graphnode G = new graphnode(Graphnodes[0], Graphnodes [1], Graphnodes[4], Integer.parseInt(Graphnodes[5]), Integer.parseInt(Graphnodes[2]), Integer.parseInt(Graphnodes[3]), 1000000000);
			AL[i].insertafterhead(G);
		}
		
		for(int i = 1; i<AL.length - 1; i++){
			for(int j = 1; j<AL.length - 1; j++){
				if (AL[i].getheadNode().getdata().getfinalplace().equals(AL[j].getheadNode().getdata().getinitialplace())){
					if (AL[i].getheadNode().getdata().getarrivaltime() + 30 < AL[j].getheadNode().getdata().getdeparturetime()){
						AL[i].insertafterhead(AL[j].getheadNode().getdata());
					}
				}
			}
		}
		//dijkstra
		int iterator = a.nextInt();
		for(int i = 0; i<iterator; i++){
			String Q = a.nextLine();
			String[] Querynodes = new String[4];
			
			int m = 0;
			int n = 0;
			
			for (int j = 0; j < Q.length(); j++) {
				if (Q.substring(j, j+1).equals(" ") || (j == Q.length()-1)){
					String num = Q.substring(m, j);
					Querynodes[n] = num;
					n++;
					m = j+1;
				}
			}
			graphnode startnode = new graphnode("start", Querynodes[0], "no flight", 0, 0, Integer.parseInt(Querynodes[2]), 0);
			graphnode endnode = new graphnode(Querynodes[1], "end", "no flight", 0, Integer.parseInt(Querynodes[3]), 0, 0);
			AL[0].getheadNode().setdata(startnode);
			AL[numflights + 1].getheadNode().setdata(endnode);
			
			for(int j = 0; j<AL.length; j++){
				if (AL[0].getheadNode().getdata().getfinalplace().equals(AL[j].getheadNode().getdata().getinitialplace())){
					if (AL[0].getheadNode().getdata().getarrivaltime() < AL[j].getheadNode().getdata().getdeparturetime()){
						AL[0].insertafterhead(AL[j].getheadNode().getdata());
					}
				}
			}
			
			for(int j = 0; j<AL.length; j++){
				if (AL[j].getheadNode().getdata().getfinalplace().equals(AL[numflights + 1].getheadNode().getdata().getinitialplace())){
					if (AL[j].getheadNode().getdata().getarrivaltime() < AL[numflights + 1].getheadNode().getdata().getdeparturetime()){
						AL[j].insertafterhead(AL[numflights + 1].getheadNode().getdata());
					}
				}
			}
			
			mypriorityqueue PQ = new mypriorityqueue();
			for(int k = 0; k<AL.length; k++){
				PQ.insert(AL[k].getheadNode());
			}
			
			while (!(PQ.isEmpty())){
				myllnode u = PQ.getmin();
				myllnode v = u.getnext();
				while (!(v == null)){
					if (v.getdata().getdistance() > u.getdata().getdistance() + v.getdata().getfare()){
						v.getdata().setdistance(u.getdata().getdistance() + v.getdata().getfare());
					}
				}
				PQ.deletemin();
			}
			System.out.println(endnode.getdistance());
			
		}
		
    }
	
	
}


public class graphnode {
	private graphnode G;
	private String ini, fin;
	private String flightnum;
	private int fare;
	private int arrivaltime;
	private int departuretime;
	private int distance;
	
	public graphnode(){
		 G = new graphnode();
	}
	public graphnode(String i, String f, String fnum, int far, int departtime, int arrtime, int dist){
		ini = i;
		fin = f;
		flightnum = fnum;
		fare = far;
		arrivaltime = arrtime;
		departuretime = departtime;
		distance = dist;
	}
	
	public int getdeparturetime(){
		return departuretime;
	}
	public int getarrivaltime(){
		return arrivaltime;
	}
	public int getfare(){
		return fare;
	}
	public String getflightnum(){
		return flightnum;
	}
	public String getinitialplace(){
		return ini;
	}
	public String getfinalplace(){
		return fin;
	}
	public int getdistance(){
		return distance;
	}
	public void setdistance(int d){
		distance = d;
	}

}

public class mylinkedlist {

	protected myllnode head;
	protected myllnode tail;
	public int size;
	
	public mylinkedlist(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty(){
		return (head == null);
	}
	
	public int getsize(){
		return size;
	}
	
	public void insertafterhead(graphnode G){
		myllnode newvertexnode = new myllnode(G, null);
		size++;
		if (head == null){
			head = newvertexnode;
			tail = head;
		}else{
			newvertexnode.setnext(head.getnext());
			head.setnext(newvertexnode);
		}
	}
	
	public myllnode getheadNode(){
		return head;
	}
}

public class mypriorityqueue {
	public static final int CAPACITY = 100000;
	private int N;
	private myllnode S[];
	private int f = 0;
	private int r = 0;
	public mypriorityqueue(){
		this(CAPACITY);
	}
	
	
	public mypriorityqueue(int cap){
		N = cap;
		S = new myllnode[N];
		//leftheap = null;
		//rightheap = null;
	}
	
	public boolean isEmpty(){
		return (f == r);
	}
	
	public int size(){
		return (N-f+r)%N;
		
	}
	
	public myllnode getmin(){
		return S[f];
	}
	
	
	
	public void deletemin(){
		
		S[f] = S[r-1];
		S[r-1] = null;
		int i = f;
		while (i < size()){
			if (S[2*i].getdata().getfare() < S[2*i + 1].getdata().getfare()){
				myllnode a = S[i];
				S[i] = S[2*i];
				S[2*i] = a;
				i = 2*i;
			}else{
				myllnode b = S[i];
				S[i] = S[2*i + 1];
				S[2*i + 1] = b;
				i = 2*i + 1;
			}
		}
	}
	
    public void insert(myllnode H){
    	S[r] = H;
    	while (S[r].getdata().getfare() < S[r/2].getdata().getfare()){
    		myllnode a = S[r];
    		S[r] = S[r/2];
    		S[r/2] = a;
    		r = r/2;
    	}
		r = (r+1) % N;
		
	}
}

public class myllnode{
		private graphnode data;
		myllnode next;
		
		public myllnode(){
			myllnode N = new myllnode();
		}
		
		public myllnode(graphnode G, myllnode N){
			data = G;
			next = N;
		}
		
		public void setnext(myllnode B){
			next = B;
		}
		
		public myllnode getnext(){
			return next;
		}
		
		public void setdata(graphnode G){
			data = G;
		}
		
		public graphnode getdata(){
			return data;
		}
	}
