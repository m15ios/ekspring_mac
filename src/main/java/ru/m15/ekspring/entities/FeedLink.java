package ru.m15.ekspring.entities;

import io.minio.messages.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import ru.m15.ekspring.entities.enums.FeedState;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="feed_links")
public class FeedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String urlSource;
    private LocalDateTime createDate;
    @Enumerated(value=EnumType.STRING)
    private FeedState state;
    private LocalDateTime durationDate;
    private Integer countAttempts;
    private LocalDateTime lastDateAttempt;
    private UUID minio;
    @Column(name="url_hash")
    private String urlHash;
    //@Type(JsonLinks.class)
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String links;
}
