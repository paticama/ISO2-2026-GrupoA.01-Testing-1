package iso2.grupoa01.parking;

import iso2.grupoa01.parking.domain.ParkingFeeCalculator;
import iso2.grupoa01.parking.ui.ConsoleInput;
import iso2.grupoa01.parking.ui.ConsoleOutput;
import iso2.grupoa01.parking.ui.ParkingConsole;

/**
 * Entry point of the parking cost calculator (ET.02.02 First Problem).
 *
 * <p>Its only job is to create the objects, connect the console user
 * interface ({@code ui} package) with the domain logic ({@code domain}
 * package) and start the program.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        ParkingConsole console = new ParkingConsole(
                new ConsoleInput(System.in),
                new ConsoleOutput(System.out),
                new ParkingFeeCalculator());
        console.run();
    }
}
