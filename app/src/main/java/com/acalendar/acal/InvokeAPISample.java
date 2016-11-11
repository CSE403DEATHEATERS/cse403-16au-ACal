package com.acalendar.acal;

import android.net.UrlQuerySanitizer;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.acalendar.acal.amazonaws.mobile.AWSMobileClient;
import com.acalendar.acal.amazonaws.mobile.api.CloudLogicAPI;
import com.acalendar.acal.amazonaws.mobile.api.CloudLogicAPIConfiguration;
import com.acalendar.acal.amazonaws.mobile.api.CloudLogicAPIFactory;
import com.acalendar.acal.amazonaws.mobile.util.ThreadUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvokeAPISample {
    public static void main(String[] args) {
        invokeAPI("POST", "/login", "");
    }

    public static void invokeAPI(String method, String path, String body) {
        // Set your request method, path, query string parameters, and request body
//        method = "POST";
//        path = "/login";
//        body = "{\"myfriend\":\"hehe\"}";
        Map<String, String> queryStringParameters = new HashMap<String, String>();
        Map<String, String> headers = new HashMap<String, String>();
        CloudLogicAPIConfiguration[] apiConfigurations = CloudLogicAPIFactory.getAPIs();

        final CloudLogicAPI client =
                AWSMobileClient.defaultMobileClient().createAPIClient(apiConfigurations[0].getClientClass());

        final byte[] content = body.getBytes(StringUtils.UTF8);

        ApiRequest tmpRequest =
                new ApiRequest(client.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(queryStringParameters);

        final ApiRequest request;

        // Only set body if it has content.
        if (body.length() > 0) {
            request = tmpRequest
                    .addHeader("Content-Length", String.valueOf(content.length))
                    .withBody(content);
        } else {
            request = tmpRequest;
        }

        // Make network call on background thread
        new Thread(new Runnable() {
            Exception exception = null;

            @Override
            public void run() {
                try {
//                    Log.d(LOG_TAG, "Invoking API w/ Request : " + request.getHttpMethod() + ":" + request.getPath());

                    long startTime = System.currentTimeMillis();

                    final ApiResponse response = client.execute(request);

                    final String responseData = IOUtils.toString(response.getContent());

                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();

    }
}