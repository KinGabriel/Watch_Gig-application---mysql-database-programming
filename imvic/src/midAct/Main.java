package midAct;
/**
 * Information Management Activity #2
 * Team Name: IMVic
 * Date: 04/08/2024
 *
 * Team Members:
 * 1. Bagsan, Lei Ceasar
 * 2. Caparas, Joaquin Gabriel
 * 3. Carino, Mark Lorenz
 * 4. De Mesa, Rovic Louie
 * 5. Sin, Lawrence Edward
 * 6. Usi, Ma. Angela Shane Lobo
 * 7. Vergara, Carlos Miguel
 *
 */

import java.sql.SQLException;
/**
 * Main method to start the program.
 *
 * @throws SQLException If an SQL exception occurs.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        // Initialize a Controller object to run the program
        Controller run = new Controller();
        // Call the run method of the Controller object
        run.run();
    }
}
