package kitchenpos.common.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
public class ProfanityFilteredNameFactory {
    private static PurgomalumClient purgomalumClient;

    @Autowired
    public ProfanityFilteredNameFactory(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public static ProfanityFilteredName createProfanityFilteredName(String name) {
        if(ObjectUtils.isEmpty(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        return new ProfanityFilteredName(name);
    }
}
