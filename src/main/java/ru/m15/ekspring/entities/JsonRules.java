package ru.m15.ekspring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/*
DATA.SAMPLE - {
  title: { docPath: "h1.title" },
  properties: {
    docPath: "ul.props li",
    name: "p.name",
    value: "p.value"
  }
}

String json = "{\"title\":{\"docPath\":\"h1.oglasi-headline-model\"},\"properties\":{\"docPath\":\"div.oglasi-osnovne-informacije li\",\"name\": \"p\",\"value\":\"span\"}}";
JsonRules jsonRules = new JsonRules( json );
log.info("============================");
*/

@Slf4j
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRules implements Serializable {

    JsonRuleTitle title;
    JsonRuleProperties properties;

    public JsonRules (String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //log.info("json" + json);
        var result = objectMapper.readValue(json, JsonRules.class);
        //log.info(result.toString());
        this.title = result.getTitle();
        this.properties = result.getProperties();
        log.info("~~~~~~~~~~~~~~~~~~~~~~");
    }

//    public String toJson() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(this);
//    }

    public String toString(){
        return "title: " + this.title.toString()
                + "\nproperties: " + this.properties.toString();
    }

}

