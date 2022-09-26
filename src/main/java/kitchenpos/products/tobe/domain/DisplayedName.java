package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NotContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.NotEmptyDisplayedNameException;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {}

    protected DisplayedName(String name, boolean isProfanity) {
        validate(name, isProfanity);
        this.name = name;
    }

    private void validate(String name, boolean isProfanity) {
        if (Strings.isEmpty(name)) {
            throw new NotEmptyDisplayedNameException();
        }
        if (isProfanity) {
            throw new NotContainsProfanityException();
        }
    }

}
