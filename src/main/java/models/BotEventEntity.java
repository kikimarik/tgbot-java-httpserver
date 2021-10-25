package models;

import java.util.ArrayList;

public interface BotEventEntity {
    ArrayList<String> getActions();
    void setAction(String action);
}
