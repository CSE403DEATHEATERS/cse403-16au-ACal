package model;

import java.util.List;
import java.util.UUID;

/**
 * Created by tongshen on 11/2/16.
 */
public class Email {
    private String type;
    private String title;
    private String htmlContent;
    private List<String> recipients;
    private String sender;

    public Email(String type) {
        this.type = type;
    }

    private String buildTitle() {
        return "";
    }

    private String buildContent() {
        return "";
    }
}