package com.acalendar.acal.LambdaInvoker;
import com.acalendar.acal.Login.LoginInput;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/*
 * A holder for lambda functions
 */
public interface LambdaInterface {

    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    String login(LoginInput loginInput);

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    @LambdaFunction(functionName = "login")
    void noEcho(LoginInput loginInput);
}