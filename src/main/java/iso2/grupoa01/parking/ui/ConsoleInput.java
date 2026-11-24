package iso2.grupoa01.parking.ui;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Reads the user's input from the console.
 *
 * <p>The input stream is received in the constructor, so tests can pass
 * their own text instead of the keyboard.
 */
public class ConsoleInput {

    /** Spanish date/time format the user must type, e.g. 15/12/2026 09:30. */
    public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

    // "uuuu" is the year in STRICT mode, which rejects dates like 30/02/2026.
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner scanner;

    public ConsoleInput(InputStream in) {
        this.scanner = new Scanner(in);
    }

    /**
     * Reads one line and converts it to a date/time.
     *
     * @throws DateTimeParseException if the text does not follow {@link #DATE_TIME_PATTERN}
     */
    public LocalDateTime readDateTime() {
        String text = scanner.nextLine().trim();
        return LocalDateTime.parse(text, FORMATTER);
    }
}
