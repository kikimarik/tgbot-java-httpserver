package models.types;

public abstract class AbstractKeyboardButton {
    public String text;

    AbstractKeyboardButton() {}

    AbstractKeyboardButton(String text) {
        this.text = text;
    }
}
