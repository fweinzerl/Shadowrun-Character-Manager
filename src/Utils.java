import java.awt.GraphicsEnvironment;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class Utils{
	public static String center (String s, int length) {
	    if (s.length() > length) {
	        return s.substring(0, length);
	    } else if (s.length() == length) {
	        return s;
	    } else {
	        int leftPadding = (length - s.length()) / 2; 
	        StringBuilder leftBuilder = new StringBuilder();
	        for (int i = 0; i < leftPadding; i++) {
	            leftBuilder.append(" ");
	        }

	        int rightPadding = length - s.length() - leftPadding;
	        StringBuilder rightBuilder = new StringBuilder();
	        for (int i = 0; i < rightPadding; i++) 
	            rightBuilder.append(" ");

	        return leftBuilder.toString() + s 
	                + rightBuilder.toString();
	    }
	}
	
	/**
	 * adds styles standard (standard), table header (h1), column header (h2) and description (desc).
	 * standard: size 14
	 * h1: bold, size 18
	 * h2: bold, (centered?)
	 * desc: italic
	 * 
	 * @param doc this is where the styles go
	 */
	public static void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style standard = doc.addStyle("standard", def);
        StyleConstants.setFontSize(standard, 14);
        StyleConstants.setFontFamily(def, "ProFontWindows");

        Style s = doc.addStyle("h1", standard);
        StyleConstants.setFontSize(s, 18);
        StyleConstants.setBold(s, true);
        /*StyleConstants.setLineSpacing(s, 1.0f);
        StyleConstants.setSpaceAbove(s, 100);
        StyleConstants.setSpaceBelow(s, 100);
        doc.setParagraphAttributes(0, doc.getLength(), standard, true);*/

        s = doc.addStyle("h2", standard);
        StyleConstants.setBold(s, true);
        
        s = doc.addStyle("desc", standard);
        StyleConstants.setItalic(s, true);
        StyleConstants.setUnderline(s, true);
    }
	
	public static int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException{
	    Document doc = comp.getDocument();
	    if(offset < 0){
	        throw new BadLocationException("Can't translate offset to line", -1);
	    }else if(offset > doc.getLength()){
	        throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
	    }else{
	        Element map = doc.getDefaultRootElement();
	        return map.getElementIndex(offset);
	    }
	}

	public static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException{
	    Element map = comp.getDocument().getDefaultRootElement();
	    if(line < 0){
	        throw new BadLocationException("Negative line", -1);
	    }else if(line >= map.getElementCount()){
	        throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
	    }else{
	        Element lineElem = map.getElement(line);
	        return lineElem.getStartOffset();
	    }
	}
}