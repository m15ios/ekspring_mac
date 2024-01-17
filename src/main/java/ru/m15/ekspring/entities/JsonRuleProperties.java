package ru.m15.ekspring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRuleProperties  implements Serializable {

    String docPath;
    String name;
    String value;

//    String namePost;
//    String valuePost;

    public String toString(){
        return "{ docPath: " + this.docPath
                + "\n\tname: " + this.name
                + "\n\tvalue: " + this.value + " }";
    }

}
