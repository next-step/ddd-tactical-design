package kitchenpos.menus.domain.vo;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(String name) {
        validateMenuGroupName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateMenuGroupName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
