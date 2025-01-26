package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuNamePolicy {
    public static void validMenuName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new ProfanityContainsMenuNameException();
        }
    }
}
