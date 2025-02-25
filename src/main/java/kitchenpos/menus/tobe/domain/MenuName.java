package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.common.external.PurgomalumClient;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuNameException;

import java.util.Objects;


@Embeddable
public class MenuName {

    private String name;

    protected MenuName(){}

    public MenuName(String name, PurgomalumClient purgomalum) {
        Objects.requireNonNull("메뉴명은 입력되어야 합니다");
        if(purgomalum.containsProfanity(name)){
            throw new InvalidMenuNameException("비속어는 입력할 수 없습니다");
        }
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
