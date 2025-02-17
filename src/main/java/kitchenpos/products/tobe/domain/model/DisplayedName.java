package kitchenpos.products.tobe.domain.model;

import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_EMPTY_EXCEPTION;
import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.DisplayedNameEmptyException;
import kitchenpos.products.tobe.domain.exception.DisplayedNameIncludeProfanityException;
import kitchenpos.products.tobe.domain.service.ProfanityFilterService;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private final String value;

    public DisplayedName(final String name, ProfanityFilterService profanityFilterService) {
        validate(name, profanityFilterService);
        this.value = name;
    }

    private void validate(String name, ProfanityFilterService profanityFilterService) {
        if (name == null || name.trim().isEmpty()) {
            throw new DisplayedNameEmptyException(DISPLAYED_NAME_EMPTY_EXCEPTION.getMessage());
        }
        if (profanityFilterService.containsProfanity(name)) {
            throw new DisplayedNameIncludeProfanityException(
                DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION.getMessage());
        }
    }

    public String getValue() {
        return this.value;
    }
}
