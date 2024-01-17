package ru.m15.ekspring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;


@Entity
@Getter
@Setter
@Builder
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rules")
public class Rules {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String url;
    private String data;
    private Boolean state;
}
