package iso2.grupoa01.parking.domain;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A stay in the parking, defined by the entry and exit times of the ticket.
 *
 * <p>The constructor validates both times, so any {@code ParkingStay} object
 * that exists is valid. The fields are private and final (encapsulation).
 */
public final class ParkingStay {

    private final LocalDateTime entry;
    private final LocalDateTime exit;

    /**
     * Creates a stay.
     *
     * @throws InvalidStayException if a time is null or the exit is before the entry
     */
    public ParkingStay(LocalDateTime entry, LocalDateTime exit) {
        if (isMissing(entry) || isMissing(exit)) {
            throw new InvalidStayException("Entry and exit times are required");
        }
        if (isExitBeforeEntry(entry, exit)) {
            throw new InvalidStayException("Exit time cannot be earlier than entry time");
        }
        this.entry = entry;
        this.exit = exit;
    }

    public LocalDateTime getEntry() {
        return entry;
    }

    public LocalDateTime getExit() {
        return exit;
    }

    /** Length of the stay in whole minutes; leftover seconds are not charged. */
    public long getDurationInMinutes() {
        return Duration.between(entry, exit).toMinutes();
    }

    // The condition checks below are package-private so tests can call them directly.

    /** True if the time was not provided. */
    static boolean isMissing(LocalDateTime time) {
        return time == null;
    }

    /** True if the exit happens before the entry (an equal time is allowed). */
    static boolean isExitBeforeEntry(LocalDateTime entry, LocalDateTime exit) {
        return exit.isBefore(entry);
    }
}
