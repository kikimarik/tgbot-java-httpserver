package models.types.reply;

import models.types.AbstractKeyboardButton;
import models.types.InlineKeyboardButton;
import models.types.ReplyMarkup;

import java.util.Arrays;

public class InlineKeyboardMarkup extends ReplyMarkup {
    public InlineKeyboardButton[][] inline_keyboard = new InlineKeyboardButton[1][];

    private int rowIndex = 0;

    public InlineKeyboardMarkup() {}
    public InlineKeyboardMarkup(AbstractKeyboardButton[] buttons) {
        assert false;
        this.resetButtons(buttons);
    }

    public void addButton(AbstractKeyboardButton button) {
        if (this.inline_keyboard[this.rowIndex] == null) {
            this.inline_keyboard[this.rowIndex] = new InlineKeyboardButton[1];
            this.inline_keyboard[this.rowIndex][0] = (InlineKeyboardButton) button;
            return;
        }
        InlineKeyboardButton[] row = Arrays.copyOf(
                this.inline_keyboard[this.rowIndex],
                this.inline_keyboard[this.rowIndex].length + 1
        );
        row[row.length -1] = (InlineKeyboardButton) button;
        this.inline_keyboard[this.rowIndex] = row;
    }

    public void addButtons(AbstractKeyboardButton[] buttons) {
        for (AbstractKeyboardButton button:
                buttons) {
            this.addButton(button);
        }
    }

    public void resetButtons(AbstractKeyboardButton[] buttons) {
        this.inline_keyboard[this.rowIndex] = (InlineKeyboardButton[]) buttons;
    }

    public void nextRow() {
        if (this.inline_keyboard[this.rowIndex].length == 0) {
            throw new RuntimeException("Current row is empty.");
        }
        if (this.inline_keyboard.length <= ++this.rowIndex) {
            this.inline_keyboard = Arrays.copyOf(
                    this.inline_keyboard,
                    this.inline_keyboard.length + 1
            );
        }
    }

    public void prevRow() {
        if (this.rowIndex == 0) {
            throw new RuntimeException("There are not prev rows yet.");
        }
        if (this.inline_keyboard[this.rowIndex].length == 0) {
            throw new RuntimeException("Current row is empty.");
        }
        this.rowIndex--;
    }
}
