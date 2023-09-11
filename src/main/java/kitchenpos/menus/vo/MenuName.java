package kitchenpos.menus.vo;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Objects;

public class MenuName {
    private final String name;
    PurgomalumClient purgomalumClient = new DefaultPurgomalumClient(new RestTemplateBuilder());

    public MenuName(String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
