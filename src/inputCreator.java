import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Sushant on 14-Sep-16.
 * Input Creator
 * Writes the specified number of integers to a file.
 */
public class inputCreator {
    public static void createInput(String fileName, int maxNumber, int numberOfNodes) {
        /*
        Function to generate input, i.e. integers to be inserted into the tree

        fileName: String, filename to store the input for the tree.
        maxNumber: int, maximum number to be inserted into the tree.
        numberOfNode: int, number of integers to be generated to be inserted in the tree.

        returns: void
        */
        Random r = new Random();
        try {
            FileWriter f = new FileWriter(fileName);
            BufferedWriter bW = new BufferedWriter(f);
            for (int i = 0; i < numberOfNodes; i++) {
                bW.write(String.valueOf(r.nextInt(maxNumber) + 1));
                bW.newLine();
                bW.flush();
            }
        } catch (IOException E) {
            System.out.println(E.toString());
        }
    }
}
