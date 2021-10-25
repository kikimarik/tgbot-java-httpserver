package models.types;

public class InputMediaAnimation extends InputMedia {
    public Integer width;
    public Integer height;
    public Integer duration;

    public InputMediaAnimation(String media) {
        super("animation", media);
    }
    public InputMediaAnimation() {
        super();
        this.type = "animation";
    }
}
