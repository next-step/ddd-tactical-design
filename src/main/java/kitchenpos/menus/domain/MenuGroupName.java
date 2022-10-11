package kitchenpos.menus.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() {
    }

    public MenuGroupName(String value) {
        this.value = value;
        validateNull(this.value);
        validateBlank(this.value);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("올바르지 않은 메뉴그룹 이름입니다.");
        }
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("메뉴그룹 이름은 공백일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
