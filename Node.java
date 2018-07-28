
public class Node {
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
