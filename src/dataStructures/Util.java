package dataStructures;

public class Util {

	
	public static String blanks(int indentationLevel) {
		String returnString="";
		String[] filling = {"*"," "};
		for(int i=0; i<indentationLevel*2
				; i++) {
			returnString+=filling[i%2
			                      ];
		}
		return returnString;
	}
	
	
	
}
