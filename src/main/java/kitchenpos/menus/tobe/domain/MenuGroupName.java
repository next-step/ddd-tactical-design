package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    protected MenuGroupName(String name) {
        validation(name);
        this.name = name;
    }

    private void validation(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("메뉴그룹이름은 필수입니다.");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹의 이름은 공백이 될수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
