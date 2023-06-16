package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private Game game;
    private Sale sale;
    private NormalTicket normalTicket;

    @BeforeEach
    void setUp() {
        game = new Game();
        normalTicket = new NormalTicket();
        sale = new Sale();
    }

    @Test
    void verifyThatTheSumTotalPriceMethodDoesTheCorrectSum() {
        //se settea precio de juego
        game.setPrice(2000.0);

        //se settea el juego en ticket
        normalTicket.setGame(game);

        //se guarda el ticket en una lista
        var normalTickets = new ArrayList<NormalTicket>();
        normalTickets.add(normalTicket);

        //se crean tickets vips
        VipTicket vipTicket1 = new VipTicket();
        vipTicket1.setPrice(5000.0);
        VipTicket vipTicket2 = new VipTicket();
        vipTicket2.setPrice(5000.0);

        //se guardan los tickets vips en una lista
        var vips = new ArrayList<VipTicket>();
        vips.add(vipTicket1);
        vips.add(vipTicket2);

        //se settean los tickets de Sale con las listas de tickets creadas
        sale.setNormalTickets(normalTickets);
        sale.setVipTickets(vips);

        assertEquals(12000.0, sale.calculateTotalPrice());
    }


}