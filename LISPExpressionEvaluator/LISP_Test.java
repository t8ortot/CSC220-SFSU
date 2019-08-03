import LISP.*;

import java.util.*;

/**
 * Simple test program which allows user to input Lisp expression string
 * To terminate, type "exit"
 *
 * @author James Clark
 */
public class LISP_Test {

    /**
    * Runs test UI
    */
    public static void main(String args[]) {
        
        // create a LispExpressionEvaluator object
        LispExpressionEvaluator expr = new LispExpressionEvaluator();

        // scan input expr string
        Scanner scanner = new Scanner(System.in);

        // current expr string, result and ID number
        String inputExpr;
        double result;
        int i = 0;

        do {
            try {
                System.out.print("\nInput an expression string:");

                // scan next input line
                inputExpr = scanner.nextLine();

                // exit
                if (inputExpr.equals("exit"))
                    break;

                // increments the expression ID number
                i++;
                // Print input
                System.out.println("Evaluate expression #" + i + " :" + inputExpr);
                // Evaluate expression
                expr.reset(inputExpr);
                result = expr.evaluate();
                // Output result
                System.out.printf("Result : %.2f\n", result);

            }                                                 
            catch (LispExpressionEvaluatorException e) {
                System.err.printf("\nException: %s\n", e);
            }                                           

        } while (true);                       
    }

}
