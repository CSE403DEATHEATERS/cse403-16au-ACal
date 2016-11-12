package com.acalendar.acal;

import android.net.UrlQuerySanitizer;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.acalendar.acal.Login.LoginActivity;
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
    public static String apiResponse;

    public static void main(String[] args) {
    }

    public static String invokeAPI(String method, String path, String body, String query) {
        Map<String, String> queryStringParameters = convertQueryStringToParameters(query);
        Map<String, String> headers = new HashMap<String, String>();
        final CloudLogicAPIConfiguration[] apiConfigurations = CloudLogicAPIFactory.getAPIs();

        final byte[] content = body.getBytes(StringUtils.UTF8);

        final CloudLogicAPI client =
                AWSMobileClient.defaultMobileClient().createAPIClient(apiConfigurations[0].getClientClass());

        final ApiRequest tmpRequest =
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

                    final ApiResponse response = client.execute(request);

                    final String responseData = IOUtils.toString(response.getContent());
                    apiResponse = responseData;

                } catch (final Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
        return apiResponse;
    }

    private static Map<String,String> convertQueryStringToParameters(String queryStringText) {

        while (queryStringText.startsWith("?") && queryStringText.length() > 1) {
            queryStringText = queryStringText.substring(1);
        }

        final UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseQuery(queryStringText);

        final List<UrlQuerySanitizer.ParameterValuePair> pairList = sanitizer.getParameterList();
        final Map<String, String> parameters = new HashMap<>();

        for (final UrlQuerySanitizer.ParameterValuePair pair : pairList) {
            Log.d("LOG", pair.mParameter + " = " + pair.mValue);
            parameters.put(pair.mParameter, pair.mValue);
        }

        return parameters;
    }
}