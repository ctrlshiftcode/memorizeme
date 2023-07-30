package com.memorizeme.card;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "card")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQCARD")
    @SequenceGenerator(name = "SQCARD", sequenceName = "SQCARD", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255)
    private String word;

    @Column(nullable = false, length = 255)
    private String translation;

    @Column(nullable = false, length = 10)
    private LocalDate registrationDate;


}
