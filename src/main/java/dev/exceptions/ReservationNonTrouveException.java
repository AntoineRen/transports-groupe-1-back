package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class ReservationNonTrouveException extends RuntimeException {

	/**
	 * Constructor
	 *
	 */
	public ReservationNonTrouveException() {
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
	public ReservationNonTrouveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public ReservationNonTrouveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public ReservationNonTrouveException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public ReservationNonTrouveException(Throwable cause) {
		super(cause);
	}

}
