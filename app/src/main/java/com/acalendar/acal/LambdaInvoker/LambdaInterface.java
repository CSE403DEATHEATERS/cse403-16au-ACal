package com.acalendar.acal.LambdaInvoker;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/*
 * A holder for lambda functions
 */
public interface LambdaInterface {

    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    String echo(NameInfo nameInfo);

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    @LambdaFunction(functionName = "echo")
    void noEcho(NameInfo nameInfo);
}