package com.svds.acquisition.slack.model;

/**
 * Created by fausto on 4/28/15.
 */
public class EventTypes {

    private String type;

    private String subtype;

    public EventTypes(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
