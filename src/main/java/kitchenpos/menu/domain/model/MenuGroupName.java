package kitchenpos.menu.domain.model;

import java.util.Objects;

public class MenuGroupName {
    private static final String MENU_CATEGORY_NAME_CREATION_EXCEPTION = "메뉴 카테고리 이름을 채워주세요!";

    private final String value;

    public MenuGroupName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(MENU_CATEGORY_NAME_CREATION_EXCEPTION);
        }
        this.value = value;
    }
}
