import java.awt.EventQueue;

import javax.swing.*;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

import java.awt.Panel;
import java.awt.Color;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JRadioButtonMenuItem;

import java.awt.Button;
import java.awt.ScrollPane;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import net.miginfocom.swing.MigLayout;

import java.awt.CardLayout;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JMenu;

import java.awt.BorderLayout;

import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JCheckBoxMenuItem;


public class Start extends JFrame {

	private JPanel contentPane;
	private JLabel lblSheetName;
	private JPanel tabPanel;
	public static Color[] sheetColours = {new Color(0, 0, 255), new Color(193, 0, 0), new Color(0, 225, 0), new Color(130, 199, 206)};
	private int sheet = 0;
	private static File selected = new File("." + File.separator + "saves");
	private JTextField editingField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("setting file path to " + selected.getAbsolutePath());
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts" + File.separator + "ProFontWindows.ttf")));
		} catch (IOException|FontFormatException e) {
		     System.out.println("Couldn't load all fonts");
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Start() {
		this.setTitle("Shadowrun Character Manager");
		
		tabPanel = new JPanel();
		tabPanel.setBounds(0, 50, 944, 50);
		
		//window-building
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 640);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		Component horizontalStrut1 = Box.createHorizontalStrut(3);
		menuBar.add(horizontalStrut1);
		
		JLabel title = new JLabel("SHADOWRUN");
		title.setForeground(new Color(0, 0, 139));
		menuBar.add(title);
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Component horizontalStrut = Box.createHorizontalStrut(3);
		menuBar.add(horizontalStrut);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser(){
		    @Override
		    public void approveSelection(){
		        File f = getSelectedFile();
		        if(f.exists() && getDialogType() == SAVE_DIALOG){
		            int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
		            switch(result){
		                case JOptionPane.YES_OPTION:
		                    super.approveSelection();
		                    return;
		                case JOptionPane.NO_OPTION:
		                    return;
		                case JOptionPane.CLOSED_OPTION:
		                    return;
		                case JOptionPane.CANCEL_OPTION:
		                    cancelSelection();
		                    return;
		            }
		        }
		        super.approveSelection();
		    }        
		};
		fc.setCurrentDirectory(selected);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Shadowrun Character Files", "src");
		fc.setFileFilter(filter);
		JMenuItem mntmSave = new JMenuItem("Save...");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e){
	        	if(selected.getName().endsWith(".src")){
	        		Tabs.saveToFile(selected);
	        	}
	        	else{
	        		int returnVal = fc.showSaveDialog(Start.this);
	        		
	                if(returnVal == JFileChooser.APPROVE_OPTION){
	                    File file = fc.getSelectedFile();
	                    if(!file.getName().endsWith(".src"))
	                    	file = new File(file.getAbsolutePath() + ".src");
	                    Tabs.saveToFile(file);
	                    selected = file;
	                }
	        	}
	        }
	    });
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		mntmSaveAs.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e){
                int returnVal = fc.showSaveDialog(Start.this);

                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    if(!file.getName().endsWith(".src"))
                    	file = new File(file.getAbsolutePath() + ".src");
                    Tabs.saveToFile(file);
                    selected = file;
                }
	        }
	    });
		
		JMenuItem mntmLoad = new JMenuItem("Load...");
		mnFile.add(mntmLoad);
		mntmLoad.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e){
                int returnVal = fc.showOpenDialog(Start.this);

                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    Tabs.loadFromFile(file);
                    selected = file;
                }
                
                Tabs.tabs[0].subTabs[0].doClick();
	        }
	    });
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnChangeListType = new JMenu("Change List Type");
		mnEdit.add(mnChangeListType);
		
		ButtonGroup group = new ButtonGroup();
		
		final JRadioButtonMenuItem rdbtnmntmDual = new JRadioButtonMenuItem("Dual");
		group.add(rdbtnmntmDual);
		mnChangeListType.add(rdbtnmntmDual);
		rdbtnmntmDual.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e){
	        	try {
					Tabs.setListType(Class.forName("DualAttrList"));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	        }
	    });
		
		final JRadioButtonMenuItem rdbtnmntmMulti = new JRadioButtonMenuItem("Multi");
		group.add(rdbtnmntmMulti);
		mnChangeListType.add(rdbtnmntmMulti);
		rdbtnmntmMulti.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e){
	        	try {
					Tabs.setListType(Class.forName("MultiAttrList"));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	        }
	    });
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel sheetPanel = new Panel();
		sheetPanel.setBounds(0, 0, 944, 50);
		contentPane.add(sheetPanel);
		sheetPanel.setLayout(null);
		
		lblSheetName = new JLabel("Character Sheet");
		lblSheetName.setForeground(Color.BLUE);
		lblSheetName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSheetName.setBounds(412, 11, 119, 28);
		sheetPanel.add(lblSheetName);
		
		JButton left = new JButton("<");
		left.setBounds(0, 0, 50, 50);
		sheetPanel.add(left);
		left.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Tabs.setTabBtnVisible(sheet, false);
	        	changeSheet(sheet = (sheet+3) % 4);
	        	Tabs.setTabBtnVisible(sheet, true);
	        }
	    });
		
		JButton right = new JButton(">");
		right.setBounds(894, 0, 50, 50);
		sheetPanel.add(right);
		right.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Tabs.setTabBtnVisible(sheet, false);
	        	changeSheet(sheet = (sheet+1) % 4);
	        	Tabs.setTabBtnVisible(sheet, true);
	        }
	    });
		
		contentPane.add(tabPanel);
		tabPanel.setLayout(null);
		
		JSplitPane displayArea = new JSplitPane();
		displayArea.setBounds(0, 100, 944, 476);
		displayArea.setOneTouchExpandable(true);
		displayArea.setDividerLocation(550);
		contentPane.add(displayArea);
		
		JScrollPane contentArea = new JScrollPane();
		displayArea.setLeftComponent(contentArea);
		
		final JPanel evaluationArea = new JPanel();
		displayArea.setRightComponent(evaluationArea);
		evaluationArea.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(25dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default)"),
				ColumnSpec.decode("0px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("21px"),
				RowSpec.decode("max(16px;default)"),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("max(12dlu;default)"),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("max(12dlu;default)"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("max(12dlu;default)"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("top:max(12dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblCurField = new JLabel("currently selected:");
		evaluationArea.add(lblCurField, "2, 2, 7, 1, center, center");
		
		final JLabel lblOldValue = new JLabel("nothing selected");
		evaluationArea.add(lblOldValue, "2, 3, 7, 1, center, center");
		
		editingField = new JTextField();
		evaluationArea.add(editingField, "3, 7, 5, 1, fill, fill");
		editingField.setColumns(10);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(lblOldValue.getText().equals(""))
	        		return;
	        	Tabs.setSelected(editingField.getText());
	        	Tabs.refreshSelected();
	        	lblOldValue.setText("> " + editingField.getText() + " <");
	        }
	    });
		evaluationArea.add(btnApply, "3, 9, 5, 1, fill, top");
		
		JButton btnAdd = new JButton("Add Row");
		btnAdd.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Tabs.addRow();
	        	Tabs.refreshSelected();
	        	lblOldValue.setText("> " + Tabs.getSelected() + " <");
	        }
	    });
		evaluationArea.add(btnAdd, "4, 29, fill, top");
		
		JButton btnDelete = new JButton("Delete Row");
		btnDelete.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Tabs.deleteRow();
	        	Tabs.refreshSelected();
	        	lblOldValue.setText("> " + Tabs.getSelected() + " <");
	        }
	    });
		evaluationArea.add(btnDelete, "6, 29, fill, center");
		
		final JTextPane txtpnText = new JTextPane();
		txtpnText.setEditable(false);
		contentArea.setViewportView(txtpnText);
		
		Tabs.initTabBtns(txtpnText, rdbtnmntmDual, rdbtnmntmMulti);
        Tabs.tabs[0].subTabs[0].doClick();
		
		TestAttrList.fillDataArrayWithTestData();
		
		Tabs.addBtnsToPnl(tabPanel);
		Tabs.setTabBtnVisible(0, true);
		
		txtpnText.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent e){
				int dot = e.getDot();
				try{
					int line = Utils.getLineOfOffset(txtpnText, dot);
					int col = dot - Utils.getLineStartOffset(txtpnText, line);
					System.out.println("Line: " + line + ", Col: " + col);
					lblOldValue.setText("> " + Tabs.selectItem(col, line) + " <");
				}catch(BadLocationException e1){
					e1.printStackTrace();
				}
	    	}
	    });
		JButton btnSettings = new JButton("Settings");
		menuBar.add(btnSettings);
		
		Component horizontalStrut2 = Box.createHorizontalStrut(3);
		menuBar.add(horizontalStrut2);
		
		final JToggleButton tglbtnLocked = new JToggleButton("locked");
		tglbtnLocked.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//toggle visibility of all components
	        	Component[] components = evaluationArea.getComponents();
	            for (Component component : components)
	                component.setVisible(!tglbtnLocked.isSelected());
	        }
	    });
		menuBar.add(tglbtnLocked);
		tglbtnLocked.doClick();
        Tabs.tabs[0].subTabs[0].doClick();
	}
	
	private void changeSheet(int nr){
		String sheetText = "";
		Color color = null;
		switch(nr){
			case 0:
				sheetText = "Character Sheet";
			break;
			
			case 1:
				sheetText = "Combat Sheet";
			break;
			
			case 2:
				sheetText = "Social Sheet";
			break;
			
			case 3:
				sheetText = "Magic Sheet";
			break;
		}
    	lblSheetName.setText(sheetText);
    	lblSheetName.setForeground(sheetColours[nr]);
    	lblSheetName.setHorizontalAlignment(JLabel.CENTER);
	}
}
