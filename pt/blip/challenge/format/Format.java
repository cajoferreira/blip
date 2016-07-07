package pt.blip.challenge.format;


/**
 * 
 * @author carlos.t.ferreira
 *
 */
public abstract class Format {

	private FormatType formatType;
	
	Format(FormatType formatType) {
		this.formatType = formatType;
	}

	/**
	 * Returns the format type
	 * @return {@link FormatType}
	 */
	public FormatType getFormatType() {
		return formatType;
	}

	/**
	 * Sets the format type
	 * @param formatType {@link FormatType}
	 */
	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}
	
	/**
	 * Constructs the output according to the specified format type
	 * @param formatTarget Data to be formatted
	 * @return {@link String} of textual data
	 */
	public abstract String construct(FormatTarget formatTarget);
}
