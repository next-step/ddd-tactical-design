package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuGroupNameException;
import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName extends ValueObject {
    private String name;

    public MenuGroupName() {

    }

    public MenuGroupName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new InvalidMenuGroupNameException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
