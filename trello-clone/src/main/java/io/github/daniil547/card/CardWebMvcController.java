package io.github.daniil547.card;

import io.github.daniil547.common.controllers.PageWebMvcController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

    @Override
    protected ResponseEntity<Page<CardDto>> handleGetAllWithRespectToUser(UUID userId, String search, Pageable pageable) {
        // FIXME
        throw new NotImplementedException();
    }
}
