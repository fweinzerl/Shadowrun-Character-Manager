import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class MultiAttrList extends AttrList{
	int[] sizes;
	
	public MultiAttrList(String name, ArrayList<String[]> rows){
		super(name, rows);
		
		if(table.size() == 0)
			table.add(new String[]{" ", " "});
		
		sizes = new int[table.get(0).length];
		Arrays.fill(sizes, 0);
		
		calcSizes();
	}
	
	@Override
	public void addRow(int pos){
		String[] newRow = new String[table.get(0).length];
		for(int i = 0; i < table.get(0).length; i++)
			newRow[i] = "<" + table.get(0)[i] + ">";
		try{
			table.add(pos+1, newRow);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: wrong parameter");
		}

		calcSizes();
	}
	
	@Override
	public String selectItem(int x, int y){
		int c = -1;//characters
		int i = 0;
		try{
			for(; c < x; c += sizes[i]+1, i++)
				continue;
		}catch(IndexOutOfBoundsException e){
			i = table.get(y-2).length;
		}
			i--;
		//leaving our footprint in manager class
		Tabs.selected[0] = i;
		Tabs.selected[1] = y-2;
		return table.get(y-2)[i];
	}
	
	@Override
	public void setItem(int x, int y, String s){
		try{
			table.get(y)[x] = s;
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: selected item doesn't exist");
		}
		
		calcSizes();
	}
	
	public void calcSizes(){
		Arrays.fill(sizes, 0);
		for(int i = 0; i < sizes.length; i++)
			for(int j = 0; j < table.size(); j++)
				if(table.get(j)[i].length() > sizes[i])
					sizes[i] = table.get(j)[i].length();
	}
	
	@Override
	public String toString(){
		String result = listName;
		
		if(table.size() > 0){
			result += "\n\n";
			for(int j = 0; j < table.get(0).length; j++)
				result += Utils.center(table.get(0)[j], sizes[j]) + "  ";
		}
		
		for(int i = 1; i < table.size(); i++){
			result += "\n";
			for(int j = 0; j < table.get(0).length; j++)
				result += String.format("%1$-" + sizes[j] + "s", table.get(i)[j]) + "  ";
		}
		
		return result;
	}
	
	@Override
	public void printToDoc(StyledDocument doc){
		Utils.addStylesToDocument(doc);
	
		//Load the text pane with styled text.
		try {
			doc.insertString(doc.getLength(), listName, doc.getStyle("h1"));//print header
			
			if(table.size() > 0){
				doc.insertString(doc.getLength(), "\n\n", doc.getStyle("standard"));//new line
				for(int j = 0; j < table.get(0).length; j++)
					doc.insertString(doc.getLength(), Utils.center(table.get(0)[j], sizes[j]) + "  ", doc.getStyle("h2"));
			}
			
			for(int i = 1; i < table.size(); i++){
				doc.insertString(doc.getLength(), "\n", doc.getStyle("standard"));//new line
				for(int j = 0; j < table.get(0).length; j++)
					doc.insertString(doc.getLength(), String.format("%1$-" + sizes[j] + "s", table.get(i)[j]) + "  ", doc.getStyle("standard"));//print a row
			}
		} catch (BadLocationException ble) {
		    System.err.println("Couldn't insert text into text pane.");
		}
	}
	
	public DualAttrList toDualAttrList(){
		ArrayList<String[]> dualRows = new ArrayList<String[]>();
		
		for(int i = 0; i < table.size(); i++)
			dualRows.add(new String[]{table.get(0)[0], table.get(0)[1]});
		
		return new DualAttrList(listName, dualRows);
	}
}
