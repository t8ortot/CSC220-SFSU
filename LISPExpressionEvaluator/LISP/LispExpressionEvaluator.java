package LISP;
import java.util.*;

/**
 * A LISP expression evaluator
 * @author James Clark
 * @version 4/5/17
 */
public class LispExpressionEvaluator {

    // Current input Lisp expression
    private String inputExpr;

    // Main expression stack & current operation stack
    private Stack<Object> inputExprStack;
    private Stack<Double> evaluationStack;

    /**
    * Default constructor
    */
    public LispExpressionEvaluator() {
        inputExpr = "";
        inputExprStack = new Stack<>();
        evaluationStack = new Stack<>();

    }

    /**
    * Constructor with input
    * @param inputExpression expression to be evaulated
    */
    public LispExpressionEvaluator(String inputExpression) {
        //if none given
        if (inputExpression == null) {
            throw new LispExpressionEvaluatorException("No expression given!");
        }
        inputExpr = inputExpression;
        inputExprStack = new Stack<>();
        evaluationStack = new Stack<>();
    }

    /**
    * Resets "this" object with new expression
    * @param inputExpression new expression to be used
    */
    public void reset(String inputExpression) {
        // if none given
        if (inputExpression == null) {
            throw new LispExpressionEvaluatorException("No expression given!");
        }
        inputExpr = inputExpression;
        inputExprStack.clear();
        evaluationStack.clear();
    }

    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    //      Pop operands from inputExprStack and push them onto 
    //          evaluationStack until you find an operator
    //      
    //          Push the result into inputExprStack
    //

    /**
    * Evaluates current operator with its operands. See complete algorithm in evaluate()
    */
    private void evaluateCurrentOperation() {
        //if input stack is empty
        if (inputExprStack.isEmpty()) {
            throw new LispExpressionEvaluatorException("The inputExprStack is empty!");
        }

        //Pop operand
        double result;
        Object expr = inputExprStack.pop();

        // if no operand popped
        if (!expr.equals(expr.toString())) {
            throw new LispExpressionEvaluatorException("No values to compute!");
        }

        try {
            // push operands into evaluation stack until operator is found
            while (expr.equals(expr.toString())) {
                evaluationStack.push(Double.parseDouble(expr.toString()));
                expr = inputExprStack.pop();
            }
        } catch (EmptyStackException e) {
            throw new LispExpressionEvaluatorException("The stack is empty!");
        }

        // Apply the operator to the operands on evaluationStack, then push result on inputExprStack
        String op = expr.toString();
        switch (op) {
            // Addition
            case "+":
                result = evaluationStack.pop();
                while (!evaluationStack.isEmpty()) {
                    result += evaluationStack.pop();
                }
                inputExprStack.push(Double.toString(result));
                break;
            // Subtraction
            case "-":
                result = evaluationStack.pop();
                if (evaluationStack.isEmpty()) {
                    result *= -1;
                }
                    while (!evaluationStack.isEmpty()) {
                        result -= evaluationStack.pop();
                    }
                     inputExprStack.push(Double.toString(result));
                    break;
            // Multiplication    
            case "*":
                result = evaluationStack.pop();
                while (!evaluationStack.isEmpty()) {
                    result *= evaluationStack.pop();
                }
                inputExprStack.push(Double.toString(result));
                break;
            // Division
            case "/":
                result = evaluationStack.pop();
                if (evaluationStack.isEmpty()) {
                    result = (1 / result);
                }
                while (!evaluationStack.isEmpty()) {
                    result /= evaluationStack.pop();
                }
                inputExprStack.push(Double.toString(result));
                break;
        }
      
    }

    /**
    * Evaluates current LISP expression in inputExpr and returns the result.
    */
    public double evaluate() {

        // scanner to tokenize inputExpr
        Scanner inputExprScanner = new Scanner(inputExpr);

        // Use zero or more white space as delimiter, which breaks the string into single character tokens
        inputExprScanner = inputExprScanner.useDelimiter("\\s*");
        int open = 0;

        // Step 1: Scan the tokens in the string.
        while (inputExprScanner.hasNext()) {
 
            // Step 2: If you see an operand, push operand object onto the inputExprStack
            if (inputExprScanner.hasNextInt()) {
                if (open > 0){
                String dataString = inputExprScanner.findInLine("\\d+");
                inputExprStack.push(dataString);
                } else {
                    throw new LispExpressionEvaluatorException("Improper use of parentheses");
                }
            // If next character is an operator
            } else {
                // Get next token, only one char in string token
                String aToken = inputExprScanner.next();
                char item = aToken.charAt(0);
                
                // Determine operator action
                switch (item) {

                    // Step 3: If you see "(", next token shoube an operator
                    case '(':
                        open++;
                        break;

                    // Step 4: If you see an operator, push operator object onto the inputExprStack
                    case '+':
                        inputExprStack.push('+');
                        break;
                    case '-':
                        inputExprStack.push('-');
                        break;
                    case '*':
                        inputExprStack.push('*');
                        break;
                    case '/':
                        inputExprStack.push('/');
                        break;

                    // Step 5: If you see ")"  
                    case ')':
                       open--;
                       // steps in evaluateCurrentOperation()
                        evaluateCurrentOperation();
                        break;
                    default:
                        throw new LispExpressionEvaluatorException(item + " is not a legal expression operator");
                }
            }
        }

        // Step 9: If you run out of tokens, the value on the top of inputExprStack is
        //         is the result of the expression.
        //
        //         return result
        
        return Double.parseDouble(inputExprStack.pop().toString());
        
    }

    
    /**
    * Used by "this" main function for testing
    * @param s String of input expression
    * @param expr LispExpressionEvaluator object to use for testing
    * @param expect Expected output
    */
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect) {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
        expr.reset(s);
        try {
            result = expr.evaluate();
            System.out.printf("Evaluated result : %.2f\n", result);
        } catch (LispExpressionEvaluatorException e) {
            System.out.println("Evaluated result :" + e);
        }

        System.out.println("-----------------------------");
    }

    /**
    * Defines a few test cases to run
    */
    public static void main(String args[]) {
        LispExpressionEvaluator expr = new LispExpressionEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 0))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )) (/ 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";

        evaluateExprTest(test1, expr, "16.50");
        evaluateExprTest(test2, expr, "-378.12");
        evaluateExprTest(test3, expr, "4.50");
        evaluateExprTest(test4, expr, "1.50");
        evaluateExprTest(test5, expr, "Infinity or LispExpressionEvaluatorException");
        evaluateExprTest(test6, expr, "LispExpressionEvaluatorException");
        evaluateExprTest(test7, expr, "LispExpressionException");
        evaluateExprTest(test8, expr, "LispExpressionException");
    }
}
