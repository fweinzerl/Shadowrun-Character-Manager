import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class AttrList{
	protected String listName;
	protected ArrayList<String[]> table;
	
	public AttrList(String name, ArrayList<String[]> rows){
		listName = name;
		table = rows;
	}
	
	public String selectItem(int x, int y){
		int c = -1;//characters
		int i = 0;
		try{
			for(; c < x; c += table.get(y-1)[i].length()+1, i++)
				continue;
		}catch(ArrayIndexOutOfBoundsException e){
			i = table.get(y-1).length;
		}
			i--;
		//leaving our footprint in manager class
		Tabs.selected[0] = i;
		Tabs.selected[1] = y-1;
		return table.get(y-1)[i];
	}
	
	public String getItem(int x, int y){
		try{
			return table.get(y)[x];
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: selected item doesn't exist");
		}
		return " ";
	}
	
	public void setItem(int x, int y, String s){
		try{
			table.get(y)[x] = s;
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: selected item doesn't exist");
		}
	}
	
	public void addRow(int pos){
		String[] newRow = new String[table.get(0).length];
		Arrays.fill(newRow, "-");
		try{
			table.add(pos+1, newRow);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: wrong parameter");
		}
	}
	
	public void deleteRow(int pos){
		try{
			table.remove(pos);
		}catch(IndexOutOfBoundsException e){
			System.out.println("Internal Error: wrong parameter");
		}
	}
	
	public boolean addRow(String[] newRow){
		if(table.get(0).length != newRow.length)
			return false;
		
		table.add(newRow);
		return true;
	}
	
	public String toString(){
		String result = listName;
		
		for(int i = 0; i < table.size(); i++){
			result += "\n";
			for(int j = 0; j < table.get(0).length; j++){
				result += table.get(i)[j] + "  ";
			}
		}
		
		return result;
	}
	
	public void printToDoc(StyledDocument doc){
		Utils.addStylesToDocument(doc);
	
		//Load the text pane with styled text.
		try {
			doc.insertString(doc.getLength(), listName, doc.getStyle("h1"));//print header
			
			for(int i = 0; i < table.size(); i++){
				doc.insertString(doc.getLength(), "\n", doc.getStyle("standard"));//new line
				for(int j = 0; j < table.get(0).length; j++)
					doc.insertString(doc.getLength(), table.get(i)[j] + "  ", doc.getStyle("standard"));//print a row
			}
		} catch (BadLocationException ble) {
		    System.err.println("Couldn't insert text into text pane.");
		}
	}
	
	public void printToStream(DataOutputStream dos){
		try {
			dos.writeUTF(this.getClass().getSimpleName());
			dos.writeUTF(listName);
			dos.writeInt(table.size());//no. of rows
			dos.writeInt(table.size()>0?table.get(0).length:0);//no. of cols
			
			for(int i = 0; i < table.size(); i++)
				for(int j = 0; j < table.get(0).length; j++)
					dos.writeUTF(table.get(i)[j]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AttrList readFromStream(DataInputStream dis){
		String type = null;
		String name = null;
		ArrayList<String[]> nTable = null;
		
		try{
			type = dis.readUTF();
			if(type.equals(""))
				return null;
			name = dis.readUTF();
			int rows = dis.readInt();//table.size()
			int cols = dis.readInt();//table.get(0).length
			
			nTable = new ArrayList<String[]>();
			
			for(int i = 0; i < rows; i++){
				nTable.add(new String[cols]);
				for(int j = 0; j < cols; j++)
					nTable.get(i)[j] = dis.readUTF();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(type == null)
			return new AttrList(name, nTable);
		
		if(type.equals("DualAttrList"))
			return new DualAttrList(name, nTable);

		if(type.equals("MultiAttrList"))
			return new MultiAttrList(name, nTable);
		
		else return new AttrList(name, nTable);
	}
}
