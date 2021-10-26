package base;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BotBootstrap {
    private Server server;
    private Map<Bot, HashMap<String, BotEventListener>> botPool;
    private Thread handlerThread;
    private Thread serverThread;

    public BotBootstrap(Server server, Map<Bot, HashMap<String, BotEventListener>> botPool) {
        this.setServer(server);
        this.setBotPool(botPool);
    }

    public BotBootstrap(Server server, Bot bot, HashMap<String, BotEventListener> listeners) {
        this.setServer(server);
        this.setBotPool(new HashMap<>());
        this.addBotToPoll(bot, listeners);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setBotPool(Map<Bot, HashMap<String, BotEventListener>> botPool) {
        this.botPool = botPool;
    }

    public void addBotToPoll(Bot bot, HashMap<String, BotEventListener> listeners) {
        this.botPool.put(bot, listeners);
    }

    public void up() {
        if (!this.validate()) {
            throw new RuntimeException("It was the BotBootstrap configuration problem.");
        }
        this.serverThread = new Thread(() -> {
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
        this.handlerThread = new Thread(() -> {
            while (true) {
                for (Bot bot : this.botPool.keySet()) {
                    bot.handleEvents();
                }
            }
        });
        this.handlerThread.start();
        this.serverThread.start();
    }

    public void down() {
        this.handlerThread.interrupt();
        this.server.shutdown();
        this.serverThread.interrupt();
    }

    private boolean validate() {
        return !(this.server == null || this.botPool == null);
    }
}
