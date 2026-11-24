package iso2.grupoa01.parking.ui;

import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Writes messages and results to the console.
 *
 * <p>The output stream is received in the constructor, so tests can capture
 * what is printed.
 */
public class ConsoleOutput {

    private final PrintStream out;

    public ConsoleOutput(PrintStream out) {
        this.out = out;
    }

    /** Prints a message on its own line. */
    public void showMessage(String message) {
        out.println(message);
    }

    /** Prints a question and leaves the cursor on the same line for the answer. */
    public void showPrompt(String prompt) {
        out.print(prompt);
    }

    /** Prints the total cost of the stay. */
    public void showCost(BigDecimal cost) {
        out.println("Total cost: " + cost);
    }

    /** Prints an error message. */
    public void showError(String message) {
        out.println("Error: " + message);
    }
}
