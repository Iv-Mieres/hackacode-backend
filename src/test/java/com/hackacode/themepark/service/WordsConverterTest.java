package com.hackacode.themepark.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WordsConverterTest {

    @InjectMocks
    private WordsConverter wordsConverter;

    @DisplayName("compruea que el método convierta la primera letra de cada palabra en mayúscula")
    @Test
    void capitalizeWords() {

        String words = "cAsO de prUEba";
        String expectedWords = "Caso De Prueba";

        var currentWords = wordsConverter.capitalizeWords(words);
        assertEquals(expectedWords, currentWords);

    }

    @DisplayName("compruea que el método retorne un String vacio si no se ingresan datos")
    @Test
    void capitalizeWordsEmpty() {

        String words = "";
        String expectedWords = "";

        var currentWords = wordsConverter.capitalizeWords(words);
        assertEquals(expectedWords, currentWords);

    }
    @DisplayName("compruea que el método retorne un String vacio si se ingresan nulos")
    @Test
    void capitalizeWordsNull() {

        String words = null;
        String expectedWords = "";

        var currentWords = wordsConverter.capitalizeWords(words);
        assertEquals(expectedWords, currentWords);

    }
}