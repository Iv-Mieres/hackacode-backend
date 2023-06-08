package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private Game game;
    private Sale sale;
    private Normal normalTicket;

    @BeforeEach
    void setUp() {
        game = new Game();
        normalTicket = new Normal();
        sale = new Sale();
    }

    @Test
    void verifyThatTheSumTotalPriceMethodDoesTheCorrectSum() {
        //se settea precio de juego
        game.setPrice(2000.0);

        //se settea el juego en ticket
        normalTicket.setGame(game);

        //se guarda el ticket en una lista
        var normalTickets = new ArrayList<Normal>();
        normalTickets.add(normalTicket);

        //se crean tickets vips
        Vip vip1 = new Vip();
        vip1.setPrice(5000.0);
        Vip vip2 = new Vip();
        vip2.setPrice(5000.0);

        //se guardan los tickets vips en una lista
        var vips = new ArrayList<Vip>();
        vips.add(vip1);
        vips.add(vip2);

        //se settean los tickets de Sale con las listas de tickets creadas
        sale.setNormalTickets(normalTickets);
        sale.setVips(vips);

        assertEquals(12000.0, sale.calculateTotalPrice());
    }


}