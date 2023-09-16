package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TobeMenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    protected TobeMenuGroupName() {
    }

    public TobeMenuGroupName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴그룹의 이름은 비어있을 수 없습니다.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TobeMenuGroupName that = (TobeMenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
