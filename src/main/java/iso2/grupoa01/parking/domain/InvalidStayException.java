package iso2.grupoa01.parking.domain;

/**
 * Thrown when a parking stay cannot be created: a missing (null) entry or
 * exit time, or an exit time earlier than the entry time.
 */
public class InvalidStayException extends IllegalArgumentException {

    public InvalidStayException(String message) {
        super(message);
    }
}
