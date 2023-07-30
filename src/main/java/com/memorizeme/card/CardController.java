package com.memorizeme.card;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "CardController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cards")
public class CardController {

    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveCard(@RequestBody @Valid CardDTO  cardDTO) {

        Card card = new Card();

        if (!cardService.existsCardByWord(cardDTO.getWord()))
        {
            BeanUtils.copyProperties(cardDTO, card);
            card.setRegistrationDate(LocalDate.now());
            cardService.saveCard(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This word already exist!");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Card>> getAllCards(){
       //TODO: Fazer paginação
        return ResponseEntity.status(HttpStatus.OK).body(cardService.cardRepository.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Object> getCount(){
        return ResponseEntity.status(HttpStatus.OK).body("count:" + cardService.cardRepository.count());
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAllCards(){
        cardService.cardRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all cards deck");
    }
}
