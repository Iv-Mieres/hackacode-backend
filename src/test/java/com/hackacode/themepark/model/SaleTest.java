package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private Game game;
    private Sale sale;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        game = new Game();
        ticket = new Ticket();
        sale = new Sale();
    }

    @Test
    void verifyThatTheSumTotalPriceMethodDoesTheCorrectSum() {
        //se settea precio de juego
        game.setPrice(2000.0);

        //se settea el juego en ticket
        ticket.setGame(game);

        //se guarda el ticket en una lista
        var tickets = new ArrayList<Ticket>();
        tickets.add(ticket);

        //se crean tickets vips
        TicketVip vip1 = TicketVip.builder().price(5000).build();
        TicketVip vip2 = TicketVip.builder().price(5000).build();

        //se guardan los tickets vips en una lista
        var vips = new ArrayList<TicketVip>();
        vips.add(vip1);
        vips.add(vip2);

        //se settean los tickets de Sale con las listas de tickets creadas
        sale.setTickets(tickets);
        sale.setTicketsVip(vips);

        assertEquals(12000.0, sale.calculateTotalPrice());
    }


}