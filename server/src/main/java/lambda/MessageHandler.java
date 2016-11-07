package lambda;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import model.*;

import java.math.BigDecimal;
import java.util.*;

public class MessageHandler {

    /**
     * Create a message and store it in our db
     *
     * @param request the request obejct used to construct the new record(s) in table(s)
     * @param context an Android specific param
     * @return a CreateMessageResponse containing the id of the record in the db
     */
    public CreateMessageResponse createMessage(CreateMessageRequest request, Context context) {
        validateCreateMessageRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        CreateMessageResponse response = helper.createMessage(request, context);
        return response;
    }

    /**
     * Retrieve messages associated with a specific event from db
     *
     * @param request an request object containing criteria used to retrieve records from db
     * @param context an Android specific param
     * @return a GetMessagesResponse containing a list of Messages matching the criteria
     *         specified by the request.
     */
    public GetMessagesResponse getMessages(GetMessagesRequest request, Context context) {
        validateGetMessagesRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        GetMessagesResponse response = helper.getMessages(request, context);
        return response;
    }

    private void validateCreateMessageRequest(CreateMessageRequest request) {
        if ((request == null) || (request.getEventId() == null) || (request.getMessageCategory() == null)
                || (request.getMessageContent() == null) || (request.getUserId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

    private void validateGetMessagesRequest(GetMessagesRequest request) {
        if ((request == null) || (request.getEventId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

}
