package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuDisplayedName {

    private String name;

    protected MenuDisplayedName() {
    }

    public MenuDisplayedName(String name, MenuProfanityClient menuProfanityClient) {
        if(StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("메뉴 이름이 비어 있습니다.");
        }

        if(menuProfanityClient.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴 이름에는 비속어가 포함될 수 없습니다.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuDisplayedName that = (MenuDisplayedName) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
