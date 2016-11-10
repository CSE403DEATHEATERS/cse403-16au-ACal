package lambda;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;

import java.util.Map;

/**
 *
 */
public class EventHandler {
    public Map<String, AttributeValue> createEvent(Map<String, AttributeValue> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
