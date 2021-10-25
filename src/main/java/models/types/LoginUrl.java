package models.types;

public class LoginUrl {
    public String url;
    public String forward_text;
    public String bot_username;
    public Boolean request_write_access;

    public LoginUrl() {}
    public LoginUrl(String url) {
        this.url = url;
    }
}
