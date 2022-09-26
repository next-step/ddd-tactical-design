package kitchenpos.menus.tobe.domain.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.exception.EmptyMenuNameException;
import kitchenpos.menus.tobe.domain.exception.ProfanityMenuNameException;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class MenuName implements Serializable {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    public MenuName(String value, PurgomalumClient purgomalumClient) {
        validate(value, purgomalumClient);
        this.value = value;
    }

    private void validate(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new EmptyMenuNameException();
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new ProfanityMenuNameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
