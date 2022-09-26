package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.NotContainsProfanityException;
import kitchenpos.products.tobe.domain.exception.NotEmptyDisplayedNameException;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {}

    protected DisplayedName(PurgomalumClient purgomalumClient, String name) {
        validate(purgomalumClient, name);
        this.name = name;
    }

    private void validate(PurgomalumClient purgomalumClient, String name) {
        if (Strings.isEmpty(name)) {
            throw new NotEmptyDisplayedNameException();
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new NotContainsProfanityException();
        }
    }

}
