package pt.blip.challenge.format;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Available output formats for textual data.
 * - HTML
 * - XML
 * - JSON
 * - Plain text
 * 
 * @author carlos.t.ferreira
 *
 */
public enum FormatType {
	HTML,
	XML,
	JSON,
	PLAIN_TEXT;
	
	private static final Map<String, FormatType> lookup = new HashMap<>();
	
	static {
        for (FormatType ft : EnumSet.allOf(FormatType.class)) {
            lookup.put(ft.name().toLowerCase(), ft);
        }
    }

    /**
     * Returns the output format
     * @param outputFormat A string representation of the output's format
     * @return {@link FormatType}
     */
    public static FormatType lookup(String outputFormat) {
        if( outputFormat == null ) {
            return PLAIN_TEXT;
        }
        FormatType ft = lookup.get(outputFormat.toLowerCase());

        // Defaults to plain text
        return ft == null ? PLAIN_TEXT : ft;
    }
}
