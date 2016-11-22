package TwoPhaseLock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Sushant,Pragya & Jaison on 23-Nov-16.
 * To create a set of input transactions.
 * XN (&);
 * X is state, N is transaction number, & is data item locked.
 */
public class inputGenerator {
    public static void main(String[] args) {
        char[] state = {'b', 'r', 'w', 'e'};
        int[] tNumber = {1, 2, 3, 4, 5};
        char[] item = {'X', 'Y', 'Z'};
        int iS, iT, iI;

        Random r = new Random();

        try {
            FileWriter f = new FileWriter("tcts.txt");
            BufferedWriter bW = new BufferedWriter(f);
            for (int i = 0; i < 20; i++) {
                iS = r.nextInt(4);
                iT = r.nextInt(5);
                iI = r.nextInt(3);
                if (state[iS] == 'b') {
                    bW.write(String.valueOf(state[iS]) + String.valueOf(tNumber[iT]) + ";");
                    bW.newLine();
                } else {
                    bW.write(String.valueOf(state[iS]) + String.valueOf(tNumber[iT]) + " (" + String.valueOf(item[iI]) + ");");
                    bW.newLine();
                }
                bW.flush();
            }
        } catch (IOException E) {
            System.out.println(E.toString());
        }

    }
}
