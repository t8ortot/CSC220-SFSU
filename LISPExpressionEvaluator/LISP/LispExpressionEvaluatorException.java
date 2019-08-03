package LISP;

/**
* Used by LispExpressionEvaluator to throw errors with messages
* @author James Clark
*/
public class LispExpressionEvaluatorException extends RuntimeException
{
	// Error with no message
    public LispExpressionEvaluatorException()
    {
	this("");
    }

    //Error with message
    public LispExpressionEvaluatorException(String errorMsg) 
    {
	super(errorMsg);
    }

}
