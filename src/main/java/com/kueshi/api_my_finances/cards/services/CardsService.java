package com.kueshi.api_my_finances.cards.services;

import com.kueshi.api_my_finances.cards.documents.Cards;
import com.kueshi.api_my_finances.cards.dto.CardCreateDTO;
import com.kueshi.api_my_finances.cards.repositories.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CardsService {

    @Autowired
    CardsRepository cardsRepository;

    public Cards create(CardCreateDTO cardCreate) {
        var card = new Cards(cardCreate);
        return this.cardsRepository.save(card);
    }

    public Page<Cards> list(Pageable pageable) {
        return this.cardsRepository.findAll(pageable);
    }


    public Cards update(CardCreateDTO cardCreate) {
        var card = new Cards(cardCreate);
        return this.cardsRepository.save(card);
    }

    public void delete(String id) {
        System.out.println("-------------------------------------------------------");
        var card = this.cardsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card with ID " + id + " not found"));
        this.cardsRepository.delete(card);
    }


}
