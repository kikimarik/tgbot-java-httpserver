package base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Bot {
    private final String token;
    volatile private Queue<BotEvent> events;
    private HashMap<String, BotEventListener> listeners;

    public Bot(String token) {
        this.setEvents(new LinkedList<>());
        this.token = token;
    }

    public String getPathUri() throws NoSuchAlgorithmException {
        byte[] md5 = MessageDigest.getInstance("MD5").digest(token.getBytes());
        StringBuilder hash = new StringBuilder();
        for (byte b : md5) {
            hash.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return "/path/" + hash;
    }

    public void setEvents(Queue<BotEvent> q) {
        this.events = q;
    }

    public void addEvent(BotEvent event) {
        try {
            this.events.add(event);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    public void setListeners(HashMap<String, BotEventListener> listeners) {
        this.listeners = listeners;
    }

    public void handleEvents()
    {
        BotEvent event = this.events.poll();
        if (event != null) {
            event.runEventListeners();
        }

    }

    public HashMap<String, BotEventListener> getListeners() {
        return this.listeners;
    }
}
