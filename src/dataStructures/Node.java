package dataStructures;
import dataStructures.Util;
public class Node {
	private String description = null;
	private String formalName= null;
	private String workName = null;
	private Node[] inputElements;
	private Node[] outputElements;
	private NodeList statements = new NodeList();
	public static NodeList namedNodes= new NodeList();
	
	public Node(String name){
		this(name,null,null);
	}
	public Node(String name, Node [] inputE){
		this(name,inputE,null);
	}
	public Node(String workName, Node[] inputE, Node[] outputE ) {
		this.workName= workName;
		if (inputE !=null) {
			this.inputElements= inputE.clone();
		} else {
			 inputElements= null;
		}	
		if (outputE !=null) {
			outputElements= outputE.clone();
		} else {
			 outputElements= null;
		}	
	}	
	
	public void addStatement(Node statement) {
		statements.add(statement);
	}
	
	
	public void setFormalName(String newName) {
		if (namedNodes.contains(newName)) {
			System.out.println(newName+" Already registered as formal name!");
			return;
		}
		formalName=newName;
		if (!namedNodes.contains(this)) {
			namedNodes.add(this);
		}
	}

	public void formalizeName() {
		setFormalName(workName);
	}
	
	public NodeList getStatements() {
		return this.statements;
	}
	
	public Node[] getInputElements() {
		return inputElements;
	}
	
	public Node[] getOutputElements() {
		return outputElements;
	}

	//Basic equals function. 
	//Same formal name -> equality (formal names are unique)
	//Same work-name and inputElements -> equality
	//Will prolly need to add a more advanced version using this one to check
	//    that all the statements of the two Nodes are equal as well.
	public boolean equals(Node other) {
		if (this.formalName!=null || other.getFormalName()!=null) {
			if (this.formalName==null || other.getFormalName()==null) {
				return false;
			}else if(this.formalName.equals(other.getFormalName())){
				return true;
			}else {
				return false;
			} 

		}else if( this.workName!=null || other.getWorkName()!= null) {
			if (! this.workName.equals(other.getWorkName())) {
				return false;
			}
		} else if((this.inputElements!=null && other.getInputElements() == null)||
				(this.inputElements==null && other.getInputElements() != null)) {
			return false;
		} else if (this.inputElements == null) {
			return true;
		} else if( this.inputElements.length!=other.getInputElements().length) {
			return false;
		} else {
			for(int i=0; i<this.inputElements.length; i++) {
				if (!this.inputElements[i].equals(other.getInputElements()[i])) {
					return false;
				}
			}
		}
		return true;
	}

	public String getFormalName() {
		return this.formalName;
	}
	
	public String getWorkName() {
		return this.workName;
	}


	public String getStr() {
		return getStr(0,1);
	}
	// Later maybe write something that tracks statements and
	//ignores the names of them-> giving comprehensible prose to read
	public String getStr(int indentation, int statementDepth
			) {
		String[] startingBrackets = { "(", "[", "{", "<", "|"};
		String[] endingBrackets =   { ")", "]", "}", ">", "|"};
		String returnString = Util.blanks(indentation);
		returnString +=
				this.workName;
		if (inputElements != null) {
			returnString+=startingBrackets[indentation%5];
			for (int i = 0; i<inputElements.length; i++) {
					returnString+="\n";
					returnString+=inputElements[i].getStr(
							indentation+1,0);		
			}
			returnString+="\n"+Util.blanks(indentation);
			returnString+=endingBrackets[indentation%5];	
		}
		if(statementDepth>0 && this.statements.size()>0) {
			returnString+="\n"+Util.blanks(indentation)+"STATEMENTS OF ";
			returnString+=this.workName
					;
			returnString+="\n";
			for(int i = 0; i< statements.size(); i++) {
				returnString+=statements.get(i).getStr(
						indentation+1,statementDepth-1);
			}
		}
		return returnString;
	}
	

}
