package kitchenpos.menus.util;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.util.StringUtils;

public class NameValidator {

    private NameValidator() {
    }

    public static void validateNameEmpty(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException();
        }
    }
    public static void validateContainsProfanity(String name, PurgomalumClient client) {
        if (client.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }
}
