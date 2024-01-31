package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Buyer buyer;

    @BeforeEach
    void beforAll(){
        game = new Game();
        buyer = new Buyer();
    }

    @Test
    void ifTheAgeOfTheBuyerMeetsTheRequiredAgeOfTheGameThenTrue(){
        buyer.setBirthdate(LocalDate.of(2005, 6, 3));
        game.setRequiredAge(18);

        var expectedTrue = game.validateAge(buyer.getBirthdate());
        assertTrue(expectedTrue);
    }

    @Test
    void ifTheAgeOfTheBuyerDoesNotMeetsTheRequiredAgeOfTheGameThenTrue(){
        buyer.setBirthdate(LocalDate.of(LocalDate.now().getYear()-10, 6, 5));
        game.setRequiredAge(18);

        var expectedFalse = game.validateAge(buyer.getBirthdate());
        assertFalse(expectedFalse);
    }


}