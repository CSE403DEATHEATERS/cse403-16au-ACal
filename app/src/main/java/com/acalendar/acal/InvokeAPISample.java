package com.acalendar.acal;

import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.acalendar.acal.amazonaws.mobile.AWSMobileClient;
import com.acalendar.acal.amazonaws.mobile.api.CloudLogicAPI;
import com.acalendar.acal.amazonaws.mobile.api.id84kwqa3pde.LambdaMicroserviceClient;
import com.acalendar.acal.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.AmazonClientException;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class InvokeAPISample {

    public static void main(String[] args) {
    }

    public void invokeAPI(String method, String path, String body,
                          Map<String, String> queryStringParameters, Map<String, String> headers) {
        // Set your request method, path, query string parameters, and request body
        method = "POST";
        path = "/login";
        body = "{\"myfriend\":\"hehe\"}";
        queryStringParameters = new HashMap<String, String>();
        headers = new HashMap<String, String>();

        final byte[] content = body.getBytes(StringUtils.UTF8);

        // Create an instance of your custom SDK client
        final AWSMobileClient mobileClient = AWSMobileClient.defaultMobileClient();
        final CloudLogicAPI client = mobileClient.createAPIClient(LambdaInvokerFactory.class);

        // Construct the request
        final ApiRequest request =
                new ApiRequest(client.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Content-Length", String.valueOf(content.length))
                        .withParameters(queryStringParameters)
                        .withBody(content);



        // Make network call on background thread
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    // Invoke the API
                    final ApiResponse response = client.execute(request);

                    final int statusCode = response.getStatusCode();
                    final String statusText = response.getStatusText();

//                    Log.d(LOG_TAG, "Response Status: " + statusCode + " " + statusMessage);

                    // TODO: Add your custom handling for server response status codes (e.g., 403 Forbidden)

                } catch (final AmazonClientException exception) {
//                    Log.e(LOG_TAG, exception.getMessage(), exception);

                    // TODO: Put your exception handling code here (e.g., network error)
                }
            }
        }).start();
    }
}