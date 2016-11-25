package TwoPhaseLock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sushant,Pragya & Jaison on 23-Nov-16.
 * To create a set of input transactions.
 * XN (&);
 * X is state, N is transaction number, & is data item locked.
 */
public class inputGenerator {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of Transactions!");
        int noOfTrans = s.nextInt();
        char[] state = {'b', 'r', 'w', 'e'};
        int[] tNumber = new int[noOfTrans];
        for (int i = 0; i < noOfTrans; i++) {
            tNumber[i] = i + 1;
            //System.out.println(tNumber[i]);
        }
        int[] startChck = new int[noOfTrans];
        int[] endChck = new int[noOfTrans];
        char[] item = {'X', 'Y', 'Z'};
        int iS, iT = 0, iI;
        boolean chck = true;
        Random r = new Random();

        try {
            FileWriter f = new FileWriter("tcts.txt");
            BufferedWriter bW = new BufferedWriter(f);
            for (int i = 0; i < 20; i++) {

                chck = true;
                iS = r.nextInt(100) % 4;
                while (chck) {
                    System.out.println(i);
                    iT = r.nextInt(10) % noOfTrans;
                    if (startChck[iT] != 1 || startChck[iT] != 2) {
                        if (state[iS] == 'b') {
                            if (startChck[iT] != 2)
                                startChck[iT] = 1;
                            chck = false;
                        } else {
                            continue;
                        }
                    } else
                        break;
                }
                iI = r.nextInt(100) % 3;
                if (state[iS] == 'b' && startChck[iT] != 2) {
                    startChck[iT] = 2;
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
