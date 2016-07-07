package pt.blip.challenge.format;

/**
 * Formats the output in Json format
 * 
 * @author carlos.t.ferreira
 *
 */
public class JsonFormat extends Format {

	JsonFormat() {
		super(FormatType.JSON);
	}

	@Override
	public String construct(FormatTarget formatTarget) {
		Object[][] input = formatTarget.organizeInput();
		
		if( input == null || input.length == 0 ) {
			return null;
		}
		
		// dummy implementation
		StringBuilder json = new StringBuilder("{\"data\":[");
		
		Object[] header = input[0];
		
		for (int i = 1; i < input.length; i++) {
			Object[] row = input[i];
			
			json.append("{");
			for (int j = 0; j < row.length; j++) {
				Object column = row[j];
				
				json.append("\"").append(header[j]).append("\":\"").append(column).append("\"");
				if( j < row.length - 1 ) {
					json.append(",");
				}
			}
			json.append("}");
			
			if( i < input.length - 1 ) {
				json.append(",");
			}
		}
		json.append("]}");
		
		return json.toString();
	}

}
