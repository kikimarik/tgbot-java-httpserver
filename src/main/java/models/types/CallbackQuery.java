package models.types;

import models.BotEventEntity;

import java.util.ArrayList;
import java.util.List;

public class CallbackQuery implements BotEventEntity {
    private final ArrayList<String> actions;

    public String id;
    public User from;
    public Message message;
    public String inline_message_id;
    public String chat_instance;
    public String data;
    public String game_short_name;

    public CallbackQuery() {
        this.actions = new ArrayList<>(List.of(
                "receiveData"
        ));
    }

    @Override
    public ArrayList<String> getActions() {
        return this.actions;
    }

    @Override
    public void setAction(String action) {
        this.actions.add(action);
    }
}
