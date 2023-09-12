package kitchenpos.menus.tobe.domain;

import kitchenpos.support.ValueObject;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
public class MenuName extends ValueObject {
    private final String value;

    protected MenuName() {
        value = null;
    }

    public MenuName(String name, PurgomalumClient purgomalumClient) {
        if (!StringUtils.hasText(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        this.value = name;
    }

    public String value() {
        return value;
    }
}