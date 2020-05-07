package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class CollegueNonTrouveException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public CollegueNonTrouveException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CollegueNonTrouveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public CollegueNonTrouveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public CollegueNonTrouveException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public CollegueNonTrouveException(Throwable cause) {
		super(cause);
	}

}
