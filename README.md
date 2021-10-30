# Example RunBots.class

```java
package usage;

import api.Client;
import api.entities.ApiMethodDTO;
import api.entities.methods.SendLocation;
import api.entities.methods.SendMessage;
import api.entities.types.InlineKeyboardButton;
import api.entities.types.reply.InlineKeyboardMarkup;
import base.*;
import models.BotEventEntity;
import models.types.CallbackQuery;
import models.types.ChatMemberUpdated;
import models.types.Message;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Run bot examples servers on tcp-ports 2220, 2221 and 2222
 */
public class RunBots {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        RunBots.runTwoBotsFromPull();
        RunBots.runTextMessageReaderBot();
        RunBots.runFindLocationBot();
    }

    private static void runTextMessageReaderBot() throws NoSuchAlgorithmException {
        String token = "2003365903:AAENDWj08cwczI4A1H9i1jp7sPP_pGNmxdI";
        Bot bot = new Bot(token);
        bot.setEvents(new PriorityQueue<>(Comparator.comparingInt(BotEvent::getId))); // example with priority queue
        System.out.println("Path to text message reader bot webhooks: " + bot.getPathUri() + "\n");
        HashMap<String, BotEventListener> listeners = new HashMap<>();
        listeners.put("receiveTextMessage", new BotEventListener() {
            private Message message;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.message = (Message) entity;
            }

            @Override
            public void exec() {
                System.out.println(this.message.printTextMessage());
                Client client = Client.getInstance();
                String response = client.send(token, new ApiMethodDTO(
                        "sendMessage",
                        new SendMessage(this.message.chat.id, "I received you message: " + message.text).toString()
                ));
                System.out.println(response);
            }
        });
        Server server = new Server(bot, 2222);
        BotBootstrap bootstrap = new BotBootstrap(server, bot, listeners);
        bootstrap.up();
    }

    private static void runFindLocationBot() throws NoSuchAlgorithmException {
        String token = "2003365903:AAENDWj08cwczI4A1H9i1jp7sPP_pGNmxdI";
        Bot bot = new Bot(token);
        System.out.println("Path to text message reader bot webhooks: " + bot.getPathUri() + "\n");
        HashMap<String, BotEventListener> listeners = new HashMap<>();
        listeners.put("receiveTextMessage", new BotEventListener() {
            private Message message;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.message = (Message) entity;
            }

            @Override
            public void exec() {
                Client client = Client.getInstance();
                SendMessage dto = new SendMessage(this.message.chat.id, "Chose you city or send me your location.");
                dto.reply_markup = new InlineKeyboardMarkup(new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Brussels", "1"),
                        new InlineKeyboardButton("London", "2"),
                        new InlineKeyboardButton("Vienna", "3")
                });
                String response = client.send(token, new ApiMethodDTO(
                        "sendMessage",
                        dto.toString()
                ));
                System.out.println(response);
            }
        });
        listeners.put("receiveData", new BotEventListener() {
            private CallbackQuery callbackQuery;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.callbackQuery = (CallbackQuery) entity;
            }

            @Override
            public void exec() {
                Client client = Client.getInstance();
                HashMap<Float, Float> londonAddresses = new HashMap<>();
                londonAddresses.put(57.14284119897725f, -2.0966693015555022f);
                londonAddresses.put(50.822923531958025f, -0.14588297444286305f);

                HashMap<Float, Float> brusselsAddresses = new HashMap<>();
                brusselsAddresses.put(50.83683564279223f, 4.358317112064303f);

                HashMap<Float, Float> viennaAddresses = new HashMap<>();
                viennaAddresses.put(48.20712383146376f, 16.371795298497297f);

                ArrayList<SendLocation> sendLocationList = new ArrayList<>();

                switch (this.callbackQuery.data) {
                    case "1":
                        for (Map.Entry<Float, Float> location : brusselsAddresses.entrySet()) {
                            sendLocationList.add(new SendLocation(
                                    this.callbackQuery.message.chat.id,
                                    location.getKey(),
                                    location.getValue()
                            ));
                        }
                        break;
                    case "2":
                        for (Map.Entry<Float, Float> location : londonAddresses.entrySet()) {
                            sendLocationList.add(new SendLocation(
                                    this.callbackQuery.message.chat.id,
                                    location.getKey(),
                                    location.getValue()
                            ));
                        }
                        break;
                    case "3":
                        for (Map.Entry<Float, Float> location : viennaAddresses.entrySet()) {
                            sendLocationList.add(new SendLocation(
                                    this.callbackQuery.message.chat.id,
                                    location.getKey(),
                                    location.getValue()
                            ));
                        }
                        break;
                    default:
                        String response = client.send(token, new ApiMethodDTO(
                                "sendMessage",
                                new SendMessage(
                                        this.callbackQuery.message.chat.id,
                                        "There is no store in your city."
                                ).toString()
                        ));
                        System.out.println(response);
                }

                for (SendLocation dto : sendLocationList) {
                    String response = client.send(token, new ApiMethodDTO(
                            "sendLocation",
                            dto.toString()
                    ));
                    System.out.println(response);
                }
            }
        });
        listeners.put("receiveChatMember", new BotEventListener() {
            private ChatMemberUpdated member;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.member = (ChatMemberUpdated) entity;
            }

            @Override
            public void exec() {
                Client client = Client.getInstance();
                if (this.member.invite_link.is_primary || this.member.invite_link.is_revoked) {
                    String response = client.send(token, new ApiMethodDTO(
                            "sendMessage",
                            new SendMessage(
                                    this.member.chat.id,
                                    "Hello! I could help to find Apple store in your region."
                            ).toString()
                    ));
                    System.out.println(response);
                }
            }
        });
        listeners.put("receiveLocation", new BotEventListener() {
            private Message message;
            private static final Double MAX_RELEVANT_DISTANCE = 200d;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.message = (Message) entity;
            }

            @Override
            public void exec() {
                if (this.message == null) {
                    return;
                }
                Client client = Client.getInstance();

                HashMap<Float, Float> addresses = new HashMap<>();
                addresses.put(57.14284119897725f, -2.0966693015555022f);
                addresses.put(50.822923531958025f, -0.14588297444286305f);
                addresses.put(50.83683564279223f, 4.358317112064303f);
                addresses.put(48.20712383146376f, 16.371795298497297f);

                Float lat = this.message.location.latitude;
                Float lon = this.message.location.longitude;

                double distance;
                double optimalDistance = MAX_RELEVANT_DISTANCE;
                SendLocation dto = null;
                for (Map.Entry<Float, Float> address : addresses.entrySet()) {
                    distance = 111.2 * Math.sqrt(
                            (lon - address.getValue())
                                    * (lon - address.getValue())
                                    + (lat - address.getKey())
                                    * Math.cos(Math.PI * lon / 180)
                                    * (lat - address.getKey())
                                    * Math.cos(Math.PI * lon / 180)
                    );
                    if (distance < optimalDistance) {
                        dto = new SendLocation(
                                this.message.from.id,
                                address.getKey(),
                                address.getValue()
                        );
                        optimalDistance = distance;
                    }
                }

                String response;
                String text = "There is no store in your city.";
                if (dto != null) {
                    response = client.send(token, new ApiMethodDTO(
                            "sendLocation",
                            dto.toString()
                    ));
                    System.out.println(response);
                    text = "I found nearest Apple store in " + optimalDistance + " km of you";
                }
                response = client.send(token, new ApiMethodDTO(
                        "sendMessage",
                        new SendMessage(
                                this.message.chat.id,
                                text
                        ).toString()
                ));

                System.out.println(response);
            }
        });
        Server server = new Server(bot, 2221);
        BotBootstrap bootstrap = new BotBootstrap(server, bot, listeners);
        bootstrap.up();
    }

    private static void runTwoBotsFromPull() throws NoSuchAlgorithmException {
        Bot firstBot = new Bot("1958107039:AAHok6pZJH0iKwOTBaeDBftNz6-sS7_7sgg");
        Bot secondBot = new Bot("2003365903:AAENDWj08cwczI4A1H9i1jp7sPP_pGNmxdI");
        System.out.println("Path to the first bot webhooks: " + firstBot.getPathUri() + "\n");
        System.out.println("Path to the second bot webhooks: " + secondBot.getPathUri() + "\n");
        LinkedList<Bot> bots = new LinkedList<>(List.of(firstBot, secondBot));

        BotEventListener messageEvent = new BotEventListener() {
            private BotEventEntity entity;

            @Override
            public void setEntity(BotEventEntity entity) {
                this.entity = entity;
            }

            @Override
            public void exec() {
                // Past your code here
                System.out.println(this.entity.toString());
            }
        };
        HashMap<String, BotEventListener> listeners = new HashMap<>();
        listeners.put("receiveTextMessage", messageEvent);

        HashMap<Bot, HashMap<String, BotEventListener>> pool = new HashMap<>();
        pool.put(firstBot, listeners);
        pool.put(secondBot, listeners);

        Server server = new Server(bots, 2220);
        BotBootstrap bootstrap = new BotBootstrap(server, pool);
        bootstrap.up();
    }
}
```

You can add this file to the `usage` package to learn our examples.
If you want to run this code on you machine you
need to inject this dependency to `Maven` via `pom.xml`

```xml

<dependency>
    <groupId>com.telegram.bot.client.lib</groupId>
    <artifactId>tgbot-java-httpclient</artifactId>
    <version>1.0</version>
</dependency>
```
Also, you must replace example tokens for your real tokens. 
After that run 2 commands:
```shell
mvn install:install-file -Dfile=lib/tgbot-java-httpclient-1.0.jar -DgroupId=com.telegram.bot.client.lib -DartifactId=tgbot-java-httpclient -Dversion=1.0 -Dpackaging=jar
```
```shell
mvn clean compile exec:java -Dexec.mainClass=usage.RunBots
```