package testing;

import dataStructures.DataBase;
import dataStructures.Node;

public class test1 {


	
	public static void main(String[] args) {
	System.out.println("-------Creating Database...");
	DataBase database= new DataBase();
	System.out.println("-------"
			+ "Database Created!");
	
	
	
//-------Test of the naive ElementOf and Subset of methods	
	Node allDogs = new Node("All Dogs");
	allDogs.addStatement(database.naiveStatementMaker(allDogs,database.subsetOf,database.allThings));
	allDogs.addStatement(database.naiveStatementMaker(allDogs, database.elementOf, database.allSets));
	Node euro = new Node("Euro");
	euro.addStatement(database.naiveStatementMaker(euro,database.elementOf,allDogs));
	System.out.println(euro.getStr(0,2));
	System.out.println("Is Euro a dog?: "+database.naiveIsElementOf(euro, allDogs));
	System.out.println("Is Euro a thing?: "+database.naiveIsElementOf(euro, database.allThings));
	System.out.println(Node.namedNodes.getStr(0,1));
	}

}
