package dataStructures;

/*To do:
 * 
 * 4. Define boolean logic
 * 5 Use bools to define rules for "classes extending Node"
 * 
 *To do someday:
 * optimize checking functions
 */

public class DataBase {
	//Lists of stuff for access
	private NodeList thingList= new NodeList();
	private NodeList statementList = new NodeList();
	private NodeList relationList = new NodeList();
	private final Node[] booleans; 

	//Sets
	public Node allThings = formalNode("All Things");
	private Node allRelations = formalNode("All Relations");
	public Node allSets = formalNode ("All Sets");
	private Node allStatements = formalNode ("All Statements");
	private Node allBooleans = formalNode ("All Booleans");
	private Node allAttributes = formalNode("All Atributes");
			
	//Relations
	public Node subsetOf = formalNode("Subset of");
	public Node supersetOf = formalNode("Superset of");
	public Node elementOf = formalNode("Element of");
	private Node hasAttribute = formalNode("Has the Attribute");
	private Node and = formalNode ("And");
	private Node or = formalNode ("Or");
	private Node implies = formalNode("Implies");
	
	//Attributes
	private Node metaStatement = formalNode("Metastatement");
	private Node TRUE = formalNode("TRUE");
	private Node FALSE = formalNode("FALSE");

	//Relations demanding type(Relation)
	private Node associative = formalNode("Associative");
	private Node commutative = formalNode("Commutative");
	private Node distributive = formalNode("Distributive");
	

	public DataBase() {

		booleans=new Node[] {FALSE,TRUE};
		//Naive Statements (preaxioms)
		subsetOf.addStatement(naiveStatementMaker(subsetOf,elementOf,allRelations));
		supersetOf.addStatement(naiveStatementMaker(supersetOf,elementOf,allRelations));
		elementOf.addStatement(naiveStatementMaker(elementOf,elementOf,allRelations));
		allThings.addStatement(naiveStatementMaker(allThings,elementOf,allSets));
		allSets.addStatement(naiveStatementMaker(allSets,subsetOf,allThings));
		TRUE.addStatement(naiveStatementMaker(TRUE,elementOf,allBooleans));
		FALSE.addStatement(naiveStatementMaker(FALSE,elementOf,allBooleans));
	}

	
	//tells that a statement is a statement 
	//help function for naiveStatementMaker
	//And that the statement saying so is a meta-statement
	//a.k.a. a recursive-ending statement.
	//This version is without waterproofing
	private void statementify(Node statement) {
		Node[] tempArr1= {statement, subsetOf, allStatements };
		Node tempStatement1= new Node("metaStatement",tempArr1);
		tempArr1= new Node [] {tempStatement1, hasAttribute, metaStatement};
		tempStatement1.addStatement(new Node("MetaStatement",tempArr1));
		Node[] tempArr2 = {tempStatement1, hasAttribute, metaStatement};
		Node tempStatement2= new Node ("metaStatement", tempArr2);
		
		tempStatement1.addStatement(tempStatement2);
		statement.addStatement(tempStatement1);
		}
		
		//Only to be called in the database.
		//Could generate bugs (no fail checking/Type checking)
	public Node naiveStatementMaker(Node object, Node relation, Node subject) {
			Node[] tempArr = {object,relation,subject};
			Node returnNode= new Node("Statement", tempArr, new Node[] {TRUE});
			statementify(returnNode);
			return returnNode;
		}
		
	public Node statementMaker(Node object, Node relation, Node subject){
		if (naiveIsElementOf(relation,allRelations)){
			return naiveStatementMaker(object,relation,subject);
		} else {
			System.out.println("ERROR! "+relation.getStr()+" not a relation");
			return null;
		}
	}
	
	public Node not(Node bool) {
		if(! naiveIsElementOf(bool,allBooleans)) {
			System.out.println("WARNING! NOT A BOOLEAN NODE!");
			return null;
		}
	return FALSE.equals(bool)? TRUE: FALSE;				
	}
	public and(Node A, Node B) {
		
	}
	public Node Negate(Node statement) {
		if (!isSubsetOf(statement,allStatements)) {
			System.out.println("ERROR! "+statement.getStr()+ "Isn't a statement!");
			return null;
		}
		Node[] bool= new Node[] {not(statement.getOutputElements()[0])};
		return new Node("Negation",new Node[] {Statement}, bool);
	}

	//Is A a subset of B?
	//Doesn't check if it's ok to make the operation (set,set)
	//Doesn't check that it's operating on Statements (subset of allStatements)
	private boolean naiveIsSubsetOf(Node A, Node B) {

		if (A.equals(B)) return true;
		NodeList inStatements=A.getStatements();
		for(int i =0; i<inStatements.size(); i++) {
			Node[] currentElements= inStatements.get(i).getInputElements();
			if (!(	currentElements!= null &&
					currentElements.length==3 &&
					currentElements[1]==subsetOf)) {
				continue;
			}
			if (currentElements[2]==B&&
					currentElements[0]==A) {
				return true;
			} else if (naiveIsSubsetOf(currentElements[2],B)){
				return true;
			}				
		}
		return false;
	}
	private boolean isSubsetOf(Node A, Node B) {
		if( (!naiveIsElementOf(A,allSets)) || (!naiveIsElementOf(B,allSets)) ){
			System.out.println("Checkpoint 1");
			return false;
		} else return naiveIsSubsetOf(A,B);
	}

	public boolean naiveIsElementOf(Node A, Node B) {
		if( B==allSets && A == allSets) {
			return true;
		}else if ((B!=allSets)&&(!naiveIsElementOf(B,allSets))){
			System.out.println("Checkpoint 2");
			return false;
		}
		
		NodeList inStatements= A.getStatements();
		for(int i =0; i<inStatements.size(); i++) {
			Node[] currentElements= inStatements.get(i).getInputElements();
			if (!(	currentElements!= null &&
					currentElements.length==3 &&
					currentElements[1]==elementOf)) {
				continue;
			}
			if (currentElements[2]==B&&
					currentElements[0]==A) {
				return true;
			} else if (isSubsetOf(currentElements[2],B)){
				return true;
			}				
		}
		return false;
	}
	


	public boolean naiveHasAttribute(Node A, Node B) {
		if ( ! naiveIsElementOf(B,allStatements)) {
			System.out.println(B.getWorkName()+" isn't an attribute!");
			return false;
		}
		boolean isSet= naiveIsSubsetOf(A,allSets);


		NodeList inStatements= A.getStatements();
		for(int i =0; i<inStatements.size(); i++) {
			Node[] currentElements= inStatements.get(i).getInputElements();
			if (!(	currentElements!= null &&
					currentElements.length==3)) {
				continue;
			} else if( currentElements[1]==hasAttribute&&
					currentElements[2]== B) {
				return true;
			}else if(isSet && currentElements[1]==subsetOf &&
					naiveHasAttribute(currentElements[2], B)) {
				return true;

			}else if( currentElements[1]==elementOf && 
					naiveHasAttribute(currentElements[2], B)){
				return true;
			}
		}
	return false;	
	}
	

	
	
	

	//Basic Node block building
	private Node formalNode(String workingName){
		Node temp= new Node(workingName);
		temp.formalizeName();
		return temp;
		
	}

}
