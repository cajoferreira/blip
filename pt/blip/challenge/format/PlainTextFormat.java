package pt.blip.challenge.format;

/**
 * Formats the output in plain text format
 * 
 * @author carlos.t.ferreira
 *
 */
public class PlainTextFormat extends Format {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	PlainTextFormat() {
		super(FormatType.PLAIN_TEXT);
	}

	@Override
	public String construct(FormatTarget formatTarget) {
		Object[][] input = formatTarget.organizeInput();
		
		// dummy implementation
		StringBuilder plain = new StringBuilder();
		
		for (Object[] row : input) {
			if( plain.length() > 0 ) {
				plain.append(LINE_SEPARATOR);
			}
			for (Object column : row) {
				plain.append(column).append(" ");
			}
		}
		
		return plain.toString();
	}

}
