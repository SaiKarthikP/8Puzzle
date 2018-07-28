import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver {

	static int nodeCounterH1=0;
	static int nodeCounterH2=0;
	static boolean printSteps = false;
	static String solution = "012345678";
	public static void main(String[] args) {
		
		String state = null;
		System.out.println("Initial state configuration\n 1.Random configuration\n 2.Enter the configuration\n 3.Read from file test.txt");
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		if (input==1){
			state = randomState();
			printSteps = true;
			System.out.println("Initial Configuration: " + state + "\nSolution using Heuristic 1: ");
			long startTime = System.nanoTime();
			aStarSearchH1(state);
			long endTime   = System.nanoTime();
			System.out.println("Time taken using Heuristic 1(Nanoseconds): " + (endTime-startTime) +
					" Search Cost: " + nodeCounterH1);
			System.out.println("Initial Configuration: " + state + "\nSolution using Heuristic 2: ");
			startTime = System.nanoTime();
			aStarSearchH2(state);
			endTime   = System.nanoTime();
			System.out.println("Time taken using Heuristic 2(Nanoseconds): " + (endTime-startTime)+
					" Search Cost: " + nodeCounterH2); 
			
		}
		else if (input==2){
			System.out.println("Enter the initial state (ex: 012345678):");
			sc.nextLine();
			state = sc.nextLine();
			printSteps = true;
			System.out.println("Initial Configuration: " + state + "\nSolution using Heuristic 1: ");
			long startTime = System.nanoTime();
			aStarSearchH1(state);
			long endTime   = System.nanoTime();
			System.out.println("Time taken using Heuristic 1(Nanoseconds): " + (endTime-startTime) +
					" Search Cost: " + nodeCounterH1);

			System.out.println("Initial Configuration: " + state + "\nSolution using Heuristic 2: ");
			startTime = System.nanoTime();
			aStarSearchH2(state);
			endTime   = System.nanoTime();
			System.out.println("Time taken using Heuristic 2(Nanoseconds): " + (endTime-startTime)+
					" Search Cost: " + nodeCounterH2); 

			
//			//checking for solvability
//			Boolean solvable=false;
//			String[] numString = state.split("");
//			int[] numbers = new int[9];
//			do{
//				for (int i=0;i<numString.length;i++){
//					String str = numString[i];
//					numbers[i] = Integer.parseInt(str);
//				}	
//				solvable = Solvable(numbers);
//				System.out.println(solvable);
//				if (solvable==false){
//					System.out.println("Make sure the intial state is solvable. Please re-enter:");
//					state = sc.nextLine();
//				}
//			}while (solvable=false);
			
			
			
		}
		else if (input==3){
			try{
				String line;
				int puzzleCounter=0;
				long totalTimeH1 = 0;				
				long totalTimeH2 = 0;
				BufferedReader r = new BufferedReader(new FileReader("test.txt"));
//				h1:
				while ((line = r.readLine())!=null){
					puzzleCounter++;
					long startTime = System.nanoTime();
					aStarSearchH1(line);
					long endTime   = System.nanoTime();
					totalTimeH1+= (endTime-startTime);
				}
//				h2:
				r.close();
				r = new BufferedReader(new FileReader("test.txt"));
				while ((line = r.readLine())!=null){
					long startTime = System.nanoTime();
					aStarSearchH2(line);
					long endTime   = System.nanoTime();
					totalTimeH2+= (endTime-startTime);
				}
				System.out.println("Average search cost for A*(h1): " + nodeCounterH1/puzzleCounter);
				System.out.println("Average search cost for A*(h2): " + nodeCounterH2/puzzleCounter);
				
				System.out.println("Average Time for A*(h1): " + totalTimeH1/puzzleCounter);
				System.out.println("Average Time for A*(h2): " + totalTimeH2/puzzleCounter);
				System.out.println("Cases tested: " + puzzleCounter);
			}
			catch (IOException ioe){
				System.out.println("read error");
			}
		}
		sc.close();
	}
	
	public static boolean aStarSearchH1(String state){
		if (state==null)
			return false;
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(1,new h1Comparator());
		HashSet<String> explored = new HashSet<String>();
		Node initial = new Node(state, null, heuristic1(state), heuristic2(state),0);
		nodeCounterH1++;
		frontier.add(initial);
		Node stateNode;
		while (!frontier.isEmpty()){
			stateNode = frontier.poll();
			explored.add(stateNode.state);
			if (stateNode.state.equals(solution)){
				if (printSteps){
					do{
						System.out.println(stateNode.toString());
						stateNode = stateNode.parent;
						
					}while  (stateNode.parent!=null);					
				}
				System.out.println(stateNode.toString());
				return true;
			}
			
			Node[] children = Children(stateNode);
			nodeCounterH1 += children.length;
			for (Node child:children){
				if (!explored.contains(child.state) && !frontier.contains(child)){
					frontier.add(child);
				}
//				else if in frontier, then check priority and rearrange 
			}
		}	
		System.out.println("no solution");
		return false;
	}

	public static boolean aStarSearchH2(String state){
		if (state==null)
			return false;
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(1,new h2Comparator());
		HashSet<String> explored = new HashSet<String>();
		Node initial = new Node(state, null, heuristic1(state), heuristic2(state),0);
		nodeCounterH2++;
		frontier.add(initial);
		Node stateNode;
		while (!frontier.isEmpty()){
			stateNode = frontier.poll();
			explored.add(stateNode.state);
			if (stateNode.state.equals(solution)){
				if (printSteps){
					do{
						System.out.println(stateNode.toString());
						stateNode = stateNode.parent;
						
					}while  (stateNode.parent!=null);					
				}
				System.out.println(stateNode.toString());
				return true;
			}
			
			
			Node[] children = Children(stateNode);
			nodeCounterH2 += children.length;
			for (Node child:children){
				if (!explored.contains(child.state) && !frontier.contains(child)){
					frontier.add(child);
				}
//				else if in frontier, then check priority and rearrange 
			}
		}	
		System.out.println("no solution");
		return false;
	}
		
//	finds all possible moves from current state
	public static Node[] Children(Node parent){
		int zeroPos;
		
		
		for(zeroPos=0;zeroPos<parent.state.length();zeroPos++){
			
			if(parent.state.charAt(zeroPos)=='0'){
				break;
			}
			
		}
		
		Node[] children;
		switch(zeroPos){
		case 0:
			children = new Node[2];
			children[0] = swap(parent,zeroPos,1);
			children[1] = swap(parent,zeroPos,3);
			break;
		case 1:
			children = new Node[3];
			children[0] = swap(parent,zeroPos,0);
			children[1] = swap(parent,zeroPos,2);
			children[2] = swap(parent,zeroPos,4);
			break;
		case 2:
			children = new Node[2];
			children[0] = swap(parent,zeroPos,1);
			children[1] = swap(parent,zeroPos,5);
			break;
		case 3:
			children = new Node[3];
			children[0] = swap(parent,zeroPos,0);
			children[1] = swap(parent,zeroPos,4);
			children[2] = swap(parent,zeroPos,6);
			break;
		case 4:
			children = new Node[4];
			children[0] = swap(parent,zeroPos,1);
			children[1] = swap(parent,zeroPos,3);
			children[2] = swap(parent,zeroPos,5);
			children[3] = swap(parent,zeroPos,7);
			
			break;
		case 5:
			children = new Node[3];
			children[0] = swap(parent,zeroPos,2);
			children[1] = swap(parent,zeroPos,4);
			children[2] = swap(parent,zeroPos,8);
			
			break;
		case 6:
			children = new Node[2];
			children[0] = swap(parent,zeroPos,3);
			children[1] = swap(parent,zeroPos,7);
			break;
		
		case 7:
			children = new Node[3];
			children[0] = swap(parent,zeroPos,4);
			children[1] = swap(parent,zeroPos,6);
			children[2] = swap(parent,zeroPos,8);
			break;
		case 8:
			children = new Node[2];
			children[0] = swap(parent,zeroPos,5);
			children[1] = swap(parent,zeroPos,7);
			break;
		default:
			children = new Node[0];
			
		}
		
		return children;
		
	}
	public static Node swap(Node parent, int a, int b){
		Node child;
		String childState = parent.state;
		char[] temp = childState.toCharArray();
		char c = temp[a];
		temp[a] = temp[b];
		temp[b] = c;
		childState = String.valueOf(temp);
		child = new Node(childState,parent,heuristic1(childState),heuristic2(childState),parent.g+1);
		return child;
		
	}
	
//	creates a random solvable configuration 
	private static String randomState() {
		String state=null;
		Boolean solvable = false;
		int[] numbers = new int[9];
		Random rn = new Random();
		
		do{
			ArrayList<Integer> list = new ArrayList<Integer> (Arrays.asList(0,1,2,3,4,5,6,7,8));
			for(int i = 0; i < numbers.length; i++){
			    numbers[i] = (int) list.remove (rn.nextInt (list.size()));
			}
			
			solvable = Solvable(numbers);
		}while (solvable == false);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<numbers.length; i++) {
			   sb.append(numbers[i]);
			}
		state = sb.toString();
		return state;
	}

//	checks if configuration is solvable, counts the number of inverted numbers
	
	private static Boolean Solvable(int[] numbers) {
		int counter =0;
		boolean result = false;
		for (int i=0;i<numbers.length-1;i++){
			if (numbers[i]==0) continue;
			for(int j=i+1;j<numbers.length;j++){
				if (numbers[j] !=0 && numbers[i]>numbers[j]){
					counter++;
				}
				
			}
		}
		if (counter%2==0)
			result = true;
		return result;
	}


	public static int heuristic1(String state){
		int score =0;
		for (int i=0; i<state.length();i++){
			if (state.charAt(i)!=solution.charAt(i) && state.charAt(i)!='0'){
				score++;
			}
		}
	return score;	
	}
//	manhattan distance formula
	public static int manhattan(int s, int g){
		int x = Math.abs(s/3-g/3) + Math.abs(s%3 - g%3);
		return x;
	}
	public static int heuristic2(String state){
		
		int score =0;
		for (int i=0; i<state.length();i++){
			if (state.charAt(i)!=solution.charAt(i) && state.charAt(i)!='0'){
				score += manhattan(i,Character.getNumericValue(state.charAt(i))); //check casting
			}
		}
		return score;
	}

	
}
class h1Comparator implements Comparator<Node>{
    // Overriding compare()method of Comparator 
    public int compare(Node n1, Node n2) {
        if (n1.h1+n1.g > n2.h1+n2.g)
        	return 1;
        else if (n1.h1+n1.g < n2.h1+n2.g)
        	return -1;
        return 0;
        }
}
class h2Comparator implements Comparator<Node>{
    // Overriding compare()method of Comparator 
    public int compare(Node n1, Node n2) {
        if (n1.h2+n1.g > n2.h2+n2.g)
        	return 1;
        else if (n1.h2+n1.g < n2.h2+n2.g)
        	return -1;
        return 0;
        }
}
class Node{
	String state;
	Node parent;
	int h1, h2;
	int g;

	Node(String state, Node parent, int h1, int h2, int g){
		this.state = state;
		this.parent = parent;
		this.h1 = h1;
		this.h2 = h2;
		this.g = g;

	}

//print the node data
	public String toString(){
		String parent;
		if (this.parent==null)
			parent = "Root";
		else
			parent = this.parent.state;
		return ("State:" + this.state + " Parent State:" + parent + " H1:" + this.h1 + " H2:" + this.h2 + "  Step Cost:" + this.g); 
		
	}
	
}


