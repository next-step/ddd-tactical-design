package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {

    private String name;

    protected MenuName() {
    }

    protected MenuName(PurgomalumClient purgomalumClient, String name) {
        validation(purgomalumClient, name);
        this.name = name;
    }

    private void validation(PurgomalumClient purgomalumClient, String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("메뉴이름은 필수입니다.");
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴이름은 비속어를 사용할수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
