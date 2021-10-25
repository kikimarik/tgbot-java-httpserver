package models.types;

import models.BotEventEntity;
import models.types.chat.ChatInviteLink;
import models.types.chat.ChatMember;

import java.util.ArrayList;
import java.util.List;

public class ChatMemberUpdated implements BotEventEntity {
    private ArrayList<String> actions;

    public Chat chat;
    public User from;
    public Integer date;
    public ChatMember old_chat_member;
    public ChatMember new_chat_member;
    public ChatInviteLink invite_link;

    public ChatMemberUpdated() {
        this.actions = new ArrayList<>(List.of(
                "receiveChatMember"
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
