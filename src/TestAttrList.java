
import java.util.ArrayList;

public class TestAttrList {
	/*public static void main(String[] args){
		ArrayList<String[]> test = new ArrayList<String[]>();
		
		test.add(new String[]{"Name", "Schnitzel"});
		test.add(new String[]{"Metatyp", "Nächtlicher"});
		test.add(new String[]{"Alter", "20"});
		test.add(new String[]{"Geschlecht", "m"});
		test.add(new String[]{"Nuyen", "76160"});
		test.add(new String[]{"Lebensstil", "Unterschicht"});
		
		DualAttrList dal = new DualAttrList("Charakterdaten", test);
		System.out.println(dal);
	}*/
	
	public static void fillDataArrayWithTestData(){
		ArrayList<String[]> test = new ArrayList<String[]>();
		
		//character sheet
		Tabs.tabs[0].subTabs[0].datafield = new DualAttrList(Tabstrings.getString("tabs.00"), new ArrayList<String[]>());
		
		test = new ArrayList<String[]>();
		test.add(new String[]{"Attribut", "Wert"});
		Tabs.tabs[0].subTabs[1].datafield = new MultiAttrList(Tabstrings.getString("tabs.01"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Attribut", "Wert"});
		Tabs.tabs[0].subTabs[2].datafield = new MultiAttrList(Tabstrings.getString("tabs.02"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Attribut", "Wert"});
		Tabs.tabs[0].subTabs[3].datafield = new MultiAttrList(Tabstrings.getString("tabs.03"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Fertigkeitsbezeichnung", "Wert", "Attr."});
		Tabs.tabs[0].subTabs[4].datafield = new MultiAttrList(Tabstrings.getString("tabs.04"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Fertigkeitsbezeichnung", "Wert", "Attr."});
		Tabs.tabs[0].subTabs[5].datafield = new MultiAttrList(Tabstrings.getString("tabs.05"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Attribut", " "});
		Tabs.tabs[0].subTabs[6].datafield = new MultiAttrList(Tabstrings.getString("tabs.06"), test);

		Tabs.tabs[0].subTabs[7].datafield = new DualAttrList(Tabstrings.getString("tabs.07"), new ArrayList<String[]>());
		
		Tabs.tabs[0].subTabs[8].datafield = new DualAttrList(Tabstrings.getString("tabs.08"), new ArrayList<String[]>());
		
		//combat sheet
		test = new ArrayList<String[]>();
		test.add(new String[]{"Waffe", "Schaden", "PB", "Modus", "RK", "Muni"});
		Tabs.tabs[1].subTabs[0].datafield = new MultiAttrList(Tabstrings.getString("tabs.10"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Waffe", "Reichweite", "Schaden", "PB"});
		Tabs.tabs[1].subTabs[1].datafield = new MultiAttrList(Tabstrings.getString("tabs.11"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Panzerung", "Bal/Sto", "Anm."});
		Tabs.tabs[1].subTabs[2].datafield = new MultiAttrList(Tabstrings.getString("tabs.12"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Hard-/Software", "Wert"});
		Tabs.tabs[1].subTabs[3].datafield = new MultiAttrList(Tabstrings.getString("tabs.13"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Implantat", "Stufe", "Essenz", "Anmerkungen"});
		Tabs.tabs[1].subTabs[4].datafield = new MultiAttrList(Tabstrings.getString("tabs.14"), test);

		test = new ArrayList<String[]>();
		test.add(new String[]{"Fahrzeug", "Handling", "Beschl.", "Geschw.", "Pilot", "Rumpf", "Panzerung", "Sensor", "Anm."});
		Tabs.tabs[1].subTabs[5].datafield = new MultiAttrList(Tabstrings.getString("tabs.15"), test);
		
		
		
		/*test.add(new String[]{"Name", "Schnitzel"});
		test.add(new String[]{"Metatyp", "Nächtlicher"});
		test.add(new String[]{"Alter", "20"});
		test.add(new String[]{"Geschlecht", "m"});
		test.add(new String[]{"Nuyen", "76160"});
		test.add(new String[]{"Lebensstil", "Unterschicht"});
		
		AttrList al = new DualAttrList("Charakterdaten", test);
		Tabs.tabs[0].subTabs[0].datafield = al;
		
		
		test = new ArrayList<String[]>();

		test.add(new String[]{"Attribut", " "});
		test.add(new String[]{"Konstitution", "5"});
		test.add(new String[]{"Geschicklichkeit", "2"});
		test.add(new String[]{"Reaktion", "4"});
		test.add(new String[]{"Stärke", "1"});
		
		al = new MultiAttrList("Körperliche Attribute", test);
		Tabs.tabs[0].subTabs[1].datafield = al;

		
		test = new ArrayList<String[]>();

		test.add(new String[]{"Attribut", " "});
		test.add(new String[]{"Charisma", "3"});
		test.add(new String[]{"Intuition", "4"});
		test.add(new String[]{"Logik", "5"});
		test.add(new String[]{"Willenskraft", "4"});
		
		al = new MultiAttrList("Geistige Attribute", test);
		Tabs.tabs[0].subTabs[2].datafield = al;

		
		test = new ArrayList<String[]>();

		test.add(new String[]{"Attribut", " "});
		test.add(new String[]{"Edge", "1"});
		test.add(new String[]{"Essenz", "5.1"});
		test.add(new String[]{"Initiative", "8"});
		test.add(new String[]{"Magie/Resonanz", "-"});
		test.add(new String[]{"Aktuelles Edge", "3"});
		test.add(new String[]{"Astrale INI", "-"});
		test.add(new String[]{"Matrix INI", "11(3)"});
		test.add(new String[]{"INI-Durchgänge", "1/3"});
		
		al = new MultiAttrList("Besondere Attribute", test);
		Tabs.tabs[0].subTabs[3].datafield = al;*/
	}
}
