package base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Handler implements HttpHandler {
    private final Bot bot;

    public Handler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream body = exchange.getRequestBody();
        String requestText = new String(body.readAllBytes());
        System.out.println(requestText);

        // TODO add component to manipulate request
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            BotEvent botEvent = mapper.readValue(requestText, BotEvent.class);
            this.bot.addEvent(botEvent);
            botEvent.registerEventListeners(bot.getListeners());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        String response = "Success";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
