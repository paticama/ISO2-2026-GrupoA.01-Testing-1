# ISO2-2026-GrupoA.01-Testing-1
Testing problem 1: Antonio &amp; Patricia

**ET.02.02 First Problem.** Step 1 (implement the program) was done by Pair 1: Antonio & Patricia.

The program takes the entry and exit times of a parking ticket and returns the cost of the stay.

## Business rules

- The first **30 minutes** of the stay are free.
- After that, every minute costs **0.05**.
- Each day costs at most **20**.
- An exception (`InvalidStayException`) is thrown if the entry or exit time is `null`,
  or if the exit time is earlier than the entry time. The check is done in the
  `ParkingStay` constructor.

## Design decisions

The statement leaves some points open. We decided:

| Question | Decision |
|---|---|
| Which minutes are charged after the free period? | Only the minutes after the first 30. A 31-minute stay costs 0.05. |
| What is a "day" for the cap of 20? | A block of 24 hours counted from the entry time. |
| Stays longer than one day | Each complete 24-hour block costs 20. The minutes left over cost 0.05 each, up to 20. |
| Are the 30 free minutes applied every day? | No, only once, at the start of the stay. |
| Seconds | Only whole minutes are charged; leftover seconds are free. The console only accepts minutes. |
| Entry time equal to exit time | Allowed. The cost is 0.00. |
| Money type | `BigDecimal`, always with 2 decimal places, to avoid rounding errors. |

### Examples

| Stay length | Cost |
|---|---|
| 0 min | 0.00 |
| 30 min | 0.00 |
| 31 min | 0.05 |
| 1 h (60 min) | 1.50 |
| 7 h 10 min (430 min) | 20.00 (400 min × 0.05, exactly the cap) |
| 7 h 11 min (431 min) | 20.00 (capped) |
| 24 h (1440 min) | 20.00 |
| 24 h 1 min (1441 min) | 20.05 |
| 48 h (2880 min) | 40.00 |

## Project structure

Domain logic and user interface are separated. The `domain` package never uses the console.

```
src/main/java/iso2/grupoa01/parking/
├── App.java                      Entry point: creates the objects and starts the program
├── domain/
│   ├── ParkingStay.java          Entry and exit times; validates them in the constructor
│   ├── ParkingFeeCalculator.java calculateCost(entry, exit): applies the business rules
│   └── InvalidStayException.java Exception for invalid stays
└── ui/
    ├── ConsoleInput.java         Reads dates from the console (Spanish format dd/MM/yyyy HH:mm)
    ├── ConsoleOutput.java        Writes messages, results and errors
    └── ParkingConsole.java       Asks for the times and shows the cost or the error
```

Notes for testing:

- Every condition is a small method that returns `boolean` (for example
  `isExitBeforeEntry`, `isWithinFreePeriod`, `exceedsDailyCap`). They are
  package-private, so tests in the same package can call them directly.
- `ConsoleInput` and `ConsoleOutput` receive the input/output streams in their
  constructors, so tests can use text instead of the keyboard and capture what is printed.

## Build and run

No Maven installation is needed; the Maven Wrapper (`mvnw`) downloads it the first time.

```bash
./mvnw clean verify                                          # compile, run tests and build the jar
java -jar target/ISO2-2026-GrupoA.01-Testing-1-1.0.0-SNAPSHOT.jar
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

Example session:

```
=== Parking cost calculator ===
Entry time (dd/MM/yyyy HH:mm): 15/12/2026 09:00
Exit time (dd/MM/yyyy HH:mm): 15/12/2026 10:00
Total cost: 1.50
```

Reports after `./mvnw test`:

- Test results (Surefire): `target/surefire-reports/`
- Coverage (JaCoCo): `target/site/jacoco/index.html`
