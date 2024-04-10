package com.gaya.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository repository;

    public CashCardController(CashCardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = repository.findById(requestedId);

        if (cashCardOptional.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cashCardOptional.get());

    }
}
