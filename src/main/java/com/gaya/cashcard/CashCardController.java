package com.gaya.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository repository;

    public CashCardController(CashCardRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    private ResponseEntity<Iterable<CashCard>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = repository.findById(requestedId);

        return cashCardOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        /*
        // above return statement is the short version of below.
        if (cashCardOptional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cashCardOptional.get());
        */

    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = repository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("/cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
