package pt.blip.challenge.format;

/**
 * Formats the output in HTML format
 * 
 * @author carlos.t.ferreira
 *
 */
public class HtmlFormat extends Format {

	HtmlFormat() {
		super(FormatType.HTML);
	}

	@Override
	public String construct(FormatTarget formatTarget) {
		Object[][] input = formatTarget.organizeInput();
		
		if( input == null || input.length == 0 ) {
			return null;
		}
		
		// dummy implementation
		StringBuilder html = new StringBuilder("<html>");
		
		html.append("<head></head>");
		html.append("<body>");
		html.append("<table>");
		for (Object[] row : input) {
			html.append("<tr>");
			for (Object column : row) {
				html.append("<td>");
				html.append(column);
				html.append("</td>");
			}
			html.append("</tr>");
		}
		html.append("</table>");
		html.append("</body>");
		html.append("</html>");
		
		return html.toString();
	}

}
