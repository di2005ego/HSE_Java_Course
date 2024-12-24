package Lab2;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the name of the input file:");
            String inName = scanner.nextLine();
            System.out.println("Enter the name of the output file for the frequency dictionary:");
            String outName = scanner.nextLine();
            try {
                FileProcessor fileProcer = new FileProcessor();
                fileProcer.createDict(inName, outName);
            }
            catch (IOException exc) {
                System.err.println("Error: " + exc.getMessage());
            }
        }
    }
}
