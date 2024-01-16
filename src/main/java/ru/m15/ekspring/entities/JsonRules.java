package ru.m15.ekspring.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
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
*/

// {"title":{"docPath":"h1.oglasi-headline-model"},"properties":{"docPath":"div.oglasi-osnovne-informacije li","name": "p","value":"span"}}
@Slf4j
public class JsonRules implements Serializable {

    JsonRuleTitle title;
    JsonRuleProperties properties;

    public JsonRules (String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //return objectMapper.readValue(json, MyObject.class);
    }
//
//    public String toJson() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(this);
//    }



}

