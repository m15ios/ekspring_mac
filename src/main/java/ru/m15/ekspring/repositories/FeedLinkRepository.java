package ru.m15.ekspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.m15.ekspring.entities.FeedLink;

import java.util.UUID;

@Repository
public interface FeedLinkRepository extends JpaRepository<FeedLink, UUID> {

    // classic methods (simple select) for link to DB

}
