package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class MenuName {
    private final String value;

    protected MenuName(){
        this(new String());
    }

    public MenuName(String value) {
        if(Objects.isNull(value)){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
    public MenuName(String value, PurgomalumClient purgomalumClient){
        if (Objects.isNull(value)| purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return this.value;
    }
}
