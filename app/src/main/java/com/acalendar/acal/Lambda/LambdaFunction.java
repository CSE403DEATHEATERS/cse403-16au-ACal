package com.acalendar.acal.Lambda;

import com.acalendar.acal.Login.Account;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;


/**
 * Created by tongshen on 11/9/16.
 */
/*
 * A holder for lambda functions
 */
public interface LambdaFunction {

    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    String login(Account nameInfo);

    /**
     * Invoke lambda function "echo". The functionName in the annotation
     * overrides the default which is the method name
     */
    @LambdaFunction(functionName = "echo")
    void noEcho(NameInfo nameInfo);
}