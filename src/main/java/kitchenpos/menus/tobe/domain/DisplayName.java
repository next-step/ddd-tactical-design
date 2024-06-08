package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

@Embeddable
public class DisplayName {

    private String name;

    protected DisplayName() {
    }

    private DisplayName(String name, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        this.name = name;
    }

    public static DisplayName of(String name, PurgomalumClient purgomalumClient) {
        return new DisplayName(name, purgomalumClient);
    }

    public void validateName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("메뉴 이름은 필수값입니다.");
        }
        if (purgomalumClient.containsProfanity(name))
            throw new IllegalArgumentException("비속어가 포함된 메뉴이름은 등록할 수 없습니다.");
    }

    public String name() {
        return name;
    }
}
