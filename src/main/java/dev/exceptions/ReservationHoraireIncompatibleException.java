package dev.exceptions;

/**
 * @author Antoine Renou
 *
 */
public class ReservationHoraireIncompatibleException extends ApplicationException {

	/**
	 * Constructor
	 *
	 */
	public ReservationHoraireIncompatibleException() {
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
	public ReservationHoraireIncompatibleException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 * @param cause
	 */
	public ReservationHoraireIncompatibleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message
	 */
	public ReservationHoraireIncompatibleException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause
	 */
	public ReservationHoraireIncompatibleException(Throwable cause) {
		super(cause);
	}

}
