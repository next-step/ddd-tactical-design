package kitchenpos.menus.tobe.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
public class MenuName {
    private String value;

    protected MenuName() { }

    public MenuName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        this.value = name;
    }

    public String value() {
        return value;
    }
}