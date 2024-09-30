package com.kueshi.api_my_finances.cards.repositories;

import com.kueshi.api_my_finances.cards.documents.Cards;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardsRepository extends MongoRepository<Cards, String> {
}
