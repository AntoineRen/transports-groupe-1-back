package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class AnnonceNonTrouveException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public AnnonceNonTrouveException() {
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
	public AnnonceNonTrouveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public AnnonceNonTrouveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public AnnonceNonTrouveException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public AnnonceNonTrouveException(Throwable cause) {
		super(cause);
	}

}
