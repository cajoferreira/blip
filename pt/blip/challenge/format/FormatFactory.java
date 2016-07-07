package pt.blip.challenge.format;

/**
 * {@link Format} Factory to create instances according to the format type.
 * 
 * @author carlos.t.ferreira
 *
 */
public class FormatFactory {

	/**
	 * Creates an instance of the appropriate format type
	 * @param formatType {@link FormatType} The desired format type
	 * @return {@link Format} instance
	 */
	public static Format buildFormat(FormatType formatType) {
		switch (formatType) {
			case HTML:
				return new HtmlFormat();
			case XML:
				return new XmlFormat();
			case JSON:
				return new JsonFormat();
			default:
				return new PlainTextFormat();
		}
	}
}
