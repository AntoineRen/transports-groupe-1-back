package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class AnnonceNotDeletedException extends ApplicationException {

	/**
	 * Constructor
	 *
	 */
	public AnnonceNotDeletedException() {
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
	public AnnonceNotDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public AnnonceNotDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public AnnonceNotDeletedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public AnnonceNotDeletedException(Throwable cause) {
		super(cause);
	}

}
