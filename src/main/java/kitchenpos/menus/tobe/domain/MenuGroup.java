package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class MenuGroup {
    private static final String INVALID_NAME = "메뉴 그룹의 이름으로 사용할 수 없습니다.";

    private final UUID id;
    private final String name;

    public MenuGroup(String name) {
        validate(name);
        this.id = UUID.randomUUID();
        this.name = name;
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
    }
}
