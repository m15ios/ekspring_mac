package ru.m15.ekspring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonRuleProperties  implements Serializable {

    String docPath;
    String name;
    String value;

//    String namePost;
//    String valuePost;

}