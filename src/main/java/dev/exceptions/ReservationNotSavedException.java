package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class ReservationNotSavedException extends ApplicationException {

	/**
	 * Constructor
	 *
	 */
	public ReservationNotSavedException() {
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
	public ReservationNotSavedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public ReservationNotSavedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public ReservationNotSavedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public ReservationNotSavedException(Throwable cause) {
		super(cause);
	}

}
