import javax.swing.JButton;

public class Tab{
	public SubTab[] subTabs;
	
	public Tab(String... names){
		subTabs = new SubTab[names.length];
		for(int i = 0; i < subTabs.length; i++)
			subTabs[i] = new SubTab(names[i]);
	}
	
	public Tab(int length){
		subTabs = new SubTab[length];
	}
}