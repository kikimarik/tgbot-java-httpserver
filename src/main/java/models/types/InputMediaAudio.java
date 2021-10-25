package models.types;

public class InputMediaAudio extends InputMedia {
    public Integer duration;
    public String performer;
    public String title;

    public InputMediaAudio(String media) {
        super("audio", media);
    }
    public InputMediaAudio() {
        super();
        this.type = "audio";
    }
}
