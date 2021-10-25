package models.types.reply;

import models.types.AbstractKeyboardButton;
import models.types.KeyboardButton;
import models.types.KeyboardInterface;
import models.types.ReplyMarkup;
import java.util.Arrays;

public class ReplyKeyboardMarkup extends ReplyMarkup implements KeyboardInterface {
    public KeyboardButton[][] keyboard = new KeyboardButton[1][];
    public Boolean resize_keyboard;
    public Boolean one_time_keyboard;
    public String input_field_placeholder;

    private int rowIndex = 0;

    public ReplyKeyboardMarkup() {}
    public ReplyKeyboardMarkup(AbstractKeyboardButton[] buttons) {
        assert false;
        this.resetButtons(buttons);
    }

    public void addButton(AbstractKeyboardButton button) {
        if (this.keyboard[this.rowIndex] == null) {
            this.keyboard[this.rowIndex] = new KeyboardButton[1];
            this.keyboard[this.rowIndex][0] = (KeyboardButton) button;
            return;
        }
        KeyboardButton[] row = Arrays.copyOf(
                this.keyboard[this.rowIndex],
                this.keyboard[this.rowIndex].length + 1
        );
        row[row.length -1] = (KeyboardButton) button;
        this.keyboard[this.rowIndex] = row;
    }

    public void addButtons(AbstractKeyboardButton[] buttons) {
        for (AbstractKeyboardButton button:
                buttons) {
            this.addButton(button);
        }
    }

    public void resetButtons(AbstractKeyboardButton[] buttons) {
        this.keyboard[this.rowIndex] = (KeyboardButton[]) buttons;
    }

    public void nextRow() {
        if (this.keyboard[this.rowIndex].length == 0) {
            throw new RuntimeException("Current row is empty.");
        }
        if (this.keyboard.length <= ++this.rowIndex) {
            this.keyboard = Arrays.copyOf(
                    this.keyboard,
                    this.keyboard.length + 1
            );
        }
    }

    public void prevRow() {
        if (this.rowIndex == 0) {
            throw new RuntimeException("There are not prev rows yet.");
        }
        if (this.keyboard[this.rowIndex].length == 0) {
            throw new RuntimeException("Current row is empty.");
        }
        this.rowIndex--;
    }
}
