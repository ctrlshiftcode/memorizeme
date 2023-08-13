package com.memorizeme.card;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "CardController")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/cards")
public class CardController {

    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("")
    public ResponseEntity<Object> save(@RequestBody @Valid CardDTO cardDTO) {

        Card card = new Card();

        if (!cardService.existsCardByWord(cardDTO.getWord()))
        {
            BeanUtils.copyProperties(cardDTO, card);
            card.setRegistrationDate(LocalDate.now());
            cardService.save(card);
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("This word already exist!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Card>> get(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.cardRepository.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Card>> getAll(){
       //TODO: Fazer paginação
        return ResponseEntity.status(HttpStatus.OK).body(cardService.cardRepository.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Object> getCount(){
        return ResponseEntity.status(HttpStatus.OK).body("count:" + cardService.cardRepository.count());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        cardService.cardRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted card deck");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAll(){
        cardService.cardRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Deleted all cards deck");
    }
}
