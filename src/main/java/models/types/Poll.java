package models.types;

import models.BotEventEntity;

import java.util.ArrayList;
import java.util.List;

public class Poll implements BotEventEntity {
    private ArrayList<String> actions;
    public String id;
    public String question;
    public PollOption[] options;
    public Integer total_voter_count;
    public Boolean is_closed;
    public Boolean is_anonymous;
    public String type;
    public Boolean allows_multiple_answers;
    public Integer correct_option_id;
    public String explanation;
    public MessageEntity[] explanation_entities;
    public Integer open_period;
    public Integer close_date;

    public Poll() {
        this.actions = new ArrayList<>(List.of(
                "receivePoll"
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
