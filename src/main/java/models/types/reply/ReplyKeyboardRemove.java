package models.types.reply;

import models.types.ReplyMarkup;

public class ReplyKeyboardRemove extends ReplyMarkup {
    public ReplyKeyboardRemove() {
        this.remove_keyboard = true;
    }
}
