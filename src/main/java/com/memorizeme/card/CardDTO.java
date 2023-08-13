package com.memorizeme.card;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardDTO {

    @NotBlank
    @NotNull
    private String word;

    @NotBlank
    @NotNull
    private String translation;

    public void setWord(String word) {
        this.word = word.toUpperCase().trim();
    }

    public void setTranslation(String translation) {
        this.translation = translation.toUpperCase().trim();
    }

}
