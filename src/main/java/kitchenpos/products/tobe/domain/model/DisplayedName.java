package kitchenpos.products.tobe.domain.model;

import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_EMPTY_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.exception.DisplayedNameEmptyException;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private final String value;

    public DisplayedName(final String name) {
        if (name == null || name.isBlank()) {
            throw new DisplayedNameEmptyException(DISPLAYED_NAME_EMPTY_EXCEPTION.getMessage());
        }
        this.value = name;
    }

    public String getValue() {
        return this.value;
    }
}
