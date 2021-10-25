package base;

import models.BotEventEntity;

import java.util.EventListener;

public interface BotEventListener extends EventListener {
    void setEntity(BotEventEntity entity);
    void exec();
}
