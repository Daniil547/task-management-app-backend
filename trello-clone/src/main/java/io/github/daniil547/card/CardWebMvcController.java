package io.github.daniil547.card;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@Api(tags = "Card Resource", description = "CRUD endpoint for cards")
public class CardWebMvcController extends PageWebMvcController<CardDto, Card> {
    private final CardService service;
    private final CardConverter converter;

    @Autowired
    public CardWebMvcController(CardService service, CardConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @Override
    public CardService service() {
        return this.service;
    }

    @Override
    public CardConverter converter() {
        return this.converter;
    }
}
