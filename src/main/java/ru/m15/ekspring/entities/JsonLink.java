package ru.m15.ekspring.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class JsonLink implements Serializable {

    String link;
    String body;
    String meta;
    String type;

}
