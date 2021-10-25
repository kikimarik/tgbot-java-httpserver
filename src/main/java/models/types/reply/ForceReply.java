package models.types.reply;

import models.types.ReplyMarkup;

public class ForceReply extends ReplyMarkup {
    public String input_field_placeholder;

    public ForceReply() {
        this.force_reply = true;
    }
    public ForceReply(String placeholder) {
        this.force_reply = true;
        this.input_field_placeholder = placeholder;
    }
}
