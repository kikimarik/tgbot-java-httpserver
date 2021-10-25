package models.types;

public interface KeyboardInterface {
    /**
     * Add the button to current row
     * @param button Inserted button
     */
    void addButton(AbstractKeyboardButton button);
    /**
     * Add some buttons to current row
     * @param buttons Inserted buttons
     */
    void addButtons(AbstractKeyboardButton[] buttons);
    /**
     * Set new buttons to current row
     * @param buttons Inserted buttons
     */
    void resetButtons(AbstractKeyboardButton[] buttons);
    /**
     * Go to the next row
     */
    void nextRow();
    /**
     * Go to the prev row
     */
    void prevRow();
}
