package ru.m15.ekspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.m15.ekspring.entities.FeedLink;
import ru.m15.ekspring.entities.enums.FeedState;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedLinkRepository extends JpaRepository<FeedLink, UUID> {

    // classic methods (simple select) for link to DB

    List<FeedLink> findByState( FeedState state );

}
