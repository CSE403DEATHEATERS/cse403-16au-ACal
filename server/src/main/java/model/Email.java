package model;

import javax.mail.Address;
import java.util.List;
import java.util.UUID;

/**
 * Created by tongshen on 11/2/16.
 */
public class Email {
    private String type;
    private String title;
    private String htmlContent;
    private Address[] recipients;
    private Address sender;

    public Email(String type, String title, String content, Address sender, Address[] recipients) {
        this.type = type;
        this.sender = sender;
        this.recipients = recipients;
        this.buildTitle(title);
        this.buildContent(content);
    }

    private void buildTitle(String title) {
        this.title = title;
    }

    private void buildContent(String content) {
         this.htmlContent = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public Address getSender() {
        return this.sender;
    }

    public Address[] getRecipients() {
        return this.recipients;
    }
}