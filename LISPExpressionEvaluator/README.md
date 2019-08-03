# LISP Expression Evaluator

A LISP expression evaluator. In the language Lisp, each of the four basic arithmetic operators appears before an arbitrary number of operands, which are separated by spaces. The resulting expression is enclosed in parentheses. The operators behave as follows:
- (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
- (- a b c ...) returns a - b - c - ..., and (- a) returns -a.
- (* a b c ...) returns the product of all the operands, and (* a) returns a.
- (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a.

Note: + * - / must have at least one operand

You can form larger arithmetic expressions by combining these basic expressions using a fully parenthesized prefix notation. For example, the following is a valid Lisp expression:
- (+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))

This expression is evaluated successively as follows:
1. (+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 1))
2. (+ -6 24 -1.5 1)
3. 17.5

Learned how to use Stacks, 
How to run (assuming in LISPExpressionEvaluator directory):
- javac LISP_Test.java
- java LISP_Test

How to use:
- Enter an LISP expression following the guidelines of the project description above.
- The program will return the result to the console