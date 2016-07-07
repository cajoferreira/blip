package pt.blip.challenge.format;

/**
 * Formats the output in XML format
 * 
 * @author carlos.t.ferreira
 *
 */
public class XmlFormat extends Format {

	XmlFormat() {
		super(FormatType.XML);
	}

	@Override
	public String construct(FormatTarget formatTarget) {
		Object[][] input = formatTarget.organizeInput();
		
		if( input == null || input.length == 0 ) {
			return null;
		}
		
		// dummy implementation
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		xml.append("<rows>");
		for (Object[] row : input) {
			xml.append("<row>");
			for (Object column : row) {
				xml.append("<column>");
				xml.append(column);
				xml.append("</column>");
			}
			xml.append("</row>");
		}
		xml.append("</rows>");
		
		return xml.toString();
	}

}
