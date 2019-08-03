import java.util.*;
import FC.*;

/**
 * This program is used to test PJ1.Fracition class
 * More info is given in Readme file
 *
 * @author James Clark
 */
class FC_Test {
    // Globally accessible scanner
    static private Scanner scanner;

    /**
     * Reads in, initializes, and returns a fraction
     */
    private static SimpleFraction readFraction() {
        System.out.print("\nTry to read a fraction x/y, please enter x y : ");
        int numerator = scanner.nextInt();
        int denominator = scanner.nextInt();
        SimpleFraction f = new SimpleFraction(numerator, denominator);
        System.out.println("\t\tRead OK:" + f);
        return f;
    }

    /**
     * Prints a available operations prompt
     */
    private static void printOperations() {
        System.out.println("==============================================");
        System.out.println("\nOperations:");
        System.out.println("  0) exit \t1) add \t\t2) subtract \t3) multiply \t4) divide");
        System.out.println("  5) compareTo \t6) equals \t7) simplifySimpleFraction t8) toDouble \t9) setSimpleFraction (x/y) ");
        System.out.print("\nEnter an operation number: ");
    }

    /**
     * Runs the main testing UI
     */
    public static void main(String args[]) {
        // scanner for input
        scanner = new Scanner(System.in);
        // determines if more input is needed
        boolean continueLoop = true;
        SimpleFraction n1 = null;
        SimpleFraction n2 = null;
        int op, x, y;

        do {
                                                                           
            try {
                // prints prompt
                printOperations();
                op = scanner.nextInt();
                // retrieves correct number of inputs depending on operation selected
                if (op == 0) {
                    break;
                } else if ((op > 0) && (op < 7)) {
                    n1 = readFraction();
                    n2 = readFraction();
                } else if ((op > 6) && (op < 9)) {
                    n1 = readFraction();
                } else if (op == 9) {
                    n1 = new SimpleFraction();
                } else {
                    System.out.print("\nInvalid input... try again\n");
                    continue;
                }

                // Performs tests operation selected
                System.out.println("\nTests:\n");
                switch (op) {
                    // Addition
                    case 1:
                        System.out.println("\t" + n1 + " + " + n1 + " = " + n1.add(n1));
                        System.out.println("\t" + n2 + " + " + n2 + " = " + n2.add(n2));
                        System.out.println("\t" + n1 + " + " + n2 + " = " + n1.add(n2));
                        System.out.println("\t" + n2 + " + " + n1 + " = " + n2.add(n1));
                        break;
                    // Subtraction
                    case 2:
                        System.out.println("\t" + n1 + " - " + n1 + " = " + n1.subtract(n1));
                        System.out.println("\t" + n2 + " - " + n2 + " = " + n2.subtract(n2));
                        System.out.println("\t" + n1 + " - " + n2 + " = " + n1.subtract(n2));
                        System.out.println("\t" + n2 + " - " + n1 + " = " + n2.subtract(n1));
                        break;
                    // Multiplication
                    case 3:
                        System.out.println("\t" + n1 + " * " + n1 + " = " + n1.multiply(n1));
                        System.out.println("\t" + n2 + " * " + n2 + " = " + n2.multiply(n2));
                        System.out.println("\t" + n1 + " * " + n2 + " = " + n1.multiply(n2));
                        System.out.println("\t" + n2 + " * " + n1 + " = " + n2.multiply(n1));
                        break;
                    // Division
                    case 4:
                        System.out.println("\t" + n1 + " / " + n1 + " = " + n1.divide(n1));
                        System.out.println("\t" + n2 + " / " + n2 + " = " + n2.divide(n2));
                        System.out.println("\t" + n1 + " / " + n2 + " = " + n1.divide(n2));
                        System.out.println("\t" + n2 + " / " + n1 + " = " + n2.divide(n1));
                        break;
                    // Compare to
                    case 5:
                        System.out.println("\t" + n1 + " compared to " + n1 + " = " + n1.compareTo(n1));
                        System.out.println("\t" + n2 + " compared to " + n2 + " = " + n2.compareTo(n2));
                        System.out.println("\t" + n1 + " compared to " + n2 + " = " + n1.compareTo(n2));
                        System.out.println("\t" + n2 + " compared to " + n1 + " = " + n2.compareTo(n1));
                        break;
                    // Equality
                    case 6:
                        System.out.println("\t" + n1 + " equals " + n1 + " = " + n1.equals(n1));
                        System.out.println("\t" + n2 + " equals " + n2 + " = " + n2.equals(n2));
                        System.out.println("\t" + n1 + " equals " + n2 + " = " + n1.equals(n2));
                        System.out.println("\t" + n2 + " equals " + n1 + " = " + n2.equals(n1));
                        break;
                    // Simplify
                    case 7:
                        System.out.print("\t" + n1);
                        n1.simplifySimpleFraction();
                        System.out.println(" simplifySimpleFraction " + n1);
                        break;
                    // Convert to double decimal
                    case 8:
                        System.out.println("\t" + n1 + " toDouble = " + n1.toDouble());
                        break;
                    // Fraction creation
                    case 9:
                        System.out.print("read a fraction x/y, please enter x y : ");
                        x = scanner.nextInt();
                        y = scanner.nextInt();
                        System.out.print("\t" + x + "/" + y + " setSimpleFraction = ");
                        n1.setSimpleFraction(x, y);
                        System.out.println(n1);
                        break;
                }

            }                                                   
            catch (SimpleFractionException e) {
                System.err.printf("\nFraction Exception: %s\n", e);
            }                                                 
        } while (continueLoop);                         
    }
} 






