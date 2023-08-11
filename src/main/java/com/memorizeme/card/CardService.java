package com.memorizeme.card;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void save(Card card) {
        cardRepository.save(card);
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    public boolean existsCardByWord (String word) {
        return cardRepository.existsCardByWord(word);
    }

    public long count() {
        return cardRepository.count();
    }

    public void deleteAll(){
        cardRepository.deleteAll();
    }
}
