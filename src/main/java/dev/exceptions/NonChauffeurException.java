package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class NonChauffeurException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public NonChauffeurException() {
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
	public NonChauffeurException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public NonChauffeurException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public NonChauffeurException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public NonChauffeurException(Throwable cause) {
		super(cause);
	}

}
