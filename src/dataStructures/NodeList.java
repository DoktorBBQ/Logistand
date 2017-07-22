package dataStructures;
import java.util.ArrayList;
import dataStructures.Util;

public class NodeList extends ArrayList<Node>{


	//Only returns something if it has the formal name of the thing requested
	public Node get(String element) {
		for (int i=0; i<this.size(); i++) {
			if (this.get(i).getFormalName().equals(element)) {
				return this.get(i);
			}
		} 
		return null;
	}

	public boolean contains(String name) {
		Node test= this.get(name);
		if (test==null) return false;
		else return true;
	}
	public String getStr() {
		return getStr(0,0);
	}
	public String getStr(int indentation, int depth) {
		String returnString=Util.blanks(indentation)+"NodeList Unfolding: ";
		for(int i=0; i<this.size(); i++) {
			returnString+="\n"+this.get(i).getStr(indentation+1,depth);
		}
		return returnString;
	}
}
