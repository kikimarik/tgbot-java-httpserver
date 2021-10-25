package models.types;

public class InlineKeyboardButton extends AbstractKeyboardButton {
    public String callback_data;
    public String url;
    public LoginUrl login_url;
    public String switch_inline_query;
    public String switch_inline_query_current_chat;
    public CallbackGame callback_game;
    public Boolean pay;

    public InlineKeyboardButton() {}

    public InlineKeyboardButton(String text) {
        super(text);
    }

    public InlineKeyboardButton(String text, String callbackData) {
        super(text);
        this.callback_data = callbackData;
    }
}
