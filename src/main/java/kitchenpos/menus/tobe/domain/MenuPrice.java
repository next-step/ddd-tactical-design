package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false)
    private final int value;

    public MenuPrice() {
        this(0);
    }

    public MenuPrice(int value) {
        if(Objects.isNull(value) || value < 0){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return value == menuPrice.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
