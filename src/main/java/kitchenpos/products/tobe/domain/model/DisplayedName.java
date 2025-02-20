package kitchenpos.products.tobe.domain.model;

import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_EMPTY_EXCEPTION;
import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import kitchenpos.products.tobe.domain.service.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.DisplayedNameEmptyException;
import kitchenpos.products.tobe.domain.exception.DisplayedNameIncludeProfanityException;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private final String value;

    public DisplayedName(final String name, PurgomalumClient purgomalumClient) {
        validate(name, purgomalumClient);
        this.value = name;
    }

    private void validate(String name, PurgomalumClient purgomalumClient) {
        if (name == null || name.trim().isEmpty()) {
            throw new DisplayedNameEmptyException(DISPLAYED_NAME_EMPTY_EXCEPTION.getMessage());
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new DisplayedNameIncludeProfanityException(
                DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION.getMessage());
        }
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DisplayedName that = (DisplayedName) obj;
        return Objects.equals(value, that.value);
    }

    public int hashCode() {
        return Objects.hash(value);
    }
}
