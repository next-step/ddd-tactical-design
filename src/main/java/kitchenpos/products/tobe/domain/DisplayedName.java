package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {

    private String name;

    public DisplayedName() {

    }

    protected DisplayedName(PurgomalumClient purgomalumClient, String name) {
        validate(purgomalumClient, name);
        this.name = name;
    }

    private void validate(PurgomalumClient purgomalumClient, String name) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

}
