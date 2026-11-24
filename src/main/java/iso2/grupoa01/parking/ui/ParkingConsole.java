package iso2.grupoa01.parking.ui;

import iso2.grupoa01.parking.domain.InvalidStayException;
import iso2.grupoa01.parking.domain.ParkingFeeCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Runs the console program: asks for the ticket times, calculates the cost
 * with the domain logic and shows the result or the error.
 *
 * <p>All dependencies are received in the constructor, so each one can be
 * replaced in tests.
 */
public class ParkingConsole {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final ParkingFeeCalculator calculator;

    public ParkingConsole(ConsoleInput input, ConsoleOutput output, ParkingFeeCalculator calculator) {
        this.input = input;
        this.output = output;
        this.calculator = calculator;
    }

    /** Asks for the entry and exit times and shows the cost of the stay. */
    public void run() {
        output.showMessage("=== Parking cost calculator ===");
        LocalDateTime entry = askDateTime("Entry time");
        LocalDateTime exit = askDateTime("Exit time");
        try {
            output.showCost(calculator.calculateCost(entry, exit));
        } catch (InvalidStayException e) {
            output.showError(e.getMessage());
        }
    }

    /** Keeps asking until the user types a valid date/time. */
    private LocalDateTime askDateTime(String label) {
        while (true) {
            output.showPrompt(label + " (" + ConsoleInput.DATE_TIME_PATTERN + "): ");
            try {
                return input.readDateTime();
            } catch (DateTimeParseException e) {
                output.showError("invalid date/time, use the format " + ConsoleInput.DATE_TIME_PATTERN);
            }
        }
    }
}
