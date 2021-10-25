package models.types;

import models.BotEventEntity;

import java.util.ArrayList;
import java.util.List;

public class InlineQuery implements BotEventEntity {
    private ArrayList<String> actions;
    public String id;
    public User from;
    public String query;
    public String offset;
    public String chat_type;
    public Location location;

    public InlineQuery() {
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
