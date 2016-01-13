package com.svds.acquisition.slack.websocket;

import java.io.IOException;
import java.net.URI;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.svds.messaging.confluent.ConfluentProducer;
import com.svds.acquisition.slack.model.EventTypes;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


import java.net.URISyntaxException;
import java.util.*;
import javax.naming.ConfigurationException;
import javax.websocket.ClientEndpoint;
import javax.websocket.WebSocketContainer;
import javax.websocket.MessageHandler;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.ContainerProvider;



//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.svds.acquisition.slack.websocket"})
public class SlackRealTimeClient implements AutoCloseable{


    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SlackRealTimeClient.class);

    ConfluentProducer kafkaProducerService;

    PublicChannelsClientEndpoint client;

    SlackConfig  slackConfig;

    public static void main(String[] args) {

        LOG.info("Opening application");
        SpringApplication.run(SlackRealTimeClient.class, args);

    }

    public SlackRealTimeClient (){

        slackConfig = new SlackConfig();

        String schemaRegistryUrl = slackConfig.getSchemaRegistryUrl();
        String bootstrapServer = slackConfig.getBoostrapServer();

        //kafka producer
        try {
            this.kafkaProducerService = getKafkaProducerService(schemaRegistryUrl, bootstrapServer);
        } catch (ConfigurationException e) {
            LOG.error(String.format("Was not able to create a Kafka producer service using schema registry URL:  %s " +
                    "and bootstrap server: %s", schemaRegistryUrl, bootstrapServer));
            System.exit(1);
        }


        //connect
        String apiUrl = slackConfig.getApiUrl();
        String token = slackConfig.getToken();
        try{
            this.connect(apiUrl, token);
        }catch(URISyntaxException e){
            LOG.error(e.toString());
            LOG.error("Exiting");
            System.exit(1);
        }
    }

    public void close(){
        if(this.kafkaProducerService != null){
            this.kafkaProducerService.close();
        }

        if(this.client != null){
            this.client.close();
            LOG.info("Closing client");
        }

    }

    public void connect(String apiUrl, String token) throws URISyntaxException {
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(apiUrl, String.class, token);

        JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
        String socketURIString = jsonObj.get("url").getAsString();

        URI socketURI = new URI(socketURIString);

        client  = new PublicChannelsClientEndpoint(socketURI);
        client.addMessageListener(new PublicChannelsFilterHandler());
    }

    private ConfluentProducer getKafkaProducerService(String schemaRegistryUrl, String bootstrapServer)
            throws ConfigurationException{
        return new ConfluentProducer(schemaRegistryUrl, bootstrapServer);
    }

    @ClientEndpoint
    public class PublicChannelsClientEndpoint{
        Session session;
        MessageHandler.Whole<String> handler;


        public PublicChannelsClientEndpoint(URI uri){
            try{
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, uri);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        public void addMessageListener(MessageHandler.Whole handler){
            this.handler = handler;
        }


        @OnOpen
        public void onOpen(Session session){
            LOG.info("Opening web socket connnection");
            this.session = session;


        }

//       // @OnClose
//        public void onClose(Session session, CloseReason reason){
//            LOG.info("Closing web socket connection");
//
//
//            try{
//                session.close();
//            }catch (IOException e){
//                LOG.error("No web socket session to close");
//            }
//        }

        @OnMessage
        public void onMessage(String message){
            this.handler.onMessage(message);
        }

        public void close(){
            try{
                session.close();
            }catch (IOException e){
                LOG.error("No web socket session to close");
            }
        }
    }


    public class PublicChannelsFilterHandler implements MessageHandler.Whole<String>{

        final List<String> channelWhitelist = new ArrayList<String>();
        final List<String> whitelist = new ArrayList<String>();
        final JsonParser parser = new JsonParser();


        public PublicChannelsFilterHandler() {

            channelWhitelist.addAll(slackConfig.getTargetChannels());
            whitelist.addAll(slackConfig.getTargetTypes());
        }


        public void onMessage(final String message) {

            JsonObject jsonObject = parser.parse(message).getAsJsonObject();
            EventTypes types = getEventTypes(message);

            String topicName = getTopicName(types);


            if(whitelist.contains(types.getType()) || whitelist.contains(types.getSubtype())){
                LOG.info("Message of type " + types.getType());
                String channel = jsonObject.get("channel").getAsString();

                try {
                    if (channelWhitelist.contains(channel)) {
                        LOG.info("Channel " + channel);
                        kafkaProducerService.sendSchemaMessage(topicName, types.getType(), message);
                        //kafkaProducerService.sendMessage(topicName, message);
                    }
                }catch (Exception e){
                    LOG.error(e.toString());
                }
            }else{
                LOG.info("Message type will not be forwarded");
            }
        }

        private EventTypes getEventTypes(String json){
            JsonObject jsonObject = parser.parse(json).getAsJsonObject();

            String type = jsonObject.get("type").getAsString();
            JsonElement subTypeElement = jsonObject.get("subtype");

            if(subTypeElement == null){
                return new EventTypes(type, null);
            }

            String subType = subTypeElement.getAsString();
            return new EventTypes(type, subType);
        }


        private String getTopicName(EventTypes types){
            String baseTopic = slackConfig.getBaseTopic();

            String type = types.getType();
            if(type == null){
                return baseTopic + "none";
            }

            String subtype = types.getSubtype();
            if(subtype == null){
                return baseTopic + type;
            }

            return baseTopic +  type + "." + subtype;
        }
    }
}
