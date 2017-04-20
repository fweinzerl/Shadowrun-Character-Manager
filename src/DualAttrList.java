import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class DualAttrList extends AttrList{
	public DualAttrList(String name, ArrayList<String[]> rows){
		super(name, rows);
	}
	
	@Override
	public void addRow(int pos){
		String[] newRow = {"<desc>", "<val>"};
		try{
			table.add(pos+1, newRow);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: wrong parameter");
		}
	}
	
	@Override
	public String toString(){
		String result = listName + "\n";
		
		for(int i = 0; i < table.size(); i++){
			result += "\n";
			result += table.get(i)[0] + ": " + table.get(i)[1];
		}
		
		return result;
	}
	
	@Override
	public void printToDoc(StyledDocument doc){
		Utils.addStylesToDocument(doc);
	
		//Load the text pane with styled text.
		try {
			doc.insertString(doc.getLength(), listName, doc.getStyle("h1"));//print header
			
			for(int i = 0; i < table.size(); i++){
				doc.insertString(doc.getLength(), "\n", doc.getStyle("standard"));//new line
				doc.insertString(doc.getLength(), table.get(i)[0] + ":", doc.getStyle("desc"));//print a row
				doc.insertString(doc.getLength(), " " + table.get(i)[1], doc.getStyle("standard"));
			}
		} catch (BadLocationException ble) {
		    System.err.println("Couldn't insert text into text pane.");
		}
	}
	
	public MultiAttrList toMultiAttrList(){
		return new MultiAttrList(listName, table);
	}
}
