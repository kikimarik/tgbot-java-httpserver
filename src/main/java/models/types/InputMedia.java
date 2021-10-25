package models.types;

import java.util.LinkedHashMap;

public abstract class InputMedia {
    public String type;
    public String media;
    public LinkedHashMap<String, String> thumb;
    public String caption;
    public String parse_mode;
    public MessageEntity[] caption_entities;

    public InputMedia(String type, String media) {
        this.type = type;
        this.media = media;
    }

    public InputMedia() {
    }

    public void setThumb(LinkedHashMap<String, String> filePathMap) {
        this.thumb = filePathMap;
    }
}
