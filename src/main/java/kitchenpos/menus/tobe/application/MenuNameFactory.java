package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MenuNameFactory {

    private final PurgomalumClient purgomalumClient;

    public MenuNameFactory(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public MenuName createMenuName(String name) {
        if (!StringUtils.hasText(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없거나 비속어를 포함하면 안됩니다.");
        }
        return new MenuName(name);
    }
}