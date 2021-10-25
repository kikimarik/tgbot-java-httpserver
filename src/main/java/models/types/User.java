package models.types;

public class User {
    public Integer id; // INT64
    public Boolean is_bot;
    public String first_name;
    public String last_name;
    public String username;
    public String language_code;
    public Boolean can_join_groups;
    public Boolean can_read_all_group_messages;
    public Boolean supports_inline_queries;
}
