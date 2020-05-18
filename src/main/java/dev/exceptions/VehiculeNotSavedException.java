package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class VehiculeNotSavedException extends ApplicationException {

	/**
	 * Constructor
	 *
	 */
	public VehiculeNotSavedException() {
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
	public VehiculeNotSavedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public VehiculeNotSavedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public VehiculeNotSavedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public VehiculeNotSavedException(Throwable cause) {
		super(cause);
	}

}
