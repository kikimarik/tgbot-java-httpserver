package base;

import models.BotEventEntity;
import models.types.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BotEvent {
    private Integer id;
    private BotEventEntity entity;
    volatile private HashMap<String, BotEventListener> listeners;

    public BotEvent() {
    }

    public void setUpdate_id(Integer update_id) {
        this.id = update_id;
    }

    public void setMessage(Message message) {
        this.entity = message;
    }

    public void setCallback_query(CallbackQuery callbackQuery) {
        this.entity = callbackQuery;
    }

    public void setInline_query(InlineQuery inlineQuery) {
        this.entity = inlineQuery;
    }

    public void setShipping_query(BotEventEntity placeholder) {
        // TODO implement
    }

    public void setPre_checkout_query(BotEventEntity placeholder) {
        // TODO implement
    }

    public void setPoll(Poll poll) {
        this.entity = poll;
    }

    public void setPoll_answer(BotEventEntity placeholder) {
        // TODO implement
    }

    public void setMy_chat_member(ChatMemberUpdated chatMemberUpdated) {
        this.entity = chatMemberUpdated;
    }

    public void setChat_member(ChatMemberUpdated chatMemberUpdated) {
        this.entity = chatMemberUpdated;
    }

    public void setChosen_inline_result(ChosenInlineResult chosenInlineResult) {
        this.entity = chosenInlineResult;
    }

    public void setEdited_message(Message message) {
        this.entity = message;
        this.entity.setAction("editMessage");
    }

    public void setChannel_post(Message message) {
        this.entity = message;
        this.entity.setAction("receiveChannelPost");
    }

    public void setEdited_channel_post(Message message) {
        this.entity = message;
        this.entity.setAction("editChannelPost");
    }

    public BotEvent(Integer id, BotEventEntity entity) {
        this.id = id;
        this.entity = entity;
    }

    public BotEvent(Object update_id, Object entity) {
        this.id = (Integer) update_id;
        this.entity = (BotEventEntity) entity;
    }

    public void registerEventListeners(HashMap<String, BotEventListener> listeners) {
        this.listeners = listeners;
    }

    public void runEventListeners() {
        ArrayList<String> actions = this.entity.getActions();
        for (String action : actions) {
            if (this.listeners.containsKey(action)) {
                this.listeners.get(action).setEntity(this.entity);
                this.listeners.get(action).exec();
            }
        }
    }

    public Integer getId() {
        return id;
    }
}
