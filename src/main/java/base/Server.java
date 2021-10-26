package base;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final LinkedList<Bot> bots;
    private final int port;
    private HttpServer httpServer;
    
    public Server(Bot bot, int port) {
        this.bots = new LinkedList<>(List.of(bot));
        this.port = port;
    }

    public Server(LinkedList<Bot> bots, int port) {
        this.bots = bots;
        this.port = port;
    }

    public void run() throws IOException, NoSuchAlgorithmException {
        this.httpServer = HttpServer.create(new InetSocketAddress(this.port), 0);
        for (Bot bot : bots) {
            this.httpServer.createContext(bot.getPathUri(), new Handler(bot));
        }
        //Thread control is given to executor service.
        this.httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        this.httpServer.start();
    }

    public void shutdown() {
        this.httpServer.stop(1);
    }
}
