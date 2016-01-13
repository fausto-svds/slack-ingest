package com.svds.acquisition.slack.websocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fausto on 1/12/16.
 */
public class SlackConfig {

    private final static String BASE_TOPIC = "slack.channel_stream.";

    private final static String API_URL = "API_URL";

    private final static String TOKEN = "TOKEN";

    private final static String BOOTSTRAP_SERVER = "BOOTSTRAP_SERVER";

    private final static String SCHEMA_REGISTRY_URL = "SCHEMA_REGISTRY_URL";

    private Set<String> targetChannels = new HashSet<String>();

    private Set<String> targetTypes = new HashSet<String>();

    public SlackConfig(){
        String[] args = null;

        //target channels
        targetChannels.add("C02E9JBPT");


        //target types
        targetTypes.add("message");

        if(args != null){
            targetChannels.addAll(getArgChannels(args));
        }

    }

    private ArrayList<String> getArgChannels(String[] args){
        return new ArrayList<String>(Arrays.asList(args));
    }


    public String getBoostrapServer(){
        return System.getenv(BOOTSTRAP_SERVER);
    }

    public String getSchemaRegistryUrl(){
        return System.getenv(SCHEMA_REGISTRY_URL);
    }

    public Set<String> getTargetChannels(){
        return targetChannels;
    }

    public Set<String> getTargetTypes(){
        return targetTypes;
    }

    public String getApiUrl(){
        return System.getenv(API_URL);
    }

    public String getToken(){
        return System.getenv(TOKEN);
    }

    public String getBaseTopic(){
        return BASE_TOPIC;
    }

}
