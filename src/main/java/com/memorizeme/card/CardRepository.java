package com.memorizeme.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsCardByWord(String word);
    long count();
    void deleteAll();
}
