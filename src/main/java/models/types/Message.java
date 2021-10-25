package models.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.BotEventEntity;
import models.types.reply.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class Message implements BotEventEntity {
    private ArrayList<String> actions;
    public Integer message_id;
    public User user;
    public Chat sender_chat;
    public Integer date;
    public Chat chat;
    public User from;
    public User forward_from;
    public Chat forward_from_chat;
    public Integer forward_from_message_id;
    public String forward_signature;
    public String forward_sender_name;
    public Integer forward_date;
    public Message reply_to_message;
    public User via_bot;
    public Integer edit_date;
    public String media_group_id;
    public String author_signature;
    public String text;
    public MessageEntity[] entities;
    public Animation animation;
    public Audio audio;
    public Document document;
    public PhotoSize[] photo;
    public Sticker sticker;
    public Video video;
    public VideoNote video_note;
    public Voice voice;
    public String caption;
    public MessageEntity[] caption_entities;
    public Contact contact;
    public Dice dice;
    public Game game;
    public Poll poll;
    public Venue venue;
    public Location location;
    public User[] new_chat_members;
    public User left_chat_member;
    public String new_chat_title;
    public PhotoSize[] new_chat_photo;
    public Boolean delete_chat_photo;
    public Boolean group_chat_created;
    public Boolean supergroup_chat_created;
    public Boolean channel_chat_created;
    public MessageAutoDeleteTimerChanged message_auto_delete_timer_changed;
    public Integer migrate_to_chat_id;
    public Integer migrate_from_chat_id;
    public Message pinned_message;
    public Invoice invoice;
    public SuccessfulPayment successful_payment;
    public String connected_website;
    public PassportData passport_data;
    public ProximityAlertTriggered proximity_alert_triggered;
    public VoiceChatScheduled voice_chat_scheduled;
    public VoiceChatStarted voice_chat_started;
    public VoiceChatEnded voice_chat_ended;
    public VoiceChatParticipantsInvited voice_chat_participants_invited;
    public InlineKeyboardMarkup reply_markup;

    public Message() {
        this.actions = new ArrayList<>(List.of(
                "receiveTextMessage"
        ));
    }

    public void setLocation(Location location) {
        this.location = location;
        this.setAction("receiveLocation");
    }

    @Override
    public ArrayList<String> getActions() {
        return this.actions;
    }

    @Override
    public void setAction(String action) {
        this.actions.add(action);
    }

    @Override
    public String toString() {
        try {
            return (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String printTextMessage() {
        return this.text;
    }
    
    public String extractBotCommand() {
        for (MessageEntity messageEntity :
                this.entities) {
            if (messageEntity.type.equals("bot_command")) {
                return this.text.substring(messageEntity.offset, messageEntity.length - 1);
            }
        }
        return null;
    }
}
