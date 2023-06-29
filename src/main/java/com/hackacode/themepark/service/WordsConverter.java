package com.hackacode.themepark.service;

import org.springframework.stereotype.Service;

@Service
public class WordsConverter implements IWordsConverter {

    //Convierte la primer letra de cada palabra en mayúscula
    @Override
    public String capitalizeWords(String phrase) {

            String[] words = phrase.toLowerCase().split(" ");
            var result = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    String converted = word.substring(0, 1).toUpperCase() + word.substring(1);
                    result.append(converted).append(" ");
                }
            }
            return result.toString().trim();
        }
}
