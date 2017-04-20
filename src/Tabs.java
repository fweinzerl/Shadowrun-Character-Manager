import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * This class is basically the archive- and manager-class for attributelists (AttrList).
 * It holds info about which lists belong to which tab and is therefore acting as a gateway between UI and datastructure
 * 
 * @author sachsenschnitzel
 */
public class Tabs{
	
	public static Tab[] tabs;
	public static int[] active = new int[2];
	public static int[] selected = new int[2];
	
	public static void setListType(Class c){
		if(!c.isInstance(Tabs.tabs[active[0]].subTabs[active[1]].datafield)){
			switch(c.getName()){
				case "DualAttrList":
					Tabs.tabs[active[0]].subTabs[active[1]].datafield = ((MultiAttrList)Tabs.tabs[active[0]].subTabs[active[1]].datafield).toDualAttrList();
				break;
	
				case "MultiAttrList":
					Tabs.tabs[active[0]].subTabs[active[1]].datafield = ((DualAttrList)Tabs.tabs[active[0]].subTabs[active[1]].datafield).toMultiAttrList();
				break;
			}
			refreshSelected();
		}
	}
	
	public static void addRow(){
		Tabs.tabs[active[0]].subTabs[active[1]].datafield.addRow(selected[1]);
	}
	
	public static void deleteRow(){
		Tabs.tabs[active[0]].subTabs[active[1]].datafield.deleteRow(selected[1]);
	}
	
	public static String selectItem(int x, int y){
		if(y == 0){
			selected[0] = 0;
			selected[1] = -1;
			return "";
		}
		return Tabs.tabs[active[0]].subTabs[active[1]].datafield.selectItem(x, y);//selected is set in selectItem-call
	}
	
	public static String getSelected(){
		return Tabs.tabs[active[0]].subTabs[active[1]].datafield.getItem(selected[0], selected[1]);
	}
	
	public static void setSelected(String s){
		if(selected[1] != -1)
			Tabs.tabs[active[0]].subTabs[active[1]].datafield.setItem(selected[0], selected[1], s);
	}
	
	public static void refreshSelected(){
		int x = selected[0], y = selected[1];
		tabs[active[0]].subTabs[active[1]].doClick();
		
		selected[0] = x;
		selected[1] = y;
	}
	
	public static void initTabBtns(final JTextPane pane, final JRadioButtonMenuItem rbd, final JRadioButtonMenuItem rbm){
		//tab button declaration
		tabs = new Tab[4];
		tabs[0] = new Tab(9);
		tabs[1] = new Tab(6);
		tabs[2] = new Tab(8);
		tabs[3] = new Tab(8);
		
		for(int i = 0; i < tabs.length; i++)
			for(int j = 0; j < tabs[i].subTabs.length; j++){
				tabs[i].subTabs[j] = new SubTab(Tabstrings.getString("tabs." + i + j)); //$NON-NLS-1$
				final int ii = i;
				final int jj = j;
				tabs[i].subTabs[j].addActionListener(new ActionListener(){
			        @Override
			        public void actionPerformed(ActionEvent e){
			        	Tabs.active[0] = ii;
			        	Tabs.active[1] = jj;
			        	pane.setText("");
			        	if(Tabs.tabs[ii].subTabs[jj].datafield != null){
			        		if(Tabs.tabs[ii].subTabs[jj].datafield instanceof DualAttrList)
			        			rbd.doClick();
			        		else if(Tabs.tabs[ii].subTabs[jj].datafield instanceof MultiAttrList)
				        		rbm.doClick();
			        		Tabs.tabs[ii].subTabs[jj].datafield.printToDoc(pane.getStyledDocument());
			        	}
			        }
			    });
			}
	}
	
	public static void setTabBtnVisible(int sheet, boolean flag){
		for(int i = 0; i < tabs[sheet].subTabs.length; i++)
			tabs[sheet].subTabs[i].setVisible(flag);
	}
	
	public static void addBtnsToPnl(JPanel pnl){
		for(int i = 0; i < tabs.length; i++){
			for(int j = 0; j < tabs[i].subTabs.length; j++){
				tabs[i].subTabs[j].setBounds(j * pnl.getWidth()/(tabs[i].subTabs.length), 0, pnl.getWidth()/(tabs[i].subTabs.length), pnl.getHeight());
				tabs[i].subTabs[j].setVisible(false);
				tabs[i].subTabs[j].setForeground(Start.sheetColours[i]);
			}
		}
		
		for(int i = 0; i < tabs.length; i++)
			for(int j = 0; j < tabs[i].subTabs.length; j++)
				pnl.add(tabs[i].subTabs[j]);
	}
	
	public static void loadFromFile(File f){
		if(!f.exists()){
			System.out.println("cannot load from non-existing file!");
			return;
		}
		System.out.println("loading...");
		
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			
			for(int i = 0; i < Tabs.tabs.length; i++)
				for(int j = 0; j < Tabs.tabs[i].subTabs.length; j++)
					Tabs.tabs[i].subTabs[j].datafield = AttrList.readFromStream(dis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveToFile(File f){
		System.out.println("saving...");
		
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
			
			for(int i = 0; i < Tabs.tabs.length; i++)
				for(int j = 0; j < Tabs.tabs[i].subTabs.length; j++)
					if(Tabs.tabs[i].subTabs[j].datafield != null)
						Tabs.tabs[i].subTabs[j].datafield.printToStream(dos);
					else
						try {
							dos.writeUTF("");
						} catch (IOException e) {
							e.printStackTrace();
						}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
