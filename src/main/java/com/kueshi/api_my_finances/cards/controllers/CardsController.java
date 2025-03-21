package com.kueshi.api_my_finances.cards.controllers;

import com.kueshi.api_my_finances.cards.documents.Cards;
import com.kueshi.api_my_finances.cards.dto.CardCreateDTO;
import com.kueshi.api_my_finances.cards.services.CardsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cards")
public class CardsController {

    @Autowired
    CardsService cardsService;

    @PostMapping("/create")
    public Cards create(@RequestBody @Valid CardCreateDTO cardCreate) {
        return this.cardsService.create(cardCreate);
    }


    @GetMapping("/list")
    public Page<Cards> list(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return this.cardsService.list(pageable);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        this.cardsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
