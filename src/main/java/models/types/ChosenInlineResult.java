package models.types;

import models.BotEventEntity;

import java.util.ArrayList;
import java.util.List;

public class ChosenInlineResult implements BotEventEntity {
    private ArrayList<String> actions;
    public String result_id;
    public User from;
    public Location location;
    public String inline_message_id;
    public String query;

    public ChosenInlineResult() {
        this.actions = new ArrayList<>(List.of(
                "receiveQuery"
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
