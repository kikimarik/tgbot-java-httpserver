package models.types.chat;

import models.types.User;

public class ChatMember {
    public String status;
    public User user;
    public Boolean can_be_edited;
    public Boolean is_anonymous;
    public Boolean can_manage_chat;
    public Boolean can_manage_voice_chats;
    public Boolean can_restrict_members;
    public Boolean can_promote_members;
    public Boolean can_change_info;
    public Boolean can_invite_users;
    public Boolean can_post_messages;
    public Boolean can_edit_messages;
    public Boolean can_pin_messages;
    public String custom_title;
}
