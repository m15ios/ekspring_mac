package ru.m15.ekspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.m15.ekspring.entities.Rules;

import java.util.List;

@Repository
public interface RulesRepository extends JpaRepository<Rules, Integer>  {

    Rules findByUrl(String url);

}
