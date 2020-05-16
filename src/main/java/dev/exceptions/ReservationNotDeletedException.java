package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class ReservationNotDeletedException extends ApplicationException {

	/**
	 * Constructor
	 *
	 */
	public ReservationNotDeletedException() {
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
	public ReservationNotDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public ReservationNotDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public ReservationNotDeletedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public ReservationNotDeletedException(Throwable cause) {
		super(cause);
	}

}
