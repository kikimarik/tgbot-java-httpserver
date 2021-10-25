package base;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BotBootstrap {
    private static final int DEFAULT_SERVER_PORT = 4909;
    private base.Server server;
    private Map<Bot, HashMap<String, BotEventListener>> botPool;

    public BotBootstrap(Server server, Map<Bot, HashMap<String, BotEventListener>> botPool) {
        this.server = server;
        this.botPool = botPool;
    }

    public BotBootstrap(Server server, Bot bot, HashMap<String, BotEventListener> listeners) {
        this.server = server;
        this.botPool = new HashMap<>();
        this.botPool.put(bot, listeners);
    }

    public void up() {
        if (!this.validate()) {
            throw new RuntimeException("It was the BotBootstrap configuration problem.");
        }
        Thread serverThread = new Thread(() -> {
            try {
                Set<Map.Entry<Bot, HashMap<String, BotEventListener>>> bots = this.botPool.entrySet();
                for (Map.Entry<Bot, HashMap<String, BotEventListener>> bot : bots) {
                    bot.getKey().setListeners(bot.getValue());
                }
                this.server.run();
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
        Thread handlerThread = new Thread(() -> {
            while (true) {
                for (Bot bot : this.botPool.keySet()) {
                    bot.handleEvents();
                }
            }
        });
        handlerThread.start();
        serverThread.start();
    }

    private boolean validate() {
        return !(this.server == null || this.botPool == null);
    }
}
