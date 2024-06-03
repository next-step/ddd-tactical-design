package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class MenuGroupName {

    private String name;

    private MenuGroupName() {
    }

    private MenuGroupName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("메뉴 그룹의 이름은 비워 둘 수 없습니다.");
        }
        this.name = name;
    }

    public static MenuGroupName of(String name) {
        return new MenuGroupName(name);
    }
}
