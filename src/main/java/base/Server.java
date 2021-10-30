package base;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Server {
    private final LinkedList<Bot> bots;
    private final int port;
    private HttpServer httpServer;
    private CountDownLatch cdl = new CountDownLatch(1);
    
    public Server(Bot bot, int port) {
        this(new LinkedList<>(List.of(bot)), port);
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
        System.out.println("UP");
        this.cdl.countDown();
    }

    public void shutdown() {
        try {
            this.cdl.await();
            this.httpServer.stop(1);
            System.out.println("DOWN");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
