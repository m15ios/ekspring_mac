package ru.m15.ekspring.entities;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class JsonLinks implements Serializable {

    List<JsonLink> links = new ArrayList<>();

    public JsonLinks( Map<String, JsonLink> links ){
        log.info("JsonLinks init");
        links.forEach( (k,v)-> {
            this.links.add(v);
        });
        log.info("JsonLinks add " + this.links.size() + " elements" );
    }

    public JsonLinks( String jsonText ){
    }

    public String createJson(){
        String result = "";

        if( this.links == null ){
            log.error("JsonLinks is null " );
            return result;
        }

        JSONArray jItems = new JSONArray();

        links.forEach( item -> {
            JSONObject jItem = new JSONObject();
            jItem.put( "link", item.getLink() );
            jItem.put( "body", item.getBody() );
            jItem.put( "meta", item.getMeta() );
            jItem.put( "type", item.getType() );
            jItems.put(jItem);
        });

        result = jItems.toString();

        return result;
    }

}
