package models.types.chat;

import models.types.User;

public class ChatInviteLink {
    public String invite_link;
    public User creator;
    public Boolean is_primary;
    public Boolean is_revoked;
    public Integer expire_date;
    public Integer member_limit;
}
