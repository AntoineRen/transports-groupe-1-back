package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class VehiculeNonTrouveException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public VehiculeNonTrouveException() {
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
	public VehiculeNonTrouveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public VehiculeNonTrouveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public VehiculeNonTrouveException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public VehiculeNonTrouveException(Throwable cause) {
		super(cause);
	}

}
