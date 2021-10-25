package models.types;

import models.types.chat.ChatLocation;
import models.types.chat.ChatPermissions;
import models.types.chat.ChatPhoto;

public class Chat {
    public Integer id;
    public String type;
    public String title;
    public String username;
    public String first_name;
    public String last_name;
    public ChatPhoto photo;
    public String bio;
    public String description;
    public String invite_link;
    public Message pinned_message;
    public ChatPermissions permissions;
    public Integer slow_mode_delay;
    public Integer message_auto_delete_time;
    public String sticker_set_name;
    public Boolean can_set_sticker_set;
    public Integer linked_chat_id;
    public ChatLocation location;
}
