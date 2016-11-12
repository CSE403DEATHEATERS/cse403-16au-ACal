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

    public static String apiResult;

    public static void main(String[] args) {
    }

    public static String invokeAPI(String method, String path, String body, Map<String, String> query) {
        Map<String, String> headers = new HashMap<String, String>();
        CloudLogicAPIConfiguration[] apiConfigurations = CloudLogicAPIFactory.getAPIs();


        final CloudLogicAPI client =
                AWSMobileClient.defaultMobileClient().createAPIClient(apiConfigurations[0].getClientClass());

        ApiRequest tempRequest =
                new ApiRequest(client.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(query);

        final ApiRequest request;
        final byte[] content = body.toString().getBytes(StringUtils.UTF8);
        if (body != null) {
            request = tempRequest.addHeader("Content-Length", String.valueOf(content.length))
                    .withBody(content);
        } else {
            request = tempRequest;
        }

        // Make network call on background thread
        Runnable run = new Runnable() {
            Exception exception = null;
            @Override
            public void run() {
                try {
                    final ApiResponse response = client.execute(request);
                    Log.v("run", "response" + response.getContent());
                    final String responseData = IOUtils.toString(response.getContent());
                    Log.v("run", "responseData" + responseData);

                    apiResult = responseData;
                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(run);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            Exception exception = null;
//            @Override
//            public void run() {
//                try {
//                    final ApiResponse response = client.execute(request);
//                    Log.v("run", "response" + response.getContent());
//                    final String responseData = IOUtils.toString(response.getContent());
//                    Log.v("run", "responseData" + responseData);
//
//                    apiResult = responseData;
//                } catch (final Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }).start();
        return apiResult;
    }


}