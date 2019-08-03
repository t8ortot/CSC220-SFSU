package FC;

/**
 * This class represents a fraction whose numerator and denominator are integers.
 *
 * @author James Clark
 * @version 3/4/17
 */

public class SimpleFraction implements SimpleFractionInterface, Comparable<SimpleFraction> {

    // numerator and denominator
    private int num;
    private int den;

    // constructor
    public SimpleFraction() {
        // default = 0/1
        num = 0;
        den = 1;
    }

    /**
     * Constructor
     *
     * @param num numerator
     * @param den denominator
     */
    public SimpleFraction(int num, int den) {
        setSimpleFraction(num, den);
    }

    /**
     * Sets "this" fraction to a given value.
     *
     * @param num is the integer numerator
     * @param den is the integer denominator
     * @throw SimpleFractionException if denominator is 0
     */
    public void setSimpleFraction(int num, int den) {

        // if the denominator is NOT equal to 0
        if (den != 0) {
            this.num = num;
            this.den = den;

            // if negative is in denominator, move to numerator
            if (this.den < 0) {
                this.num *= -1;
                this.den *= -1;
            }
        // if the denominator is 0, throw an error message
        } else if (den == 0) {
            throw new SimpleFractionException("The denominator is equal to zero!");
        }

    }

    /**
     * Reduce "this" fraction to lowest term, i.e divide the numerator and denominator by their Greatest Common Divisor
     */
    public void simplifySimpleFraction() {
        reduceSimpleFractionToLowestTerms();
    }

    /**
     * Compute floating value of "this" fraction
     *
     * @return the double floating point value of a fraction
     */
    public double toDouble() {
        // return double floating point value
        double value = (double) num / (double) den;
        return value;
    }

    /**
     * Adds two fractions.
     *
     * @param secondFraction is a fraction that is the second operand of the addition
     * @return a new reduced fraction which is the sum of "this" fraction and the secondFraction
     */
    public SimpleFractionInterface add(SimpleFractionInterface secondFraction) {

        // a/b + c/d is (ad + cb)/(bd)
        int tempN = (num * ((SimpleFraction) secondFraction).den) + (((SimpleFraction) secondFraction).num * den);
        int tempD = den * ((SimpleFraction) secondFraction).den;

        // return result which is a new reduced SimpleFraction object
        SimpleFraction result = new SimpleFraction(tempN, tempD);
        result.reduceSimpleFractionToLowestTerms();
        return result;
    }

    /**
     * Subtracts two fractions.
     *
     * @param secondFraction a fraction that is the second operand of the subtraction
     * @return a new reduced fraction which is the difference of "this" fraction and the second operand
     */
    public SimpleFractionInterface subtract(SimpleFractionInterface secondFraction) {

        // a/b - c/d is (ad - cb)/(bd)
        int tempN = (num * ((SimpleFraction) secondFraction).den) - (((SimpleFraction) secondFraction).num * den);
        int tempD = den * ((SimpleFraction) secondFraction).den;


        // return result which is a new reduced SimpleFraction object
        SimpleFraction result = new SimpleFraction(tempN, tempD);
        result.reduceSimpleFractionToLowestTerms();
        return result;
    }

    /**
     * Multiplies two fractions.
     *
     * @param secondFraction a fraction that is the second operand of the multiplication
     * @return a new reduced fraction which is the product of "this" fraction and the secondFraction
     */
    public SimpleFractionInterface multiply(SimpleFractionInterface secondFraction) {

        // a/b * c/d is (ac)/(bd)
        int tempN = (num * ((SimpleFraction) secondFraction).num);
        int tempD = den * ((SimpleFraction) secondFraction).den;

        // return result which is a new reduced SimpleFraction object
        SimpleFraction result = new SimpleFraction(tempN, tempD);
        result.reduceSimpleFractionToLowestTerms();
        return result;
    }

    /**
     * Divides two fractions.
     *
     * @param secondFraction a fraction that is the second operand of the division
     * @return a new reduced fraction which the quotient of "this" fraction and the secondFraction
     * @throw SimpleFractionException if secondFraction is 0
     */
    public SimpleFractionInterface divide(SimpleFractionInterface secondFraction) {

        // a/b / c/d is (ad)/(bc)
        if (((SimpleFraction) secondFraction).den != 0 && den != 0) {
            int tempN = (num * ((SimpleFraction) secondFraction).den);
            int tempD = den * ((SimpleFraction) secondFraction).num;

            // return result which is a new reduced SimpleFraction object
            SimpleFraction result = new SimpleFraction(tempN, tempD);
            result.reduceSimpleFractionToLowestTerms();
            return result;

        // throw an error if secondFraction is 0
        } else {
            throw new SimpleFractionException("The denominator is equal to zero!");
        }
    }

    /**
     * Assess if the two fractions are equal.
     *
     * @param other other fraction object to be compared.
     */
    public boolean equals(Object other) {
        SimpleFraction fraction = (SimpleFraction) other;
        boolean equal = false;
        double n1 = (double) num;
        double d1 = (double) den;
        double n2 = (double) fraction.num;
        double d2 = (double) fraction.den;

        // if equal, return true.
        if (n1 / d1 == n2 / d2) {
            equal = true;
        }

        return equal;
    }

    /**
     * Compares this fraction to determine is it is less than, greater than, or equal to the other fraction
     *
     * @param other other fraction to be compared to
     */
    public int compareTo(SimpleFraction other) {
        double n1 = (double) num;
        double d1 = (double) den;
        double n2 = (double) other.num;
        double d2 = (double) other.den;

        // if greater than, return 1
        // if equal to, return 0
        // if less than, return -1
        if ((n1 / d1) > (n2 / d2)) {
            return 1;
        } else if ((n1 / d1) < (n2 / d2)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Converts fraction to a string
     */
    public String toString() {
        return num + "/" + den;
    }

    /**
     * Reduces a fraction to lowest terms.
     */
    private void reduceSimpleFractionToLowestTerms() {
        if (num != 0) {
            // calculates greatest common denominator
            int gcd = GCD(Math.abs(num), den);
            // divide the numerator and denominator by the greatest common denominator
            num /= gcd;
            den /= gcd;

            // if the negative ends up in the denominator, move to numerator
            if (den < 0) {
                den *= -1;
                num *= -1;
            }
        // if the denominator is 0, throw an error.
        } else if (den == 0) {
            throw new SimpleFractionException("The denominator is equal to zero!");
        }

    }

    /**
     * Computes the greatest common divisor of two integers with a
     * recursive method
     *
     * @param integerOne an integer
     * @param integerTwo another integer
     * @return the greatest common divisor of the two integers
     */
    private int GCD(int integerOne, int integerTwo) {
        int result;

        if (integerOne % integerTwo == 0) {
            result = integerTwo;
        } else {
            result = GCD(integerTwo, integerOne % integerTwo);
        }

        return result;
    }

    /**
     * Some tests for this class are given here
     */
    public static void main(String[] args) {
        SimpleFractionInterface firstOperand = null;
        SimpleFractionInterface secondOperand = null;
        SimpleFractionInterface result = null;
        double doubleResult = 0.0;

        System.out.println("\n=========================================\n");

        // simplify tests
        // simplify 12/20 to 3/5
        firstOperand = new SimpleFraction(12, 20);
        System.out.println("Fraction before simplification:\t\t" + firstOperand);
        System.out.println("\tExpected result :\t\t12/20\n");
        firstOperand.simplifySimpleFraction();
        System.out.println("\nFraction after simplification:\t\t" + firstOperand);
        System.out.println("\tExpected result :\t\t3/5\n");

        // simplify -20/40 to -1/2
        firstOperand = new SimpleFraction(20, -40);
        System.out.println("\nFraction before simplification:\t\t" + firstOperand);
        System.out.println("\tExpected result :\t\t-20/40\n");
        firstOperand.simplifySimpleFraction();
        System.out.println("\nFraction after simplification:\t\t" + firstOperand);
        System.out.println("\tExpected result :\t\t-1/2\n");

        // SimpleFraction creation tests
        // 9/16
        SimpleFraction nineSixteenths = new SimpleFraction(9, 16);
        // 1/4
        SimpleFraction oneFourth = new SimpleFraction(1, 4);

        System.out.println("\n=========================================\n");

        // Addition tests
        // 7/8 + 9/16 = 23/16
        firstOperand = new SimpleFraction(7, 8);
        result = firstOperand.add(nineSixteenths);
        System.out.println("The sum of " + firstOperand + " and "
                + nineSixteenths + " is \t\t" + result);
        System.out.println("\tExpected result :\t\t23/16\n");

        // -21/2 + 7/8
        firstOperand = new SimpleFraction(-21, 2);
        secondOperand = new SimpleFraction(7, 8);
        result = firstOperand.add(secondOperand);
        System.out.println("The sum of " + firstOperand
                + " and " + secondOperand + " is \t\t" + result);
        System.out.println("\tExpected result :\t\t-77/8\n");

        // Subtraction test
        // 9/16 - 7/8 = -5/16
        firstOperand = nineSixteenths;
        secondOperand = new SimpleFraction(7, 8);
        result = firstOperand.subtract(secondOperand);
        System.out.println("The difference of " + firstOperand
                + " and " + secondOperand + " is \t" + result);
        System.out.println("\tExpected result :\t\t-5/16\n");

        // Multiplication test
        // 15/-2 * 1/4 = -15/8
        firstOperand = new SimpleFraction(15, -2);
        result = firstOperand.multiply(oneFourth);
        System.out.println("The product of " + firstOperand
                + " and " + oneFourth + " is \t" + result);
        System.out.println("\tExpected result :\t\t-15/8\n");

        // Division test
        // (-21/2) / (3/7) = -49/2
        firstOperand = new SimpleFraction(-21, 2);
        secondOperand = new SimpleFraction(3, 7);
        result = firstOperand.divide(secondOperand);
        System.out.println("The quotient of " + firstOperand
                + " and " + secondOperand + " is \t" + result);
        System.out.println("\tExpected result :\t\t-49/2\n");

        // toDouble tests
        // 0/10 = 0.0
        firstOperand = new SimpleFraction(0, 10);
        doubleResult = firstOperand.toDouble();
        System.out.println("The double floating point value of " + firstOperand + " is \t" + doubleResult);
        System.out.println("\tExpected result \t\t\t0.0\n");

        // 5/(-15) = -0.3333...
        firstOperand = new SimpleFraction(5, -15);
        doubleResult = firstOperand.toDouble();
        System.out.println("The double floating point value of " + firstOperand + " is \t" + doubleResult);
        System.out.println("\tExpected result \t\t\t-0.333333333...\n");

        // (-22)/7 = -3.142857142857143
        firstOperand = new SimpleFraction(-22, 7);
        doubleResult = firstOperand.toDouble();
        System.out.println("The double floating point value of " + firstOperand + " is \t" + doubleResult);
        System.out.println("\tExpected result \t\t\t-3.142857142857143");
        System.out.println("\n=========================================\n");

        // Equality tests
        // -21/2 == -21/2: true
        firstOperand = new SimpleFraction(-21, 2);
        System.out.println("First = " + firstOperand);
        // equality check
        System.out.println("check First equals First: ");
        if (firstOperand.equals(firstOperand)) {
            System.out.println("Identity of fractions OK");
        } else {
            System.out.println("ERROR in identity of fractions");
        }

        // -42/4 == -42/4: true
        secondOperand = new SimpleFraction(-42, 4);
        System.out.println("\nSecond = " + secondOperand);
        System.out.println("check First equals Second: ");
        if (firstOperand.equals(secondOperand)) {
            System.out.println("Equality of fractions OK");
        } else {
            System.out.println("ERROR in equality of fractions");
        }

        // Comparison tests
        SimpleFraction first = (SimpleFraction) firstOperand;
        SimpleFraction second = (SimpleFraction) secondOperand;

        // -21/2 == -42/4
        System.out.println("\ncheck First compareTo Second: ");
        if (first.compareTo(second) == 0) {
            System.out.println("SimpleFractions == operator OK");
        } else {
            System.out.println("ERROR in fractions == operator");
        }

        // -21/2 < 7/8
        second = new SimpleFraction(7, 8);
        System.out.println("\nSecond = " + second);
        System.out.println("check First compareTo Second: ");
        if (first.compareTo(second) < 0) {
            System.out.println("SimpleFractions < operator OK");
        } else {
            System.out.println("ERROR in fractions < operator");
        }

        // 7/8 > -21/2
        System.out.println("\ncheck Second compareTo First: ");
        if (second.compareTo(first) > 0) {
            System.out.println("SimpleFractions > operator OK");
        } else {
            System.out.println("ERROR in fractions > operator");
        }

        System.out.println("\n=========================================");

        // denominator eqaul to 0 tests
        System.out.println("\ncheck SimpleFractionException: 1/0");
        try {
            SimpleFraction a1 = new SimpleFraction(1, 0);
            System.out.println("Error! No SimpleFractionException");
        } catch (SimpleFractionException fe) {
            System.err.printf("Exception: %s\n", fe);
        }
        System.out.println("Expected result : SimpleFractionException!\n");

        System.out.println("\ncheck SimpleFractionException: division");
        try {
            SimpleFraction a2 = new SimpleFraction();
            SimpleFraction a3 = new SimpleFraction(1, 2);
            a3.divide(a2);
            System.out.println("Error! No SimpleFractionException");
        } catch (SimpleFractionException fe) {
            System.err.printf("Exception: %s\n", fe);
        }
        System.out.println("Expected result : SimpleFractionException!\n");

    }
}

