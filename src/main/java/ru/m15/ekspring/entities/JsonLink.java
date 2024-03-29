package ru.m15.ekspring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonLink implements Serializable {

    String link;
    String body;
    String meta;
    String type;

    public String toString(){
        return "{ link: " + this.link
                + "\n\tbody: " + this.body
                + "\n\tmeta: " + this.meta
                + "\n\ttype: " + this.type + " }";

    }
}
