package iso2.grupoa01.parking.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Calculates the cost of a parking stay.
 *
 * <p>Business rules:
 * <ul>
 *   <li>The first 30 minutes of the stay are free.</li>
 *   <li>After that, every minute costs 0.05.</li>
 *   <li>Each day costs at most 20. A day is a block of 24 hours counted
 *       from the entry time.</li>
 *   <li>The free 30 minutes apply only once, at the start of the stay.</li>
 * </ul>
 *
 * <p>Money is handled with {@link BigDecimal} to avoid rounding errors;
 * results always have 2 decimal places.
 */
public class ParkingFeeCalculator {

    static final long FREE_MINUTES = 30;
    static final long MINUTES_PER_DAY = 24 * 60;
    static final BigDecimal PRICE_PER_MINUTE = new BigDecimal("0.05");
    static final BigDecimal DAILY_CAP = new BigDecimal("20.00");

    /**
     * Returns the total cost of a stay.
     *
     * @param entry entry time written on the ticket
     * @param exit  exit time written on the ticket
     * @return the cost, with 2 decimal places
     * @throws InvalidStayException if a time is null or the exit is before the entry
     */
    public BigDecimal calculateCost(LocalDateTime entry, LocalDateTime exit) {
        ParkingStay stay = new ParkingStay(entry, exit);
        long totalMinutes = stay.getDurationInMinutes();

        // Every complete 24-hour block is charged at the daily cap.
        long fullDays = totalMinutes / MINUTES_PER_DAY;
        BigDecimal fullDaysCost = DAILY_CAP.multiply(BigDecimal.valueOf(fullDays));

        // The minutes left over form an incomplete day.
        long remainingMinutes = totalMinutes % MINUTES_PER_DAY;
        if (isWithinFirstDay(totalMinutes)) {
            remainingMinutes = minutesAfterFreePeriod(remainingMinutes);
        }

        return fullDaysCost.add(partialDayCost(remainingMinutes));
    }

    /** Minutes that must be paid once the free period is discounted. */
    long minutesAfterFreePeriod(long minutes) {
        if (isWithinFreePeriod(minutes)) {
            return 0;
        }
        return minutes - FREE_MINUTES;
    }

    /** Cost of the given billable minutes, limited to the daily cap. */
    BigDecimal partialDayCost(long billableMinutes) {
        BigDecimal cost = PRICE_PER_MINUTE.multiply(BigDecimal.valueOf(billableMinutes));
        if (exceedsDailyCap(cost)) {
            return DAILY_CAP;
        }
        return cost;
    }

    // The condition checks below are package-private so tests can call them directly.

    /** True if the whole stay is shorter than one day (24 hours). */
    boolean isWithinFirstDay(long totalMinutes) {
        return totalMinutes < MINUTES_PER_DAY;
    }

    /** True if the minutes are still inside the free period (30 minutes or less). */
    boolean isWithinFreePeriod(long minutes) {
        return minutes <= FREE_MINUTES;
    }

    /** True if the cost is greater than the daily cap. */
    boolean exceedsDailyCap(BigDecimal cost) {
        return cost.compareTo(DAILY_CAP) > 0;
    }
}
