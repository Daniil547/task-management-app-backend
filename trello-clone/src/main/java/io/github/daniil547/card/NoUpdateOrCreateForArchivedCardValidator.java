package io.github.daniil547.card;

import io.github.daniil547.common.exceptions.CustomValidationFailureException;
import io.github.daniil547.common.exceptions.EntityNotFoundException;
import io.github.daniil547.common.validation.CustomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class NoUpdateOrCreateForArchivedCardValidator implements CustomValidator<CardDto> {
    private final CardRepository cardRepository;
    private final CardConverter cardConverter;

    @Override
    public void validate(CardDto newCard) {
        if (!newCard.getActive()) {
            UUID id = newCard.getId();
            if (id == null) {
                throw new CustomValidationFailureException("creation of a card that is archived is forbidden", "active", false);
            } else {
                CardDto oldCard = cardConverter.dtoFromEntity(
                        cardRepository.findById(id)
                                      .orElseThrow(() -> {
                                          throw new EntityNotFoundException(id);
                                      })
                );
                if (!oldCard.getActive()) {
                    throw new CustomValidationFailureException("update of an archived card is forbidden", "active", false);
                }
            }
        }
    }
}
