package models.types;

public class KeyboardButton extends AbstractKeyboardButton {
    public Boolean request_contact;
    public Boolean request_location;
    public KeyboardButtonPollType request_poll;

    public KeyboardButton(String text) {
        super(text);
    }

    public KeyboardButton(String text, String pollType) {
        super(text);
        this.request_poll = new KeyboardButtonPollType(pollType);
    }
}
