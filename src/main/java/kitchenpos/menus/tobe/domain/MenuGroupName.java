package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(final String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹 이름은 필수로 입력해야 합니다.");
        }
        this.name = name;
    }

    public String value() {
        return name;
    }
}
